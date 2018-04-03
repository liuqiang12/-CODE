package com.JH.dgather.frame.common.snmp;

import java.util.ArrayList;
import java.util.Collection;

import org.opennms.protocols.snmp.SnmpHandler;
import org.opennms.protocols.snmp.SnmpPduPacket;
import org.opennms.protocols.snmp.SnmpSession;
import org.opennms.protocols.snmp.SnmpSyntax;
import org.opennms.protocols.snmp.SnmpVarBind;

import com.JH.dgather.frame.common.snmp.bean.OIDbean;

public class DefaultSnmpHandler implements SnmpHandler {
	private boolean success = true;
	
	public boolean isSuccess() {
		
		return success;
	}
	
	public void setSuccess(boolean success) {
		
		this.success = success;
	}
	
	private Collection<OIDbean> result = new ArrayList<OIDbean>(10);
	
	public void snmpInternalError(SnmpSession session, int arg1, SnmpSyntax arg2) {
		System.out.println("SnmpTimeout");
		success = false;
		synchronized (session) {
			session.notify();
		}
	}
	
	public void snmpReceivedPdu(SnmpSession session, int arg1, SnmpPduPacket pdu) {
		
		int responseLength = pdu.getLength();
		for (int i = 0; i < responseLength; i++) {
			SnmpVarBind varBind = pdu.getVarBindAt(i);
			//            System.out.println("Received value: " + varBind.getName() + "=" + varBind.getValue());
			OIDbean bean = new OIDbean();
			bean.setOid(varBind.getName().toString());
			bean.setSuccess(true);
			bean.setValue(varBind.getValue().toString());
			result.add(bean);
		}
		
		synchronized (session) {
			session.notify();
		}
		
	}
	
	public void snmpTimeoutError(SnmpSession session, SnmpSyntax pdu) {
		
		success = false;
		synchronized (session) {
			session.notify();
		}
		//		 System.out.println("InternalError");
		
	}
	
	public OIDbean[] getResult() {
		return this.result.toArray(new OIDbean[0]);
	}
	
}

package com.JH.dgather.frame.common.snmp;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.opennms.protocols.snmp.SnmpEndOfMibView;
import org.opennms.protocols.snmp.SnmpHandler;
import org.opennms.protocols.snmp.SnmpObjectId;
import org.opennms.protocols.snmp.SnmpPduPacket;
import org.opennms.protocols.snmp.SnmpPduRequest;
import org.opennms.protocols.snmp.SnmpSession;
import org.opennms.protocols.snmp.SnmpSyntax;
import org.opennms.protocols.snmp.SnmpVarBind;

import com.JH.dgather.frame.common.snmp.bean.OIDbean;

public class WalkHandler implements SnmpHandler {
	private Logger logger = Logger.getLogger(WalkHandler.class);
	
	private boolean success = true;
	
	private Collection<OIDbean> result = new ArrayList<OIDbean>(20);
	
	/**
	 * The object identifier where the walk of 
	 * the tree should stop.
	 */
	SnmpObjectId m_stopAt = null;
	
	/**
	 * Defined by the SnmpHandler interface. Used to process internal session
	 * errors.
	 *
	 * @param session   The SNMP session in error.
	 * @param err       The Error condition
	 * @param pdu       The pdu associated with this error condition
	 *
	 */
	public void snmpInternalError(SnmpSession session, int err, SnmpSyntax pdu) {
		logger.error("An unexpected error occured with the SNMP Session, The error code is " + err);
		success = false;
		synchronized (session) {
			session.notify();
		}
	}
	
	public boolean isSuccess() {
		
		return success;
	}
	
	public void setSuccess(boolean success) {
		
		this.success = success;
	}
	
	/**
	 * This method is define by the SnmpHandler interface and invoked
	 * if an agent fails to respond.
	 *
	 * @param session   The SNMP session in error.
	 * @param pdu       The PDU that timedout.
	 *
	 */
	public void snmpTimeoutError(SnmpSession session, SnmpSyntax pdu) {
		logger.error("The session timed out trying to communicate with the remote host");
		success = false;
		synchronized (session) {
			session.notify();
		}
	}
	
	/**
	 * This method is defined by the SnmpHandler interface and invoked
	 * when the agent responds to the management application.
	 *
	 * @param session   The session receiving the pdu.
	 * @param cmd       The command from the pdu. 
	 * @param pdu       The received pdu.
	 *
	 * @see org.opennms.protocols.snmp.SnmpPduPacket#getCommand
	 */
	public void snmpReceivedPdu(SnmpSession session, int cmd, SnmpPduPacket pdu) {
		SnmpPduRequest req = null;
		if (pdu instanceof SnmpPduRequest) {
			req = (SnmpPduRequest) pdu;
		}
		
		if (pdu.getCommand() != SnmpPduPacket.RESPONSE) {
			logger.error("Error: Received non-response command " + pdu.getCommand());
			synchronized (session) {
				session.notify();
			}
			return;
		}
		
		if (req.getErrorStatus() != 0) {
			//            System.out.println("End of mib reached");
			logger.error("error:" + req.getErrorIndex() + "|" + req.getVarBindAt(0));
			synchronized (session) {
				session.notify();
			}
			return;
		}
		
		//
		// Passed the checks so lets get the first varbind and
		// print out it's value
		//
		SnmpVarBind vb = pdu.getVarBindAt(0);
		if (vb.getValue().typeId() == SnmpEndOfMibView.ASNTYPE || (m_stopAt != null && m_stopAt.compare(vb.getName()) < 0)) {
			//            System.out.println("End of mib reached");
			synchronized (session) {
				session.notify();
			}
			return;
		}
		
		//        System.out.println(vb.getName().toString() + ": " + vb.getValue().toString());
		OIDbean bean = new OIDbean();
		bean.setOid(vb.getName().toString());
		bean.setValue(vb.getValue().toString());
		bean.setSuccess(true);
		result.add(bean);
		//
		// make the next pdu
		//
		SnmpVarBind[] vblist = { new SnmpVarBind(vb.getName()) };
		SnmpPduRequest newReq = new SnmpPduRequest(SnmpPduPacket.GETNEXT, vblist);
		newReq.setRequestId(SnmpPduPacket.nextSequence());
		
		session.send(newReq);
	}
	
	/**
	 * 
	 * ȡ�ý��
	 * @return
	 */
	public OIDbean[] getResult() {
		return result.toArray(new OIDbean[0]);
	}
}

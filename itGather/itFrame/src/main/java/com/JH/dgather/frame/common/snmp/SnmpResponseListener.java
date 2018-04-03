package com.JH.dgather.frame.common.snmp;

import java.util.ArrayList;

import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.smi.VariableBinding;

import com.JH.dgather.frame.common.snmp.bean.OIDbean;

class SnmpResponseListener implements ResponseListener {
	OIDbean[] result = null;
	
	public SnmpResponseListener() {
		
	}
	
	public void onResponse(ResponseEvent event) {
		// Always cancel async request when response has been received
		// otherwise a memory leak is created! Not canceling a request
		// immediately can be useful when sending a request to a broadcast
		// address.
		Object synSignal = event.getUserObject();
		((Snmp) event.getSource()).cancel(event.getRequest(), this);
		// System.out.println("Received response PDU is: "+event.getResponse());
		try {
			result = readResponse(event.getResponse());
		} catch (Exception e) {
			
		}
		
		synchronized (synSignal) {
			synSignal.notify();
		}
		
	}
	
	/**
	 * 读取响应
	 * @param response
	 * @return
	 */
	private OIDbean[] readResponse(PDU response) {
		//Vector<VariableBinding> recVBs = response.getVariableBindings();
		VariableBinding[] recVBs = response.toArray();
		ArrayList<OIDbean> result = new ArrayList<OIDbean>();
		int size = recVBs.length;
		for (int i = 0; i < size; i++) {
			VariableBinding vb = recVBs[i];
			if (!vb.isException()) {
				result.add(new OIDbean(vb.getOid().toString(), vb.getVariable().toString(), true, null));
			}
			else {
				result.add(new OIDbean(vb.getOid().toString(), vb.getVariable().toString(), false, null));
				System.err.println("" + vb.getOid() + " has exception");
			}
		}
		
		return result.toArray(new OIDbean[0]);
	}
	
	/**
	 * 
	 * 返回结果
	 * @return
	 */
	public OIDbean[] getResult() {
		return this.result;
	}
}
package com.JH.dgather.frame.common.snmp;

/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2013-3-22
 */

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

public class V3CollectValue {
	Logger logger = Logger.getLogger(V3CollectValue.class.getName());

	private static String oid = "1.3.6.1.2.1.2.2.1.1";

	private Snmp snmp = null;
	
	public static void main(String[] args) {
		V3CollectValue v3 = new V3CollectValue();
		v3.get("1.3.6.1.2.1.1.6.0");
		v3.walk("1.3.6.1.2.1.2.2.1.1");
	}
	
	public V3CollectValue(){
		AbstractTransportMapping transportMapping = null;
		try {
			transportMapping = new DefaultUdpTransportMapping();
			snmp = new Snmp(transportMapping);
			transportMapping.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void get(String oid) {
		Target agent = createV3Target(snmp, "212.30.73.70", 161);// Agent

		ScopedPDU pduV3 = new ScopedPDU();// V3独有的类型
		pduV3.setType(PDU.GET);
		/*
		 * ContextName用于标识一个管理信息集合， 如果agent本地有多块网卡，那么一般来说就有多个context
		 */
		//pduV3.setContextName(new OctetString("priv"));
		pduV3.add(new VariableBinding(new OID(oid)));

		try {
			ResponseEvent response = snmp.send(pduV3, agent);
			if (response == null || response.getResponse() == null) {
				System.out.println("SNMP request timed out");
			}
			if (response != null) {
				PDU responsePDU = response.getResponse();
				if (responsePDU.getErrorStatus() == PDU.noError) {
					for (int k = 0; k < responsePDU.size(); k++) {
						VariableBinding vb = responsePDU.get(k);
						if (vb != null) {
							System.out.println(vb.getOid() + "-" + vb.getVariable().toString());
						}
					}
				} else {
					System.out.println("SNMP Error:" + responsePDU.getErrorStatusText());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void walk(String oid) {		
		Target agent = createV3Target(snmp, "212.30.73.70", 161);// Agent

		//final ScopedPDU pduV3 = new ScopedPDU();// V3独有的类型
		/*
		 * ContextName用于标识一个管理信息集合， 如果agent本地有多块网卡，那么一般来说就有多个context
		 */
		// pduV3.setType(PDU.GETNEXT);
		// pduV3.setContextName(new OctetString("priv"));
		// pduV3.add(new VariableBinding(new OID(oid)));
		
		
		TreeUtils utils = new TreeUtils(snmp,
				new DefaultPDUFactory(PDU.GETNEXT));
		
		List<TreeEvent> l = utils.getSubtree(agent, new OID("1.3.6.1.2.1.2.2.1.1"));
		for (TreeEvent e : l) {
			VariableBinding[] vbs = e.getVariableBindings();
			if(vbs.length>0){
				System.out.println(vbs[0]);
				System.out.println(vbs[0].getOid());
				System.out.println(vbs[0].getVariable());
				System.out.println("------------------");
			}
		}

	}


	private static Target createV3Target(Snmp snmp, String agentIp, int agentPort) {
		String userName = "MD5_DES_User";
		String securityName = "MD5_DES_User";// 安全模块的名称
		String authPassword = "AuthPassword";
		String privPassword = "PrivPassword";

		UserTarget userTarget = new UserTarget();
		userTarget.setVersion(SnmpConstants.version3);
		userTarget.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		userTarget.setSecurityName(new OctetString(securityName));

		// 设置USM-用户安全模型
		MPv3 mpv3 = (MPv3) snmp.getMessageProcessingModel(MessageProcessingModel.MPv3);
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(mpv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);

		// 设置用户
		UsmUser usmUser = new UsmUser(new OctetString(userName), AuthMD5.ID, new OctetString(authPassword), PrivDES.ID, new OctetString(privPassword));
		snmp.getUSM().addUser(new OctetString(userName), usmUser);

		userTarget.setRetries(3);
		userTarget.setTimeout(5000);// MS
		userTarget.setAddress(GenericAddress.parse("udp:" + agentIp + "/" + agentPort));
		return userTarget;
	}
}

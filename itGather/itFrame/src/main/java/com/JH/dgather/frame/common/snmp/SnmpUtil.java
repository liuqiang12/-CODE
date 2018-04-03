package com.JH.dgather.frame.common.snmp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.SNMP4JSettings;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeListener;
import org.snmp4j.util.TreeUtils;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.common.db.DataUtil;
import com.JH.dgather.frame.common.snmp.bean.OIDbean;


/**
 * snmp工具类(暂不支持snmpV3的采集) 注意：说明的是Get,GetNext,Set操作都是原子的. 对于Get,GetNext操作,
 * 如果variable-bindings列表中的一个变量的值无法成功获取,
 * 则代理返回的GetResponse消息variable-bindings列表中所有的值返回为NULL
 * 
 * @author yangDS
 * 
 */
public class SnmpUtil implements PDUFactory {
	Logger logger = Logger.getLogger(SnmpUtil.class.getName());

	public static final String FLAG_OID = "1.3.6.1.2.1.1.2.0";// snmp
	public static final int SNMP_V1 = SnmpConstants.version1;
	public static final int SNMP_V2 = SnmpConstants.version2c;
	public static final int SNMP_V3 = SnmpConstants.version3;
	public static final int GET = PDU.GET;
	public static final int SET = PDU.SET;
	public static final int GETNEXT = PDU.GETNEXT;
	public static final int GETBULK = PDU.GETBULK;
	private Snmp session;
	private AbstractTransportMapping tm;
	

	private DataUtil du = null;

	public SnmpUtil() {
		try {
			session = initSnmp();
			
		} catch (Exception e) {
			session = null;
			logger.error("SnmpUtil初始化Snmp异常：", e);
		}

		du = new DataUtil();
	}

	/**
	 * 判断设备的SNMP版本，同时判定是否支持SNMP访问
	 * 
	 * @param ip
	 * @param community
	 * @param port
	 * @param deviceName
	 * @return
	 * @throws Exception
	 */

	public boolean canSNMP(DeviceInfoBean device) {
		if (device.getSnmpVersion() != -1){
			OIDbean obean = get(device, FLAG_OID, true, SnmpUtil.GET, 3, 3000l, 60 * 1000l);
			if (obean == null || obean.getValue() == null || obean.getValue().isEmpty()) {
				return false;
			} 
			return true;
		}
		int snmpVersion = -1;
		if (device.getSnmpCommunity() == null) {
			return false;
		}

		try {
			if (session == null) {
				return false;
			}
			// su.setVersion( SnmpUtil.SNMP_V1 );
			logger.info("device[" + device.getDeviceName() + "]团体字：" + device.getSnmpCommunity() + ";port:" + device.getSnmpPort());
			if (device.getSnmpVersion() != SnmpUtil.SNMP_V2 && device.getSnmpVersion() != SnmpUtil.SNMP_V1) {
				device.setSnmpVersion(SNMP_V2);
				OIDbean obean = get(device, FLAG_OID, true, SnmpUtil.GET, 3, 3000l, 60 * 1000l);
				if (obean == null || obean.getValue() == null || obean.getValue().isEmpty()) {
					device.setSnmpVersion(SNMP_V1);
					obean = get(device, FLAG_OID, true, SnmpUtil.GET, 3, 3000l, 60 * 1000l);

					if (obean == null || obean.getValue() == null || obean.getValue().isEmpty()) {
						logger.info("device[" + device.getDeviceName() + "]'s version not in snmp v1 v2");

						return false;
					} else {
						logger.info("device[" + device.getDeviceName() + "]'s version is v1");
						snmpVersion = SnmpUtil.SNMP_V1;
					}
				} else {
					logger.info("device[" + device.getDeviceName() + "]'s version is V2");
					snmpVersion = SnmpUtil.SNMP_V2;
				}
			}

		} catch (Exception e) {
			logger.error("snmp验证设备【设备名：" + device.getDeviceName() + "ip:" + device.getDeviceIp() + "】SNMP版本失败！", e);
			return false;
		} finally {
			device.setSnmpVersion(snmpVersion);
		}

		if (snmpVersion != -1) {
			du.updateSnmpVersion(device.getDeviceId(), snmpVersion);
		}
		return true;

	}

	// ----------muyp modify 2012-11-29---------------
	public boolean set(String oid, Variable var, String community, boolean asyn, int operType, String ip, int port, int retries, long timeout, int version, long waitTimeOut) {
		if (oid == null) {
			return false;
		}
		CommunityTarget target = createV1V2CTarget(community, retries, timeout, version, ip, port);
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid), var));
		pdu.setType(operType);

		int[] synSignal = new int[0];
		SnmpResponseListener listener = createResponseListener();
		try {
			session.send(pdu, target, synSignal, listener);
			synchronized (synSignal) {
				try {
					synSignal.wait(waitTimeOut);
				} catch (Exception e) {
					logger.error("snmp等待数据中断", e);
				}
			}

			OIDbean[] list = listener.getResult();
			if (list != null && list.length > 0) {
				return list[0].isSuccess();
			} else {
				return false;
			}

		} catch (Exception e) {
			logger.error("SNMP", e);
			return false;
		}
	}

	public boolean set(PDU pdu, String community, boolean asyn, String ip, int port, int retries, long timeout, int version, long waitTimeOut) {
		CommunityTarget target = createV1V2CTarget(community, retries, timeout, version, ip, port);

		int[] synSignal = new int[0];
		SnmpResponseListener listener = createResponseListener();
		try {
			session.send(pdu, target, synSignal, listener);
			synchronized (synSignal) {
				try {
					synSignal.wait(waitTimeOut);
				} catch (Exception e) {
					logger.error("snmp等待数据中断", e);
				}
			}

			OIDbean[] list = listener.getResult();
			if (list != null && list.length > 0) {
				return list[0].isSuccess();
			} else {
				return false;
			}

		} catch (Exception e) {
			logger.error("SNMP", e);
			return false;
		}
	}

	// ----------------new code--------------

	/*
	 * public OIDbean get(String oid, String community, boolean asyn, int
	 * operType, String ip, int port, int retries, long timeout, int version,
	 * long waitTimeOut) { if (oid == null) { return null; } Collection<String>
	 * oids = new ArrayList<String>(); oids.add(oid); OIDbean[] list = get(oids,
	 * community, asyn, operType, ip, port, retries, timeout, version,
	 * waitTimeOut); if (list != null && list.length > 0) { return list[0]; }
	 * else { return null; } }
	 */

	public OIDbean get(DeviceInfoBean device, String oid, boolean asyn, int operType, int retries, long timeout, long waitTimeOut) {
		if (oid == null) {
			return null;
		}
		if (device.getSnmpVersion() == SNMP_V3) {
			Target agent = createV3Target(device.getUserTargetBean(), retries, timeout, device.getSnmpVersion(), device.getIPAddress(), device.getSnmpPort());// Agent
			return get(device, oid, agent);
		} else {
			Collection<String> oids = new ArrayList<String>();
			oids.add(oid);
			OIDbean[] list = get(oids, device.getSnmpCommunity(), asyn, operType, device.getIPAddress(), device.getSnmpPort(), retries, timeout, device.getSnmpVersion(), waitTimeOut);
			if (list != null && list.length > 0) {
				return list[0];
			} else {
				return null;
			}
		}
	}

	/**
	 * 
	 * 用GETBULK的方式进行数据采集
	 * 
	 * @param oids
	 *            指定的oid列表
	 * @param community
	 *            agent的团体字
	 * @param maxRepition
	 *            最大操作次数
	 * @param asyn
	 *            是否是异步消息 (true-异步，false-同步)
	 * @return
	 */
	public OIDbean[] getBulk(Collection<String> oids, String community, int maxRepetitions, boolean asyn, String ip, int port, int retries, long timeout, int version, long waitTimeOut) {
		if (asyn) {
			return getBulkInAsynWay(oids, community, maxRepetitions, ip, port, retries, timeout, version, waitTimeOut);
		} else {
			return getBulkInSynWay(oids, community, maxRepetitions, ip, port, retries, timeout, version);
		}
	}

	public List<OIDbean> walkTable(DeviceInfoBean device, String rootOIDs, int retries, long timeout, long waitTimeOut) throws IOException {
		Target target = createV1V2CTarget(device.getSnmpCommunity(), retries, timeout, device.getSnmpVersion(), device.getIPAddress(), device.getSnmpPort());
		TableUtils tu = new TableUtils(session, new DefaultPDUFactory());
		List<OIDbean> beans = null;
		OID[] columns = new OID[1];
		columns[0] = new OID(rootOIDs);
		List<TableEvent> teList = tu.getTable(target, columns, null, null);
		if (teList.size() > 0 && teList.get(teList.size() - 1).isError()) {
			throw new IOException(teList.get(teList.size() - 1)
			.getErrorMessage(), teList.get(teList.size() - 1)
			.getException());
		}
		beans = new ArrayList<OIDbean>();
		for(TableEvent te : teList) {
			OIDbean bean = new OIDbean(); 
			bean.setOid(te.getColumns()[0].getOid().toString());
			bean.setValue(te.getColumns()[0].getVariable().toString());
			beans.add(bean);
			}
		return beans;
	}
	
	
	public List<OIDbean> walk(DeviceInfoBean device, String rootOID, int retries, long timeout, long waitTimeOut) {
		try {
			if (device.getSnmpVersion() == SNMP_V3) {
				// SnmpUserTargetBean userTargetBean, int retries, long timeout,
				// int version, String ip, int port
				Target agent = createV3Target(device.getUserTargetBean(), retries, timeout, device.getSnmpVersion(), device.getIPAddress(), device.getSnmpPort());// Agent

				// final ScopedPDU pduV3 = new ScopedPDU();// V3独有的类型
				/*
				 * ContextName用于标识一个管理信息集合， 如果agent本地有多块网卡，那么一般来说就有多个context
				 */
				// pduV3.setType(PDU.GETNEXT);
				// pduV3.setContextName(new OctetString("priv"));
				// pduV3.add(new VariableBinding(new OID(oid)));
				TreeUtils utils = new TreeUtils(session, new DefaultPDUFactory(PDU.GETNEXT));
				List<OIDbean> result = new ArrayList<OIDbean>();
				OIDbean oidBean = null;
				List<TreeEvent> l = utils.getSubtree(agent, new OID(rootOID));
				for (TreeEvent e : l) {
					VariableBinding[] vbs = e.getVariableBindings();
					if (vbs.length > 0) {
						for (VariableBinding variableBinding : vbs) {
							oidBean = new OIDbean();
							oidBean.setOid(variableBinding.getOid().toString());
							oidBean.setValue(variableBinding.getVariable().toString());
							result.add(oidBean);
						}
					}
				}
				return result;
			} else {
				Target target = createV1V2CTarget(device.getSnmpCommunity(), retries, timeout, device.getSnmpVersion(), device.getIPAddress(), device.getSnmpPort());
				PDU response = walk(new OID(rootOID), target, waitTimeOut);
				return readNumberedTableResponse(response);
			}
		} catch (Exception e) {
			logger.error("walk get Exception:", e);
		}
		return null;
	}

	/*
	 * public List<OIDbean> walk(String community, String rootOID, String ip,
	 * int port, int retries, long timeout, int version, long waitTimeOut) { try
	 * { Target target = createV1V2CTarget(community, retries, timeout, version,
	 * ip, port); PDU response = walk(new OID(rootOID), target, waitTimeOut);
	 * 
	 * return readNumberedTableResponse(response);
	 * 
	 * } catch (Exception e) { logger.error("walk get Exception:", e); } return
	 * null; }
	 */

	public PDU createPDU(Target target) {

		PDU request = new PDU();
		request.setType(PDU.GETNEXT);

		return request;
	}

	public void close() {
		try {
			if (session != null)
				session.close();
		} catch (Exception e) {
			logger.error("session close Exception:", e);
		}
	}

	/*
	 * public int getVersion() {
	 * 
	 * return version; }
	 */

	/**
	 * 设置被管设备的snmp版本，version: SnmpUtil.SNMP_V1,SnmpUtil.SNMP_V2,SnmpUtil.SNMP_V3
	 * 
	 * @param version
	 */
	/*
	 * public void setVersion(int version) {
	 * 
	 * this.version = version; }
	 */

	private Address getAddress(String ip, int port) {

		String parseStr = "udp:" + ip + "/" + port;

		return GenericAddress.parse(parseStr);
	}

	/*
	 * public SnmpUtil(String ip, int port) {
	 * 
	 * String parseStr = "udp:" + ip + "/" + port;
	 * 
	 * targetAddress = GenericAddress.parse(parseStr);
	 * 
	 * try { session = initSnmp(); } catch (Exception e) { session = null;
	 * logger.error("SnmpUtil初始化Snmp异常：", e); } }
	 */

	/*
	 * public void setRetries(int retries) { this.retries = retries; }
	 * 
	 * public void setTimeOut(long timeout) { this.timeout = timeout; }
	 */

	/**
	 * 创建snmp实例
	 * 
	 * @param ip
	 * @param port
	 * @return
	 * @throws IOException
	 */
	private Snmp initSnmp() throws IOException {

		tm = new DefaultUdpTransportMapping();
		// SecurityProtocols.getInstance().addDefaultProtocols();
		// MessageDispatcher disp = new MessageDispatcherImpl();
		// disp.addMessageProcessingModel(new MPv1());
		// disp.addMessageProcessingModel(new MPv2c());

		Snmp snmp = new Snmp(tm);
		SNMP4JSettings.setAllowSNMPv2InV1(true);
		tm.setAsyncMsgProcessingSupported(true);
		snmp.listen();
		return snmp;
	}

	/**
	 * 创建target
	 * 
	 * @param community
	 * @return
	 */
	private CommunityTarget createV1V2CTarget(String community, int retries, long timeout, int version, String ip, int port) {
		CommunityTarget t = new CommunityTarget();
		t.setCommunity(new OctetString(community));
		t.setVersion(version);
		t.setAddress(getAddress(ip, port));
		t.setRetries(retries);
		t.setTimeout(timeout);
		return t;
	}

	private UserTarget createV3Target(SnmpUserTargetBean userTargetBean, int retries, long timeout, int version, String ip, int port) {
		UserTarget userTarget = new UserTarget();
		userTarget.setVersion(version);
		userTarget.setSecurityLevel(userTargetBean.getSecurityLevel());
		userTarget.setSecurityName(new OctetString(userTargetBean.getSecurityName()));

		// 设置USM-用户安全模型
		MPv3 mpv3 = (MPv3) session.getMessageProcessingModel(MessageProcessingModel.MPv3);
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(mpv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);

		// 设置用户
		UsmUser usmUser = new UsmUser(new OctetString(userTargetBean.getUserName()), userTargetBean.getAuthProtocol(), new OctetString(userTargetBean.getAuthPasshrase()),
				userTargetBean.getPrivProtocol(), new OctetString(userTargetBean.getPrivPassphrase()));
		session.getUSM().addUser(new OctetString(userTargetBean.getUserName()), usmUser);

		userTarget.setRetries(retries);
		userTarget.setTimeout(timeout);
		userTarget.setAddress(GenericAddress.parse("udp:" + ip + "/" + port));
		return userTarget;
	}

	public OIDbean get(DeviceInfoBean device, String oid, Target agent) {
		OIDbean oidBean = null;

		ScopedPDU pduV3 = new ScopedPDU();// V3独有的类型
		pduV3.setType(PDU.GET);
		/*
		 * ContextName用于标识一个管理信息集合， 如果agent本地有多块网卡，那么一般来说就有多个context
		 */
		// pduV3.setContextName(new OctetString("priv"));
		pduV3.add(new VariableBinding(new OID(oid)));

		try {
			ResponseEvent response = session.send(pduV3, agent);
			if (response == null || response.getResponse() == null) {
				logger.error("SNMP request timed out");
			}
			if (response != null) {
				PDU responsePDU = response.getResponse();
				if (responsePDU.getErrorStatus() == PDU.noError) {
					for (int k = 0; k < responsePDU.size(); k++) {
						VariableBinding vb = responsePDU.get(k);
						if (vb != null) {
							oidBean = new OIDbean();
							oidBean.setOid(vb.getOid().toString());
							oidBean.setValue(vb.getVariable().toString());
						}
					}
				} else {
					System.out.println("SNMP Error:" + responsePDU.getErrorStatusText());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return oidBean;
	}

	/**
	 * 
	 * 进行指定次数的getNext操作
	 * 
	 * @param response
	 * @param request
	 * @param times
	 * @return
	 */
	private PDU getNextInCycle(Snmp sn, OID startOID, PDU request, int times, CommunityTarget t) {
		request.setNonRepeaters(0);

		PDU responseGather = new PDU();
		responseGather.setType(PDU.RESPONSE);
		ResponseEvent reven = null;
		VariableBinding save = null;
		VariableBinding next = null;
		PDU res = null;
		for (int i = 1; i <= times; i++) {
			try {
				reven = sn.send(request, t);
				res = reven.getResponse();
				if (res == null)
					break;
				next = res.get(res.size() - 1);
				if ((next.getOid() == null) || (next.getOid().size() < startOID.size()) || (startOID.leftMostCompare(startOID.size(), next.getOid()) != 0)) {
					break;
				}
				save = new VariableBinding();
				save.setOid(next.getOid());
				save.setVariable(next.getVariable());
				responseGather.add(save);

				next.setVariable(new Null());

				request.set(0, next);
				request.setRequestID(new Integer32(0));
			} catch (Exception e) {
				logger.error("SNMP", e);
				break;

			}
		}

		return responseGather;
	}

	// public HashMap<String,OIDbean> sendPDU(String){
	//
	// }
	/**
	 * <p>
	 * 对目标机器发送请求pdu, pdu将包含 param中的所有OIDbean的oid信息。 结果将保存在传入参数result中。
	 * result中的key将与param中的key一致， 作为取出结果的依据. 返回的结果中
	 * OIDbean的isSuccess可以判断该oid是否正常返回值 snmp版本v2c
	 * </p>
	 * 
	 * @param community
	 *            snmp通信口令
	 * @param result
	 *            返回结果
	 * @param param
	 *            需要采集的oid信息
	 * @param pduType
	 *            用于标志snmp采集的方式 PDU.GET,PDU.GETNEXT,PDU.GETBULK
	 * 
	 * @param oid
	 */
	private void sendPDU(HashMap<String, OIDbean> result, HashMap<String, OIDbean> param, String community, int pduType, String ip, int port, int retries, long timeout, int version)
			throws IOException {

		// 设置 target

		CommunityTarget t = createV1V2CTarget(community, retries, timeout, version, ip, port);

		// tm.setAsyncMsgProcessingSupported( false );
		// 创建 PDU

		PDU pdu = new PDU();

		Set<Entry<String, OIDbean>> set = param.entrySet();
		OIDbean ob = null;
		for (Entry<String, OIDbean> entry : set) {
			ob = entry.getValue();

			pdu.add(new VariableBinding(new OID(ob.getOid())));

		}

		// MIB的访问方式

		pdu.setType(pduType);

		ResponseEvent respEvnt = session.send(pdu, t);

		// 解析Response

		readResponseNotTable(result, param, community, pduType, respEvnt, ip, port, retries, timeout, version);

	}

	/**
	 * 
	 * 
	 * 读取响应
	 * 
	 * @param respEvnt
	 */
	private void readResponseNotTable(HashMap<String, OIDbean> result, HashMap<String, OIDbean> param, String community, int pduType, ResponseEvent respEvnt, String ip, int port, int retries,
			long timeout, int version) {

		OIDbean beanInfo = null;
		String value = null;
		String oid = null;

		// 解析Response

		PDU pdu = respEvnt.getResponse();
		int errIndex = 0;
		if (respEvnt != null && pdu != null) {
			// System.out.println("respEvnt!=null");
			VariableBinding recVB = null;
			// Vector<VariableBinding> recVBs = pdu.getVariableBindings();
			VariableBinding[] recVBs = pdu.toArray();
			if (pdu.getErrorIndex() != 0) {
				// System.out.println("getErrorIndex!=0");
				errIndex = pdu.getErrorIndex();
				recVB = recVBs[errIndex - 1];
				oid = recVB.getOid().toString();
				beanInfo = new OIDbean();
				value = recVB.getVariable().toString();
				beanInfo.setOid(oid);
				// beanInfo.setValue("访问出错，请检查该设备是否支持此OID");

				beanInfo.setSuccess(false);
				result.put(oid, beanInfo);

				// ------------------从param中去除错误的OID再进行访问--------//
				param.remove(oid);
				try {
					sendPDU(result, param, community, pduType, ip, port, retries, timeout, version);
				} catch (Exception e) {
					logger.error("SNMP", e);
				}

			} else {// 全部成功
				String key = null;
				for (int i = 0; i < recVBs.length; i++) {
					recVB = recVBs[i];
					oid = recVB.getOid().toString();
					value = recVB.getVariable().toString();
					for (Entry<String, OIDbean> entry : param.entrySet()) {
						if (entry.getValue().getOid().equals(oid)) {

							key = entry.getKey();
						}
					}

					beanInfo = new OIDbean();
					beanInfo.setOid(oid);
					// System.out.println("value::"+value);
					if ("noSuchObject".equals(value)) {
						// beanInfo.setValue("访问出错，请检查该设备是否支持此OID（或该OID是否可访问）:"+oid);
						beanInfo.setSuccess(false);
					} else if ("noSuchInstance".equals(value)) {
						// beanInfo.setValue("请检查您的OID是否为单值类型");
						beanInfo.setSuccess(false);
					} else {
						beanInfo.setValue(value);
						beanInfo.setSuccess(true);
					}

					result.put(key, beanInfo);

					beanInfo = null;

				}

			}

		} else {
			System.out.println("全部失败");
			// result.putAll(param);

		}

	}

	/**
	 * <p>
	 * 发送单个oid的snmp getNext请求, 将会返回times指定个数的结果。
	 * 将会从指定的startOid进行times次数的getNext操作， 并将每次的操作结果记录，并返回。 返回的结果中的key值是由
	 * 传入参数key值与当前次数的字符连接
	 * </p>
	 * 
	 * @param community
	 *            snmp通信口令
	 * @param key
	 *            指定返回数据hashMap中key的前缀
	 * @param times
	 *            访问次数
	 * @param oid
	 * @param community
	 * @param targetAddress
	 * @param expireTime
	 * @throws IOException
	 */
	private HashMap<String, OIDbean> sendPDU(String startOid, String community, String key, int times, String ip, int port, int retries, long timeout, int version) throws IOException {

		HashMap<String, OIDbean> hm = null;
		CommunityTarget t = createV1V2CTarget(community, retries, timeout, version, ip, port);
		// tm.setAsyncMsgProcessingSupported( true );
		// 创建 PDU

		PDU request = new PDU();
		OID startOID = new OID(startOid);
		request.add(new VariableBinding(startOID));

		// MIB的访问方式

		request.setType(PDU.GETNEXT);

		// 向Agent发送PDU，并接收Response

		PDU resp = getNextInCycle(session, startOID, request, times, t);

		if (resp == null) {
			System.out.println("没有任何数据");
			return null;
		} else {
			hm = new HashMap<String, OIDbean>();
		}

		VariableBinding vb = null;
		OIDbean ob = null;
		for (int i = 0; i < resp.size(); i++) {
			vb = resp.get(i);
			if (vb != null) {
				ob = new OIDbean();
				ob.setOid(vb.getOid() == null ? null : vb.getOid().toString());
				ob.setValue(vb.getVariable() == null ? null : vb.getVariable().toString());

			}
			hm.put(key + i, ob);
		}

		return hm;
	}

	/**
	 * 
	 * 发送单个oid的snmp请求此方法只支持GET操作
	 * 
	 * @param oid
	 * @param community
	 * @param targetAddress
	 * @param expireTime
	 * @throws IOException
	 */
	private OIDbean sendPDU(String oid, String community, String ip, int port, int retries, long timeout, int version) throws IOException {

		CommunityTarget t = createV1V2CTarget(community, retries, timeout, version, ip, port);
		// 创建 PDU

		PDU pdu = new PDU();

		pdu.add(new VariableBinding(new OID(oid)));

		// MIB的访问方式

		pdu.setType(PDU.GET);

		ResponseEvent respEvnt = session.send(pdu, t);

		return readResponse(respEvnt)[0];
	}

	/**
	 * 对单个oid的snmp回应的读取
	 * 
	 * @param respEvnt
	 * @return
	 */
	private OIDbean[] readResponse(ResponseEvent respEvnt) {
		OIDbean beanInfo = null;
		String value = null;
		String oid = null;
		PDU pdu = respEvnt.getResponse();
		int errorIndex = 0;
		ArrayList<OIDbean> result = new ArrayList<OIDbean>();
		if (respEvnt != null && pdu != null) {
			// beanInfo = new OIDbean();
			VariableBinding[] recVBs = pdu.toArray();
			// Vector<VariableBinding> recVBs = pdu.getVariableBindings();
			for (int i = 0; i < recVBs.length; i++) {
				VariableBinding recVB = recVBs[i];
				oid = recVB.getOid().toString();
				beanInfo = new OIDbean();

				beanInfo.setOid(oid);

				errorIndex = pdu.getErrorIndex();
				// System.out.println("eIndex="+errorIndex);
				if (errorIndex != 0) {
					// beanInfo.setErrMsg("");
					beanInfo.setSuccess(false);
					// beanInfo.setValue("-1");
				} else {
					value = recVB.getVariable().toString();
					beanInfo.setSuccess(true);
					beanInfo.setValue(value);
				}

				result.add(beanInfo);
			}

		}

		return result.toArray(new OIDbean[0]);
	}

	/**
	 * 
	 * 读取table响应
	 * 
	 * @param response
	 * @return
	 */
	private List<OIDbean> readNumberedTableResponse(PDU response) {
		List<OIDbean> result = null;
		OIDbean beanInfo = null;
		if (response != null) {
			// Vector<VariableBinding> recVBs = response.getVariableBindings();
			VariableBinding[] recVBs = response.toArray();
			VariableBinding vb = null;
			int rows = recVBs.length;
			if (rows == 0)
				return null;
			int columns = 1;
			result = new ArrayList<OIDbean>();
			for (int i = 0; i < rows; i++) {
				beanInfo = new OIDbean();
				vb = recVBs[i];
				beanInfo.setOid(vb.getOid().toString());
				beanInfo.setValue(vb.getVariable().toString());
				result.add(beanInfo);
				beanInfo = null;

			}
		}

		return result;
	}

	/**
	 * @param oids
	 * @param community
	 * @return
	 */
	private OIDbean[] getInSynchronizedWay(Collection<String> oids, String community, int operType, String ip, int port, int retries, long timeout, int version) {
		if (session == null)
			return null;

		CommunityTarget target = createV1V2CTarget(community, retries, timeout, version, ip, port);
		PDU pdu = new PDU();
		for (String oid : oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		pdu.setType(operType);
		try {
			ResponseEvent event;
			if (operType == GET) {
				event = session.get(pdu, target);
			} else {// we thought it of GETNEXT
				event = session.getNext(pdu, target);
			}

			return readResponse(event);
		} catch (Exception e) {
			logger.error("SNMP", e);
		}
		return null;
	}

	/**
	 * 
	 * 用异步请求的方式发送snmp get请求，并且读取结果
	 * 
	 * @param oids
	 * @param community
	 * @return
	 */
	private OIDbean[] getInAsynchronizedWay(Collection<String> oids, String community, int operType, String ip, int port, int retries, long timeout, int version, long waitTimeOut) {
		if (session == null)
			return null;
		CommunityTarget target = createV1V2CTarget(community, retries, timeout, version, ip, port);
		PDU pdu = new PDU();
		for (String oid : oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		pdu.setType(operType);

		int[] synSignal = new int[0];
		SnmpResponseListener listener = createResponseListener();
		try {
			if (operType == GET) {
				session.get(pdu, target, synSignal, listener);
			} else {// we consider it of GETNEXT
				session.getNext(pdu, target, synSignal, listener);
			}

			synchronized (synSignal) {

				try {
					synSignal.wait(waitTimeOut);
				} catch (Exception e) {
					logger.error("snmp等待数据中断", e);
				}

			}

			return listener.getResult();
		} catch (Exception e) {
			logger.error("SNMP", e);
		}
		return null;
	}

	/**
	 * 
	 * 创建snmp响应的侦听器
	 * 
	 * @return
	 */
	private SnmpResponseListener createResponseListener() {

		return new SnmpResponseListener();

	}

	/**
	 * 对agent进行取值操作
	 * 
	 * @param oids
	 *            给定oid列表
	 * @param community
	 *            agent的团体字
	 * @param asyn
	 *            是否是异步请求
	 * @param operType
	 *            操作类型 取值为(SnmpUtil.GET,SnmpUtil.GETNEXT);
	 * @return
	 */
	private OIDbean[] get(Collection<String> oids, String community, boolean asyn, int operType, String ip, int port, int retries, long timeout, int version, long waitTimeOut) {
		if (asyn) {
			return getInAsynchronizedWay(oids, community, operType, ip, port, retries, timeout, version, waitTimeOut);
		} else {
			return getInSynchronizedWay(oids, community, operType, ip, port, retries, timeout, version);
		}
	}

	private OIDbean[] getBulkInSynWay(Collection<String> oids, String community, int maxRepetitions, String ip, int port, int retries, long timeout, int version) {
		CommunityTarget target = createV1V2CTarget(community, retries, timeout, version, ip, port);
		PDU pdu = new PDU();
		for (String oid : oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		pdu.setType(GETBULK);
		pdu.setMaxRepetitions(maxRepetitions);
		pdu.setNonRepeaters(0);

		try {
			ResponseEvent event = session.getBulk(pdu, target);
			return readResponse(event);
		} catch (Exception e) {
			logger.error("SNMP", e);
		}
		return null;

	}

	/**
	 * 异步的方式发送GETBULK请求
	 * 
	 * @param oids
	 *            指定的oid列表
	 * @param community
	 *            agent的团体字
	 * @param maxRepition
	 *            操作次数
	 * @return
	 */
	private OIDbean[] getBulkInAsynWay(Collection<String> oids, String community, int maxRepetitions, String ip, int port, int retries, long timeout, int version, long waitTimeOut) {
		CommunityTarget target = createV1V2CTarget(community, retries, timeout, version, ip, port);
		PDU pdu = new PDU();
		for (String oid : oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		pdu.setType(GETBULK);
		pdu.setMaxRepetitions(maxRepetitions);
		pdu.setNonRepeaters(0);

		int[] synSignal = new int[0];
		SnmpResponseListener listener = createResponseListener();
		try {
			session.getBulk(pdu, target, synSignal, listener);

			synchronized (synSignal) {

				try {
					synSignal.wait(waitTimeOut);
				} catch (Exception e) {
					logger.error("snmp等待数据中断", e);
				}

			}

			return listener.getResult();
		} catch (Exception e) {

			logger.error("SNMP", e);
		}
		return null;
	}

	private PDU walk(OID rootOID, final Target target, long waitTimeOut) throws IOException {

		final PDU response = new PDU();
		TreeUtils treeUtils = new TreeUtils(session, this);
		treeUtils.setIgnoreLexicographicOrder(true);// 忽略walk时oid大小的比较
		TreeListener treeListener = new TreeListener() {

			private boolean finished;

			public boolean next(TreeEvent e) {

				if (e.getVariableBindings() != null) {
					VariableBinding[] vbs = e.getVariableBindings();
					response.addAll(e.getVariableBindings());
				}
				return true;
			}

			public void finished(TreeEvent e) {
				if ((e.getVariableBindings() != null) && (e.getVariableBindings().length > 0)) {
					next(e);
				}
				if (e.isError()) {
					VariableBinding[] vbs = e.getVariableBindings();
				}
				finished = true;
				Object obj = e.getUserObject();
				synchronized (obj) {
					obj.notify();
				}
			}

			public boolean isFinished() {
				return finished;
			}

		};
		int[] synSignal = new int[0];// 线程通信信号量

		synchronized (synSignal) {
			treeUtils.getSubtree(target, rootOID, synSignal, treeListener);
			try {
				synSignal.wait(waitTimeOut);
			} catch (Exception e) {
				logger.error("synchronized synSignal wait Exception：", e);
			}
		}
		return response;

	}


	
	public static void main(String[] args) {

		SnmpUtil util = new SnmpUtil();
		DeviceInfoBean device = new DeviceInfoBean();
		device.setFactory(0);
		device.setIPAddress("221.182.80.42");
		device.setSnmpCommunity("SC321mcc");
		device.setSnmpPort(161);
		device.setSnmpSetCommunity("SC321mcc");
		device.setSnmpVersion(SNMP_V2);
		device.setRoutId(1);
		device.setRoutName("SCLUZ-MC-CMNET-SLDD-SW01");

		try {

			List<OIDbean> beans = util.walkTable(device, "1.3.6.1.2.1.2.2.1.16", 2, 500l, 60000l);
			OIDbean bean;
			Iterator<OIDbean> it = beans.iterator();
			while (it.hasNext()) {
				bean = it.next();
				System.out.println("Oid: " + bean.getOid() + ";value:" + bean.getValue());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String hexString = "0123456789abcdef";

	private String toStringHex(String bytes) {

		if (bytes.length() % 2 != 0) {

			return bytes;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2) {
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		}
		try {
			return new String(baos.toByteArray(), "gbk").trim();
		} catch (Exception e) {
			logger.error("SNMP", e);
		}
		return null;
	}

	/**
	 * 得到中文字符
	 * 
	 * @param ch
	 * @return
	 */
	public String getChineseStr(String ch) {
		StringBuffer sb = new StringBuffer();

		String[] str = ch.split("\\:");

		int intLength = str.length;

		for (int i = 0; i < intLength; i++) {
			sb.append(str[i]);
		}
		if (str.length <= 1) {

			return ch;
		}
		return toStringHex(specialCharFilter(sb.toString()));
	}

	/**
	 * @param ch
	 * @return
	 */
	private String specialCharFilter(String ch) {// 过滤编码中的“:”

		String[] str = ch.split("\\:");
		int intLength = str.length;

		StringBuffer temp = new StringBuffer();
		for (int j = 0; j < str.length; j++) {
			temp.append(str[j]);
		}

		return temp.toString();
	}

	@Override
	public PDU createPDU(MessageProcessingModel arg0) {
		return null;
	}
}

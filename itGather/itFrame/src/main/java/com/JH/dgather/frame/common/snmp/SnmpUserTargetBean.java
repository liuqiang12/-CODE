package com.JH.dgather.frame.common.snmp;

import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.PrivAES128;
import org.snmp4j.security.PrivAES192;
import org.snmp4j.security.PrivAES256;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.smi.OID;

/**
 * @author gamesdoa
 * @email gamesdoa@gmail.com
 * @date 2012-7-24
 */

public class SnmpUserTargetBean {
	private String userName;// 安全名称(用户)
	private OID authProtocol;// 认证协订
	private String authPasshrase;// 认证的通行密码
	private OID privProtocol;// 保密协定
	private String privPassphrase;// 保密的通行密码
/*	private int securityLevel;// the security level for this target
	private String securityName;// //安全模块的名称
	private int securityModel;// Adds a security model to the central repository
								// of security models.
*/
	
	public SnmpUserTargetBean(){
		userName = null;// 安全名称(用户)
		authProtocol = null;// 认证协订
		authPasshrase = null;// 认证的通行密码
		privProtocol = null;// 保密协定
		privPassphrase = null;// 保密的通行密码
	}
	/**
	 * 安全名称(用户) add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 安全名称(用户) add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 认证协订 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @return
	 */
	public OID getAuthProtocol() {
		return authProtocol;
	}

	/**
	 * 认证协订 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @param authProtocol
	 */
	public void setAuthProtocol(String authProtocol) {
		if(authProtocol==null){
			this.authProtocol = null;
			return;
		}
		if (authProtocol.equalsIgnoreCase("md5"))
			this.authProtocol = AuthMD5.ID;
		else if (authProtocol.equalsIgnoreCase("sha"))
			this.authProtocol = AuthSHA.ID;
		else
			this.authProtocol = null;

	}

	/**
	 * 认证的通行密码 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @return
	 */
	public String getAuthPasshrase() {
		return authPasshrase;
	}

	/**
	 * 认证的通行密码 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @param authPasshrase
	 */
	public void setAuthPasshrase(String authPasshrase) {
		this.authPasshrase = authPasshrase;
	}

	/**
	 * 保密协定 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @return
	 */
	public OID getPrivProtocol() {
		return privProtocol;
	}

	/**
	 * 保密协定 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @param privProtocol
	 */
	public void setPrivProtocol(String privProtocol) {
		if(privProtocol == null){
			this.privProtocol = null;
			return;
		}
		if (privProtocol.equalsIgnoreCase("des"))
			this.privProtocol = PrivDES.ID;
		else if (privProtocol.equalsIgnoreCase("aes128"))
			this.privProtocol = PrivAES128.ID;
		else if (privProtocol.equalsIgnoreCase("aes192"))
			this.privProtocol = PrivAES192.ID;
		else if (privProtocol.equalsIgnoreCase("aes256"))
			this.privProtocol = PrivAES256.ID;
		else
			this.privProtocol = null;
	}

	/**
	 * 保密的通行密码 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @return
	 */
	public String getPrivPassphrase() {
		return privPassphrase;
	}

	/**
	 * 保密的通行密码 add @author gamesdoa
	 * 
	 * @email gamesdoa@gmail.com
	 * @date 2012-7-24
	 * @param privPassphrase
	 */
	public void setPrivPassphrase(String privPassphrase) {
		this.privPassphrase = privPassphrase;
	}

	public int getSecurityLevel() {
		if ((authPasshrase == null || authPasshrase.equalsIgnoreCase("")) && (privPassphrase == null || privPassphrase.equalsIgnoreCase("")))
			return SecurityLevel.NOAUTH_NOPRIV;
		else if (authPasshrase != null && (privPassphrase == null || privPassphrase.equalsIgnoreCase("")))
			return SecurityLevel.AUTH_NOPRIV;
		else
			return SecurityLevel.AUTH_PRIV;
	}

	public String getSecurityName() {
		//return securityName;
		return userName;
	}

	/*public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}*/


}

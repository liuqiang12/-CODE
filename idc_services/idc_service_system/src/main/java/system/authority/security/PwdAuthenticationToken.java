package system.authority.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class PwdAuthenticationToken extends AbstractAuthenticationToken{

	public PwdAuthenticationToken() {  
        super(null);  
    }  
      
    private String userName;  
    private String idCode;  
    private String password;  
    private String errCode;  
  
    private static final long serialVersionUID = 1L;  
  
    @Override  
    public Object getCredentials() {  
        return this.idCode+"_"+this.userName;  
    }  
  
    @Override  
    public Object getPrincipal() {  
        return this.password;  
    }  
  
    public String getUserName() {  
        return userName;  
    }  
  
    public void setUserName(String userName) {  
        this.userName = userName;  
    }  
  
    public String getIdCode() {  
        return idCode;  
    }  
  
    public void setIdCode(String idCode) {  
        this.idCode = idCode;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public String getErrCode() {  
        return errCode;  
    }  
  
    public void setErrCode(String errCode) {  
        this.errCode = errCode;  
    }  
  

}

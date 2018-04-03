package system.data.basedata;

public enum ErrorEnum {
		PARAM_ERROR("0001","参数异常"),
	    CACHE_ALREADY_EXIST("0002","缓存已存在"),
	    CACHE_VERSION_INVALID("0003","缓存版本异常"),
	    DB_OPERATION_FAIL("0004","db操作异常"),
	    SESSION_NOT_EXIST("0005","session不存在，需要登录"),
	    PERMISSION_NOT_ALLOWED("0006","无权限访问"),
	    DATA_NOT_FOUND("0007","数据不存在"),

	    ACTIVE_MEMBER_ERROR("1002","激活用户异常"),
	    CACHE_USER_ADDR_ERROR("1003","缓存用户地址异常"),
	    CACHE_IS_NULL("1004","缓存为空"),

	    USER_IS_NOT_EXIST("2002","用户不存在"),
	    USER_ALREADY_EXIST("2003","用户已存在");


	    private String errorCode;

	    private  String errorInfo;

	    ErrorEnum(String errorCode , String errorInfo){
	        this.errorCode = errorCode;
	        this.errorInfo = errorInfo;
	    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}

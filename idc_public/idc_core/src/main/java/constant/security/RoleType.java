package constant.security;

public enum RoleType {
	USER("USER"),
    DBA("DBA"),
    ADMIN("ADMIN");
     
    String roleType;
     
    private RoleType(String roleType){
        this.roleType = roleType;
    }
     
    public String getRoleType(){
        return roleType;
    }
    public static void main(String[] args) {
		// 测试
    	System.out.println(RoleType.DBA.getRoleType());
	}
}

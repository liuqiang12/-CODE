package constant.security;


public enum StaticSecurity {
    // 超级管理员权限
    ADMIN("ADMIN"),
    //普通用户权限
    USER("USER"),
    //登录用户权限
    LOGIN("LOGIN");

    private String permission;

    public String getPermission() {
        return permission;
    }

    private StaticSecurity(String permission) {
        this.permission = permission;
    }
    public static void main(String[] args) {
		System.out.println(StaticSecurity.ADMIN.getPermission());
	}

}

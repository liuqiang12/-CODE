package system.data.msg.ajax;


/**
 * 返回的值类型：成功与否
 * @author Administrator
 *
 */
public enum ResponseType {
	TRUE(true),
    FALSE(false);
     
	Boolean type;
     
    private ResponseType(Boolean type){
        this.type = type;
    }
     
    public Boolean getType(){
        return type;
    }
    public static void main(String[] args) {
		// 测试
    	System.out.println(ResponseType.FALSE.getType());
	}
}

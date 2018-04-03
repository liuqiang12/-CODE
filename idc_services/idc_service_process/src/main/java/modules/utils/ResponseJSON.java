package modules.utils;

/**
 * Created by DELL on 2017/6/9.
 * 用于返回给客户端的结果
 */
public class ResponseJSON {
    //默认返回值是true
    private boolean success = true;
    private String msg = "SUCCESSFUL";
    private Object result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


}

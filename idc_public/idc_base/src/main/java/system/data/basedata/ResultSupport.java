package system.data.basedata;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class ResultSupport<T> implements Result<T>, Cloneable {
    private static final long serialVersionUID = 3976733653567025460L;
    private boolean success = true;
    private Map models = new HashMap(4);
    private String defaultModelKey;
    private String errorMessage;
    private String errorCode;
    private String resultCode;
    private T model;
    private long totalCount;

    /**
     * 创建一个result。
     */
    public ResultSupport() {
    }

    /**
     * 创建一个result。
     *
     * @param success
     *           是否成功
     */
    public ResultSupport(boolean success) {
        this.success = success;
    }

    /**
     * 创建一个result。
     *
     * @param defaultModel
     *           是否成功
     */
    public static <T> ResultSupport<T> newSuccessResult(T defaultModel) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.success = true;
        result.setDefaultModel(defaultModel);
        return result;
    }
    public static <T> ResultSupport<T> newSuccessResult(List<String> listValue) {
    	ResultSupport<T> result = new ResultSupport<T>();
    	result.success = true;
    	return result;
    }

    /**
     * 创建一个result。
     *
     * @param errorMessage
     *           是否成功
     */
    public static <T> ResultSupport<T> newErrorResult(String errorMessage) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.success = false;
        result.setErrorMessage(errorMessage);
        return result;
    }

    /**
     * 创建一个result。
     *
     * @param errorMessage
     * @param errorCode
     *           是否成功
     */
    public static <T> ResultSupport<T> newErrorResult(String errorMessage, String errorCode) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.success = false;
        result.setErrorMessage(errorMessage);
        result.setErrorCode(errorCode);
        return result;
    }

    /**
     * 创建一个result。
     *
     * @param benefitError
     *           是否成功
     */
    public static <T> ResultSupport<T> newErrorResult(ErrorEnum benefitError) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.success = false;
        if(benefitError != null){
            result.setErrorMessage(benefitError.getErrorInfo());
            result.setErrorCode(benefitError.getErrorCode());
        }

        return result;
    }

    public static <T> ResultSupport<T> copyErrorResult(Result r){
        ResultSupport<T> result = new ResultSupport<T>();
        result.success = false;
        if(r != null){
            result.setErrorMessage(r.getErrorMessage());
            result.setErrorCode(r.getErrorCode());
        }
        return result;
    }

    /**
     * 创建一个result。
     *
     * @param benefitError
     *           是否成功
     */
    public static <T> ResultSupport<T> newErrorResult(ErrorEnum benefitError , String otherInfo) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.success = false;
        if(benefitError != null){
            result.setErrorMessage(benefitError.getErrorInfo()+","+otherInfo);
            result.setErrorCode(benefitError.getErrorCode());
        }

        return result;
    }

    /**
     * 创建一个result。
     *
     * @param benefitError
     *           是否成功
     */
    public static <T> ResultSupport<T> newErrorResult(ErrorEnum benefitError,Class<?> clazz) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.success = false;
        if(benefitError != null){
            result.setErrorMessage("["+clazz.getSimpleName()+"]-"+benefitError.getErrorInfo());
            result.setErrorCode(benefitError.getErrorCode());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public ResultSupport(ResultSupport<T> source) {
        this.success = source.success;
        this.model = source.model;
        this.models = new HashMap(source.models);
        this.defaultModelKey = source.defaultModelKey;
        this.errorMessage = source.errorMessage;
        this.totalCount = source.totalCount;
    }

    /**
     * 请求是否成功。
     *
     * @return 如果成功，则返回<code>true</code>
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置请求成功标志。
     *
     * @param success
     *           成功标志
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 取得默认model对象的key。
     *
     * @return 默认model对象的key
     */
    public String getDefaultModelKey() {
        return defaultModelKey==null||defaultModelKey.equals("")?DEFAULT_MODEL_KEY:defaultModelKey;
    }

    /**
     * 取得model对象。
     * <p>
     * 此调用相当于<code>getModels().get(DEFAULT_MODEL_KEY)</code>。
     * </p>
     *
     * @return model对象
     */
    public T getDefaultModel() {
        return (T) models.get(getDefaultModelKey());
    }

    /**
     * 设置model对象。
     * <p>
     * 此调用相当于<code>getModels().put(DEFAULT_MODEL_KEY, model)</code>。
     * </p>
     *
     * @param model
     *           model对象
     */
    public void setDefaultModel(T model) {
        setDefaultModel(DEFAULT_MODEL_KEY, model);
    }

    /**
     * 设置model对象。
     * <p>
     * 此调用相当于<code>getModels().put(key, model)</code>。
     * </p>
     *
     * @param key
     *           字符串key
     * @param model
     *           model对象
     */
    public void setDefaultModel(String key, Object model) {
        defaultModelKey = key==null||key.equals("")?DEFAULT_MODEL_KEY:key;
        models.put(key, model);
    }

    /**
     * 取得所有model对象。
     *
     * @return model对象表
     */
    public Map getModels() {
        return models;
    }

    /**
     * 转换成字符串的表示。
     *
     * @return 字符串表示
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Result {\n");
        buffer.append("    success    = ").append(success).append(",\n");
        buffer.append("    resultCode = ").append(success ? resultCode : errorCode).append(",\n");
        buffer.append("    models     = {");

        if (models.isEmpty()) {
            buffer.append("}\n");
        } else {
            buffer.append("\n");

            for (Iterator i = models.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Map.Entry) i.next();
                Object key = entry.getKey();
                Object value = entry.getValue();

                buffer.append("        ").append(key).append(" = ");

                if (value != null) {
                    buffer.append("(").append(value.getClass().getName()).append(") ");
                }

                buffer.append(value);

                if (i.hasNext()) {
                    buffer.append(",");
                }

                buffer.append("\n");
            }

            buffer.append("    }\n");
        }

        buffer.append("}");
        buffer.append("    errorMessage = ").append(errorMessage).append(",\n");

        return buffer.toString();
    }

    /**
     * 获取错误信息
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public <V> void setExtendx(String modelKey, V model) {
        if (modelKey != null && modelKey.equals(getDefaultModelKey())) {
            throw new IllegalArgumentException("modelKey [" + modelKey + "] can't equals defaultModelKey[" + getDefaultModelKey() + "]");
        }
        this.models.put(modelKey, model);
    }

    public <V> V getExtendx(String key) {
        return (V) this.models.get(key);
    }
}
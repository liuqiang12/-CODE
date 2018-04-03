package system.data.supper.service;

/**
 * 方向序列号和正向序列号
 */
public interface JavaSerializer {
	/**
	 * 序列化
	 * @param obj
	 * @return
	 */
    byte[] serialize(Object obj);
    /**
     * 反序列化
     * @param str
     * @return
     */
    Object unserialize(byte[] str);
}

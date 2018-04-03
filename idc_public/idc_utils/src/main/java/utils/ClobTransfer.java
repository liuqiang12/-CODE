package utils;

import java.io.Reader;
import java.sql.Clob;

/**
 * Created by DELL on 2017/9/4.
 */
public class ClobTransfer {
    /**
     * 将String转成Clob ,静态方法
     *
     * @param str
     *            字段
     * @return clob对象，如果出现错误，返回 null
     */
    public static Clob stringToClob(String str) {
        if (null == str)
            return null;
        else {
            try {
                java.sql.Clob c = new javax.sql.rowset.serial.SerialClob(str
                        .toCharArray());
                return c;
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * 将Clob转成String ,静态方法
     *
     * @param clob
     *            字段
     * @return 内容字串，如果出现错误，返回 null
     */
    public static String clobToString(Clob clob) {
        if (clob == null)
            return null;
        StringBuffer sb = new StringBuffer();
        Reader clobStream = null;
        try {
            clobStream = clob.getCharacterStream();
            char[] b = new char[60000];// 每次获取60K
            int i = 0;
            while ((i = clobStream.read(b)) != -1) {
                sb.append(b, 0, i);
            }
        } catch (Exception ex) {
            sb = null;
        } finally {
            try {
                if (clobStream != null) {
                    clobStream.close();
                }
            } catch (Exception e) {
            }
        }
        if (sb == null)
            return null;
        else
            return sb.toString();
    }

    public static String clobToString(oracle.sql.CLOB clob){
        try{
            Reader inStream = clob.getCharacterStream();
            char[] c = new char[(int) clob.length()];
            inStream.read(c);
            String data = new String(c);
            inStream.close();
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
}

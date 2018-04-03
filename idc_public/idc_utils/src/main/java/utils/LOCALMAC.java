package utils;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
/**
 * Created by DELL on 2017/9/26.
 */
public class LOCALMAC {
    /**
     * @param args
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static void main(String[] args) throws UnknownHostException, SocketException {
        // TODO Auto-generated method stub

        //得到IP，输出PC-201309011313/122.206.73.83

        getLocalMac();
    }
    public static String getLocalMac(){
        // TODO Auto-generated method stub
        //获取网卡，获取地址
        InetAddress ia = null;
        StringBuffer sb = new StringBuffer("");
        try {
            ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            for(int i=0; i<mac.length; i++) {
                if(i!=0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i]&0xff;
                String str = Integer.toHexString(temp);
                if(str.length()==1) {
                    sb.append("0"+str);
                }else {
                    sb.append(str);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return sb.toString().toUpperCase();
    }
}

package constant.isp;

/**
 * Created by DELL on 2017/8/29.
 */
public enum ISPContant {
    压缩格式_无压缩("0"),
    压缩格式_Zip压缩格式("1"),
    哈希算法_无hash("0"),
    哈希算法_MD5("1"),
    哈希算法_SHA_1("2"),
    版本("V2.0"),
    对称加密算法_明文传输("0"),
    对称加密算法_AES加密("1"),
    许可证号("A2.B1.B2-20100001"),
    AES密钥("LVVKVXWF56496929"),
    AES偏移量("MSFBUWFS86537785"),
    AES密码器("AES/CBC/PKCS7Padding"),
    AES("AES"),
    认证密钥("LLX239")
    ;
    private final String value;

    ISPContant(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}

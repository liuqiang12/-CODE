package utils.typeHelper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class EncryptUtil implements PasswordEncoder {
	private static final String SITE_WIDE_SECRET = "my-secret-key";
	private static final PasswordEncoder encoder = new StandardPasswordEncoder(SITE_WIDE_SECRET);

	/**
	 * 加密方法
	 * 
	 * @param rawPassword
	 *            被加密的字符串
	 * @return
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return encoder.encode(rawPassword);
	}

	/**
	 * 比较是否相等
	 * 
	 * @param rawPassword
	 *            加密后的字符串
	 * @param password
	 *            源字符串
	 * @return
	 */
	@Override
	public boolean matches(CharSequence password, String rawPassword) {
		boolean isSuccess = false;
		try {
			isSuccess = encoder.matches(password, rawPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	public static void main(String[] args) {
		String source = "aaaaa";
		String target = "595790f6a95d2effe03be0c36fca0ce00a480a6148a772a0555444e0d885d913201e4f4ce73fd7f5";
		EncryptUtil util = new EncryptUtil();
		System.out.println(util.encode(source));
		System.out.println(util.matches(source, target));
	}
}

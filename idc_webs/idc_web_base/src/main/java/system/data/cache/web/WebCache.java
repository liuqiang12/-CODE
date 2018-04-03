package system.data.cache.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * 
 * <b>描述<b>：WebUtils公共类，获取地址，端口等等
 */
public class WebCache {
	public static String getCurTomcatPath() {
		ServletContext servletContext = RequestCache.getRequest().getSession()
				.getServletContext();
		String contextPath = servletContext.getContextPath();
		String realPath = servletContext.getRealPath(contextPath);
		return realPath;
	}

	public static String getCurWebAppPath() {
		ServletContext servletContext = RequestCache.getRequest().getSession()
				.getServletContext();
		String realPath = servletContext.getRealPath("");
		return realPath;
	}

	public static String getLocalAddr() {
		String ret = null;

		HttpServletRequest request = RequestCache.getRequest();

		ret = request.getLocalAddr();
		return ret;
	}

	public static int getLocalPort() {
		int ret = 8080;

		HttpServletRequest request = RequestCache.getRequest();

		ret = request.getLocalPort();
		return ret;
	}

	/**
	 * 获取请求导入的页面
	 * @return
	 */
	public static String getBackPath(){
		HttpServletRequest request = RequestCache.getRequest();
		String uri=request.getRequestURI(),referer=request.getHeader("Referer");
		uri=removeFirst(uri);
		Pattern p = Pattern.compile("/*/");
		Pattern p2 = Pattern.compile(""+p.split(uri)[0]+"");
		String refererSplit = p2.split(referer)[1];
		refererSplit=removeFirst(refererSplit);
		refererSplit=removeLast(refererSplit);
		return refererSplit;
	}
	
	private static String removeFirst(String str){
		if(str!=null){
			while (true) {
				if(str.startsWith("/")){
					str=str.substring(1);
				}else{
					break;
				}
			}
		}
		return str;
	}
	
	private static String removeLast(String str){
		if(str!=null){
			str=str.substring(0,str.lastIndexOf("."));
		}
		return str;
	}
	
	
}

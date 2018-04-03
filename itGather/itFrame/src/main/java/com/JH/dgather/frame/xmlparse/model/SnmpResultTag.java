package com.JH.dgather.frame.xmlparse.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class SnmpResultTag extends BaseTag {
	
	static String Key_String = "^\\$\\{(.*)\\.(.*)\\}$";
	static Pattern keypattern = Pattern.compile(Key_String);

	private String m_name;
	private String m_expr;
	
	public SnmpResultTag(Element element, BaseTag parentTag)
			throws TagException {
		super("snmpResult", element, parentTag);
		parse();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void parse() throws TagException {
		List<Attribute> attrs = getElement().attributes();
		if(attrs == null || attrs.isEmpty())
			throw new TagException("snmpResult tag must be have name attribute");
		for(Attribute attr : attrs) {
			if("name".equals(attr.getName())) {
				m_name = attr.getValue();
			}
		}
		
		m_expr = getElement().getText();
		if(m_expr == null || m_expr.length() == 0)
			throw new TagException("Expression can not be empty.");
	}

	@SuppressWarnings("unchecked")
	public void precess(OperaterHandle handle) throws DataStorageException {
		Rpn rpn = new Rpn();
		String[] rpnarray = rpn.toRpn(m_expr);
		Map<String, String> keymap = null;
		Map<String, Map<String, String>> cachemap = new HashMap<String, Map<String, String>>();
		for(String param : rpnarray) {
			Matcher m = keypattern.matcher(param);
			if(m.find()) {
				String mainkey = m.group(1);
				String subkey = m.group(2);
				VarBean bean = handle.getData(mainkey, subkey);
				if(bean == null)
					throw new RuntimeException("Could not find the cache data from storagedata. " +
							"config error? mainkey=" + mainkey + " subkey=" + subkey);
				Map<String, String> valmap = (Map<String, String>) bean.getObject();
				cachemap.put(param, valmap);
				
				if(keymap == null)
					keymap = valmap;
			}
		}
		
		Map<String, String> resultmap = new HashMap<String, String>();
		for(Map.Entry<String, String> entry : keymap.entrySet()) {
			String key = entry.getKey();
			String[] rpnexpr = new String[rpnarray.length];
			for(int i = 0; i < rpnarray.length; i ++) {
				String val = rpnarray[i];
				if(rpnarray[i].matches(Key_String)) {
					val = cachemap.get(rpnarray[i]).get(key);
				}
				rpnexpr[i] = val;
			}
			float result = rpn.calculate(rpnexpr);
			resultmap.put(key, result + "");
		}
		
//		VarBean resbean = new VarBean(VarBean.TYPE_HASHMAP, resultmap);
		handle.addResult(m_name, resultmap);
	}
	
	public static void main(String[] args) throws TagException {
		Matcher m = keypattern.matcher("${capture.costEcho1}");
		if(m.find()) {
			System.out.println(m.group(1));
			System.out.println(m.group(2));
		}
	}
}

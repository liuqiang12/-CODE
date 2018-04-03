package com.JH.dgather.frame.xmlparse.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public abstract class FilterableTag extends BaseTag {

	static Pattern conditionp = Pattern.compile("^\\$\\{(.*)\\.(.*)\\}(.*)");
	
	final static String[] filter = { "eq", "neq", "gt", "lt", "gte", "lte", "not", "iseven", "isodd", "between" };
	final static String[] calculate = { "cost", "max", "min", "sum", "avg", "count" };
	
	String operation;
	String[] params;
	boolean isfilter = false;
	String mainkey, subkey;
	
	public FilterableTag(String tagName, Element element, BaseTag parentTag)
			throws TagException {
		super(tagName, element, parentTag);
		// TODO Auto-generated constructor stub
	}
	
	//	 "eq", "neq", "gt", "lt", "gte", "lte", "not", "iseven", "isodd", "between" 
	public boolean filter(String oper, String value) {
		double d = Double.parseDouble(value);
		if(oper.equals("eq")) {
			if(d == Double.parseDouble(params[0]))
				return true;
		} else if(oper.equals("neq")) {
			if(d != Double.parseDouble(params[0]))
				return true;
		} else if(oper.equals("gt")) {
			if(d > Double.parseDouble(params[0]))
				return true;
		} else if (oper.equals("lt")) {
			if(d < Double.parseDouble(params[0]))
				return true;
		} else if(oper.equals("gte")) {
			if(d >= Double.parseDouble(params[0]))
				return true;
		} else if(oper.equals("lte")) {
			if(d <= Double.parseDouble(params[0]))
				return true;
		} else if(oper.equals("not")) {
			if(d != Double.parseDouble(params[0]))
				return true;
		} else if(oper.equals("iseven")) {
			if(d % 2 == 0)
				return true;
		} else if(oper.equals("isodd")) {
			if(d % 2 != 0)
				return true;
		} else if(oper.equals("between")) {
			if(d >= Double.parseDouble(params[0]) && d <= Double.parseDouble(params[1]))
				return true;
		} else {
			// do nothing
		}
		return false;
	}

	protected String parseOperation(String value) {
		log().info("parse string:[" + value + "]");
		value = value.trim();
		Matcher m = conditionp.matcher(value);
		if(m.find()) {
			mainkey = m.group(1);
			subkey = m.group(2);
			value = m.group(3).trim();
		}
		log().info("parse string2:[" + value + "]");
		for(int i = 0; i < filter.length; i ++) {
			if(value.startsWith(filter[i])) {
				operation = filter[i];
				if(value.length() > operation.length()) {
					String paramstr = value.substring(operation.length()).trim();
					params = paramstr.split(" ");
					isfilter = true;
				}
				return operation;
			}
		}
		
		for(int i = 0; i < calculate.length; i ++) {
//			log().warn("value=" + value + "cal=" + calculate[i]);
			if(value.startsWith(calculate[i])) {
				operation = calculate[i];
				if(value.length() > operation.length()) {
					String paramstr = value.substring(operation.length()).trim();
					params = paramstr.split(" ");
				}
				return operation;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		String operation = null;
		String value = "cost";
		for(int i = 0; i < calculate.length; i ++) {
			if(value.startsWith(calculate[i])) {
				operation = calculate[i];
				System.out.println(operation);
				if(value.length() > operation.length()) {
					String paramstr = value.substring(operation.length()).trim();
					System.out.println(operation);
				}
			}
		}
	}
	
	private Logger log() {
		return Logger.getLogger(getClass());
	}
}

package com.JH.dgather.frame.xmlparse.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

public class CaptureStepTag extends FilterableTag {
	
	String m_value;
	
	public CaptureStepTag(Element element, BaseTag parentTag)
			throws TagException {
		super("captureStep", element, parentTag);
		parse();
	}

	@Override
	protected void parse() throws TagException {
		m_value = getElement().getText();
		if(m_value == null || m_value.length() == 0)
			throw new TagException(getTagName() + " tag config Invalid, content=" + m_value);
		
	}

	public Map<String, String> process(Map<String, String> map) {
		String okstr = parseOperation(m_value);
		if(okstr == null)
			throw new IllegalArgumentException("Incorrect operation or not supported, value:" + m_value);
		
		Map<String, String> ret = new HashMap<String, String>();
		if(isfilter) {
			for(Map.Entry<String, String> entry : map.entrySet()) {
				if(filter(operation, entry.getValue()))
					ret.put(entry.getKey(), entry.getValue());
			}
		} else {
			if(operation.equals("cost"))
				return map;
			else if(operation.equals("max")) {
				double max = - Double.MAX_VALUE;
				for(Map.Entry<String, String> entry : map.entrySet()) {
					double tmp = Double.parseDouble(entry.getValue());
					if(tmp > max)
						max = tmp;
				}
				ret.put("max", max + "");
			}
			else if(operation.equals("min")) {
				double min = Double.MAX_VALUE;
				for(Map.Entry<String, String> entry : map.entrySet()) {
					double tmp = Double.parseDouble(entry.getValue());
					if(tmp < min)
						min = tmp;
				}
				ret.put("min", min + "");
			}
			else if(operation.equals("sum")) {
				ret.put("sum", sum(map) + "");
			}
			else if(operation.equals("avg")) {
				ret.put("avg", sum(map) / map.size() + "");
			}
			else if(operation.equals("count")) {
				ret.put("count", map.size() + "");
			}
		}
		return ret;
	}
	
	public double sum(Map<String, String> map) {
		double sum = 0d;
		for(Map.Entry<String, String> entry : map.entrySet()) {
			double tmp = Double.parseDouble(entry.getValue());
			sum += tmp;
		}
		return sum;
	}
	
	private Logger log() {
		return Logger.getLogger(getClass());
	}
}

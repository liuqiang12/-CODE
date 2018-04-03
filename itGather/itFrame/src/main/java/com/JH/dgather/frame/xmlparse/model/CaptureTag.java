package com.JH.dgather.frame.xmlparse.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class CaptureTag extends BaseTag {

	private String name;
	
	private String keyPosition;
	
	private List<CaptureStepTag> captureSteps = new ArrayList<CaptureStepTag>();
	
	public CaptureTag(Element element, BaseTag parentTag)
			throws TagException {
		super("capture", element, parentTag);
		parse();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void parse() throws TagException {
		String errmsg = getTagName() + " 标签规则: \r\n";
		errmsg += "【可包含的子标签】\r\n";
		errmsg += "<keyposition ><captureStep >";
		
		List<Attribute> attrs = getElement().attributes();
		if(attrs != null && !attrs.isEmpty()) {
			for(Attribute attr : attrs) {
				if(attr.getName().equals("name")) {
					name = attr.getValue();
				} else {
					log().error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					log().error(errmsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
		} else {
			throw new TagException(this.getTagName() + " 标签必须包含属性: name");
		}
		
		List<Element> elements = getElement().elements();
		if(elements != null && !elements.isEmpty()) {
			for(Element el : elements) {
				if(el.getName().equals("keyposition")) {
					keyPosition = el.getText();
				} else if(el.getName().equals("captureStep")) {
					captureSteps.add(new CaptureStepTag(el, this));
				}
			}
		} else {
			throw new TagException(this.getTagName() + "标签内必须配置元素!");
		}
	}
	

	private Logger log() {
		return Logger.getLogger(getClass());
	}
}

package com.JH.dgather.frame.xmlparse.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

//import com.zone.ics.frame.common.snmp.SnmpService;
//import com.zone.ics.frame.common.snmp.bean.OIDbean;


import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class ObjIdTag extends BaseTag {
	
	private String buffer;
	
	private String objId;

	public ObjIdTag(Element element, BaseTag parentTag)
			throws TagException {
		super("objId", element, parentTag);
		parse();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void parse() throws TagException {
		String errmsg = getTagName() + " 标签规则: \r\n";
		errmsg += "【不包含子标签】\r\n";
		errmsg += "标签必须包含内容。例如: <objId buffer=\"echo1\">oid1</objId>";
		
		List<Attribute> attrs = getElement().attributes();
		if(attrs != null && !attrs.isEmpty()) {
			for(Attribute attr : attrs) {
				if(attr.getName().equals("buffer"))
					buffer = attr.getValue();
				else {
					log().error(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
					log().error(errmsg);
					throw new TagException(this.getTagName() + " 标签不允许包含属性: " + attr.getName());
				}
			}
		} else {
			throw new TagException(this.getTagName() + " 标签必须包含属性: buffer");
		}
		
		/**
		 * 获取objId
		 */
		if (this.getElement().getText().equals("")) {
			log().error(this.getTagName() + " 标签的内容不能为空。");
			log().error(errmsg);
			throw new TagException(this.getTagName() + " 标签的内容不能为空。");
		}
		objId = this.getElement().getText();
		log().debug("Oid=" + objId);
	}

//	public String process(SnmpService snmpService, OperaterHandle handle) throws Exception {
//		List<OIDbean> list = snmpService.walk(objId);
//		handle.addBuffer(buffer, list);
//		return buffer;
//	}
	
	private Logger log() {
		return Logger.getLogger(getClass());
	}
}

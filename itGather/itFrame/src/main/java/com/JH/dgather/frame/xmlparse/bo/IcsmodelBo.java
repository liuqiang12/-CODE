package com.JH.dgather.frame.xmlparse.bo;

import java.util.Map;

import com.JH.dgather.frame.xmlparse.bo.iface.IBase;

/**
 * icsmodel标签业务对象
 * 
 * @author zhengbin
 * 
 */
public class IcsmodelBo implements IBase {
	
	/**
	 * 唯一编号，必须包含属性
	 */
	private String id;
	
	/**
	 * 对应的采集类型，必须包含属性
	 */
	private String gatherclass;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * icsmodel子标签集合
	 */
	private Map<String, FactoryBo> factoryBoMap;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getGatherclass() {
		return gatherclass;
	}


	public void setGatherclass(String gatherclass) {
		this.gatherclass = gatherclass;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Map<String, FactoryBo> getFactoryBoMap() {
		return factoryBoMap;
	}


	public void setFactoryBoMap(Map<String, FactoryBo> factoryBoMap) {
		this.factoryBoMap = factoryBoMap;
	}


	@Override
	public String getTagNameByObject() {
		return "icsmodel";
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("icsmodel标签 [id属性=");
		builder.append(id);
		builder.append(", gatherclass属性=");
		builder.append(gatherclass);
		builder.append(", name属性=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}

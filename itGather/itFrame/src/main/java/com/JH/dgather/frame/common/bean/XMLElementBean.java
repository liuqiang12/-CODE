package com.JH.dgather.frame.common.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 用于进行soap传输的数据模型
 * 
 * @author yangDS
 * 
 */
public class XMLElementBean {
	private String nodeName;// 元素名称
	private HashMap<String, String> attAndVal;// 元素的属性和值
	private ArrayList<XMLElementBean> sons;// 元素的子元素

	public XMLElementBean() {
		attAndVal = new HashMap<String, String>();
		// sons =new ArrayList<XMLElementBean>();
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 在属性与值的集合中添加属性和值
	 * 
	 * @param att
	 * @param value
	 */
	public synchronized void putAttribute(String att, String value) {
		attAndVal.put(att, value);
	}

	/**
	 * @param attAndVal
	 */
	public void setAttAndVal(HashMap<String, String> attAndVal) {
		this.attAndVal = attAndVal;
	}

	public HashMap<String, String> getAttAndVal() {
		return attAndVal;
	}

	public String getElementAttributeByName(String attName) {
		return attAndVal.get(attName);
	}

	/**
	 * 删除某个属性
	 * 
	 * @param att
	 */
	public synchronized void removeAttribute(String att) {
		attAndVal.remove(att);
	}

	public void clearAllAttribute() {
		attAndVal.clear();
	}

	/**
	 * 添加一个字节点
	 * 
	 * @return
	 */
	public synchronized XMLElementBean addSon() {
		if (sons == null)
			sons = new ArrayList<XMLElementBean>();
		XMLElementBean xeb = new XMLElementBean();

		sons.add(xeb);
		return xeb;
	}

	/**
	 * 预判性添加
	 * 
	 * @param sonName
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized XMLElementBean addSon(String sonName, String key, String value) {
		if (sons == null)
			sons = new ArrayList<XMLElementBean>();
		XMLElementBean xeb = findThisSon(sonName, key, value);
		if (xeb == null) {
			xeb = new XMLElementBean();
			xeb.setNodeName(sonName);
			xeb.putAttribute(key, value);
			sons.add(xeb);

		}

		return xeb;
	}

	private XMLElementBean findThisSon(String sonName, String attr, String attrVal) {
		XMLElementBean aSon = null;
		Iterator<XMLElementBean> it = sons.iterator();
		while (it.hasNext()) {
			aSon = it.next();
			if (aSon.getNodeName().equals(sonName) && aSon.getElementAttributeByName(attr).equals(attrVal))
				return aSon;
		}
		return null;
	}

	public ArrayList<XMLElementBean> getSons() {
		return sons;
	}
}

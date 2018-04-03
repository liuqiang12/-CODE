/*
 * @(#)XMLParseProcess.java 03/06/2012
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.xmlparse.model;

import org.dom4j.Element;

import com.JH.dgather.frame.xmlparse.exception.TagException;

/**
 * <code>BaseTag</code> 基本标签类
 * @author Administrator
 *
 */
public abstract class BaseTag {
	private String tagName;
	private Element element;
	private BaseTag parent;
	
	public BaseTag(String tagName, Element element, BaseTag parentTag) throws TagException {
		this.tagName = tagName;
		this.parent = parentTag;
		if (element == null) {
			throw new TagException(tagName + "标签为null");
		}
		this.element = element;
	}
	
	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}
	
	/**
	 * @return the element
	 */
	public Element getElement() {
		return element;
	}
	
	/**
	 * @return the parent
	 */
	public BaseTag getParent() {
		return parent;
	}
	
	protected abstract void parse() throws TagException;
}

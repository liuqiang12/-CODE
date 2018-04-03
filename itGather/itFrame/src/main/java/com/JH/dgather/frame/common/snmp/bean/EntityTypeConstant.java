package com.JH.dgather.frame.common.snmp.bean;

/**
 * 用于描述设备上物理实体的类型
 * 
 * @author yangDS
 *
 */
public class EntityTypeConstant {
	
	public static final int OTHER = 1;
	
	public static final int UNKNOWN = 2;//UNKNOWN
	
	public static final int CHASSIS = 3;//ROUT CHASSIS
	
	public static final int BACKPLANE = 4;
	
	public static final int CONTAINER = 5; //chassis slot or daughter-card holder
	
	public static final int POWER_SUPPLY = 6;//PWR
	
	public static final int FAN = 7;//FAN
	
	public static final int SENSOR = 8;//SENSOR FOR TEMPERATURE or power fan
	
	public static final int MODEL = 9;//plug-in card or daughter-card
	
	public static final int PORT = 10;//JUST A PHYSICALL PORT
	
	public static final int STACK = 11;//
}

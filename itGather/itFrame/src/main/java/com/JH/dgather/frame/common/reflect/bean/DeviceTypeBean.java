package com.JH.dgather.frame.common.reflect.bean;

public class DeviceTypeBean {
	
	private MainClass mainClass;
	private Type type;
	private Factory factory;
	private String sysObjectId;
	private String model;
	
	/**
	 * 
	 * 对应XMl中的mainclass元素
	 * 
	 * @author yang
	 *
	 */
	public class MainClass {
		private String value;
		private String name;
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	/**
	 * 对应xml中type元素
	 * 
	 * @author yang
	 *
	 */
	public class Type {
		private String value;
		private String name;
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 对应xml中factory元素
	 * 
	 * @author yang
	 *
	 */
	public class Factory {
		private String value;
		private String name;
		private String descript;
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}

		public String getDescript() {
			return descript;
		}

		public void setDescript(String descript) {
			this.descript = descript;
		}
		
	}
	
	public MainClass getMainClass() {
		return mainClass;
	}
	
	public void setMainClass(MainClass mainClass) {
		this.mainClass = mainClass;
	}
	
	/**
	 * 设置mainClass的方法
	 * 
	 * @param name
	 * @param value
	 */
	public void setMainClass(final String name, final String value) {
		this.mainClass = new MainClass() {
			{
				setName(name);
				setValue(value);
			}
		};
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * 设置type的方法
	 * 
	 * @param name
	 * @param value
	 */
	public void setType(final String name, final String value) {
		this.type = new Type() {
			{
				setName(name);
				setValue(value);
			}
		};
	}
	
	public Factory getFactory() {
		return factory;
	}
	
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	
	/**
	 * 设置factory的方法
	 * 
	 * @param name
	 * @param value
	 */
	public void setFactory(final String name, final String value) {
		this.factory = new Factory() {
			{
				setName(name);
				setValue(value);
			}
		};
	}
	
	/**
	 * 得到factory的名称
	 * @return
	 */
	public String getFactoryName() {
		return this.factory.getName();
	}
	
	/**
	 * 得到厂家的值
	 * @return
	 */
	public int getFactoryValue() {
		return Integer.parseInt(this.factory.getValue());
	}
	
	/**
	 * 返回设备的二级类型名称
	 * @return
	 */
	public String getTypeName() {
		return this.type.getName();
	}
	
	/**
	 * 返回设备的二级类型值
	 * @return
	 */
	public int getTypeValue() {
		return Integer.parseInt(this.type.getValue());
	}
	
	/**
	 * 返回设备主类型名称
	 * @return
	 */
	public String getMainClassName() {
		return this.mainClass.getName();
	}
	
	/**
	 * 
	 * 返回设备主类型值
	 * @return
	 */
	public int getMainClassValue() {
		return Integer.parseInt(this.mainClass.getValue());
	}
	
	public String getSysObjectId() {
		return sysObjectId;
	}
	
	public void setSysObjectId(String sysObjectId) {
		this.sysObjectId = sysObjectId;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
}

package utils.strategy.code.model;


/**
 * 类型枚举
 * @author Administrator
 */
@Deprecated
public enum TypeEnum {
	CHAR("String"),
	INT("Integer"),
	FLOAT("Float"),
	DOUBLE("Double"),
	NUMBER("NUMBER_complex");
	private final String value;
	private TypeEnum(final String value) {
		this.value = value;
	}
	public String value() {
		return this.value;
	}
}

package utils.strategy.code.model;
/**
 * 主键的描述
 */
public class TablePrimary {
	/**
	 * 实体类对应的属性
	 */
	private String entitypro;
	/**
	 * 数据库对应的属性
	 */
	private String databasepro;
	/**
	 * 实体类属性类型 
	 */
	private String entityproType;
	
	public String getEntitypro() {
		return entitypro;
	}
	public void setEntitypro(String entitypro) {
		this.entitypro = entitypro;
	}
	public String getDatabasepro() {
		return databasepro;
	}
	public void setDatabasepro(String databasepro) {
		this.databasepro = databasepro;
	}
	public String getEntityproType() {
		return entityproType;
	}
	public void setEntityproType(String entityproType) {
		this.entityproType = entityproType;
	}
}

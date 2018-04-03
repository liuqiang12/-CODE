package utils.strategy.code.model;

import utils.strategy.code.utils.CodeUtils;
import utils.strategy.code.utils.JdbcHelper;
import utils.typeHelper.StringHelper;
/**
 * 有效的实体类
 * @author Administrator
 *
 */
public class EffectModel {
	  /**class类名称**/ 
	  private String className;
	  /**class类名称**/ 
	  private String modelComment;
	  /**model列名称**/ 
	  private String modelName;
	  /**数据类型**/ 
	  private String modelType;
	  /**列的最大长度**/ 
	  private String modelcharmaxLength = "";
	  /**是否可空**/
	  private String modelnullable;
	  /**有效位数**/
	  private String modelprecision;
	  /**小数位数**/
	  private String modelscale;
	  /**是主键吗**/
	  private Boolean isPri = false;
	  /**默认值**/
	  private Object modelcolumnDefault;
	  /**get方法**/
	  private String getMethod;
	  /**set方法**/
	  private String setMethod;
	  
	public String getClassName() {
		return className;
	}
	public void setClassName(String belong_to_table) {
		//根据父类的tableName变换得来
		this.className =  StringHelper.getInstance().reEscapeName(belong_to_table, true);
	}
	public String getModelName() {
		return modelName;
	}
	/**
	 * 是否添加Str
	 * @param columnName
	 * @param appendStr
	 */
	public void setModelName(String columnName,Boolean appendStr) {
		//根据父类的tableName变换得来
		if(appendStr){
			this.modelName =  StringHelper.getInstance().reEscapeName(columnName, true)+"Str";
		}else{
			this.modelName =  StringHelper.getInstance().reEscapeName(columnName, true);
		}
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String columnType,String precision,String scale) {
		this.modelType = JdbcHelper.getInstance().getTypeName(columnType, precision, scale);
	}
	public String getModelcharmaxLength() {
		return modelcharmaxLength;
	}
	public void setModelcharmaxLength(String charmaxLength) {
		this.modelcharmaxLength = charmaxLength;
	}
	public String getModelnullable() {
		return modelnullable;
	}
	public void setModelnullable(String nullable) {
		this.modelnullable = CodeUtils.getInstance().new ToolsConvert().getNullAble(nullable);
	}
	public String getModelprecision() {
		return modelprecision;
	}
	public void setModelprecision(String precision) {
		this.modelprecision = precision;
	}
	public String getModelscale() {
		return modelscale;
	}
	public void setModelscale(String scale) {
		this.modelscale = scale;
	}
	public Object getModelcolumnDefault() {
		return modelcolumnDefault;
	}
	public void setModelcolumnDefault(Object columnDefault) {
		this.modelcolumnDefault = columnDefault;
	}
	public String getModelComment() {
		return modelComment;
	}
	public void setModelComment(String modelComment) {
		this.modelComment = modelComment;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getGetMethod() {
		return getMethod;
	}
	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	public String getSetMethod() {
		return setMethod;
	}
	public void setSetMethod(String setMethod) {
		this.setMethod = setMethod;
	}
	public Boolean getIsPri() {
		return isPri;
	}
	public void setIsPri(Boolean isPri) {
		this.isPri = isPri;
	}
}

package utils.plugins.sort.model;
/**
 * 排序实体类
 * @author Administrator
 * <b>柱状list时先排序的先保存到list中</b>
 */
public class SortModel {
	/**
	 * 排序名称
	 */
	private String name;
	/**
	 * 是否允许null
	 */
	private Boolean isNullHighComparator = Boolean.TRUE;
	/**
	 * 排序类型:orderType【正序-asc，倒序-desc】
	 */
	private String orderType = "asc";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsNullHighComparator() {
		return isNullHighComparator;
	}
	public void setIsNullHighComparator(Boolean isNullHighComparator) {
		this.isNullHighComparator = isNullHighComparator;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}

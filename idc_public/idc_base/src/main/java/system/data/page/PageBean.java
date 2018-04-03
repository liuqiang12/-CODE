package system.data.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>描述<b>：分页实体类
 * <b>分页显示的标准类,基本操作,是先给予-当前页数一共的数据条数-每页显示的条数,<b><br>
 * <b>然后在初始化该类,得到总共页数,和开始序号和结束序号,<b><br>
 * <b>然后数据库分页用到开始序号和结束序号,得到数据集合后赋值给该类的list属性,<b><br>
 *
 * <b>然后把该类发送到jsp页面,进行访问<b><br>
 * @param
 */

/**
 * req.setCharacterEncoding("UTF-8");
 * resp.setContentType("text/html;charset=utf-8"); int currentPage=0; MessageDao
 * md=new MessageDaoImpl(); String
 * currentPageStr=req.getParameter("currentPage"); if(currentPageStr!=null){
 * try{ currentPage=Integer.parseInt(currentPageStr); }catch (Exception e) { } }
 * PageBean<Message> pb=new
 * PageBean<Message>(currentPage,md.getMessagetotalRows(),10); pb.init();
 * pb.setList(md.getMessageListOfPage(pb.getStart(), pb.getEnd()));
 * req.setAttribute("pagebean", pb);
 * req.getRequestDispatcher("header.jsp").forward(req, resp);
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class PageBean<T> {
	public static final int DEFAULT_PAGE_SIZE = 15;

	protected int pageNo = 0; // 当前页, 默认为第1页
	protected int pageSize = DEFAULT_PAGE_SIZE; // 每页记录数
	protected long totalRecord = -1; // 总记录数, 默认为-1, 表示需要查询
	protected int totalPage = -1; // 总页数, 默认为-1, 表示需要计算

	protected List<T> items; // 当前页记录List形式

	public Map<String, Object> params = new HashMap<String, Object>();// 设置页面传递的查询参数

	public Map<String, Object> extendparams = new HashMap<String, Object>();// 额外扩展参数

	private int firstResultNo;
	private int maxResultNo;


/*------------getter    and    setter----------*/
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		computeTotalPage();



	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		computeTotalPage();
	}

	protected void computeTotalPage() {
		if (getPageSize() > 0 && getTotalRecord() > -1) {
			this.totalPage = (int) (getTotalRecord() % getPageSize() == 0 ? getTotalRecord()
					/ getPageSize()
					: getTotalRecord() / getPageSize() + 1);
			this.firstResultNo = (getPageNo() - 1) * getPageSize() + 1;
			this.maxResultNo = firstResultNo + getPageSize();
		}else{
			this.firstResultNo = 1;
			this.maxResultNo = DEFAULT_PAGE_SIZE;
		}
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder().append("Page [pageNo=")
				.append(pageNo).append(", pageSize=").append(pageSize)
				.append(", totalRecord=")
				.append(totalRecord < 0 ? "null" : totalRecord)
				.append(", totalPage=")
				.append(totalPage < 0 ? "null" : totalPage)
				.append(", curPageObjects=")
				.append(items == null ? "null" : items.size()).append("]");
		return builder.toString();
	}

	public Map<String, Object> getExtendparams() {
		return extendparams;
	}

	public void setExtendparams(Map<String, Object> extendparams) {
		this.extendparams = extendparams;
	}

	public int getFirstResultNo() {
		return firstResultNo;
	}

	public void setFirstResultNo(int firstResultNo) {
		this.firstResultNo = firstResultNo;
	}

	public int getMaxResultNo() {
		return maxResultNo;
	}

	public void setMaxResultNo(int maxResultNo) {
		this.maxResultNo = maxResultNo;
	}
}

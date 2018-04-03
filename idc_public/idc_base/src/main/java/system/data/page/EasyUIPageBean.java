package system.data.page;

import java.io.Serializable;

/**
 * Created by mylove on 2016/12/6.
 */
public class EasyUIPageBean extends PageBean implements Serializable {

    protected int page = -1; // 当前页, 默认为第1页
    protected int rows = 20; // 每页记录数
//    protected long total = 0; // 每页记录数
    public int getPage() {
        return page;
    }
	public EasyUIPageBean(){
	}
	public EasyUIPageBean(int page,int rows){
		setPage(page);
		setRows(rows);
		
	}
    public void setPage(int page) {
        this.page = page;
        this.setPageNo(page);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        this.setPageSize(rows);
    }

//    public long getTotal() {
//        return this.getTotalRecord();
//    }
//
//    public void setTotal(long total) {
//        this.setTotalRecord(total);
//    }
}

package system.data.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2016/12/6.
 */
public class EasyUIData implements Serializable{
    protected long total = 0;

    protected List rows = new ArrayList();

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
    public EasyUIData() {

    }
    public EasyUIData(EasyUIPageBean pageBean) {
        this.total = pageBean.getTotalRecord();
        this.rows = pageBean.getItems();
    }
}

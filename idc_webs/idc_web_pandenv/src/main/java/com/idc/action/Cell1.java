package com.idc.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mylove on 2017/12/25.
 */
public class Cell1 {
    int cellnum = 0;
    Object data;

    Cell1(int cellnum, Object data) {
        this.cellnum = cellnum;
        this.data = data;
    }

    public int getCellnum() {
        return cellnum;
    }

    public void setCellnum(int cellnum) {
        this.cellnum = cellnum;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

package com.idc.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2017/12/25.
 */
public class Row1 {
    int rownum = 0;
    List<Cell1> cells = new ArrayList<>();

    Row1(int rownum) {
        this.rownum = rownum;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public List<Cell1> getCells() {
        return cells;
    }

    public void setCells(List<Cell1> cells) {
        this.cells = cells;
    }

    public void addCell(Cell1 cells) {
        this.cells.add(cells);
    }

    public void addCell(int index, Cell1 cells) {
        this.cells.add(index, cells);
    }

}

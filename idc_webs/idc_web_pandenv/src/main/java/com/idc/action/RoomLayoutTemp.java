package com.idc.action;

import com.idc.model.IdcRack;
import utils.typeHelper.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylove on 2017/12/25.
 */
public class RoomLayoutTemp {
    //计算机架的位置 X  Y      有模块
    public List<Row1> updateRackXAndY(List<IdcRack> list) {
        List<Row1> rows = new ArrayList<>();
        updateRackXAndY(rows, list);
        return rows;
    }

    public void updateRackXAndY(List<Row1> rows, List<IdcRack> list) {
        List<IdcRack> odfList = new ArrayList<>();
        List<IdcRack> pdfList = new ArrayList<>();
        String[] split;
        String last;
        char c;
        int row;
        for (IdcRack idcRack : list) {
            String name = idcRack.getCodeView();
            if (name.indexOf("PX") > -1) {//配线柜
                split = name.split("-");
                last = split[split.length - 1];
                c = last.charAt(0);
                row = c - 65;//获取行号
                idcRack.setNameType("PX");
                addRow(rows, row, 2, idcRack);
            } else if (name.indexOf("WL") > -1) {//网络
                split = name.split("-");
                last = split[split.length - 1];
                c = last.charAt(0);
                row = c - 65;//获取行号
                idcRack.setNameType("WL");
                addRow(rows, row, 1, idcRack);
            } else if (name.indexOf("ODF") > -1) {//最后处理
                idcRack.setNameType("ODF");
                odfList.add(idcRack);
            } else if (name.indexOf("PDF") > -1) {//最后处理
                idcRack.setNameType("PDF");
                pdfList.add(idcRack);
            } else {//客户柜
                split = name.split("_");
                last = split[split.length - 1];
                c = last.charAt(0);
                row = c - 65;//获取行号
                idcRack.setNameType("KH");
                int cell = Integer.parseInt(last.substring(1));
                addRow(rows, row, cell + 2, idcRack);
            }
        }
        //处理ODF架排列位置
        updateOdfRackXAndY(rows, odfList);
        //处理PDF架排列位置
        updatePdfRackXAndY(rows, pdfList);
    }

    //处理PDF架排列位置
    public void updatePdfRackXAndY(List<Row1> rows, List<IdcRack> list) {
        for (IdcRack idcRack : list) {
            String name = idcRack.getCodeView();
            String[] split = name.split("_");
            String last = split[split.length - 1];
            String type = split[split.length - 2];//倒数第2个如果是PX或WL 就是配线柜和网络柜 否则是客户柜
            String typelast = split[split.length - 3];
            if ("PDF".equals(type)) {//不是两头柜   直接放在首列
                char c = last.charAt(0);
                int rownum = c - 65;//获取行号
                for (int i = 0; i < rows.size(); i++) {
                    if (rows.get(i).rownum == rownum) {
                        addRow(rows, rownum, 0, idcRack);
                    }
                }
            } else if ("PDF".equals(typelast)) {//两头柜  视两头柜个数平均放置在首尾
                char c = type.charAt(0); //A
                int rownum = c - 65;//获取行号 0
                char c1 = last.charAt(0); //a  b
                int value = c1 - 96; //1
                String nameStr = name.substring(0, name.length() - 2);//获取PDF架共有字符串如：B04-111-PDF-A-a====》B04-111-PDF-A
                //查看当前列中此类机架个数
                for (int i = 0; i < rows.size(); i++) {//a b        c d
                    int rackNum = 0;
                    for (IdcRack param : list) {
                        if (param.getName().indexOf(nameStr) > -1) {
                            rackNum += 1;
                        }
                    }
                    if (rows.get(i).rownum == rownum) {
                        if (rackNum % 2 == 0) {//2\4\6\8...   前后平均放置
                            if (rackNum / 2 >= value) {//放置在前面
                                if (value == 1) {//位a直接放在首尾
                                    addRow(rows, rownum, 0, idcRack);
                                } else {//后面的架所在列依次加1
                                    for (int v = 0; v < rows.get(i).getCells().size(); v++) {
                                        if (rows.get(i).getCells().get(v).cellnum != 0) {
                                            rows.get(i).getCells().get(v).setCellnum(rows.get(i).getCells().get(v).cellnum + 1);
                                        }
                                    }
                                    addRow(rows, rownum, value - 1, idcRack);
                                }
                            } else {//放置在后面
                                addRow(rows, rownum, 22 + value, idcRack);
                            }
                        } else {//1\3\5\7\9... 前面比后面多一个
                            if (rackNum / 2 >= value - 1) {//放置在前面
                                if (value == 1) {//位a直接放在首尾
                                    addRow(rows, rownum, 0, idcRack);
                                } else {//后面的架所在列依次加1
                                    for (int v = 0; v < rows.get(i).getCells().size(); v++) {
                                        if (rows.get(i).getCells().get(v).cellnum != 0) {
                                            rows.get(i).getCells().get(v).setCellnum(rows.get(i).getCells().get(v).cellnum + 1);
                                        }
                                    }
                                    addRow(rows, rownum, value - 1, idcRack);
                                }
                            } else {//放置在后面
                                addRow(rows, rownum, 19 + value, idcRack);
                            }
                        }
                    }
                }
            }
        }
    }

    //处理ODF架排列位置
    public void updateOdfRackXAndY(List<Row1> rows, List<IdcRack> list) {
        for (IdcRack idcRack : list) {
            String name = idcRack.getCodeView();
            String[] split = name.split("_");
            String last = split[split.length - 1];//倒数第1个
            String type = split[split.length - 2];//倒数第2个
            String typelast = split[split.length - 3];//倒数第3个
            if (StringUtil.isNumeric(typelast)) {//判断倒数第3个是否是数字   若为数字   则表示为普通ODF柜  直接放在最后面  单独成行
                char c = last.charAt(0);
                int row = c - 65;
                if (row == 0) {
                    int cell = Integer.parseInt(last.substring(1));
                    addRow(rows, 9, cell + 2, idcRack);
                } else {
                    int cell = Integer.parseInt(last.substring(1));
                    addRow(rows, 10, cell + 2, idcRack);
                }
            } else {//先判断是否有网络柜  若有则不处理  若没有则将ODF柜放在网络柜的位置
                char c = typelast.charAt(0);
                int row = c - 65;
                char c1 = last.charAt(0);
                int row1 = c1 - 97;
                for (int i = 0; i < rows.size(); i++) {
                    //查看当前行是否存在网络柜
                    boolean flag = false;
                    for (int j = 0; j < rows.get(i).getCells().size(); j++) {
                        IdcRack param = (IdcRack) rows.get(i).getCells().get(j).data;
                        if (param.getName().contains("WL")) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {//存在网络架    暂时不处理
                    } else {//不存在网络架
                        if (row == i) {
                            if (row1 == 0) {
                                for (int v = 0; v < rows.get(i).getCells().size(); v++) {
                                    if (rows.get(i).getCells().get(v).cellnum >= 3) {
                                        rows.get(i).getCells().get(v).setCellnum(rows.get(i).getCells().get(v).cellnum + 1);
                                    }
                                }
                                addRow(rows, i, 3, idcRack);
                            } else {
                                for (int v = 0; v < rows.get(i).getCells().size(); v++) {
                                    if (rows.get(i).getCells().get(v).cellnum >= 22) {
                                        rows.get(i).getCells().get(v).setCellnum(rows.get(i).getCells().get(v).cellnum + 1);
                                    }
                                }
                                addRow(rows, i, 22, idcRack);
                            }
                        }
                    }
                }
            }
        }
    }

    public void addRow(List<Row1> rows, int row, int cell, IdcRack idcRack) {
        int index = 0;
        boolean flag = false;//没有对应行
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).rownum == row) {
                flag = true;
                index = i;
            }
        }
        if (flag) {//当前机架所在行已有机架，直接添加机架到对应前行
            rows.get(index).addCell(new Cell1(cell, idcRack));
        } else {//生成新行  并放入机架
            Row1 r = new Row1(row);
            r.addCell(new Cell1(cell, idcRack));
            rows.add(r);
        }
    }



}

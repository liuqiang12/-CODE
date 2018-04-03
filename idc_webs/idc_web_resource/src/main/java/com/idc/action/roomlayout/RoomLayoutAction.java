package com.idc.action.roomlayout;

import com.alibaba.fastjson.annotation.JSONField;
import com.idc.model.IdcRack;
import com.idc.model.ZbMachineroom;
import com.idc.service.IdcRackService;
import com.idc.service.ZbMachineroomService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/5/24.
 */
@Controller
public class RoomLayoutAction {
    protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    @Autowired
    private ZbMachineroomService zbMachineroomService;
    @Autowired
    private IdcRackService idcRackService;

    @RequestMapping("/roomlayout/{roomid}")
    public String index(@PathVariable int roomid, ModelMap map) {
        ZbMachineroom modelById = zbMachineroomService.getModelById(roomid);
        //获取机架统计信息   只统计客户架\网络柜 总数、占用数
        Map<String, Object> racks = zbMachineroomService.getRoomStatistics(roomid);
        JSONObject jsonObject = JSONObject.fromObject(modelById);
        IdcRack idcRack = new IdcRack();
        idcRack.setRoomid(roomid + "");
        //获取所有机架
        List<IdcRack> idcRacks = idcRackService.queryListByObject(idcRack);
        //获取当前机内所有模块
        List<Map<String, Object>> moduleList = zbMachineroomService.queryAllModuleByRoomId(roomid);
        RoomLayoutTemp roomTemp = new RoomLayoutTemp();
        //初始化机架位置
        List<Row1> resultList = roomTemp.updateRackXAndY(idcRacks);
        map.put("resultList", com.alibaba.fastjson.JSONObject.toJSON(resultList));
        //初始化模块位置
        List<Map<String, Object>> resultModuleList = new ArrayList<>();
        for (int i = 0; i < moduleList.size(); i++) {//遍历模块
            Map<String, Object> resultMap = new HashedMap();
            int r = 0, c = 0, w = 0, h = 0, sty = i % 2;
            String moduleName = moduleList.get(i).get("NAME").toString();
            for (int j = 0; j < resultList.size(); j++) {//遍历行
                Row1 row1 = resultList.get(j);
                int rownum = row1.rownum;
                for (int k = 0; k < row1.getCells().size(); k++) {//遍历列
                    IdcRack param = (IdcRack) row1.getCells().get(k).getData();
                    if (param.getModulename() != null && param.getModulename().equals(moduleName)) {
                        r = r > rownum ? r : rownum;
                        w = w > row1.getCells().size() + 3 ? w : row1.getCells().size() + 3;
                        h = 2;
                        break;
                    }
                }
            }
            resultMap.put("r", r);
            resultMap.put("c", c);
            resultMap.put("w", w);
            resultMap.put("h", h);
            resultMap.put("n", moduleName);
            resultMap.put("sty", sty);
            resultModuleList.add(resultMap);
        }

        map.put("resultModuleList", com.alibaba.fastjson.JSONObject.toJSON(resultModuleList));
        map.put("idcRacks", com.alibaba.fastjson.JSONObject.toJSON(idcRacks));
        map.put("room", jsonObject);
        map.put("roomid", modelById.getId());
        map.put("rackStatistics", racks);
        return "roomlayout/index";
    }

    @RequestMapping("/roomlayout/select/{roomid}/{distriType}")
    public String select(@PathVariable int roomid, @PathVariable int distriType, ModelMap map) {
        ZbMachineroom modelById = zbMachineroomService.getModelById(roomid);
        JSONObject jsonObject = JSONObject.fromObject(modelById);
        IdcRack idcRack = new IdcRack();
        idcRack.setRoomid(roomid + "");
        List<IdcRack> idcRacks = idcRackService.queryListByObject(idcRack);
        Plan plan = new Plan();
        plan.setIdcRacks(idcRacks);
        plan.planSort();
        map.put("plan", com.alibaba.fastjson.JSONObject.toJSON(plan));
        map.put("room", jsonObject);
        map.put("action", "select");
        map.put("roomid", modelById.getId());
        map.put("distriType", distriType);//分配类型
        return "roomlayout/select";
    }
    @RequestMapping("/roomlayout1/{roomid}")
    public String index1(@PathVariable int roomid, ModelMap map) {
        ZbMachineroom modelById = zbMachineroomService.getModelById(roomid);
        JSONObject jsonObject = JSONObject.fromObject(modelById);
        map.put("room", jsonObject);
        map.put("roomid", modelById.getId());
        return "roomlayout/index1";
    }

    /***
     * 获取机房布局的数据
     *
     * @param roomid
     * @return
     */
    @RequestMapping("/roomlayout/getNodes/{roomid}")
    @ResponseBody
    public List<Map<String, Object>> getLayout(@PathVariable long roomid) {
        IdcRack idcRack = new IdcRack();
        idcRack.setRoomid(roomid + "");
        List<IdcRack> idcRacks = idcRackService.queryListByObject(idcRack);
        int x = 1, y = 1;
        List<Map<String, Object>> list = new ArrayList<>();
        for (IdcRack rack : idcRacks) {
//          //后面几句真实数据需要转换为坐标系X,Y
            rack.setX(rack.getX().subtract(new BigDecimal("40")).divide(new BigDecimal("40"), 2));
            rack.setY(rack.getY().subtract(new BigDecimal("40")).divide(new BigDecimal("40"), 2));
            y++;
            System.out.println(rack.getId() + "::" + rack.getX() + ":" + rack.getY());
            JSONObject j = JSONObject.fromObject(rack);
            j.put("status", rack.getStatus());
            list.add(j);
        }
//        JSONArray jsonArray = JSONArray.fromObject(idcRacks);
//        list.addAll(jsonArray);
        return list;
    }
}

class Plan {
    protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    static final int rowMax = 50;
    static final int cellMax = 50;
    static final int rowStart = 1;
    static final int cellStart = 1;
    static final int rowEnd = rowMax - 1;
    static final int cellEnd = cellMax - 1;
    static final int PDFINDEX = 0;
    static final int WLINDEX = PDFINDEX + 1;
    static final int PXINDEX = WLINDEX + 1;
    static final int OTHERINDEX = PXINDEX + 1;
    private List<Row> rows = new ArrayList<>();

    public List<Row> getRows() {
        return rows;
    }

    @JSONField(serialize = false)
    private List<IdcRack> idcRacks = new ArrayList<>();

    public List<IdcRack> getIdcRacks() {
        return idcRacks;
    }

    public void setIdcRacks(List<IdcRack> idcRacks) {
        this.idcRacks = idcRacks;
    }

    public Plan() {
        init();
    }

    public void init() {
        for (int rowNum = 0; rowNum < rowMax; rowNum++) {
            Row row = new Row();
            for (int cellNum = 0; cellNum < cellMax; cellNum++) {
                Cell c = new Cell();
                c.setCellnum(cellNum);
                IdcRack idc = new IdcRack();
                c.setData(idc);
                row.addCell(c);
            }
            row.setRownum(rowNum);
            rows.add(row);
        }
    }

    public Object getData(int row, int cell) {
        Cell cell1 = rows.get(row).getCells().get(cell);
        return cell1.getData();
    }

    /***
     * 在制定位置添加机架
     *
     * @param row
     * @param cell
     * @param rack
     */
    public void addRack(int row, int cell, IdcRack rack) {
        rack.setX(new BigDecimal(row));
        rack.setY(new BigDecimal(cell));
        if (row >= rowEnd - 1) {
            row = rowEnd - 1;
        }
        if (cell >= cellEnd ) {
            cell = cellEnd - 1;
        }
        Object data = getData(row, cell);
        if (data == null || ((IdcRack) data).getId() == null) {
            rows.get(row).getCells().get(cell).setData(rack);
        } else {
            if (cell >= cellEnd - 1) {
                cell = 1;
                row += 1;
            } else {
                cell += 1;
            }
            addRack(row, cell, rack);
        }
    }

    /***
     * 坐标排序
     */
    public void planSort() {
        for (IdcRack idcRack : idcRacks) {
            try {
                String name = idcRack.getCodeView();
                String[] split;
                String last;
                char c;
                int row;
                if (name.indexOf("WL") > -1) {//网络柜
                    split = name.split("-");
                    last = split[split.length - 1];
                    c = last.charAt(0);
                    row = c - 64;//获取行号
                    idcRack.setNameType("WL");
                    addRack(row, WLINDEX, idcRack);
                } else if (name.indexOf("PX") > -1) {//配线柜
                    split = name.split("-");
                    last = split[split.length - 1];
                    c = last.charAt(0);
                    row = c - 64;//获取行号
                    idcRack.setNameType("PX");
                    addRack(row, PXINDEX, idcRack);
                } else if (name.indexOf("PDF") > -1) {//PDF柜
                    split = name.split("_");
                    last = split[split.length - 1];
                    String typelast = split[split.length - 3];
                    idcRack.setNameType("PDF");
                    if ("PDF".equals(typelast)) {//两头动力柜
                        String rowstr = split[split.length - 2];
                        String cellstr = split[split.length - 1];
                        row = rowstr.charAt(0) - 64;
                        addRack(row, "a".equals(cellstr.toLowerCase()) ? PDFINDEX : cellEnd, idcRack);
                    } else {
                        c = last.charAt(0);
                        row = c - 64;//获取行号
                        addRack(row, PDFINDEX, idcRack);
                    }
                } else if (name.indexOf("ODF") > -1) {//ODF柜
                    idcRack.setNameType("ODF");
                } else {//客户柜
                    split = name.split("_");
                    last = split[split.length - 1];
                    c = last.charAt(0);
                    row = c - 64;
                    idcRack.setNameType("KH");
                    CharSequence charSequence = last.subSequence(1, last.length());
                    Integer cell = Integer.valueOf(String.valueOf(charSequence));
                    addRack(row, OTHERINDEX + cell, idcRack);
                }
            } catch (Exception e) {
                logger.error("添加机架错误:" + idcRack.getName(), e);
            }
        }
    }
}

class Row {
    int rownum = 0;
    List<Cell> cells = new ArrayList<>();
    Map<String, Cell> orderCell = new HashMap<>();

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void addCell(Cell cells) {
        this.cells.add(cells);
    }
}

class Cell {
    int cellnum = 0;
    Object data;

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
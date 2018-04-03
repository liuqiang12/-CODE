package com.idc.action.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.idc.action.ImportExcelUtils;
import com.idc.model.*;
import com.idc.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import utils.plugins.excel.ExcelHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/6.
 */
@Controller
@RequestMapping("idcmcb")
public class IdcMcbAction extends BaseController{

    @Autowired
    private IdcMcbService idcMcbService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private IdcRackService idcRackService;

    /**
     * 获取MCB列表
     * @param page
     * @param name pwr_mcbno
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, String name,Long rackId) {
        EasyUIData easyUIData = new EasyUIData();
        IdcMcb idcMcb= new IdcMcb();
        idcMcb.setName(name);
        idcMcb.setPwrInstalledrackId(rackId);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if(page==null||page.getPage()<0){//所有
            list= idcMcbService.queryListByObjectMap(idcMcb);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        }else{
            list = idcMcbService.queryListPageMap(page, idcMcb);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }
    @RequestMapping("/preAddIdcmcbInfo.do")
    public String preAddIdcMcbInfo(Long pdfRackId,ModelMap map){
        IdcRack pdfRack = idcRackService.getModelById(pdfRackId.intValue());
        map.put("pdfRackId",pdfRackId);
        map.put("pdfRack", pdfRack);
        return "idcmcb/info";
    }
    /**
     * 新增或修改mcb
     * @param idcMcb
     * @return
     */
    @RequestMapping("/addIdcmcbInfo.do")
    @ResponseBody
    public ExecuteResult addIdcmcbInfo(IdcMcb idcMcb,Long mcbId){
        ExecuteResult executeResult = new ExecuteResult();
        if(idcMcb!=null){
            try {
                if(mcbId != null && mcbId != 0){
                    idcMcb.setId(mcbId);
                    //修改基本设备信息
                    idcMcbService.updateByObject(idcMcb);
                    executeResult.setState(true);
                    executeResult.setMsg("修改MCB成功");
                }else{
                    //新增设备
                    if (idcMcb.getPwrServicerackId() != null) {
                        idcMcb.setPwrPwrstatus(60);
                    } else {
                        idcMcb.setPwrPwrstatus(20);
                    }
                    idcMcbService.insert(idcMcb);
                    executeResult.setState(true);
                    executeResult.setMsg("新增MCB成功");
                }
                //添加日志
            }catch (Exception e){
                e.printStackTrace();
                logger.error("保存MCB失败:",e);
                executeResult.setState(false);
                executeResult.setMsg("保存MCB失败");
            }
        }else{
            executeResult.setState(false);
            executeResult.setMsg("新增MCB失败,没有MCB信息");
            model.addAttribute("msg", "新增失败,没有MCB信息");
        }
        return executeResult;
    }

    /**
     * 根据主键加载MCB信息
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/idcmcbDetails.do")
    public String getIdcmcbDetails(Long id, ModelMap map,String buttonType){
        logger.debug("==================根据主键加载MCB信息================");
        IdcMcb idcMcb = null;
        IdcRack pdfRack = null;
        IdcRack serverRack = null;
        try {
            idcMcb = idcMcbService.getModelById(id);
            pdfRack = idcRackService.getModelById(idcMcb.getPwrInstalledrackId().intValue());
            if (idcMcb.getPwrServicerackId() != null) {
                serverRack = idcRackService.getModelById(idcMcb.getPwrServicerackId().intValue());
            } else {
                serverRack = new IdcRack();
            }
        }catch (Exception e){
            e.printStackTrace();
            idcMcb=new IdcMcb();
        }
        //添加日志
        map.addAttribute("idcMcb",idcMcb);
        map.addAttribute("id",id);
        map.addAttribute("pdfRack", pdfRack);
        map.addAttribute("serverRack", serverRack);
        map.addAttribute("pdfRackId",idcMcb.getPwrInstalledrackId());
        map.addAttribute("buttonType",buttonType);
        return "idcmcb/info";
    }

    /**
     * 删除MCB
     * @param ids
     * @return
     */
    @RequestMapping("/deleteIdcmcbByIds.do")
    @ResponseBody
    public ExecuteResult deleteIdcmcbByIds(@RequestParam(value="ids") String ids){
        logger.debug("==================根据id删除设备================");
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> mcbIdList = Arrays.asList(ids.split(","));
            //删除MCB信息
            idcMcbService.deleteByList(mcbIdList);
            executeResult.setState(true);
            executeResult.setMsg("删除MCB成功");
            //添加日志
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除MCB失败:",e);
            executeResult.setState(false);
            executeResult.setMsg("删除MCB失败");
        }
        return executeResult;
    }
    /**
     * 导出MCB信息
     * @return
     */
    @RequestMapping("/exportDeviceData")
    @ResponseBody
    public ExecuteResult exportResourceData() {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "MCB信息";
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("ID","MCB标识");headMap.put("NAME","MCB名称");
        headMap.put("PWRPWRSTATUS","MCB使用状态");headMap.put("PWRMCBNO","MCBNO");headMap.put("INSTALLNAME","PDF机架");
        headMap.put("SYSDESCR","MCB描述");headMap.put("SERVICENAME","客户机架");
        OutputStream outXlsx = null;
        List<Map<String,Object>> list = idcMcbService.getAllMcbInfos();
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename=mcb.xlsx");
            //response.setCharacterEncoding("utf-8");
            outXlsx = response.getOutputStream();
            ExcelHelper.exportExcelX(title,headMap, JSONArray.parseArray(JSON.toJSONString(list)),null,0,outXlsx);
            outXlsx.close();
            executeResult.setState(true);
            executeResult.setMsg("数据导出成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("数据导出失败");
        }
        return executeResult;
    }
    @RequestMapping("/importDeviceData")
    @ResponseBody
    public ExecuteResult importResourceData(HttpServletRequest request, HttpServletResponse response) {
        ExecuteResult executeResult = new ExecuteResult();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartRequest.getFile("uploadFile");
        Map<String,Object> map= new HashMap<String,Object>();
        if(file.isEmpty()){
            map.put( "result", "error");
            map.put( "msg", "上传文件不能为空" );
        } else{
            String originalFilename=file.getOriginalFilename();
            try {
                List<List<Object>> list = ImportExcelUtils.getDataListByExcel(file.getInputStream(),originalFilename );
                List<IdcMcb> mcbList = new ArrayList<IdcMcb>();
                for (int i =0;i<list.size();i++){
                    List<Object> obj = list.get(i);
                    IdcMcb idcMcb = new IdcMcb();
                    //MCB信息
                    idcMcb.setName(String.valueOf(obj.get(0)));
                    idcMcb.setPwrPwrstatus(Integer.parseInt(String.valueOf(obj.get(1))));
                    idcMcb.setPwrMcbno(String.valueOf(obj.get(2)));
                    idcMcb.setPwrInstalledrackId(Long.parseLong(String.valueOf(obj.get(3))));
                    idcMcb.setPwrServicerackId(Long.parseLong(String.valueOf(obj.get(4))));
                    idcMcb.setSysdescr(String.valueOf(obj.get(5)));
                    mcbList.add(idcMcb);
                }
                idcMcbService.insertList(mcbList);
                executeResult.setState(true);
                executeResult.setMsg("导入成功");
            } catch (Exception e) {
                e.printStackTrace();
                executeResult.setState(false);
                executeResult.setMsg("导入失败");
            }
        }
        return executeResult;
    }
    //pdf架对应的MCB列表
    @RequestMapping("/getMcbList.do")
    public String getMcbListByRackId(ModelMap map,Long rackId){
        map.addAttribute("rackId",rackId);
        return "idcmcb/mcbList";
    }

    /*资源分配-根据机架Id获取MCB*/
    @RequestMapping("/distributionIdcMcbList")
    @ResponseBody
    public List<Map<String, Object>> distributionIdcMcbList(Long rackId, String name) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (rackId != null && rackId != 0l) {
            IdcMcb idcMcb = new IdcMcb();
            idcMcb.setPwrInstalledrackId(rackId);
            idcMcb.setPwrPwrstatus(20);
            idcMcb.setName(name);
            list = idcMcbService.queryListByObjectMap(idcMcb);
        }
        return list;
    }

    //绑定服务架
    @RequestMapping("/bindServiceRackById")
    @ResponseBody
    public ExecuteResult bindServiceRackById(String ids, Long serviceRackId) {
        ExecuteResult executeResult = new ExecuteResult();
        if (ids != null && !"".equals(ids) && serviceRackId != null) {
            List<String> list = Arrays.asList(ids.split(","));
            Map<String, Object> map = new HashedMap();
            map.put("list", list);
            map.put("serviceRackId", serviceRackId);
            try {
                idcMcbService.bindServiceRackById(map);
                executeResult.setState(true);
                executeResult.setMsg("绑定服务架成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("绑定服务架失败");
                e.printStackTrace();
            }
        } else {
            executeResult.setState(false);
            executeResult.setMsg("数据不全ids=" + ids + ",serviceRackId=" + serviceRackId);
        }
        return executeResult;
    }
}

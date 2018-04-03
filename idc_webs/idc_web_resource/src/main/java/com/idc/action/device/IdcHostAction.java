package com.idc.action.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.idc.action.ImportExcelUtils;
import com.idc.model.*;
import com.idc.service.IdcDeviceService;
import com.idc.service.IdcHostService;
import com.idc.service.SysOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import system.data.supper.action.BaseController;
import utils.plugins.excel.ExcelHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/6.
 */
@Controller
@RequestMapping("/idchost")
public class IdcHostAction extends BaseController {

    @Autowired
    private IdcDeviceService idcDeviceService;
    @Autowired
    private IdcHostService idcHostService;
    @Autowired
    private SysOperateLogService sysOperateLogService;

    /**
     * 导出主机设备信息
     * @return
     */
    @RequestMapping("/exportDeviceData")
    @ResponseBody
    public ExecuteResult exportResourceData(String searchStr) {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "主机设备信息";
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        //设备基本信息
        headMap.put("IDEVICEID", "设备标识");
        headMap.put("INAME", "设备名称");
        headMap.put("ICODE", "设备编码");//headMap.put("IBUSINESSTYPEID","设备类型");
        headMap.put("IRACKID","所属机架");headMap.put("ISTATUS","设备状态");headMap.put("ITICKETID","工单编号");headMap.put("IMODEL","规格");
        headMap.put("IVENDOR","厂商");headMap.put("IOWNER","联系人");headMap.put("IUINSTALL","安装位置(U)");headMap.put("IUHEIGHT","设备高度(U)");
        headMap.put("IOWNERTYPE", "产权性质");
        headMap.put("IPWRPOWERTYPE", "电源类型");
        headMap.put("IUPLINEDATE", "上架日期");
        headMap.put("IDESCRIPTION","设备描述");headMap.put("IPOWER","设备功耗");headMap.put("IPHONE","联系人电话");headMap.put("IDEVICECLASS","设备类别");
        //主机设备信息
        headMap.put("HDEVICETYPE", "主机设备类型");
        headMap.put("HIPADDRESS", "IP");
        headMap.put("HOS", "操作系统");
        headMap.put("HCPUSIZE", "CPU描述");
        headMap.put("HMEMSIZE", "内存大小(G)");
        headMap.put("HDISKSIZE","硬盘大小(G)");headMap.put("HUSERID","操作用户");headMap.put("HSYSDESCR","主机设备描述");
        OutputStream outXlsx = null;
        Map map = new HashMap();
        map.put("tableName",IdcHost.tableName);
        map.put("deviceClass",2);
        if(searchStr!=null&&!"".equals(searchStr)){
            String[] strArr = searchStr.split("_");
            map.put("searchType",strArr[0]);
            map.put("searchId",strArr[1]);
        }
        List<Map<String,Object>> list = idcDeviceService.getAllDeviceInfos(map);
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename=idcHost.xlsx");
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
                idcHostService.importIdcHostByExcel(list);
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
}

package com.idc.action.linkoutport;

import com.idc.model.ExecuteResult;
import com.idc.model.IdcLinkOutport;
import com.idc.model.IdcTransmissionForm;
import com.idc.service.AssetAttachmentinfoService;
import com.idc.service.IdcLinkOutportService;
import com.idc.service.IdcTransmissionFormService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/28.
 * 链路信息表
 */
@Controller
@RequestMapping("/idcLinkOutport")
public class IdcLinkOutportAction extends BaseController {
    @Autowired
    private IdcLinkOutportService idcLinkOutportService;
    @Autowired
    private AssetAttachmentinfoService assetAttachmentinfoService;
    @Autowired
    private IdcTransmissionFormService idcTransmissionFormService;

    @RequestMapping("/index.do")
    public String index() {
        return "linkoutport/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, String name) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("name", name);
        List<Map<String, Object>> list = new ArrayList<>();
        if (page == null || page.getPage() < 0) {//查询所有
            list = idcLinkOutportService.queryLinkOutportList(mapQ);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = idcLinkOutportService.queryLinkOutportListPage(page, mapQ);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    //新增获取修改链路信息 ---- 打开info页面
    @RequestMapping("/getIdcLinkOutportInfo")
    public String getIdcLinkOutportInfo(ModelMap map, Long id, String buttonType, String attachname) {
        IdcLinkOutport linkOutport = new IdcLinkOutport();
        IdcTransmissionForm idcTransmissionForm = new IdcTransmissionForm();
        if (id != null) {//修改
            linkOutport = idcLinkOutportService.getModelById(id);
            if (linkOutport != null && linkOutport.getIdcTransmissionId() != null) {
                idcTransmissionForm = idcTransmissionFormService.getModelById(linkOutport.getIdcTransmissionId());
            }
        }
        try {
            attachname = URLDecoder.decode(attachname, "utf-8");
        } catch (Exception e) {

        }
        map.put("id", id);
        map.put("linkOutport", linkOutport);
        map.put("idcTransmissionForm", idcTransmissionForm);
        map.put("buttonType", buttonType);
        map.put("attachname", com.alibaba.fastjson.JSONObject.toJSON(attachname));
        return "/linkoutport/info";
    }

    //新增或修改链路
    @RequestMapping("/save")
    @ResponseBody
    public ExecuteResult saveIdcLinkOutport(IdcLinkOutport idcLinkOutport, Long id, HttpServletRequest request) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            assetAttachmentinfoService.saveOrUpdateLinkFrom(idcLinkOutport, id, request);
            //idcLinkOutportService.saveOrUpdateLink(idcLinkOutport,id,request);
            executeResult.setState(true);
            executeResult.setMsg("保存链路信息成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("保存链路信息失败");
            e.printStackTrace();
        }
        return executeResult;
    }

    //删除链路信息  一并删除附件
    @RequestMapping("/deleteLinkOutport")
    @ResponseBody
    public ExecuteResult deleteLinkOutport(String linkIds, String transIds) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> linkList = Arrays.asList(linkIds.split(","));
            List<String> transList = Arrays.asList(transIds.split(","));
            //idcLinkOutportService.deleteByList(list);
            assetAttachmentinfoService.deleteLinkInfoAndAchment(linkList, transList);
            executeResult.setState(true);
            executeResult.setMsg("删除链路信息成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("删除链路信息失败");
            e.printStackTrace();
        }
        return executeResult;
    }

    //下载附件
    @RequestMapping("/downLoadFileByTransmissionId")
    public void downLoadFileByTransmissionId(HttpServletResponse response, String idcTransmissionId) {
        try {
            assetAttachmentinfoService.downLoadFileByTransmissionId(response, idcTransmissionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

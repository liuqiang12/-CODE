package com.idc.action.idcReBusinessOpportunity;

import com.idc.model.ExecuteResult;
import com.idc.model.IdcReBusinessOpportunity;
import com.idc.model.SysUserinfo;
import com.idc.service.IdcReBusinessOpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;

import java.util.*;

@Controller
@RequestMapping("/idcReBusinessOpportunity")
public class IdcReBusinessOpportunityAction extends BaseController {

    @Autowired
    private IdcReBusinessOpportunityService idcReBusinessOpportunityService;

    /*打开业务商机信息首页*/
    @RequestMapping("/index")
    public String index(){
        return "/idcReBusinessOpportunity/index";
    }

    /*获取业务商机信息列表数据*/
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page,IdcReBusinessOpportunity idcReBusinessOpportunity){
        EasyUIData easyUIData = new EasyUIData();
        List<IdcReBusinessOpportunity> list = new ArrayList<>();
        if(page!=null&&page.getPage()>0){//分页
            list = idcReBusinessOpportunityService.queryListPage(page,idcReBusinessOpportunity);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }else{
            list = idcReBusinessOpportunityService.queryListByObject(idcReBusinessOpportunity);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        }
        return easyUIData;
    }

    /*打开新增或修改业务商机信息页面*/
    @RequestMapping("/preSaveIdcReBusinessOpportunity")
    public String preSaveIdcReBusinessOpportunity(ModelMap modelMap, String buttonType, String id){
        IdcReBusinessOpportunity idcReBusinessOpportunity = new IdcReBusinessOpportunity();
        if(id!=null&&!"".equals(id)){//当为修改时  将业务商机信息反馈到页面
            idcReBusinessOpportunity = idcReBusinessOpportunityService.getModelById(id);
            idcReBusinessOpportunity.setOrderPredictTimeStr(idcReBusinessOpportunity.getOrderPredictTime());
        }
        modelMap.put("buttonType",buttonType);
        modelMap.put("idcReBusinessOpportunity",idcReBusinessOpportunity);
        return "/idcReBusinessOpportunity/info";
    }

    /*新增或修改业务商机信息*/
    @RequestMapping("/saveIdcReBusinessOpportunity")
    @ResponseBody
    public ExecuteResult saveIdcReBusinessOpportunity(IdcReBusinessOpportunity idcReBusinessOpportunity){
        ExecuteResult executeResult = new ExecuteResult();
        try{
            if(idcReBusinessOpportunity!=null&&idcReBusinessOpportunity.getId()!=null
                    &&!"".equals(idcReBusinessOpportunity.getId())){//修改
                idcReBusinessOpportunityService.updateByObject(idcReBusinessOpportunity);
            }else{//新增
                idcReBusinessOpportunity.setId(UUID.randomUUID().toString());
                //获取当前登录用户
                SysUserinfo userinfo = getPrincipal();
                idcReBusinessOpportunity.setUserName(userinfo.getUsername());
                idcReBusinessOpportunity.setUserId(userinfo.getId().toString());
                idcReBusinessOpportunity.setCreateTime(new Date());
                //进行新增操作
                idcReBusinessOpportunityService.insert(idcReBusinessOpportunity);
            }
            executeResult.setState(true);
            executeResult.setMsg("保存业务商机信息成功");
        }catch(Exception e){
            executeResult.setState(false);
            executeResult.setMsg("保存业务商机信息失败");
            e.printStackTrace();
        }
        return executeResult;
    }

    /*根据IDS删除业务商机数据*/
    @RequestMapping("/deleteBusinessOpportunityByIds")
    @ResponseBody
    public ExecuteResult deleteBusinessOpportunityByIds(String ids){
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            idcReBusinessOpportunityService.deleteByList(idList);
            executeResult.setState(true);
            executeResult.setMsg("删除业务商机数据成功");
        }catch(Exception e){
            executeResult.setState(false);
            executeResult.setMsg("删除业务商机数据失败");
            e.printStackTrace();
        }
        return executeResult;
    }
}

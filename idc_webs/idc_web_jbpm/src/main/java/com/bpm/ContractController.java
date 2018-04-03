package com.bpm;

import com.idc.model.AssetAttachmentinfo;
import com.idc.model.IdcContract;
import com.idc.model.SysRoleinfo;
import com.idc.model.SysUserinfo;
import com.idc.service.AssetAttachmentinfoService;
import com.idc.service.IdcContractService;
import com.idc.service.IdcHisTicketService;
import com.idc.utils.CategoryEnum;
import modules.utils.ResponseJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.PageBean;
import system.data.supper.action.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/6/6.
 * 合同基本信息
 */
@Controller
@RequestMapping("/contractController")
public class ContractController extends  BaseController{
    private Logger logger = LoggerFactory.getLogger(ContractController.class);
    @Autowired
    private IdcContractService idcContractService;
    @Autowired
    private IdcHisTicketService idcHisTicketService;
    @Autowired
    private AssetAttachmentinfoService assetAttachmentinfoService;
    /*** 常见的增删改查功能 ***/
    /*1:合同   start*/
    @RequestMapping("/contractQuery.do")
    public String contractQuery(HttpServletRequest request) {
        return "jbpm/contract/contractGridQuery";
    }
    @RequestMapping("/contractQueryData.do")
    @ResponseBody
    public PageBean contractQueryData(HttpServletRequest request, PageBean result, IdcContract idcContract) {
        result = result == null ? new PageBean() : result;
        // 获取所有创建的流程模型
        SysUserinfo sysUserinfo = getPrincipal();
        Integer loginUserID = sysUserinfo.getId();
        //添加查询条件
        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("contractname",request.getParameter("contractname"));  //合同名称
        paramsMap.put("contractno",request.getParameter("contractno"));   //合同编码(合同编码是唯一的,一个订单只会有一个合同编码)
        paramsMap.put("loginUserID",loginUserID);   //系统登陆人的ID

        List<SysRoleinfo> sysRoleinfos = sysUserinfo.getSysRoleinfos();
        for(SysRoleinfo sysRoleinfo : sysRoleinfos){
            String name = sysRoleinfo.getName();
            //如果角色名多包函了  ‘系统管理员'这个字段，那么他就可以查看所有的合同
            if(name.contains("系统管理员")){
                paramsMap.remove("loginUserID");
            }
        }

        idcContractService.queryContractByUserListPage(result,paramsMap);
        return result;
    }
    //新增信息
    @RequestMapping("/singleCreateOrUpdate.do")
    public String singleCreateOrUpdate(HttpServletRequest request, Model model,IdcContract idcContract) {
        //如果主键是null代表是新增  否则就是修改
        if(idcContract.getId() != null && idcContract.getId() != 0){
            idcContract = idcContractService.getModelByContractId(idcContract.getId());
            model.addAttribute("idcContract",idcContract);
        }
        SysUserinfo principal=getPrincipal();
        idcContract.setApplyId(String.valueOf(principal.getId()));
        idcContract.setApplyName(principal.getName());
        model.addAttribute("idcContract",idcContract);
        if(idcContract != null){
            Map<String,String> attachmentinfoMap = new HashMap<String,String>();
            attachmentinfoMap.put("tableName","IDC_CONTRACT");
            attachmentinfoMap.put("relationalId",String.valueOf(idcContract.getId()));
            List<AssetAttachmentinfo> attachmentinfoList = assetAttachmentinfoService.getAttachmentinfoByRelationalId(attachmentinfoMap);
            model.addAttribute("attachmentinfoList",attachmentinfoList);
        }
        return "jbpm/contract/singleCreateOrUpdate";
    }
    @RequestMapping("/linkQueryWin.do")
    public String linkQueryWin(HttpServletRequest request, Model model,IdcContract idcContract) {
        //如果主键是null代表是新增  否则就是修改
        if(idcContract.getId() != null && idcContract.getId() != 0){
            idcContract = idcContractService.getModelByContractId(idcContract.getId());
            model.addAttribute("idcContract",idcContract);
        }
        return "jbpm/contract/linkQueryWin";
    }

    /*通过合同信息得到合同关联的所有的工单*/
    @RequestMapping("/getContractListPage.do")
    public String getContractListPage(HttpServletRequest request,Model model) {
        model.addAttribute("prodInstId",request.getParameter("prodInstId"));
        model.addAttribute("ticketInstId",request.getParameter("ticketInstId"));
        model.addAttribute("contractId",request.getParameter("contractId"));
        return "jbpm/contract/getContractListPage";
    }

    @RequestMapping("/getContractListPageData")
    @ResponseBody
    public List<Map<String,Object>> getContractListPageData(HttpServletRequest request) throws IOException {
        List<Map<String,Object>> hisTicketMapList=null;
        try {
            String prodInstId = request.getParameter("prodInstId");
            String ticketInstId = request.getParameter("ticketInstId");
            String contractId = request.getParameter("contractId");

            if(prodInstId == null || prodInstId.equals("0")){
                prodInstId = (idcHisTicketService.queryProdInstIdByTicketInstId(Long.valueOf(ticketInstId))).toString();
            }

            hisTicketMapList=idcHisTicketService.getContractListPageData(Long.valueOf(prodInstId));

        } catch (Exception e) {
        }
        return hisTicketMapList;
    }

    @RequestMapping("/singleDelete/{id}")
    @ResponseBody
    public ResponseJSON singleDelete(@PathVariable Long id,HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        String contractname = request.getParameter("contractname");
        try {
            //这里使用oracle的常用方法[新增和修改相同方法]
            idcContractService.deleteRecordById(id);
            result.setMsg("删除成功,合同名称是[" + contractname+"]");
        } catch (Exception e) {
            logger.error("删除失败,合同名称是[" + contractname+"]",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}

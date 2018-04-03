package com.bpm;

import com.idc.model.IdcReCustomer;
import com.idc.model.IdcReProduct;
import com.idc.model.ServiceApplyImgStatus;
import com.idc.model.SysUserinfo;
import com.idc.service.IdcReCustomerService;
import com.idc.service.IdcReProddefService;
import com.idc.service.IdcReProductService;
import com.idc.service.IdcRunProcCmentService;
import com.idc.utils.CategoryEnum;
import com.idc.utils.ResourceEnum;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/6/6.
 * 资源申请
 */
@Controller
@RequestMapping("/resourceApplyController")
public class ResourceApplyController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(ResourceApplyController.class);
    @Autowired
    private IdcReProductService idcReProductService;
    @Autowired
    private IdcReProddefService idcReProddefService;//产品模型
    /*@Autowired
    private IdcReRackModelService idcReRackModelService;*//*机架*//*
    @Autowired
    private IdcRePortModelService idcRePortModelService;*//*端口带宽*//*
    @Autowired
    private IdcReIpModelService idcReIpModelService;//ip租用
    @Autowired
    private IdcReServerModelService idcReServerModelService;//主机租赁
    @Autowired
    private  IdcReNewlyModelService idcReNewlyModelService;//增值业务*/
    @Autowired
    private  IdcRunProcCmentService idcRunProcCmentService;//增值业务*/
    @Autowired
    private  IdcReCustomerService idcReCustomerService;//客户信息*/

    /*资源申请   start*/
    @RequestMapping("/productGridQuery.do")
    public String productGridQuery(HttpServletRequest request,Model model) {
        String ticket_ctl = request.getParameter("ticket_ctl");/*主要目的是控制是否需要显示所有的单子*/
        model.addAttribute("ticket_ctl",ticket_ctl);
        return "jbpm/product/productGridQuery";
    }
    @RequestMapping("/test.do")
    public String test(HttpServletRequest request) {
        return "jbpm/product/test";
    }

    @RequestMapping("/productGridQueryData.do")
    @ResponseBody
    public PageBean productGridQueryData(HttpServletRequest request, PageBean result, IdcReProduct idcReProduct) {
        result = result == null ? new PageBean() : result;
        String ticket_ctl = request.getParameter("ticket_ctl");/*主要目的是控制是否需要显示所有的单子*/


        /*抽取了一部分字段显示*/
        /*申请人和申请名称*/
        SysUserinfo sysUserinfo = getPrincipal();
        idcReProduct.setApplyId("user_key_"+sysUserinfo.getName()+"@id_"+sysUserinfo.getId());
        /*这里就只查询预勘预占的任务节点工单  procticketStatusList  */
        if(ticket_ctl != null && "true".equals(ticket_ctl)){
            //此时是查询所有的单子情况:这里是排除了草稿状态
            idcReProduct.setExclude_pre("true");
        }else{
            if("idc_ticket_pre_accept".equals(idcReProduct.getProcessDefinitonKey())){
                List<Integer> procticketStatusList = new ArrayList<Integer>();
                procticketStatusList.add(10);
                procticketStatusList.add(20);
                procticketStatusList.add(21);
                idcReProduct.setProcticketStatusList(procticketStatusList);
            }else if("idc_ticket_self_pre_accept".equals(idcReProduct.getProcessDefinitonKey())){
                List<Integer> procticketStatusList = new ArrayList<Integer>();
                procticketStatusList.add(10);
                procticketStatusList.add(20);
                procticketStatusList.add(21);
                idcReProduct.setProcticketStatusList(procticketStatusList);
            }
        }
        idcReProductService.queryGridFilterListPage(result,idcReProduct);
        return result;
    }
    //新增信息
    @RequestMapping("/singleCreateOrUpdate.do")
    public String singleCreateOrUpdate(HttpServletRequest request, Model model, IdcReProduct idcReProduct) {
        //如果主键是null代表是新增  否则就是修改
        if(idcReProduct.getId() != null && idcReProduct.getId() != 0){
            idcReProduct = idcReProductService.getModelByProductId(idcReProduct.getId());
            model.addAttribute("idcReProduct",idcReProduct);
            //此处可以通过级联查询：【我高兴就这么写】
            /*model.addAttribute("idcReRackModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.机架.value(),idcReProduct.getId()));
            model.addAttribute("idcRePortModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.端口带宽.value(),idcReProduct.getId()));
            model.addAttribute("idcReIpModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.IP租用.value(),idcReProduct.getId()));
            model.addAttribute("idcReServerModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.主机租赁.value(),idcReProduct.getId()));
            model.addAttribute("idcReNewlyModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.增值业务.value(),idcReProduct.getId()));*/
            /*获取由哪些类型已经被填写*/
            ServiceApplyImgStatus serviceApplyImgStatus = idcReProddefService.getSelfModelByProdInstId(idcReProduct.getId());
            model.addAttribute("serviceApplyImgStatus",serviceApplyImgStatus);
        }
        return "jbpm/product/singleCreateOrUpdate";
    }

    //新增信息
    @RequestMapping("/singleCreateOrUpdate_new.do")
    public String singleCreateOrUpdate_new(HttpServletRequest request, Model model, IdcReProduct idcReProduct) throws Exception{
        //如果主键是null代表是新增  否则就是修改
        String catalog = request.getParameter("catalog");
        model.addAttribute("category",catalog);

        //如果isCustomerView=yes,说明是客户经理视图，客户经理视图发起预堪是发起政企业务，不需要写死客户信息
        String isCustomerView = request.getParameter("isCustomerView");
        if(idcReProduct.getProdCategory() == 0 /*|| idcReProduct.getCatalog().equals(CategoryEnum.临时测试流程.value())*/){
            logger.debug("自有业务的默认是中国移动！！！");
            IdcReCustomer idcReCustomer = idcReCustomerService.getModelByChinaData();//自有业务的话是获取 移动客户
            model.addAttribute("idcReCustomer",idcReCustomer);
        }


        model.addAttribute("idcReProduct",idcReProduct);
        return "jbpm/product/singleCreateOrUpdate_new";
    }

    /**
     *
     * @param id
     * @param request
     * @return
     * @throws IOException
     */

    @RequestMapping("/singleDelete/{id}")
    @ResponseBody
    public ResponseJSON singleDelete(@PathVariable Long id, HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        String name = request.getParameter("name");
        try {
            //这里使用oracle的常用方法[新增和修改相同方法]
            idcReProductService.deleteRecordById(id);
            result.setMsg("删除成功,订单名称是[" + name+"]");
        } catch (Exception e) {
            logger.error("删除失败,订单名称是[" + name+"]",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/updateIsActive/{id}/{isActive}")
    @ResponseBody
    public ResponseJSON updateIsActive(@PathVariable Long id,@PathVariable Integer isActive, HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        String name = request.getParameter("name");
        try {
            // 修改isActive
            if(isActive == 1){
                isActive = 0;
            }else{
                isActive = 1;
            }
            idcReProductService.updateRcordByIdWithIsActive(id,isActive);
            result.setMsg("成功将[" + name+"]状态");
        } catch (Exception e) {
            logger.error("成功将[" + name+"]状态",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /*详情界面*/
    @RequestMapping("/linkQueryWin.do")
    public String linkQueryWin(HttpServletRequest request, Model model,IdcReProduct idcReProduct) {
        //如果主键是null代表是新增  否则就是修改
        if(idcReProduct.getId() != null && idcReProduct.getId() != 0){
            idcReProduct = idcReProductService.getModelByProductId(idcReProduct.getId());
            model.addAttribute("idcReProduct",idcReProduct);
            //此处可以通过级联查询：【我高兴就这么写】
            /*model.addAttribute("idcReRackModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.机架.value(),idcReProduct.getId()));
            model.addAttribute("idcRePortModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.端口带宽.value(),idcReProduct.getId()));
            model.addAttribute("idcReIpModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.IP租用.value(),idcReProduct.getId()));
            model.addAttribute("idcReServerModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.主机租赁.value(),idcReProduct.getId()));
            model.addAttribute("idcReNewlyModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.增值业务.value(),idcReProduct.getId()));*/
            /*获取由哪些类型已经被填写*/
            ServiceApplyImgStatus serviceApplyImgStatus = idcReProddefService.getSelfModelByProdInstId(idcReProduct.getId());
            model.addAttribute("serviceApplyImgStatus",serviceApplyImgStatus);
        }
        return "jbpm/product/linkQueryWin";
    }

    /*详情界面*/
    @RequestMapping("/linkQueryWin_new.do")
    public String linkQueryWin_new(HttpServletRequest request, Model model,IdcReProduct idcReProduct) {
        //如果主键是null代表是新增  否则就是修改
        if(idcReProduct.getId() != null && idcReProduct.getId() != 0){
            idcReProduct = idcReProductService.getModelByProductId(idcReProduct.getId());
            model.addAttribute("idcReProduct",idcReProduct);
            //此处可以通过级联查询：【我高兴就这么写】/*
            /*model.addAttribute("idcReRackModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.机架.value(),idcReProduct.getId()));
            model.addAttribute("idcRePortModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.端口带宽.value(),idcReProduct.getId()));
            model.addAttribute("idcReIpModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.IP租用.value(),idcReProduct.getId()));
            model.addAttribute("idcReServerModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.主机租赁.value(),idcReProduct.getId()));
            model.addAttribute("idcReNewlyModel",idcReProddefService.getModelByCategory(ServiceApplyEnum.增值业务.value(),idcReProduct.getId()));*/
            /*获取由哪些类型已经被填写*/
            ServiceApplyImgStatus serviceApplyImgStatus = idcReProddefService.getSelfModelByProdInstId(idcReProduct.getId());
            model.addAttribute("serviceApplyImgStatus",serviceApplyImgStatus);
        }
        return "jbpm/product/linkQueryWinNew";
    }



    /*资源申请   end*/
    /*******************创建工单start*********************/
    /*@RequestMapping("/singleCreateTicket/{prodInstId}")
    @ResponseBody
    public ResponseJSON singleCreateTicket(@PathVariable("prodInstId") Long prodInstId,HttpServletRequest request) throws IOException {
        String prodName = request.getParameter("ProdName");
        ResponseJSON result = new ResponseJSON();
        try {
            //这里使用oracle的常用方法[新增和修改相同方法]
            idcReProductService.singleCreateTicket(prodInstId);
            result.setMsg("创建[" + prodName+"]订单的工单成功");
        } catch (Exception e) {
            logger.error("创建[" + prodName+"]订单的工单失败",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }*/
    /*******************创建工单end*********************/

}

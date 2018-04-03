package com.bpm;

import com.idc.model.*;
import com.idc.service.IdcReCustomerService;
import com.idc.utils.CategoryEnum;
import modules.utils.ResponseJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.PageBean;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/6/6.
 * 客户基本信息
 */
@Controller
@RequestMapping("/customerController")
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private IdcReCustomerService idcReCustomerService;
    /** 客户选择弹出框 start**/
    @RequestMapping("/customerQuery.do")
    public String customerQuery(HttpServletRequest request) {
        return "jbpm/customer/customerGridQuery";
    }

    @RequestMapping("/customerQueryData.do")
    @ResponseBody
    public PageBean customerQueryData(HttpServletRequest request, PageBean result, IdcReCustomer idcReCustomer) {
        result = result == null ? new PageBean() : result;
        /*抽取了一部分字段显示*/

        SysUserinfo sysUserinfo = getPrincipal();  //拿到当前登陆信息
        idcReCustomer.setSubordinateManagerId(Long.valueOf(sysUserinfo.getId()));  //放入当前登陆用户的id，每个客户经理的客户不共享的
        idcReCustomer.setIsChinaMobile(true);
        idcReCustomerService.queryMngGridFilterListPage(result,idcReCustomer);
        return result;
    }
    /** 客户选择弹出框 end**/
    /** 客户管理 start**/
    @RequestMapping("/customerMngQuery.do")
    public String customerMngQuery(HttpServletRequest request, Model model) {
        SysUserinfo sysUserinfo = getPrincipal();  //拿到当前登陆信息
        String userNick = sysUserinfo.getNick();
        //把用户昵称放进去
        model.addAttribute("userNick",userNick);
        return "jbpm/customer/customerMngQuery";
    }

    @RequestMapping("/customerMngQueryData.do")
    @ResponseBody
    public PageBean customerMngQueryData(HttpServletRequest request, PageBean result, IdcReCustomer idcReCustomer) {
        result = result == null ? new PageBean() : result;

        idcReCustomer.setLookAllCustomer(0);
        SysUserinfo sysUserinfo = getPrincipal();
        List<SysRoleinfo> sysRoleinfos = sysUserinfo.getSysRoleinfos();
        for(SysRoleinfo sysRoleinfo : sysRoleinfos){
            String name = sysRoleinfo.getName();
            //如果角色名多包函了  ‘查看所有客户'这个字段，那么他就可以查看所有的客户
            if(name.contains("查看所有客户")){
                idcReCustomer.setLookAllCustomer(Integer.valueOf(CategoryEnum.查看所有客户.value()));
            }
        }
        idcReCustomer.setSubordinateManagerId(Long.valueOf(sysUserinfo.getId()));
        idcReCustomer.setSubordinateManagerName(sysUserinfo.getNick());

        /*抽取了一部分字段显示*/
        idcReCustomerService.queryMngGridFilterListPage(result,idcReCustomer);
        return result;
    }


    /** 商机 start**/
    @RequestMapping("/opportunity.do")
    public String opportunity(HttpServletRequest request) {
        return "jbpm/customer/opportunityQuery";
    }
    @RequestMapping("/opportunityData.do")
    @ResponseBody
    public PageBean opportunityData(HttpServletRequest request, PageBean result, IdcReCustomer idcReCustomer) {
        result = result == null ? new PageBean() : result;

        List<Map<String,Object>> newList=new ArrayList<>();
        Map<String,Object> maps1=new HashMap<>();

        maps1.put("busName","机架申请");
        maps1.put("name","四川省铁路产业投资集团有限责任公司");
        maps1.put("time","2017-12");
        maps1.put("yesOrNo","否");
        maps1.put("id","81");

        Map<String,Object> maps2=new HashMap<>();
        maps2.put("busName","申请机位");
        maps2.put("name","合一信息技术（北京）有限公司");
        maps2.put("time","2017-11");
        maps2.put("yesOrNo","是");
        maps2.put("id","103");

        Map<String,Object> maps3=new HashMap<>();
        maps3.put("busName","IP申请");
        maps3.put("name","成都盛世云图信息技术有限公司");
        maps3.put("time","2018-3");
        maps3.put("yesOrNo","是");
        maps3.put("id","122");

        Map<String,Object> maps4=new HashMap<>();
        maps4.put("busName","资源整合申请");
        maps4.put("name","中国移动通信集团四川有限公司");
        maps4.put("time","2017-12");
        maps4.put("yesOrNo","否");
        maps4.put("id","41");

        Map<String,Object> maps5=new HashMap<>();
        maps5.put("busName","年初准备");
        maps5.put("name","众力佳华四川有限公司");
        maps5.put("time","2017-11");
        maps5.put("yesOrNo","否");
        maps5.put("id","21");

        Map<String,Object> maps6=new HashMap<>();
        maps6.put("busName","大量申请");
        maps6.put("name","淘宝（中国）软件有限公司");
        maps6.put("time","2018-5");
        maps6.put("yesOrNo","否");
        maps6.put("id","101");

        Map<String,Object> maps7=new HashMap<>();
        maps7.put("busName","申请IP及主机");
        maps7.put("name","北京汽车之家信息技术有限公司");
        maps7.put("time","2018-2");
        maps7.put("yesOrNo","否");
        maps7.put("id","141");

        newList.add(maps1);
        newList.add(maps2);
        newList.add(maps3);
        newList.add(maps4);
        newList.add(maps5);
        newList.add(maps6);
        newList.add(maps7);

        result.setItems(newList);
        return result;
    }


    /** 商机 start**/
    @RequestMapping("/opportunityCreate.do")
    public String opportunityCreate(HttpServletRequest request) {
        return "jbpm/customer/opportunityCreatePage";
    }

    //新增信息
    @RequestMapping("/singleCreateOrUpdate.do")
    public String singleCreateOrUpdate(HttpServletRequest request, Model model, IdcReCustomer idcReCustomer) {
        //如果主键是null代表是新增  否则就是修改
        if(idcReCustomer.getId() != null && idcReCustomer.getId() != 0){
            idcReCustomer = idcReCustomerService.getModelByReCustomerId(idcReCustomer.getId());
            model.addAttribute("idcReCustomer",idcReCustomer);
        }

        SysUserinfo sysUserinfo = getPrincipal();  //拿到当前登陆信息
        String userNick = sysUserinfo.getNick();
        //把用户昵称放进去
        model.addAttribute("userNick",userNick);

        return "jbpm/customer/singleCreateOrUpdate";
    }
    @RequestMapping("/customerFormSaveOrUpdate")
    @ResponseBody
    public ResponseJSON customerFormSaveOrUpdate(IdcReCustomer idcReCustomer) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            //每个客户要关联创建他的客户经理
            SysUserinfo sysUserinfo = getPrincipal();
            idcReCustomer.setSubordinateManagerId(Long.valueOf(sysUserinfo.getId()));
            idcReCustomer.setSubordinateManagerName(sysUserinfo.getUsername());

            //这里使用oracle的常用方法[新增和修改相同方法]
            idcReCustomerService.mergeInto(idcReCustomer);
            result.setMsg("保存成功,客户名称是[" + idcReCustomer.getName()+"]");
        } catch (Exception e) {
            logger.error("保存失败,客户名称是[" + idcReCustomer.getName()+"]",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/verifyAlias")
    @ResponseBody
    public ResponseJSON verifyAlias(HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {

            String alias = request.getParameter("alias");
            Long aliasCount = idcReCustomerService.verifyAlias(alias);
            result.setResult(aliasCount);
        } catch (Exception e) {
            logger.error("验证客户别名失败，失败原因：",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping("/linkQueryWin.do")
    public String linkQueryWin(HttpServletRequest request, Model model,IdcReCustomer idcReCustomer) {
        //如果主键是null代表是新增  否则就是修改
        if(idcReCustomer.getId() != null && idcReCustomer.getId() != 0){
            idcReCustomer = idcReCustomerService.getModelByReCustomerId(idcReCustomer.getId());
            model.addAttribute("idcReCustomer",idcReCustomer);


            /*------start----费用预计--------------------------------*/
            Long id1 = idcReCustomer.getId();
            if(id1<80){
                model.addAttribute("isOpportunity","1");
                Long id=idcReCustomer.getId();
                if(id ==1){

                }else if(id==62){
                    model.addAttribute("cost8","6.8万");
                    model.addAttribute("cost9","4.3万");
                    model.addAttribute("cost10","6万");
                }else if(id==41){
                    model.addAttribute("cost8","65.4万");
                    model.addAttribute("cost9","77.2万");
                    model.addAttribute("cost10","71.8万");
                }else if(id==141){
                    model.addAttribute("cost8","4.3万");
                    model.addAttribute("cost9","5.7万");
                    model.addAttribute("cost10","2.4万");
                }else if(id==101){
                    model.addAttribute("cost8","77万");
                    model.addAttribute("cost9","72万");
                    model.addAttribute("cost10","68万");
                }else if(id==21){
                    model.addAttribute("cost8","32万");
                    model.addAttribute("cost9","23.9万");
                    model.addAttribute("cost10","17.8万");
                }
            }
            /*-----end--------------费用预计-----------------------*/
        }
        return "jbpm/customer/linkQueryWin";
    }
    @RequestMapping("/singleDelete/{id}")
    @ResponseBody
    public ResponseJSON singleDelete(@PathVariable Long id, HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        String name = request.getParameter("name");
        try {
            //这里使用oracle的常用方法[新增和修改相同方法]
            idcReCustomerService.deleteRecordById(id);
            result.setMsg("删除成功,客户名称是[" + name+"]");
        } catch (Exception e) {
            logger.error("删除失败,客户名称是[" + name+"]",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /** 获取当前登陆客户的信息**/
    public SysUserinfo getPrincipal(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof SysUserinfo) {
            return (SysUserinfo) principal;
        }else{
            return null;
        }
    }


}

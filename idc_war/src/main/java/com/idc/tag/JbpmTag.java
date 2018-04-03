package com.idc.tag;

import com.idc.model.*;
import com.idc.service.*;
import com.idc.service.impl.*;
import com.idc.utils.CategoryEnum;
import com.idc.utils.ServiceApplyEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;

/**
 * 的标签
 */
@Controller
public class JbpmTag extends TagSupport {
    private static final Log log = LogFactory.getLog(JbpmTag.class);
    private static final long serialVersionUID = 3894558024533163703L;

    private static ApplicationContext act;
    private List items;//可能传递附件信息
    private String module;/*标签类别：合同附件[IDC_CONTRACT_ATTACH]...
    TICKET_PRODUCET_MODULE
    ACTIVITI_DIAGRAM
    TICKET_RECUSTOMER:客户信息
    TICKET_PRODUCET_INFO:预受理
    TICKET_NET_SERVER:服务信息标签
    HIS_TICKET_COMMENT:历史工单审批意见信息
    OTHER_LINKED_TICKET:其他工单
    FLOAT_TIP_MSG:浮动提示框
    TICKET_ATTACHMENT:工单附件
    HIS_TICKET_APPLY:评价打分
    TICKET_CONCTRACT:合同信息
    TICKET_PAUSE：停机原因
    */
    /*合同附件可能用到的参数*/
    private int width;
    private int lineHeight;
    private int height;
    private int maxHeight;

    private String actName;
    private String serialNumber;
    private String comment;
    private String clazzName;//类的名称：目的是可以将items转换成相应的类:此时需要通过合同ID查询数据
    private String contractId; //:是合同主键
    private String prodInstId;//：是订单主键
    private IdcReProddefService idcReProddefService;
    private IdcContractService idcContractService;//合同信息
    private IdcHisTicketService idcHisTicketService;//
    private AssetAttachmentinfoService assetAttachmentinfoService;//合同附件信息
    private IdcNetServiceinfoService idcNetServiceinfoService;//服务信息附件
    private IdcReProductService idcReProductService;
    private IdcRunProcCmentService idcRunProcCmentService;
    private IdcRunTicketPauseService idcRunTicketPauseService;
    /*private ProductCategory productCategory;*/
    /*链接相应的流程图IFRAME*/
    private String processInstanceId;
    private String processDefinitionId;
    private String processDefinitionKey;
    private Object item;//可以代表任意对象
    private Object ticketItem;
    private String ticketInstId;
    private Integer ticketStatus;//工单状态
    private IdcReCustomer idcReCustomer;
    private IdcRunProcCment idcRunProcCment;

    private IdcReProduct idcReProduct;
    private String title;
    private String category;
    private IdcRunTicket idcRunTicket;
    private IdcHisTicket idcHisTicket;

    private Boolean ZQYWFlag;
    private Boolean is_author_apply_show;
    public static  String httpSuffix;
    private ServletRequest request;
    private String toolbar;//grid中的toolbar
    private Boolean readOnly;//判断是否可读的属性【驳回的时候使用】
    private Integer hasOtherTicket;//是否存在，其他工单信息
    private String gridId;//列表ID
    private Boolean isShowGridColumnHandlerFlag;//操作标志
    private Long count;//个数
    private Boolean ticketResourceReadOnly = false;
    private Boolean isFormEdit = false;
    private Integer satisfaction;//评价的分数
    private Boolean isHasOpenEdit;//开通工单是否是属于查看标志。。。。true编辑   false查询




    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        httpSuffix = pageContext.getServletContext().getContextPath();
        request = pageContext.getRequest();
        StringBuffer sb = new StringBuffer();

        /*********************************合同附件相关控件 start******************************************/
        try {
            if (module != null && "IDC_CONTRACT_ATTACH".equalsIgnoreCase(module)) {
                log.debug("合同调用附件信息......");
                /*通过合同ID查询附件信息*/
                Map<String, String> attachmentinfoMap = new HashMap<String, String>();
                attachmentinfoMap.put("tableName", "IDC_CONTRACT");
                attachmentinfoMap.put("relationalId", contractId);
                ServletContext sc = pageContext.getServletContext();
                ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);

                if (assetAttachmentinfoService == null) {
                    assetAttachmentinfoService = ac.getBean(AssetAttachmentinfoServiceImpl.class);
                }
                List<AssetAttachmentinfo> attachmentinfoList = assetAttachmentinfoService.getAttachmentinfoByRelationalId(attachmentinfoMap);
                if (!attachmentinfoList.isEmpty()) {
                    Iterator it = attachmentinfoList.iterator();
                    while (it.hasNext()) {
                    /*不用做得太智能，否则需要反射*/
                        AssetAttachmentinfo assetAttachmentinfo = (AssetAttachmentinfo) it.next();
                        sb.append("<div class=\"attachHasedCls\">");
                        sb.append("<input value=\"" + assetAttachmentinfo.getAttachName() + "\" class=\"easyui-textbox\" data-options=\"editable:false,width:" + width + "\" />");
                        sb.append("<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"downLoadAttach(" + assetAttachmentinfo.getId() + ")\" style=\"width:38px\">下载</a>");
                        sb.append("</div>");
                    }
                    out.print(sb.toString());
                }
            }else  if(module != null && "TICKET_PRODUCET_MODULE".equalsIgnoreCase(module) ) {
                log.debug("【工单订单资源配置信息】");
                ServletContext sc = pageContext.getServletContext();

                ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
                if (idcReProddefService == null) {
                    idcReProddefService = ac.getBean(IdcReProddefServiceImpl.class);
                }
                if (idcReProductService == null){
                    //产品情况
                    idcReProductService = ac.getBean(IdcReProductServiceImpl.class);
                }
                Integer ticketStatus = null;
                String ticketCategory = null;
                String ticketCategoryFrom = null;
                Long ticketInstId = null;
                if(ticketItem instanceof IdcHisTicket){
                    ticketStatus = ((IdcHisTicket)ticketItem).getTicketStatus();
                    ticketCategory = ((IdcHisTicket)ticketItem).getTicketCategory();
                    ticketCategoryFrom = ((IdcHisTicket)ticketItem).getTicketCategoryFrom();
                    ticketInstId = ((IdcHisTicket)ticketItem).getId();
                }else if(ticketItem instanceof IdcRunTicket){
                    ticketStatus = ((IdcRunTicket)ticketItem).getTicketStatus();
                    ticketCategory = ((IdcRunTicket)ticketItem).getTicketCategory();
                    ticketCategoryFrom = ((IdcRunTicket)ticketItem).getTicketCategoryFrom();
                    ticketInstId = ((IdcRunTicket)ticketItem).getId();
                }
                /**
                 * 判断该状态,如果是驳回状态。则需要修改修改
                 * 状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束
                 */
                //ProductCategory productCategory =  idcReProductService.getProductCategoryByProdInstId(prodInstId,ticketInstId);
                ProductCategory productCategory =new ProductCategory();
                productCategory.setProdInstId(Long.valueOf(prodInstId));
                productCategory.setAdd(true);
                productCategory.setIp(true);
                productCategory.setPort(true);
                productCategory.setRack(true);
                productCategory.setServer(true);


                request.setAttribute("productCategory",productCategory);
                /*****  被驳回且属于预占流程或者业务变更的情况，是可以修改的。否则不能修改 *******/
                if(ticketStatus == -1 && (ticketCategory.equals(CategoryEnum.预堪预占流程.value()) || ticketCategory.equals(CategoryEnum.业务变更流程.value()))){
                    if(productCategory != null){
                        log.debug("============这个是需要修改的============");
                        //writeProductForceWriteTag(out,sb,productCategory,ticketInstId);
                    }
                }else if(ticketCategory.equals(CategoryEnum.业务变更流程.value()) && ticketCategoryFrom.equals(CategoryEnum.开通流程.value())){
                    if(productCategory != null){
                        //writeProductForceWriteTag(out,sb,productCategory,ticketInstId);
                    }
                }else if(ticketCategory.equals(CategoryEnum.业务变更流程.value()) && ticketCategoryFrom.equals(CategoryEnum.开通变更流程.value())){
                    if(productCategory != null){
                        //writeProductForceWriteTag(out,sb,productCategory,ticketInstId);
                    }
                }else{
                    if(productCategory != null){
                        log.debug("============只是查看============");
                        //writeProductTag(out,sb,productCategory,ticketInstId);
                    }
                }



            }else if(module != null && "TICKET_CONCTRACT".equalsIgnoreCase(module) ) {
                /*合同的相关信息* */
                ServletContext sc = pageContext.getServletContext();

                ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
                if (idcNetServiceinfoService == null) {
                    idcNetServiceinfoService = ac.getBean(IdcNetServiceinfoServiceImpl.class);
                }
                if (idcContractService == null){
                    idcContractService = ac.getBean(IdcContractServiceImpl.class);
                }
                if (assetAttachmentinfoService == null){
                    assetAttachmentinfoService = ac.getBean(AssetAttachmentinfoServiceImpl.class);
                }
                /*通过工单信息查询合同信息:获取合同信息*/
                IdcContract idcContract = idcContractService.getContractByTicketInstId(Long.valueOf(ticketInstId));
                IdcNetServiceinfo idcNetServiceinfo = idcNetServiceinfoService.getIdcNetServiceinfoByTicketInstId(Long.valueOf(ticketInstId));
                if(idcNetServiceinfo == null){
                    idcNetServiceinfo = new IdcNetServiceinfo();
                }

                if(idcContract == null){
                    idcContract = new IdcContract();
                }

                log.debug("开通界面属于只读！！！！！！！！");
                writeContractTag(out,sb,idcContract,idcNetServiceinfo);
                /*}*/
            }

            if(module != null && "TICKET_PAUSE".equalsIgnoreCase(module) ) {
                /*停机申请有申请信息，停机原因* */
                ServletContext sc = pageContext.getServletContext();

                ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
                if (idcRunProcCmentService == null) {
                    idcRunProcCmentService = ac.getBean(IdcRunProcCmentServiceImpl.class);
                }
                if (idcRunTicketPauseService == null) {
                    idcRunTicketPauseService = ac.getBean(IdcRunTicketPauseServiceImpl.class);
                }
                Long aLong = Long.valueOf(ticketInstId);
                IdcRunProcCment idcRunProcCment = idcRunProcCmentService.queryByTicketInstId(aLong);
                IdcRunTicketPause idcRunTicketPause=idcRunTicketPauseService.queryByTicketInstId(aLong);

                log.debug("停机申请有申请信息，停机原因！！！！！！！！");
                String ticketCategory=null;
                String prodCategory=null;
                String ticketCategoryFrom=null;
                String ticketInstId=null;
                String prodInstId=null;
                String parentId=null;
                Integer ticketStatus=null;
                Date reservestart=null;
                Date reserveend =null;
                String comment="";


                if(ticketItem instanceof IdcHisTicket){
                    ticketCategory = ((IdcHisTicket) ticketItem).getTicketCategory();
                    prodCategory = ((IdcHisTicket) ticketItem).getProdCategory();
                    ticketCategoryFrom = ((IdcHisTicket) ticketItem).getTicketCategoryFrom();
                    ticketInstId = ((IdcHisTicket) ticketItem).getTicketInstId().toString();
                    prodInstId = ((IdcHisTicket) ticketItem).getProdInstId().toString();
                    parentId = ((IdcHisTicket) ticketItem).getParentId().toString();
                }else if(ticketItem instanceof IdcRunTicket){
                    ticketCategory = ((IdcRunTicket) ticketItem).getTicketCategory();
                    prodCategory = ((IdcRunTicket) ticketItem).getProdCategory();
                    ticketCategoryFrom = ((IdcRunTicket) ticketItem).getTicketCategoryFrom();
                    ticketInstId = ((IdcRunTicket) ticketItem).getTicketInstId().toString();
                    prodInstId = ((IdcRunTicket) ticketItem).getProdInstId().toString();
                    parentId = ((IdcRunTicket) ticketItem).getParentId().toString();
                    ticketStatus = ((IdcRunTicket) ticketItem).getTicketStatus();
                }
                if(idcRunTicketPause != null){
                    reservestart = idcRunTicketPause.getReservestart();
                    reserveend = idcRunTicketPause.getReserveend();
                    comment=idcRunTicketPause.getConstructComment() == null ? "" : idcRunTicketPause.getConstructComment();

                }

                writePauseTag(out,sb,idcRunProcCment,ticketCategory,ticketCategoryFrom,ticketInstId,prodCategory,prodInstId,parentId,reservestart,reserveend,comment,ticketStatus);

            }

            if (module != null && "ACTIVITI_DIAGRAM".equalsIgnoreCase(module)) {
                writeACTTag(out,sb);
            }

            if(module != null && "TICKET_RECUSTOMER".equalsIgnoreCase(module) && item != null) {
                ServletContext sc = pageContext.getServletContext();
                ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
                //得到idcHisTicketService
                if (idcHisTicketService == null) {
                    idcHisTicketService = ac.getBean(IdcHisTicketServiceImpl.class);
                }
                //得到业务类型，是自有业务还是政企业务
                String prodCategory = idcHisTicketService.getProdCategoryById(ticketInstId);

                /*显示客户信息*/
                IdcReCustomer idcReCustomer = (IdcReCustomer)item;
                writeCustomTag(out,sb,idcReCustomer,prodCategory);
            }else  if(module != null && "TICKET_PRODUCET_INFO".equalsIgnoreCase(module) && item != null) {
                /*IdcReProduct idcReProduct = (IdcReProduct)item;
                *//*获取对应的idcReProduct*//*


                writeProductTag(out,sb,idcReProduct);*/
            }else  if(module != null && "TICKET_NET_VIRTUAL_SERVER".equalsIgnoreCase(module) && item != null) {
                IdcNetServiceinfo idcNetServiceinfo = (IdcNetServiceinfo)item;
                writeNetVIRTUALServiceinfoTag(out,sb,idcNetServiceinfo);
            }else  if(module != null && "TICKET_NET_VIRTUAL_BTN_SERVER".equalsIgnoreCase(module) && item != null) {
                IdcNetServiceinfo idcNetServiceinfo = (IdcNetServiceinfo)item;
                writeNetVIRTUALBtnServiceinfoTag(out,sb,idcNetServiceinfo);
            }else  if(module != null && "HIS_TICKET_COMMENT".equalsIgnoreCase(module)){
                writeHisCommentTag(out,sb);
            }else  if(module != null && hasOtherTicket != null && "OTHER_LINKED_TICKET".equalsIgnoreCase(module) && hasOtherTicket > 0 ){
                writeOtherTicketTag(out,sb);
            }else  if(module != null  && "FLOAT_TIP_MSG".equalsIgnoreCase(module) ){
                writeTipTag(out,sb);
            }else  if( module != null  && "TICKET_ATTACHMENT".equalsIgnoreCase(module) ){
                writeTicketAttachTag(out,sb);
            }else  if( module != null  && "TICKET_NET_SERVER".equalsIgnoreCase(module) ){
                IdcNetServiceinfo idcNetServiceinfo = (IdcNetServiceinfo)item;
                writeNetServiceinfoTag(out,sb,idcNetServiceinfo);
            }else if( module != null  && "HIS_TICKET_APPLY".equalsIgnoreCase(module) ){
                writeTicketApplyAttachTag(out,sb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*********************************合同附件相关控件 end******************************************/
        return SKIP_BODY;
    }

    public void writeTipTag(JspWriter out,StringBuffer sb) throws Exception{
        sb = new StringBuffer();
        sb.append("<div id=\"FLOAT_TIP_MSG_ID\" class=\"tip_float_right_info\" style=\"height:"+height+"px;line-height:"+lineHeight+"px;width: "+width+"px;\">\n" +
                "    <marquee direction=\"left\"onmouseover=\"this.stop();\" onmouseout=\"this.start();\" onstart=\"this.firstChild.innerHTML+=this.firstChild.innerHTML;\" scrollamount=\"20\" scrolldelay=\"500\" behavior=\"SCROLL\" hspace=\"0\" vspace=\"0\" loop=\"INFINITE\">\n" +
                "        <span style=\"color: #006107\">当前活动节点:<span style=\"color: blue;\">"+actName+"</span>;工单号:<span style=\"color: blue;\">"+serialNumber+"</span>;"+comment+"....</span>\n" +
                "    </marquee>\n" +
                "</div>");
        out.print(sb.toString());
    }
    public void writeOtherTicketTag(JspWriter out,StringBuffer sb) throws Exception{
        sb = new StringBuffer();
        log.debug("其他工单,可以从缓冲区拿数据。。。。。。暂时没有");
        /*分山下布局*/
        sb.append("<div title=\""+this.title+"\" data-options=\"closable:false\" style=\"overflow:auto;padding:10px;display:none;\">");
        sb.append("<div class=\"easyui-layout\" fit=\"true\">\n" +
               /* "    <div data-options=\"region:'north',title:'',split:true\" style=\"height:100px;\"></div>\n" +*/
                "            <div id=\""+this.toolbar+"\">\n" +
                "                工单编号: <input class=\"easyui-textbox\"  id=\"linkedHis_serialNumber\" style=\"width:100px;text-align: left;\" data-options=\"\">\n" +
                "                <a href=\"javascript:void(0);\" onclick=\"reloadHisTicketGrid('"+gridId+"')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\">查询</a>\n" +
                "            </div>"+
                /*"   </div>"+*/
                "    <div data-options=\"region:'center',title:''\" style=\"padding:5px;background:#eee;\">" +
                        "<table class=\"easyui-datagrid\" data-options=\"url:'"+httpSuffix+"/actJBPMController/loadHisTicketGridData/"+this.prodInstId+"/"+this.ticketInstId+"',toolbar:'#"+this.toolbar+"',loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},iconCls:'',singleSelect: true,striped : true,rownumbers: true,fitColumns: true,autoRowHeight:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40]\" id=\"his_ticket_gridId\" fit=\"true\">\n" +
        "                    <thead>\n" +
        "                        <tr>\n" +
        "                            <th data-options=\"field:'BUSNAME',width:100,halign:'left',formatter:loadSingleTicketInfo_w\">名称</th>\n" +
        "                            <th data-options=\"field:'SERIALNUMBER',width:100,halign:'left'\">工单编号</th>\n" +
        "                            <th data-options=\"field:'CUSTOMER_NAME',width:150,halign:'left'\">客户名称</th>\n" +
        "                            <th data-options=\"field:'ACTNAME',width:60,halign:'left',formatter:columnCSS\">状态</th>\n" +
        "                            <th data-options=\"field:'APPLY_NAME',width:70,halign:'left'\">申请人</th>\n" +
        "                            <th data-options=\"field:'CREATETIME',width:70,halign:'left',formatter:fmtDateAction\">时间</th>\n" +
        "                        </tr>\n" +
        "                    </thead>\n" +
        "                </table>"+
                "    </div>\n" +
                "</div>");
        sb.append("</div>");
        out.print(sb.toString());
    }
    public void writeTicketNetServerTag(JspWriter out,StringBuffer sb) throws Exception{

    }

    //得到当前系统登陆用户
    public SysUserinfo getPrincipal(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof SysUserinfo) {
            return (SysUserinfo) principal;
        }else{
            return null;
        }
    }

    public void writeTicketAttachTag(JspWriter out,StringBuffer sb) throws Exception{
        sb = new StringBuffer();
        //后期控制下，如果没有附件则不需要显示：this.isShowGridColumnHandlerFlag
        Long prodInstId = null;
        Long id = null;
        Boolean resourceAllocationStatus = null;
        Boolean ticketResourceHandStatus = null;//资源操作控制   1编辑状态，0查询状态
        /*是否是查询的关键是状态*/
        if(ticketItem instanceof IdcHisTicket){
            prodInstId = ((IdcHisTicket)ticketItem).getProdInstId();
            id = ((IdcHisTicket)ticketItem).getId();
            resourceAllocationStatus = ((IdcHisTicket)ticketItem).getResourceAllocationStatus();
            ticketResourceHandStatus = ((IdcHisTicket)ticketItem).getTicketResourceHandStatus();
        }else if(ticketItem instanceof IdcRunTicket){
            prodInstId = ((IdcRunTicket)ticketItem).getProdInstId();
            id = ((IdcRunTicket)ticketItem).getId();
            resourceAllocationStatus = ((IdcRunTicket)ticketItem).getResourceAllocationStatus();
            ticketResourceHandStatus = ((IdcRunTicket)ticketItem).getTicketResourceHandStatus();//资源操作控制   1编辑状态，0查询状态
        }
        String pageQueryDataStatus = String.valueOf(request.getAttribute("pageQueryDataStatus"));

        SysUserinfo sysUserinfo = getPrincipal();
        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("prodInstId",prodInstId);
        queryParams.put("ticketInstId",id);
        queryParams.put("loginUserID",sysUserinfo.getId());

        /*List<SysRoleinfo> sysRoleinfos = sysUserinfo.getSysRoleinfos();
        for(SysRoleinfo sysRoleinfo : sysRoleinfos){
            String name = sysRoleinfo.getName();
            //如果角色名多包函了  ‘查看所有工单'这个字段，那么他就可以查看所有的工旱
            if(name.contains("查看所有工单")){
                queryParams.put("lookAllTicketRole",CategoryEnum.查看所有工单的角色.value());
                break;
            }
        }*/

        Map<String, Object> handleSecurity = new HashMap<>();
        try {
            //下面是控制显示的资源附件  ISP   合同附件等的
           // handleSecurity = assetAttachmentinfoService.call_AttachmentHandleSecurity(queryParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean handleResource= handleSecurity.get("handleResource") != null && String.valueOf(handleSecurity.get("handleResource")).equals("1");  //操作资源附件的权限
        Boolean handleIDCISP= handleSecurity.get("handleIDCISP") != null && String.valueOf(handleSecurity.get("handleIDCISP")).equals("1");   //操作IDC_ISP附件的权限
        Boolean handleOther= handleSecurity.get("handleOther") != null && String.valueOf(handleSecurity.get("handleOther")).equals("1");    //操作其他附件的权限
        Boolean handleContract= handleSecurity.get("handleContract") != null && String.valueOf(handleSecurity.get("handleContract")).equals("1");   //操作合同附件的权限
        Boolean handleMoney= handleSecurity.get("handleDelete") != null && String.valueOf(handleSecurity.get("handleDelete")).equals("1");    //查看合同金额的权限
        Boolean handleDelete= handleSecurity.get("handleMoney") != null && String.valueOf(handleSecurity.get("handleMoney")).equals("1");    //操作删除资源附件的权限
         handleResource=true;  //操作资源附件的权限
         handleIDCISP=true;   //操作IDC_ISP附件的权限
         handleOther=true;    //操作其他附件的权限
         handleContract=true;   //操作合同附件的权限
         handleMoney=true;    //查看合同金额的权限
         handleDelete=true;    //操作删除资源附件的权限

        /*Boolean handleResource=true;  //操作资源附件的权限
        Boolean handleIDCISP=true;   //操作IDC_ISP附件的权限
        Boolean handleOther=true;    //操作其他附件的权限
        Boolean handleContract=true;   //操作合同附件的权限
        Boolean handleMoney=true;    //查看合同金额的权限
        Boolean handleDelete=true;  */  //操作删除资源附件的权限

        //有资源分配
        //工单资源可以操作的情况
        sb.append("  <div id=\""+this.toolbar+"\">\n");
        sb.append("\t\t\t <div style=\"padding: 5px;\" id=\"attachmentToolbar\">\n");

        if(handleResource){
            sb.append("  <a href=\"javascript:void(0);\" onclick=\"openBaiduPluginAttachment('"+prodInstId+"','"+id+"','"+CategoryEnum.添加资源附件.value()+"')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add-attach'\">添加资源附件</a>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
        }
        if(handleIDCISP){
            sb.append("  <a href=\"javascript:void(0);\" onclick=\"openBaiduPluginAttachment('"+prodInstId+"','"+id+"','"+CategoryEnum.添加IDC_ISP附件.value()+"')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add-attach'\">添加 IDC_ISP 附件</a>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
        }
        if(handleOther){
            sb.append("  <a href=\"javascript:void(0);\" onclick=\"openBaiduPluginAttachment('"+prodInstId+"','"+id+"','"+CategoryEnum.添加其他附件.value()+"')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add-attach'\">添加其他附件</a>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
        }
        if(handleContract){
            sb.append("  <a href=\"javascript:void(0);\" onclick=\"openBaiduPluginAttachment('"+prodInstId+"','"+id+"','"+CategoryEnum.添加合同附件.value()+"')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add-attach'\">添加合同附件</a>\n" );
        }


        sb.append("  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   附件类型:<input class=\"easyui-combobox\"  data-options=\"\n" +
                "\t\t\t\t\t\t\t\tvalueField: 'value',\n" +
                "\t\t\t\t\t\t\t\ttextField: 'label',\n" +
                "\t\t\t\t\t\t\t\teditable:false,\n" +
                "\t\t\t\t\t\t\t\twidth:200,\n" +
                "\t\t\t\t\t\t\t\tdata: [{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '全部',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: ''\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '资源附件',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '1111'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'IDC_ISP附件',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '2222'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '合同附件',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '4444'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '其他附件',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '3333'\n" +
                "\t\t\t\t\t\t\t\t}]\" id=\"attachmentType\"/>\n" );
        sb.append("  <a href=\"javascript:void(0);\" onclick=\"loadAttachmentGrid('"+prodInstId+"','"+id+"')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\">查询</a>\n" );

        sb.append("  </div>");
        sb.append("  </div>");
        //附件信息列表
        sb.append("\t\t\t <table class=\"easyui-datagrid\" id=\"attachmentTable\" data-options=\"singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true\"></table>\n");

        out.print(sb.toString());
    }

    public void writeHisCommentTag(JspWriter out,StringBuffer sb) throws Exception{
        sb = new StringBuffer();
        log.debug("历史审批");
        sb.append(
                "                <table class=\"easyui-datagrid\" data-options=\"url:'"+httpSuffix+"/actJBPMController/runProcCommentQueryData/"+this.ticketStatus+"/"+this.ticketInstId+"',queryParams:{},iconCls:'',border:false,autoRowHeight:true,title:'',singleSelect:true,striped:true,rownumbers:true,fitColumns:true\" fit=\"true\">\n" +
                "                  <thead>\n" +
                "                    <tr>\n" +
                 "                        <th data-options=\"field:'actName',width:100,halign:'left' \">名称</th>\n" +
                 "                        <th data-options=\"field:'author',width:100,halign:'left' \">处理人</th>\n" +
                "                        <th data-options=\"field:'comment',width:100,halign:'left' \">处理意见</th>\n" +
                "                        <th data-options=\"field:'status',width:130,halign:'left',formatter:function(value,row,index){return value == 1?'通过':'不通过';} \">状态</th>\n" +
                "                        <th data-options=\"field:'createTime',width:200,halign:'left',formatter:fmtDateAction \">时间</th>\n" +
                "                    </tr>\n" +
                "                  </thead>\n" +
                "                </table>\n"
                );
        out.print(sb.toString());
    }
    public void writeNetVIRTUALBtnServiceinfoTag(JspWriter out,StringBuffer sb,IdcNetServiceinfo idcNetServiceinfo) throws Exception{
        sb = new StringBuffer();
        log.debug("服务按钮");
        if(idcNetServiceinfo != null && idcNetServiceinfo.getOpenTime() != null) {
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                    <legend>&diams;服务信息</legend>\n" +
                    "                    <table class=\"kv-table\">\n" +
                    "                        <tr>\n" +
                    "                            <td class=\"kv-label\" style=\"width:215px\">服务开通时间</td>\n" +
                    "                            <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                                <input class=\"easyui-datetimebox\" readonly=\"readonly\"  name=\"idcNetServiceinfo.openTime\" value=\""+idcNetServiceinfo.getOpenTime()+"\" data-options=\"required:true,editable:false,showSeconds:true,width:350\">\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </table>\n" +
                    "                </fieldset>");
        }
        out.print(sb.toString());
    }
    public void writeNetVIRTUALServiceinfoTag(JspWriter out,StringBuffer sb,IdcNetServiceinfo idcNetServiceinfo) throws Exception{
        sb = new StringBuffer();
        log.debug("虚拟机服务");
        if(idcNetServiceinfo != null && "1".equals(idcNetServiceinfo.getIcpaccesstype())){
            sb.append("<div id=\"vm_content\">\n" +
                    "                <!-- 默认如果是虚拟主机  则需要显示 -->\n" +
                    "                    <fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                        <legend>&diams;虚拟机信息</legend>\n" +
                    "                        <table class=\"kv-table\">\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\" style=\"width: 200px;\">名称</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-textbox\" readonly=\"readonly\" name=\"idcNetServiceinfo.vmName\" value=\""+idcNetServiceinfo.getVmName()+"\"  id=\"idcNetServiceinfo.vmName\" data-options=\"required:true,validType:'length[0,128]',width:200\"/>\n" +
                    "                                </td>\n" +
                    "                                <td class=\"kv-label\">状态</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                    "                                               valueField: 'value',\n" +
                    "                                                   textField: 'label',\n" +
                    "                                                   required:true,\n" +
                    "                                                   editable:false,\n" +
                    "                                                   width:200,\n" +
                    "                                                   data: [{\n" +
                    "                                                   label: '可用',\n" +
                    "                                                   value: '1'\n" +
                    "                                               },{\n" +
                    "                                                   label: '不可用',\n" +
                    "                                                   value: '-1'\n" +
                    "                                               }]\" value=\""+idcNetServiceinfo.getVmStatus()+"\"  name=\"idcNetServiceinfo.vmStatus\" id=\"idcNetServiceinfo.vmStatus\"/>\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\">类型</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                    "                                                   valueField: 'value',\n" +
                    "                                                       textField: 'label',\n" +
                    "                                                       width:200,\n" +
                    "                                                      required:true,\n" +
                    "                                                       editable:false,\n" +
                    "                                                       data: [{\n" +
                    "                                                       label: '共享式',\n" +
                    "                                                       value: '1'\n" +
                    "                                                   },{\n" +
                    "                                                       label: '专用式',\n" +
                    "                                                       value: '2'\n" +
                    "                                                   },{\n" +
                    "                                                       label: '云虚拟',\n" +
                    "                                                       value: '3'\n" +
                    "                                                   }]\" value=\""+idcNetServiceinfo.getVmCategory()+"\"  name=\"idcNetServiceinfo.vmCategory\" id=\"idcNetServiceinfo.vmCategory\"/>\n" +
                    "                                </td>\n" +
                    "                                <td class=\"kv-label\">网络地址</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <!-- idcNetServiceinfo.vmNetAddr -->\n" +
                    "                                    <input class=\"easyui-textbox\"  readonly=\"readonly\" name=\"idcNetServiceinfo.vmNetAddr\" value=\""+idcNetServiceinfo.getVmNetAddr()+"\"  id=\"idcNetServiceinfo.vmNetAddr\" data-options=\"required:true,validType:'length[0,128]',width:200\"/>\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\">管理地址</td>\n" +
                    "                                <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                                    <input class=\"easyui-textbox\" readonly=\"readonly\" name=\"idcNetServiceinfo.vmManagerAddr\" value=\""+idcNetServiceinfo.getVmManagerAddr()+"\"  id=\"idcNetServiceinfo.vmManagerAddr\" data-options=\"required:true,validType:'length[0,128]',width:200\"/>\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                        </table>\n" +
                    "                    </fieldset>\n" +
                    "            </div>");
        }
        out.print(sb.toString());
    }

    public void writeTicketApplyAttachTag(JspWriter out,StringBuffer sb) throws Exception{
        sb = new StringBuffer();
        /*判断什么时候具有满意度显示情况*/
        IdcRunTicket idcRunTicket  = ((IdcRunTicket)ticketItem);
        /**
         * /*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束*/
        if(idcRunTicket.getTicketStatus() != 1 && 2==2/*最后一步骤*/){

        }

        sb.append("<tr>\n" +
                "                        <td class=\"kv-label\" style=\"width:100px\">满意度<span style=\"color:red\">&nbsp;*</span></td>\n" +
                "                        <td class=\"kv-content\" colspan=\"3\">\n" +
                "                            <!-- 提交状态：同意提交申请 暂不提交申请 -->\n" +
                "                            <input name=\"status\" id=\"idcRunProcCment_status_stand\" value=\"1\" type=\"hidden\"/>\n" +
                "                            <input name=\"starNum\" id=\"StarNum\" value=\""+satisfaction+"\" type=\"hidden\"/>\n" +
                "                            <div class=\"quiz\">\n" +
                "                                <div class=\"quiz_content\">\n" +
                "                                    <div class=\"goods-comm\">\n" +
                "                                        <div class=\"goods-comm-stars\">\n" +
                "                                            <span class=\"star_l\"></span>\n" +
                "                                            <div id=\"rate-comm-1\" class=\"rate-comm\"></div>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                </div><!--quiz_content end-->\n" +
                "                            </div><!--quiz end-->\n" +
                "                        </td>\n" +
                "                    </tr>");
        out.write(sb.toString());
    }
    public void writeNetServiceinfoTag(JspWriter out,StringBuffer sb,IdcNetServiceinfo idcNetServiceinfo) throws Exception{
        log.debug("服务信息表单情况");
        if(idcNetServiceinfo.getIcpsrvname() != null && !"".equalsIgnoreCase(idcNetServiceinfo.getIcpsrvname())){
            sb = new StringBuffer();
            log.debug("服务-----------");
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                    <legend>&diams;服务信息</legend>\n" +
                    "                    <table class=\"kv-table\">\n" +
                    "                        <tr>\n" +
                    "                            <td class=\"kv-label\" style=\"width: 200px;\">服务名称</td>\n" +
                    "                            <td class=\"kv-content\">\n" +
                   /* "                                <input type=\"hidden\" name=\"idcNetServiceinfo.id\" value=\""+idcNetServiceinfo.getId()+"\" id=\"idcNetServiceinfo.id\"/>\n" +*/
                    "                                <input name=\"idcNetServiceinfo.ticketInstId\" type=\"hidden\" value=\""+ticketInstId+"\">\n" +
                    "                                <input name=\"prodInstId\" type=\"hidden\" value=\""+prodInstId+"\">\n" +
                    "                                <input name=\"category\" type=\"hidden\" value=\""+category+"\">\n" +
                    "                                <input type=\"hidden\" value=\""+idcReProduct.getProdCategory()+"\" name=\"prodCategory\"/>\n" +
                    "                                <input name=\"isRejectTicket\" type=\"hidden\" value=\""+idcRunTicket.getTicketStatus()+"\">\n" +
                    "                                <input name=\"idcContract_ticketInstId\" type=\"hidden\" value=\""+ticketInstId+"\">\n" +
                    "                                <input name=\"idcNetServiceinfo.ticketInstId\" type=\"hidden\" value=\""+ticketInstId+"\">\n" +
                    "                                <input class=\"easyui-textbox\" readonly=\"readonly\"  name=\"idcNetServiceinfo.icpsrvname\" value=\""+idcNetServiceinfo.getIcpsrvname()+"\"  id=\"icpsrvname\" data-options=\"required:true,validType:'length[0,64]',width:200\"/>\n" +
                    "                            </td>\n" +
                    "                            <td class=\"kv-label\">所属用户</td>\n" +
                    "                            <td class=\"kv-content\">\n" +
                    "                                <input name=\"idcNetServiceinfo.customerId\" value=\""+idcReCustomer.getId()+"\" type=\"hidden\" />\n" +
                    "                                <input class=\"easyui-textbox\" readonly=\"readonly\"  readonly=\"readonly\" name=\"idcNetServiceinfo.customerName\" value=\""+idcReCustomer.getName()+"\"  id=\"idcNetServiceinfo.customerName\" data-options=\"required:true,validType:'length[0,128]',width:200\"/>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td class=\"kv-label\">服务内容</td>\n" +
                    "                            <td class=\"kv-content\">\n" +
                    "<input  id=\"ttFWNR\"  class=\"easyui-combotree\" readonly=\"readonly\" data-options=\"\n" +
                    "                                       valueField: 'value',\n" +
                    "                                           textField: 'label',\n" +
                    "                                           editable:false,\n" +
                    "                                           width:200,\n" +
                    "                                           url:'"+httpSuffix+"/assetBaseinfo/checkboxOpenMSG/9999'\" name=\"idcNetServiceinfo.icpsrvcontentcode\"  value=\""+idcNetServiceinfo.getIcpsrvcontentcode()+"\" />\n"+

                    "                            </td>\n" +
                    "                            <td class=\"kv-label\">备案类型</td>\n" +
                    "                            <td class=\"kv-content\">\n" +
                    "                                <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                    "                                            valueField: 'value',\n" +
                    "                                            textField: 'label',\n" +
                    "                                            required:true,\n" +
                    "                                            editable:false,\n" +
                    "                                            width:200,\n" +
                    "                                            data: [{\n" +
                    "                                                label: '无',\n" +
                    "                                                value: '0'\n" +
                    "                                            },{\n" +
                    "                                                label: '经营性网站',\n" +
                    "                                                value: '1'\n" +
                    "                                            },{\n" +
                    "                                                label: '非经营性网站',\n" +
                    "                                                value: '2'\n" +
                    "                                            },{\n" +
                    "                                                label: 'SP',\n" +
                    "                                                value: '3'\n" +
                    "                                            },{\n" +
                    "                                                label: 'BBS',\n" +
                    "                                                value: '4'\n" +
                    "                                            },{\n" +
                    "                                                label: '其它',\n" +
                    "                                                value: '999'\n" +
                    "                                            }]\" value=\""+idcNetServiceinfo.getIcprecordtype()+"\"  name=\"idcNetServiceinfo.icprecordtype\" id=\"idcNetServiceinfo.icprecordtype\"/>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td class=\"kv-label\">备案号[许可证号]</td>\n" +
                    "                            <td class=\"kv-content\">\n" +
                    "                                <input class=\"easyui-textbox\" readonly=\"readonly\" name=\"idcNetServiceinfo.icprecordno\" value=\""+idcNetServiceinfo.getIcprecordno()+"\"  id=\"icprecordno\" data-options=\"required:true,validType:'length[0,4000]',width:200\"/>\n" +
                    "                            </td>\n" +
                    "                            <td class=\"kv-label\">接入方式</td>\n" +
                    "                            <td class=\"kv-content\">\n" +
                    "                                <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                    "                                            valueField: 'value',\n" +
                    "                                            textField: 'label',\n" +
                    "                                            required:true,\n" +
                    "                                            width:200,\n" +
                    "                                            editable:false,\n" +
                    "                                            onChange:icpaccesstypeOnChange,\n" +
                    "                                            data: [{\n" +
                    "                                                label: '专线',\n" +
                    "                                                value: '0'\n" +
                    "                                            },{\n" +
                    "                                                label: '虚拟主机',\n" +
                    "                                                value: '1'\n" +
                    "                                            },{\n" +
                    "                                                label: '主机托管',\n" +
                    "                                                value: '2'\n" +
                    "                                            },{\n" +
                    "                                                label: '整机租用',\n" +
                    "                                                value: '3'\n" +
                    "                                            },{\n" +
                    "                                                label: '其它',\n" +
                    "                                                value: '999'\n" +
                    "                                            }]\" value=\""+idcNetServiceinfo.getIcpaccesstype()+"\"  name=\"idcNetServiceinfo.icpaccesstype\" id=\"icpaccesstype\"/>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td class=\"kv-label\">域名信息</td>\n" +
                    "                            <td class=\"kv-content\">\n" +
                    "                                <input class=\"easyui-textbox\" readonly=\"readonly\" name=\"idcNetServiceinfo.icpdomain\" value=\""+idcNetServiceinfo.getIcpdomain()+"\"  id=\"idcNetServiceinfo.icpdomain\" data-options=\"required:true,validType:'length[0,128]',width:200\"/>\n" +
                    "                            </td>\n" +
                    "                            <td class=\"kv-label\">业务类型</td>\n" +
                    "                            <td class=\"kv-content\">\n" +
                    "                                <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                    "                                            valueField: 'value',\n" +
                    "                                            textField: 'label',\n" +
                    "                                            required:true,\n" +
                    "                                            width:200,\n" +
                    "                                            editable:false,\n" +
                    "                                            data: [{\n" +
                    "                                                label: 'IDC业务',\n" +
                    "                                                value: '1'\n" +
                    "                                            },{\n" +
                    "                                                label: 'ISP业务',\n" +
                    "                                                value: '2'" +
                    "                                            }]\" value=\""+idcNetServiceinfo.getIcpbusinesstype()+"\"  name=\"idcNetServiceinfo.icpbusinesstype\" id=\"idcNetServiceinfo.icpbusinesstype\"/>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </table>\n" +
                    "                </fieldset>");
            out.write(sb.toString());
        }
        if(idcNetServiceinfo.getOpenTime() != null ){
            sb = new StringBuffer();
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                    <legend>&diams;服务信息</legend>\n" +
                    "                    <table class=\"kv-table\">\n" +
                    "                        <tr>\n" +
                    "                            <td class=\"kv-label\" style=\"width:215px\">服务开通时间</td>\n" +
                    "                            <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                                <input class=\"easyui-datetimebox\" readonly=\"readonly\"  name=\"idcNetServiceinfo.openTime\" value=\""+idcNetServiceinfo.getOpenTime()+"\" data-options=\"required:true,editable:false,showSeconds:true,width:350\">\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </table>\n" +
                    "                </fieldset>");
            out.write(sb.toString());
        }
        //如果开通开通服务时间是空  且  服务名称是null
        if( ( idcNetServiceinfo.getIcpsrvname() == null || "".equalsIgnoreCase(idcNetServiceinfo.getIcpsrvname()) )
                && idcNetServiceinfo.getOpenTime() == null
                ){
            sb = new StringBuffer();
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                    <legend>&diams;服务信息</legend>\n" +
                    "                    <table class=\"kv-table\">\n" +
                    "                        <tr>\n" +
                    "                            <td class=\"kv-label\" style=\"width:215px\">服务开通时间</td>\n" +
                    "                            <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                                <input class=\"easyui-datetimebox\" readonly=\"readonly\"  name=\"idcNetServiceinfo.openTime\" data-options=\"required:true,editable:false,showSeconds:true,width:350\">\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </table>\n" +
                    "                </fieldset>");
            out.write(sb.toString());
        }

        sb = new StringBuffer();
        log.debug("默认如果是虚拟主机  则需要显示");
        sb.append("<div id=\"vm_content\">");
        if(idcNetServiceinfo.getIcpaccesstype() != null && "1".equals(idcNetServiceinfo.getIcpaccesstype())){
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                        <legend>&diams;虚拟机信息</legend>\n" +
                    "                        <table class=\"kv-table\">\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\" style=\"width: 200px;\">名称</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-textbox\" readonly=\"readonly\" name=\"idcNetServiceinfo.vmName\" value=\""+idcNetServiceinfo.getVmName()+"\"  id=\"idcNetServiceinfo.vmName\" data-options=\"required:true,validType:'length[0,128]',width:150\"/>\n" +
                    "                                </td>\n" +
                    "                                <td class=\"kv-label\">状态</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                    "                                               valueField: 'value',\n" +
                    "                                                   textField: 'label',\n" +
                    "                                                   required:true,\n" +
                    "                                                   editable:false,\n" +
                    "                                                   width:150,\n" +
                    "                                                   data: [{\n" +
                    "                                                   label: '可用',\n" +
                    "                                                   value: '1'\n" +
                    "                                               },{\n" +
                    "                                                   label: '不可用',\n" +
                    "                                                   value: '-1'\n" +
                    "                                               }]\" value=\""+idcNetServiceinfo.getVmStatus()+"\"  name=\"idcNetServiceinfo.vmStatus\" id=\"idcNetServiceinfo.vmStatus\"/>\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\">类型</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                    "                                                   valueField: 'value',\n" +
                    "                                                       textField: 'label',\n" +
                    "                                                       width:150,\n" +
                    "                                                      required:true,\n" +
                    "                                                       editable:false,\n" +
                    "                                                       data: [{\n" +
                    "                                                       label: '共享式',\n" +
                    "                                                       value: '1'\n" +
                    "                                                   },{\n" +
                    "                                                       label: '专用式',\n" +
                    "                                                       value: '2'\n" +
                    "                                                   },{\n" +
                    "                                                       label: '云虚拟',\n" +
                    "                                                       value: '3'\n" +
                    "                                                   }]\" value=\""+idcNetServiceinfo.getVmCategory()+"\"  name=\"idcNetServiceinfo.vmCategory\" id=\"idcNetServiceinfo.vmCategory\"/>\n" +
                    "                                </td>\n" +
                    "                                <td class=\"kv-label\">网络地址</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <!-- idcNetServiceinfo.vmNetAddr -->\n" +
                    "                                    <input class=\"easyui-textbox\"  readonly=\"readonly\" name=\"idcNetServiceinfo.vmNetAddr\" value=\""+idcNetServiceinfo.getVmNetAddr()+"\"  id=\"idcNetServiceinfo.vmNetAddr\" data-options=\"required:true,validType:'length[0,128]',width:150\"/>\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\">管理地址</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-textbox\" readonly=\"readonly\" name=\"idcNetServiceinfo.vmManagerAddr\" value=\""+idcNetServiceinfo.getVmManagerAddr()+"\"  id=\"idcNetServiceinfo.vmManagerAddr\" data-options=\"required:true,validType:'length[0,128]',width:150\"/>\n" +
                    "                                </td>\n" +
                    "                                <td class=\"kv-label\"></td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                        </table>\n" +
                    "                    </fieldset>");
        }
        sb.append("</div>");
        out.write(sb.toString());
    }
    public void writeProductForceWriteTag(JspWriter out,StringBuffer sb,ProductCategory productCategory,Long ticketInstId) throws Exception{
        sb = new StringBuffer();
        isFormEdit = true;//控制可以编辑
        sb.append("<div title=\""+title+"\" data-options=\"closable:false\" style=\"overflow:auto;padding:10px;display:none;\">");
        //增加form的情况，目的是获取序列化
        sb.append("<form method=\"post\" id=\"productReWriteForm\">");//客户服务信息重新修改问题
        preProductInfo(sb,productCategory);//预受理
        custProductInfo(sb);//客户需求
        if(productCategory.getRack() != null && productCategory.getRack()){
            writeRackTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }else{
            sb.append("<div id=\"rack_fieldset_content_Id\"></div>");
        }
        if(productCategory.getPort() != null && productCategory.getPort()){
            writePortTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }else{
            sb.append("<div id=\"port_fieldset_content_Id\"></div>");
        }
        if(productCategory.getIp() != null && productCategory.getIp()){
            writeIpTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }else{
            sb.append("<div id=\"ip_fieldset_content_Id\"></div>");
        }
        if(productCategory.getServer() != null && productCategory.getServer()){
            writeServerTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }else{
            sb.append("<div id=\"server_fieldset_content_Id\"></div>");
        }
        if(productCategory.getAdd() != null && productCategory.getAdd()){
            writeReNewlyTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }else{
            sb.append("<div id=\"add_fieldset_content_Id\"></div>");
        }
        sb.append("</form>");
        sb.append("</div>");
        out.print(sb.toString());
    }

    /**
     * 客户需求
     */
    public void custProductInfo(StringBuffer sb){
        /*****其中存在是不是临时测试*******/
        String processdefinitonkey = null;//
        if(ticketItem instanceof IdcHisTicket){
            processdefinitonkey = ((IdcHisTicket)ticketItem).getProcessdefinitonkey();
        }else if(ticketItem instanceof IdcRunTicket){
            processdefinitonkey = ((IdcRunTicket)ticketItem).getProcessdefinitonkey();
        }
        /*****  类型情况 ******/
        /*if(processdefinitonkey != null && "idc_ticket_temporary".equals(processdefinitonkey)){
            log.debug("属于临时测试");
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "        <legend>&diams;服务项选择</legend>\n" +
                    "        <table class=\"kv-table\">\n" +
                    "            <tr>\n" +
                    "                <td colspan=\"8\">\n" +
                    "                    <div style=\"position:relative;margin: 17px 42px 3px 207px\"> \n" +
                    "                        <div style=\"float:left\">\n" +
                    "                             <span class=\"checkbackgrd\" id=\"port_check\">\n" +
                    "                                <input type=\"checkbox\" name=\"productCategory\" value=\"200\"  class=\"opacity_default_0\">端口带宽\n" +
                    "                            </span>\n" +
                    "                        </div>\n" +
                    "                        <div style=\"float:left\">\n" +
                    "                             <span class=\"checkbackgrd\" id=\"ip_check\">\n" +
                    "                                <input type=\"checkbox\"   name=\"productCategory\" value=\"300\" class=\"opacity_default_0\">IP租用\n" +
                    "                            </span>\n" +
                    "                        </div> \n" +
                    "                        <div style=\"float:left\">\n" +
                    "                             <span class=\"checkbackgrd\" id=\"server_check\">\n" +
                    "                                <input type=\"checkbox\"   name=\"productCategory\" value=\"400\"  class=\"opacity_default_0\">测试主机\n" +
                    "                             </span>\n" +
                    "                        </div> \n" +
                    "                    </div>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "    </fieldset>");
        }else{
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "        <legend>&diams;服务项选择</legend>\n" +
                    "        <table class=\"kv-table\">\n" +
                    "            <tr>\n" +
                    "                <td colspan=\"8\">\n" +
                    "                    <div style=\"position:relative;margin: 17px 42px 3px 207px\">\n" +
                    "                        <div style=\"float:left\">\n" +
                    "                                <span class=\"checkbackgrd\" id=\"rack_check\">\n" +
                    "                                    <input type=\"checkbox\" name=\"productCategory\" value=\"100\" class=\"opacity_default_0\">机架机位\n" +
                    "                                </span>\n" +
                    "                        </div>\n" +
                    "                        <div style=\"float:left\">\n" +
                    "                             <span class=\"checkbackgrd\" id=\"port_check\">\n" +
                    "                                <input type=\"checkbox\" name=\"productCategory\" value=\"200\"  class=\"opacity_default_0\">端口带宽\n" +
                    "                            </span>\n" +
                    "                        </div>\n" +
                    "                        <div style=\"float:left\">\n" +
                    "                             <span class=\"checkbackgrd\" id=\"ip_check\">\n" +
                    "                                <input type=\"checkbox\"   name=\"productCategory\" value=\"300\" class=\"opacity_default_0\">IP租用\n" +
                    "                            </span>\n" +
                    "                        </div>\n" +
                    "                        <div style=\"float:left\">\n" +
                    "                             <span class=\"checkbackgrd\" id=\"server_check\">\n" +
                    "                                <input type=\"checkbox\"   name=\"productCategory\" value=\"400\"  class=\"opacity_default_0\">主机租赁\n" +
                    "                            </span>\n" +
                    "                        </div>\n" +
                    "                        <div style=\"float:left\">\n" +
                    "                                 <span class=\"checkbackgrd\" id=\"add_check\">\n" +
                    "                                    <input type=\"checkbox\"  name=\"productCategory\" value=\"500\" class=\"opacity_default_0\">增值业务\n" +
                    "                                </span>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "    </fieldset>");

        }*/
    }
    /*
        预受理:只要具有开通信息
     */
    public String preProductInfo(StringBuffer sb,ProductCategory productCategory){
        IdcReProduct idcReProduct = idcReProductService.getFilterModelByProductId(productCategory.getProdInstId());
        String isReadOnly = isFormEdit?"":"readonly=\"readonly\"";
        //isReadOnly=" ";
        String applyerName = idcReProduct.getApplyerName()==null?"":idcReProduct.getApplyerName();
        String applyerRoles = idcReProduct.getApplyerRoles()==null?"":idcReProduct.getApplyerRoles();
        String applyerPhone = idcReProduct.getApplyerPhone()==null?"":idcReProduct.getApplyerPhone();

        String ticketCategoryTo = "";
        String ticketCategoryFrom = "";
        String ticketInstId = "";
        String prodCategory = "";
        String prodInstId = "";

        if(ticketItem instanceof IdcHisTicket){
            ticketCategoryTo  = ((IdcHisTicket)ticketItem).getTicketCategory();
            ticketCategoryFrom  = ((IdcHisTicket)ticketItem).getTicketCategoryFrom();
            if(((IdcHisTicket)ticketItem).getId() != null ){
                ticketInstId  = String.valueOf(((IdcHisTicket)ticketItem).getId());
            }
            prodCategory = ((IdcHisTicket)ticketItem).getProdCategory();
            if(((IdcHisTicket)ticketItem).getProdInstId() != null ){
                prodInstId  = String.valueOf(((IdcHisTicket)ticketItem).getProdInstId());
            }
        }else if(ticketItem instanceof IdcRunTicket){
            ticketCategoryTo  = ((IdcRunTicket)ticketItem).getTicketCategory();
            ticketCategoryFrom  = ((IdcRunTicket)ticketItem).getTicketCategoryFrom();
            if(((IdcRunTicket)ticketItem).getId() != null ){
                ticketInstId  = String.valueOf(((IdcRunTicket)ticketItem).getId());
            }
            prodCategory = ((IdcRunTicket)ticketItem).getProdCategory();
            if(((IdcRunTicket)ticketItem).getProdInstId() != null ){
                prodInstId  = String.valueOf(((IdcRunTicket)ticketItem).getProdInstId());
            }
        }
        //如果是业务变更过程。则属于只读状态。
        if(ticketCategoryTo != ticketCategoryFrom && ticketCategoryFrom.equals(CategoryEnum.开通流程.value()) && ticketCategoryTo.equals(CategoryEnum.业务变更流程.value())){
            isReadOnly = "readonly=\"readonly\"";
        }
        sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                "            <legend>&diams;预受理信息表</legend>\n" +
                "            <table class=\"kv-table\">\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\" style=\"width: 120px;\">业务名称</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input "+" readonly=\"readonly\" id=\"busName\" name=\"busName\" value=\""+idcReProduct.getBusName()+"\" class=\"easyui-textbox\" data-options=\"required:false,width:150\">\n" +
                "                    </td>\n" +
                "                    <td class=\"kv-label\" style=\"width: 120px;\">意向IDC</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input readonly=\"readonly\" class=\"easyui-combobox\" "+isReadOnly+" data-options=\"\n" +
                "                                       valueField: 'value',\n" +
                "                                           textField: 'label',\n" +
                "                                           editable:false,\n" +
                "                                           width:200,\n" +
                "                                           url:'"+httpSuffix+"/assetBaseinfo/getIDC/"+idcReProduct.getIdcName()+"'\" name=\"idcName\"  value=\""+idcReProduct.getIdcName()+"\"  />\n" +
                "                    </td>\n" +
                "                    <td class=\"kv-label\" style=\"width: 120px;\">预占有效天数</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input "+isReadOnly+" id=\"validity\" name=\"validity\" value=\""+idcReProduct.getValidity()+"\" class=\"easyui-numberbox\" data-options=\"required:false,validType:'length[0,4]',width:150\">\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\" style=\"width: 120px;\" >申请人</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input readonly=\"readonly\" name=\"applyerName\" value=\""+applyerName +"\" class=\"easyui-textbox\" data-options=\"width:150\">\n" +
                "                    </td>\n" +
                "                    <td class=\"kv-label\" style=\"width: 120px;\">申请人角色</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input readonly=\"readonly\" name=\"applyerRoles\" value=\""+applyerRoles+"\" class=\"easyui-textbox\" data-options=\"width:200\">\n" +
                "\n" +
                "                    </td>\n" +
                "                    <td class=\"kv-label\" style=\"width: 120px;\">联系电话</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input "+isReadOnly+" name=\"applyerPhone\" value=\""+applyerPhone+"\" class=\"easyui-textbox\" data-options=\"width:150\">\n" +
                "                        <input type=\"hidden\" value=\""+idcReProduct.getId()+"\" name=\"id\"/>\n" +
                "                        <input type=\"hidden\" value=\""+idcReProduct.getCustomerName()+"\" id=\"customerName\"/>\n" +
                "                        <input type=\"hidden\" name=\"customerId\" value=\""+idcReProduct.getCustomerId()+"\" id=\"customerId\"/>\n" +
                //业务变更工单.....
                "                <input type=\"hidden\" name=\"idcRunTicket.ticketCategoryTo\" value=\""+ticketCategoryTo+"\"/>\n" +
                "                <input type=\"hidden\" name=\"idcRunTicket.ticketCategoryFrom\" value=\""+ticketCategoryFrom+"\"/>\n" +
                "                <!-- 已经进入开通状态后的工单 -->\n" +
                "                <input type=\"hidden\" name=\"idcRunTicket.ticketInstId\" value=\""+ticketInstId+"\">\n" +
                "                <input type=\"hidden\" name=\"idcRunTicket.prodCategory\" value=\""+prodCategory+"\"/>\n" +
                "                <input type=\"hidden\" name=\"idcRunTicket.prodInstId\" value=\""+prodInstId+"\"/>\n" +

                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </fieldset>");
        return sb.toString();
    }
    public void writeProductTag(JspWriter out,StringBuffer sb,ProductCategory productCategory,Long ticketInstId) throws Exception{
        isFormEdit = false;
        sb = new StringBuffer();
        sb.append("<div title=\""+title+"\" data-options=\"closable:false\" style=\"overflow:auto;padding:10px;display:none;\">");
        preProductInfo(sb,productCategory);//预受理
        if(productCategory.getRack() != null && productCategory.getRack()){
            writeRackTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }
        if(productCategory.getPort() != null && productCategory.getPort()){
            writePortTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }
        if(productCategory.getIp() != null && productCategory.getIp()){
            writeIpTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }
        if(productCategory.getServer() != null && productCategory.getServer()){
            writeServerTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }
        if(productCategory.getAdd() != null && productCategory.getAdd()){
            writeReNewlyTag(out,sb,productCategory.getProdInstId(),ticketInstId);
        }
        sb.append("</div>");
        out.write(sb.toString());
    }
    /**
     * 显示客户信息
     * @param out
     * @param sb
     * @throws Exception
     */
    public void writeCustomTag(JspWriter out,StringBuffer sb,IdcReCustomer idcReCustomer,String prodCategory) throws Exception{
        log.debug("客户信息");
        sb = new StringBuffer();
        sb.append("<div title=\""+title+"\" style=\"padding:10px;display:none;\">");
        if(ticketInstId != null && !"".equalsIgnoreCase(ticketInstId)){
            sb.append("<input id=\"ticketInstId_param\" value=\""+ticketInstId+"\"  type=\"hidden\"/>");
        }
        String sla = idcReCustomer.getSla()==null?"":idcReCustomer.getSla();
        String category = idcReCustomer.getCategory()==null?"":idcReCustomer.getCategory();
        String tracestate = idcReCustomer.getTracestate()==null?"":idcReCustomer.getTracestate();
        String customerlevel = idcReCustomer.getCustomerlevel()==null?"":idcReCustomer.getCustomerlevel();
        String contactEmail = idcReCustomer.getContactEmail()==null?"":idcReCustomer.getContactEmail();
        String contactMobile = idcReCustomer.getContactMobile()==null?"":idcReCustomer.getContactMobile();
        String contactPhone = idcReCustomer.getContactPhone()==null?"":idcReCustomer.getContactPhone();
        String contactIdcardno = idcReCustomer.getContactIdcardno()==null?"":idcReCustomer.getContactIdcardno();
        String contactIdcardtype = idcReCustomer.getContactIdcardtype()==null?"":idcReCustomer.getContactIdcardtype();
        sb.append("<fieldset style=\"border-color: #c0e7fb;\" class=\"fieldsetCls\">\n" +
                "            <legend>&diams;客户信息</legend>\n" +
                "            <table class=\"kv-table\" style=\"width: 100%;height: 100%\">\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\" style=\"width: 200px;\">单位名称</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input type=\"hidden\" name=\"idcReCustomer.id\" value=\""+idcReCustomer.getId()+"\" id=\"idcReCustomer_id\"/>\n" +
                "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"name\" value=\""+idcReCustomer.getName()+"\"  id=\"name\" data-options=\"validType:'length[0,64]'\"/>\n" +
                "                    </td>\n" +
                "                    <td class=\"kv-label\">单位属性</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                "\t\t\t\t\t\t\t\tvalueField: 'value',\n" +
                "\t\t\t\t\t\t\t\ttextField: 'label',\n" +
                "\t\t\t\t\t\t\t\teditable:false,\n" +
                "\t\t\t\t\t\t\t\trequired:true\n" +
                "\t\t\t\t\t\t\t\t,width:150,\n" +
                "\t\t\t\t\t\t\t\tdata: [{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '军队',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '1'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '政府机关',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '2'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '事业单位',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '3'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '企业',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '4'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '个人',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '5'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '社会团体',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '6'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '其他',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '999'\n" +
                "\t\t\t\t\t\t\t\t}]\" value=\""+idcReCustomer.getAttribute()+"\"  name=\"attribute\" id=\"attribute\"/>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\">证件类型</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <!-- 下拉框 -->\n" +
                "                        <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                "\t\t\t\t\t\t\t\tvalueField: 'value',\n" +
                "\t\t\t\t\t\t\t\ttextField: 'label',\n" +
                "\t\t\t\t\t\t\t\teditable:false,\n" +
                "\t\t\t\t\t\t\t\trequired:true,width:150,\n" +
                "\t\t\t\t\t\t\t\tdata: [{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '工商营业执照',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '1'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '身份证',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '2'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '组织机构代码证书',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '3'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '事业法人证书',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '4'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '军队代号',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '5'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '社团法人证书',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '6'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '护照',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '7'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '军官证',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '8'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '台胞证',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '9'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '其他',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '999'\n" +
                "\t\t\t\t\t\t\t\t}]\" value=\""+idcReCustomer.getIdcardtype()+"\"  name=\"idcardtype\" id=\"idcardtype\"/>\n" +
                "                    </td>\n" +
                "\n" +
                "                    <td class=\"kv-label\">证件号</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"idcardno\" value=\""+idcReCustomer.getIdcardno()+"\"  id=\"idcardno\" data-options=\"validType:'length[0,64]'\"/>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\">邮政编码</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"zipcode\" value=\""+idcReCustomer.getZipcode()+"\"  id=\"zipcode\" data-options=\"validType:'length[0,6]'\"/>\n" +
                "                    </td>\n" +
                "\n" +
                "                    <td class=\"kv-label\">注册时间</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"createTimeStr\" value=\""+idcReCustomer.getCreateTimeStr()+"\"  id=\"createTimeStr\" data-options=\"validType:'length[0,128]'\"/>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\">单位地址</td>\n" +
                "                    <td class=\"kv-content\" colspan=\"3\">\n" +
                "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:592\" name=\"addr\" value=\""+idcReCustomer.getAddr()+"\"  id=\"addr\" data-options=\"validType:'length[0,128]'\"/>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </fieldset>　\n" );

        /*只有政企业务，才需要显示客户的联系人信息。自有业务，不需要显示中国移动的联系人信息*/
        if(prodCategory != null && prodCategory.equalsIgnoreCase(CategoryEnum.政企业务.value())){
                sb.append(
                        "        <fieldset style=\"border-color: #c0e7fb;\" class=\"fieldsetCls\">\n" +
                        "            <legend>&diams;客户联系人</legend>\n" +
                        "            <table class=\"kv-table\">\n" +
                        "                <tr>\n" +
                        "                    <td class=\"kv-label\" style=\"width: 200px;\">联系人</td>\n" +
                        "                    <td class=\"kv-content\">\n" +
                        "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"contactname\" value=\""+idcReCustomer.getContactname()+"\"  id=\"contactname\" data-options=\"validType:'length[0,64]'\"/>\n" +
                        "                    </td>\n" +
                        "                    <td class=\"kv-label\">证件类型</td>\n" +
                        "                    <td class=\"kv-content\">\n" +
                        "                        <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                        "\t\t\t\t\t\t\t\tvalueField: 'value',\n" +
                        "\t\t\t\t\t\t\t\ttextField: 'label',\n" +
                        "\t\t\t\t\t\t\t\teditable:false,\n" +
                        "\t\t\t\t\t\t\t\trequired:true,width:150,\n" +
                        "\t\t\t\t\t\t\t\tdata: [{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '工商营业执照',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '1'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '身份证',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '2'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '组织机构代码证书',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '3'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '事业法人证书',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '4'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '军队代号',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '5'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '社团法人证书',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '6'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '护照',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '7'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '军官证',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '8'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '台胞证',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '9'\n" +
                        "\t\t\t\t\t\t\t\t},{\n" +
                        "\t\t\t\t\t\t\t\t\tlabel: '其他',\n" +
                        "\t\t\t\t\t\t\t\t\tvalue: '999'\n" +
                        "\t\t\t\t\t\t\t\t}]\" value=\""+contactIdcardtype+"\"  name=\"contactIdcardtype\" id=\"contactIdcardtype\"/>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td class=\"kv-label\">证件号</td>\n" +
                        "                    <td class=\"kv-content\">\n" +
                        "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"contactIdcardno\" value=\""+contactIdcardno+"\"  id=\"contactIdcardno\" data-options=\"validType:'length[0,64]'\"/>\n" +
                        "                    </td>\n" +
                        "\n" +
                        "                    <td class=\"kv-label\">固定电话</td>\n" +
                        "                    <td class=\"kv-content\">\n" +
                        "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"contactPhone\" value=\""+contactPhone+"\"  id=\"contactPhone\" data-options=\"validType:'length[0,15]'\"/>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td class=\"kv-label\">移动号码</td>\n" +
                        "                    <td class=\"kv-content\">\n" +
                        "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"contactMobile\" value=\""+contactMobile+"\"  id=\"contactMobile\" data-options=\"validType:'length[0,15]'\"/>\n" +
                        "                    </td>\n" +
                        "\n" +
                        "                    <td class=\"kv-label\">邮箱</td>\n" +
                        "                    <td class=\"kv-content\">\n" +
                        "                        <input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" name=\"contactEmail\" value=\""+contactEmail+"\"  id=\"contactEmail\" data-options=\"validType:'length[0,64]'\"/>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </fieldset>\n" );

        }

        sb.append(
                "        <fieldset style=\"border-color: #c0e7fb;\" class=\"fieldsetCls\">\n" +
                "            <legend>&diams;附加类型</legend>\n" +
                "            <table class=\"kv-table\">\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\" style=\"width: 200px;\">客户级别</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                "\t\t\t\t\t\t\t\tvalueField: 'value',\n" +
                "\t\t\t\t\t\t\t\ttextField: 'label',\n" +
                "\t\t\t\t\t\t\t\teditable:false,width:150,\n" +
                "\t\t\t\t\t\t\t\tdata: [{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'A类个人客户',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '10'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'B类个人客户',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '20'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'C类个人客户',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '30'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'A类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '40'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'A1类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '50'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'A2类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '60'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'B类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '70'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'B1类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '80'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'B2类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '90'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'C类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '100'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'D类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '110'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: 'Z类集团',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '120'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '其他',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '999'\n" +
                "\t\t\t\t\t\t\t\t}]\" value=\""+customerlevel+"\"  name=\"customerlevel\" id=\"customerlevel\"/>\n" +
                "                    </td>\n" +
                "                    <td class=\"kv-label\">跟踪状态</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                "\t\t\t\t\t\t\t\tvalueField: 'value',\n" +
                "\t\t\t\t\t\t\t\ttextField: 'label',width:150,\n" +
                "\t\t\t\t\t\t\t\teditable:false,\n" +
                "\t\t\t\t\t\t\t\tdata: [{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '跟踪状态',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '10'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '有意向',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '20'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '继续跟踪',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '30'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '无意向',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '40'\n" +
                "\t\t\t\t\t\t\t\t}]\" value=\""+tracestate+"\"  name=\"tracestate\" id=\"tracestate\"/>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\">服务等级</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                "\t\t\t\t\t\t\t\tvalueField: 'value',\n" +
                "\t\t\t\t\t\t\t\ttextField: 'label',width:150,\n" +
                "\t\t\t\t\t\t\t\teditable:false,\n" +
                "\t\t\t\t\t\t\t\tdata: [{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '金牌',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '10'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '银牌',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '20'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '铜牌',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '30'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '铁牌',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '40'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '标准',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '999'\n" +
                "\t\t\t\t\t\t\t\t}]\" value=\""+sla+"\"  name=\"sla\" id=\"sla\"/>\n" +
                "                    </td>\n" +
                "                    <td class=\"kv-label\">客户类别</td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-combobox\" readonly=\"readonly\" data-options=\"\n" +
                "\t\t\t\t\t\t\t\tvalueField: 'value',\n" +
                "\t\t\t\t\t\t\t\ttextField: 'label',width:150,\n" +
                "\t\t\t\t\t\t\t\teditable:false,\n" +
                "\t\t\t\t\t\t\t\tdata: [{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '局方',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '10'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '第三方',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '20'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '测试',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '30'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '退场客户',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '40'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '国有',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '50'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '集体',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '60'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '私营',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '70'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '股份',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '80'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '外商投资',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '90'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '港澳台投资',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '100'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '客户',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '110'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '自由合作',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '120'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '内容引入',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '130'\n" +
                "\t\t\t\t\t\t\t\t},{\n" +
                "\t\t\t\t\t\t\t\t\tlabel: '其他',\n" +
                "\t\t\t\t\t\t\t\t\tvalue: '999'\n" +
                "\t\t\t\t\t\t\t\t}]\" value=\""+category+"\"  name=\"category\" id=\"category\"/>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </fieldset>");
        sb.append("</div>");
        out.print(sb.toString());
    }
    public void writeContractTag(JspWriter out,StringBuffer sb,IdcContract idcContract,IdcNetServiceinfo idcNetServiceinfo) throws Exception{
        sb = new StringBuffer();
        sb.append("<div title=\"开通信息\" data-options=\"closable:false\" style=\"overflow:auto;padding:10px;display:none;\">");
        //String addAttachHtmlClick  = isHasOpenEdit?"<span class=\"icon_item_apply legend-cls\" onclick=\"addAttachContract()\">附件</span>&nbsp;&nbsp;&nbsp;]":"";
        //String addAttachHtmlClick  = isHasOpenEdit?"<span class=\"icon_item_apply legend-cls\" onclick=\"addAttachContract()\">附件</span>&nbsp;&nbsp;&nbsp;]":"";
        String isReadOnly = isHasOpenEdit?"":"readonly=\"readonly\"";
        String ticketCategoryTo = "";
        String ticketCategoryFrom = "";
        String ticketInstId = "";
        String prodCategory = "";
        String prodInstId = "";
        String formKey = "";

        if(ticketItem instanceof IdcHisTicket){
            ticketCategoryTo  = ((IdcHisTicket)ticketItem).getTicketCategory();
            ticketCategoryFrom  = ((IdcHisTicket)ticketItem).getTicketCategoryFrom();
            if(((IdcHisTicket)ticketItem).getId() != null ){
                ticketInstId  = String.valueOf(((IdcHisTicket)ticketItem).getId());
            }
            prodCategory = ((IdcHisTicket)ticketItem).getProdCategory();
            if(((IdcHisTicket)ticketItem).getProdInstId() != null ){
                prodInstId  = String.valueOf(((IdcHisTicket)ticketItem).getProdInstId());
            }
        }else if(ticketItem instanceof IdcRunTicket){
            ticketCategoryTo  = ((IdcRunTicket)ticketItem).getTicketCategory();
            ticketCategoryFrom  = ((IdcRunTicket)ticketItem).getTicketCategoryFrom();
            formKey=((IdcRunTicket)ticketItem).getFormKey();
            if(((IdcRunTicket)ticketItem).getId() != null ){
                ticketInstId  = String.valueOf(((IdcRunTicket)ticketItem).getId());
            }
            prodCategory = ((IdcRunTicket)ticketItem).getProdCategory();
            if(((IdcRunTicket)ticketItem).getProdInstId() != null ){
                prodInstId  = String.valueOf(((IdcRunTicket)ticketItem).getProdInstId());
            }
        }
        if(ticketCategoryFrom != null && ticketCategoryTo != null &&
                !ticketCategoryFrom.equals(ticketCategoryTo) && "100".equals(ticketCategoryFrom) &&
                formKey.equals("open_accept_apply_form") ){
            idcContract = new IdcContract();
            idcNetServiceinfo = new IdcNetServiceinfo();
        }

        String openTimeStr = idcNetServiceinfo.getOpenTimeStr()!=null?idcNetServiceinfo.getOpenTimeStr():"";
        Long idcNetServiceinfoId = idcNetServiceinfo.getId()!=null?idcNetServiceinfo.getId(): null;
        String contractName = idcContract.getContractname()==null?"":idcContract.getContractname();
        String contractno = idcContract.getContractno()==null?"":idcContract.getContractno();
        String contractstartStr = idcContract.getContractstartStr()==null?"":idcContract.getContractstartStr();
        Long contractperiod = idcContract.getContractperiod()==null?0:idcContract.getContractperiod();
        String contractsigndateStr = idcContract.getContractsigndateStr()==null?"":idcContract.getContractsigndateStr();
        String contractexpirreminder = idcContract.getContractexpirreminder()==null?"":idcContract.getContractexpirreminder();
        Float contractprice = idcContract.getContractprice()==null?0:idcContract.getContractprice().floatValue();
        String contractremark = idcContract.getContractremark()==null?"":idcContract.getContractremark();
        Long domainStatus = idcNetServiceinfo.getDomainStatus();//是否是域名的情况
        Long contractid = idcContract.getId();
        sb.append("<form id=\"contractFrom\" method=\"post\"  enctype=\"multipart/form-data\" >");

        String isDomainStatusCls = "";//是域名用户
        String isNotDomainStatusCls = "";//不是域名用户
        String domainStatusStr = "0";
        if(domainStatus == null){
            isDomainStatusCls = "on_check";
            isNotDomainStatusCls ="on_not_check";
        }else if(domainStatus == 0){
            isDomainStatusCls = !isHasOpenEdit?"on_check_disabled disabled_check":"on_check";
            isNotDomainStatusCls = !isHasOpenEdit?"disabled_not_check disabled_check":"on_not_check";
        }else{
            isNotDomainStatusCls = !isHasOpenEdit?"on_check_disabled disabled_check":"on_check";
            isDomainStatusCls = !isHasOpenEdit?"disabled_not_check disabled_check":"on_not_check";
            domainStatusStr = "1";
        }
        //得到合同的列表
        List<AssetAttachmentinfo> attachmentinfoList = assetAttachmentinfoService.getAttachmentinfoByTicketIdList(null,prodInstId,IdcContract.tableName);

        //只有政企业务才有合同信息
        if(!prodCategory.equals("0")){
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "        <legend>&diams;合同信息 &nbsp;" + "</legend>\n" +
                    /*"        <legend>&diams;合同信息[ &nbsp;&nbsp;&nbsp;"+addAttachHtmlClick+"</legend>\n" +*/
                    "        <table class=\"kv-table\">\n" +
                    "            <tr>\n" );
            if(contractid != null && contractid != 0){
                sb.append("                <input type=\"hidden\" name=\"id\" value=\""+contractid+"\"/>\n");
            }

                sb.append("                <!-- 工单类型 -->\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.ticketCategoryTo\" value=\""+ticketCategoryTo+"\"/>\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.ticketCategoryFrom\" value=\""+ticketCategoryFrom+"\"/>\n" +
                    "                <!-- 已经进入开通状态后的工单 -->\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.ticketInstId\" id=\"idcRunTicket_ticketInstId\" value=\""+ticketInstId+"\">\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.prodCategory\" id=\"idcRunTicket_prodCategory\" value=\""+prodCategory+"\"/>\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.prodInstId\"  id=\"idcRunTicket_prodInstId\"  value=\""+prodInstId+"\"/>\n"
                );


            //这个判断很复杂，不要动哦。否则开能正常了，那么变更开通肯定要出问题的呢！！！
            /*if(!ticketCategoryFrom.equals(CategoryEnum.预堪预占流程.value())){
                sb.append(
                        "                <input type=\"hidden\" name=\"idcNetServiceinfo.id\"  id=\"idcNetServiceinfo_id\"  value=\""+idcNetServiceinfoId+"\"/>\n"
                );
            }*/



            sb.append(
                    "\n" +
                    "                <td class=\"kv-label\" style=\"width: 200px;\">合同名称<span style=\"color:red\">&nbsp;*</span></td>\n" +
                    "                <td class=\"kv-content\">\n" +
                    "                    <input class=\"easyui-textbox\" name=\"contractname\" "+isReadOnly+" value=\""+contractName+"\"  id=\"contractname\" data-options=\"width:250\"/>\n" +
                    "                </td>\n" +
                    "                <td class=\"kv-label\">合同编码<span style=\"color:red\">&nbsp;*</span></td>\n" +
                    "                <td class=\"kv-content\">\n" +
                    "                    <input class=\"easyui-textbox\" name=\"contractno\" "+isReadOnly+" value=\""+contractno+"\"  id=\"contractno\" data-options=\"width:250\"/>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <td class=\"kv-label\">客户信息</td>\n" +
                    "                <td class=\"kv-content\">\n" +
                    "                    <!-- 客户附加信息 -->\n" +
                    "                    <input name=\"idcReCustomer.id\" value=\""+idcReCustomer.getId()+"\" type=\"hidden\">\n" +
                    "                    <input name=\"idcReCustomer.customerName\" "+isReadOnly+" value=\""+idcReCustomer.getName()+"\" class=\"easyui-textbox\" data-options=\"editable:false,width:250\">\n" +
                    "                </td>\n" +
                    "                <td class=\"kv-label\">开始时间<span style=\"color:red\">&nbsp;*</span></td>\n" +
                    "                <td class=\"kv-content\">\n" +
                    "                    <input class=\"easyui-datetimebox\"  "+isReadOnly+" name=\"contractstart\" value=\""+contractstartStr+"\" data-options=\"editable:false,showSeconds:true,width:250\">\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <td class=\"kv-label\">合同期限(月)<span style=\"color:red\">&nbsp;*</span></td>\n" +
                    "                <td class=\"kv-content\">\n" +
                    "                    <input class=\"easyui-numberbox\" "+isReadOnly+" name=\"contractperiod\" value=\""+contractperiod+"\" id=\"contractperiod\" data-options=\"precision:0,min:0,validType:'length[0,3]',width:250\"/>\n" +
                    "                </td>\n" +
                    "\n" +
                    "                <td class=\"kv-label\">签订日期<span style=\"color:red\">&nbsp;*</span></td>\n" +
                    "                <td class=\"kv-content\">\n" +
                    "                    <input class=\"easyui-datetimebox\" "+isReadOnly+" name=\"contractsigndate\" value=\""+contractsigndateStr+"\" data-options=\"editable:false,showSeconds:true,width:250\">\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <td class=\"kv-label\">到期提醒方案<span style=\"color:red\">&nbsp;*</span></td>\n" +
                    "                <td class=\"kv-content\">\n" +
                    "                    <input class=\"easyui-combobox\" data-options=\"\n" +
                    "                                        valueField: 'value',\n" +
                    "                                        textField: 'label',\n" +
                    "                                        editable:false,\n" +
                    "                                        width:250,\n" +
                    "                                        data: [{\n" +
                    "                                            label: '联系客户经理',\n" +
                    "                                            value: '0'\n" +
                    "                                        },{\n" +
                    "                                            label: '短信、邮件',\n" +
                    "                                            value: '1'\n" +
                    "                                        }]\" "+isReadOnly +" value=\""+contractexpirreminder+"\"  name=\"contractexpirreminder\" id=\"contractexpirreminder\"/>\n" +
                    "                </td>\n" +
                    "                <td class=\"kv-label\">价格(元)<span style=\"color:red\">&nbsp;*</span></td>\n" +
                    "                <td class=\"kv-content\">\n" +
                    "                    <input class=\"easyui-numberbox\" name=\"contractprice\" "+isReadOnly+" value=\""+contractprice+"\" id=\"contractprice\" data-options=\"width:250,min:0,validType:'length[0,9]'\"/>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <td class=\"kv-label\">合同备注</td>\n" +
                    "                <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                    <input class=\"easyui-textbox\" multiline=\"true\" "+isReadOnly+"  name=\"contractremark\"  value=\""+contractremark+"\" style=\"width:83%;height:50px\" data-options=\"validType:'length[0,255]'\">\n" +
                    "                </td>\n" +
                    "            </tr>");

            sb.append("</table>\n" +
            "    </fieldset>");
        }else{

            sb.append("                <!-- 工单类型 -->\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.ticketCategoryTo\" value=\""+ticketCategoryTo+"\"/>\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.ticketCategoryFrom\" value=\""+ticketCategoryFrom+"\"/>\n" +
                    "                <!-- 已经进入开通状态后的工单 -->\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.ticketInstId\" value=\""+ticketInstId+"\">\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.prodCategory\" value=\""+prodCategory+"\"/>\n" +
                    "                <input type=\"hidden\" name=\"idcRunTicket.prodInstId\" value=\""+prodInstId+"\"/>\n");
        }

        sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "        <legend>&diams;选择域名信息</legend>\n" +
                    "        <table class=\"kv-table\">\n" +
                    "            <tr>\n" +

                    "                <td class=\"kv-label\" style=\"width:215px\">域名信息<span style=\"color:red\">&nbsp;*</span>" +
                    "                    <input type=\"hidden\"  id=\"idcNetServiceinfo_domainStatus\" name=\"idcNetServiceinfo.domainStatus\" value=\""+domainStatusStr+"\"   "+
                    "               </td>\n" +
                    "                <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                    <div style=\"position:relative;margin: 0px;\" class=\"checkboxGrpCls\" >\n" +
                    "                        <div style=\"float:left\">\n" +
                    "                            <span class=\"checkbackgrd "+isDomainStatusCls+" \" id=\"other_check\">\n" +
                    "                                <input type=\"checkbox\"  name=\"domainStatus\" id=\"qtNetServiceId\" value=\"0\" class=\"opacity_default_0 \">其他用户\n" +
                    "                            </span>\n" +
                    "                        </div>\n" +
                    "                        <div style=\"float:left\">\n" +
                    "                             <span class=\"checkbackgrd "+isNotDomainStatusCls+" \" style=\"width:160px;\" id=\"dns_check\">\n" +
                    "                                <input type=\"checkbox\"  name=\"domainStatus\"  id=\"fuwuNetServiceId\" value=\"1\" class=\"opacity_default_0 \">提供域名信息用户\n" +
                    "                             </span>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "    </fieldset>");
        if(domainStatus == null){
            domainHTML(sb,isReadOnly,openTimeStr);
        }else if(domainStatus == 0){
            domainHTML(sb,isReadOnly,openTimeStr);
        }else{
            String icpsrvcontentcode = idcNetServiceinfo.getIcpsrvcontentcode();
            if(icpsrvcontentcode != null && !icpsrvcontentcode.equals("") && icpsrvcontentcode.contains("0")){

                idcNetServiceinfo.setIcpsrvcontentcode(assemblyIcpsrvContentCode(icpsrvcontentcode));

            }
            notDomainHTML(sb,isReadOnly,idcNetServiceinfo);
        }
        sb.append("<div id=\"vm_content\">\n" +
                "    </div>");
        sb.append("</form>");

        sb.append("</div");
        out.print(sb.toString());
    }
    public void notDomainHTML(StringBuffer sb,String isReadOnly,IdcNetServiceinfo idcNetServiceinfo){
        /* 查询使用 */
        String icpsrvname = idcNetServiceinfo.getIcpsrvname()!=null?idcNetServiceinfo.getIcpsrvname():"";
        String icpsrvcontentcode = idcNetServiceinfo.getIcpsrvcontentcode()!=null?idcNetServiceinfo.getIcpsrvcontentcode():"";
        String icprecordtype = idcNetServiceinfo.getIcprecordtype()!=null?idcNetServiceinfo.getIcprecordtype():"";
        String icprecordno = idcNetServiceinfo.getIcprecordno()!=null?idcNetServiceinfo.getIcprecordno():"";
        String icpaccesstype = idcNetServiceinfo.getIcpaccesstype()!=null?idcNetServiceinfo.getIcpaccesstype():"";
        String icpdomain = idcNetServiceinfo.getIcpdomain()!=null?idcNetServiceinfo.getIcpdomain():"";
        String icpbusinesstype = idcNetServiceinfo.getIcpbusinesstype()!=null?idcNetServiceinfo.getIcpbusinesstype():"";
        String vmName = idcNetServiceinfo.getVmName()!=null?idcNetServiceinfo.getVmName():"";

        sb.append("<div id=\"contract_content\"> \n");
        sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                "            <legend>&diams;服务信息</legend>\n");
        sb.append("<table class=\"kv-table\">\n" +
                "   <tr>\n" +
                "   <td class=\"kv-label\" style=\"width: 200px;\">名称</td>\n" +
                "    <td class=\"kv-content\">\n" +
                "           <input class=\"easyui-textbox\" "+isReadOnly+" name=\"idcNetServiceinfo.icpsrvname\" value=\""+icpsrvname+"\" data-options=\"width:200\"/>\n" +
                "                            </td>\n" +
                "                            <td class=\"kv-label\">所属用户</td>\n" +
                "                            <td class=\"kv-content\">\n" +
                "                                <input class=\"easyui-textbox\"    readonly=\"readonly\" name=\"idcNetServiceinfo.customerName\" value=\""+idcReCustomer.getName()+"\"  id=\"idcNetServiceinfo.customerName\" data-options=\"validType:'length[0,128]',width:200\"/>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"kv-label\">服务内容</td>\n" +
                "                            <td class=\"kv-content\">\n" +
                "<input id=\"ttFWNR\" class=\"easyui-combotree\" readonly=\"readonly\" data-options=\"\n" +
                "                                       valueField: 'value',\n" +
                "                                           textField: 'label',\n" +
                "                                           editable:false,\n" +
                "                                           width:200,\n" +
                "                                           url:'"+httpSuffix+"/assetBaseinfo/checkboxOpenMSG/9999'\" name=\"idcNetServiceinfo.icpsrvcontentcode\"  value=\""+idcNetServiceinfo.getIcpsrvcontentcode()+"\" />\n"+
                "\n" +
                "                            </td>\n" +
                "                            <td class=\"kv-label\">备案类型</td>\n" +
                "                            <td class=\"kv-content\">\n" +
                "                                <input class=\"easyui-combobox\" "+isReadOnly+" data-options=\"\n" +
                "                                            valueField: 'value',\n" +
                "                                            textField: 'label',\n" +
                "                                            editable:false,\n" +
                "                                            width:200,\n" +
                "                                            data: [{\n" +
                "                                                label: '无',\n" +
                "                                                value: '0'\n" +
                "                                            },{\n" +
                "                                                label: '经营性网站',\n" +
                "                                                value: '1'\n" +
                "                                            },{\n" +
                "                                                label: '非经营性网站',\n" +
                "                                                value: '2'\n" +
                "                                            },{\n" +
                "                                                label: 'SP',\n" +
                "                                                value: '3'\n" +
                "                                            },{\n" +
                "                                                label: 'BBS',\n" +
                "                                                value: '4'\n" +
                "                                            },{\n" +
                "                                                label: '其它',\n" +
                "                                                value: '999'\n" +
                "                                            }]\" value=\""+icprecordtype+"\"  name=\"idcNetServiceinfo.icprecordtype\" id=\"idcNetServiceinfo.icprecordtype\"/>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"kv-label\">备案号[许可证号]</td>\n" +
                "                            <td class=\"kv-content\">\n" +
                "                                <input class=\"easyui-textbox\" "+isReadOnly+"  name=\"idcNetServiceinfo.icprecordno\" value=\""+icprecordno+"\"  id=\"icprecordno\" data-options=\"validType:'length[0,4000]',width:200\"/>\n" +
                "                            </td>\n" +
                "                            <td class=\"kv-label\">接入方式</td>\n" +
                "                            <td class=\"kv-content\">\n" +
                "                                <input class=\"easyui-combobox\" "+isReadOnly+" data-options=\"\n" +
                "                                            valueField: 'value',\n" +
                "                                            textField: 'label',\n" +
                "                                            width:200,\n" +
                "                                            editable:false,\n" +
                "                                            onChange:icpaccesstypeOnChange,\n" +
                "                                            data: [{\n" +
                "                                                label: '专线',\n" +
                "                                                value: '0'\n" +
                "                                            },{\n" +
                "                                                label: '虚拟主机',\n" +
                "                                                value: '1'\n" +
                "                                            },{\n" +
                "                                                label: '主机托管',\n" +
                "                                                value: '2'\n" +
                "                                            },{\n" +
                "                                                label: '整机租用',\n" +
                "                                                value: '3'\n" +
                "                                            },{\n" +
                "                                                label: '其它',\n" +
                "                                                value: '999'\n" +
                "                                            }]\" value=\""+icpaccesstype+"\"  name=\"idcNetServiceinfo.icpaccesstype\" id=\"icpaccesstype\"/>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"kv-label\">域名信息</td>\n" +
                "                            <td class=\"kv-content\">\n" +
                "                                <input class=\"easyui-textbox\" "+isReadOnly+" name=\"idcNetServiceinfo.icpdomain\" value=\""+icpdomain+"\"  id=\"idcNetServiceinfo.icpdomain\" data-options=\"width:200\"/>\n" +
                "                            </td>\n" +
                "                            <td class=\"kv-label\">业务类型</td>\n" +
                "                            <td class=\"kv-content\">\n" +
                "                                <input class=\"easyui-combobox\" "+isReadOnly+" data-options=\"\n" +
                "                                            valueField: 'value',\n" +
                "                                            textField: 'label',\n" +
                "                                            width:200,\n" +
                "                                            editable:false,\n" +
                "                                            data: [{\n" +
                "                                                label: 'IDC业务',\n" +
                "                                                value: '1'\n" +
                "                                            },{\n" +
                "                                                label: 'ISP业务',\n" +
                "                                                value: '2'" +
                "                                            }]\" value=\""+icpbusinesstype+"\"  name=\"idcNetServiceinfo.icpbusinesstype\" id=\"idcNetServiceinfo.icpbusinesstype\"/>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n");
        sb.append("        </fieldset>\n");

        sb.append("</div>");
        /** 判断是否具有虚拟机信息 **/
        sb.append("<div id=\"vm_content\">");
        String vmStatus = idcNetServiceinfo.getVmStatus()!=null?idcNetServiceinfo.getVmStatus():"";
        String getVmCategory = idcNetServiceinfo.getVmCategory()!=null?idcNetServiceinfo.getVmCategory():"";
        String getVmNetAddr = idcNetServiceinfo.getVmNetAddr()!=null?idcNetServiceinfo.getVmNetAddr():"";
        String getVmManagerAddr = idcNetServiceinfo.getVmManagerAddr()!=null?idcNetServiceinfo.getVmManagerAddr():"";
        if(icpaccesstype != null && "1".equals(icpaccesstype)){
            log.debug("虚拟机信息");
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                        <legend>&diams;虚拟机信息</legend>\n" +
                    "                        <table class=\"kv-table\">\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\" style=\"width: 200px;\">名称</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-textbox\" "+isReadOnly+"  name=\"idcNetServiceinfo.vmName\" value=\""+vmName+"\"  id=\"idcNetServiceinfo.vmName\" data-options=\"width:150\"/>\n" +
                    "                                </td>\n" +
                    "                                <td class=\"kv-label\">状态</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-combobox\" "+isReadOnly+"  data-options=\"\n" +
                    "                                               valueField: 'value',\n" +
                    "                                                   textField: 'label',\n" +
                    "                                                   editable:false,\n" +
                    "                                                   width:150,\n" +
                    "                                                   data: [{\n" +
                    "                                                   label: '可用',\n" +
                    "                                                   value: '1'\n" +
                    "                                               },{\n" +
                    "                                                   label: '不可用',\n" +
                    "                                                   value: '-1'\n" +
                    "                                               }]\" value=\""+vmStatus+"\"  name=\"idcNetServiceinfo.vmStatus\" id=\"idcNetServiceinfo.vmStatus\"/>\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\">类型</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-combobox\" "+isReadOnly+" data-options=\"\n" +
                    "                                                   valueField: 'value',\n" +
                    "                                                       textField: 'label',\n" +
                    "                                                       width:150,\n" +
                    "                                                       editable:false,\n" +
                    "                                                       data: [{\n" +
                    "                                                       label: '共享式',\n" +
                    "                                                       value: '1'\n" +
                    "                                                   },{\n" +
                    "                                                       label: '专用式',\n" +
                    "                                                       value: '2'\n" +
                    "                                                   },{\n" +
                    "                                                       label: '云虚拟',\n" +
                    "                                                       value: '3'\n" +
                    "                                                   }]\" value=\""+getVmCategory+"\"  name=\"idcNetServiceinfo.vmCategory\" id=\"idcNetServiceinfo.vmCategory\"/>\n" +
                    "                                </td>\n" +
                    "                                <td class=\"kv-label\">网络地址</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <!-- idcNetServiceinfo.vmNetAddr -->\n" +
                    "                                    <input class=\"easyui-textbox\" "+isReadOnly+" name=\"idcNetServiceinfo.vmNetAddr\" value=\""+getVmNetAddr+"\"  id=\"idcNetServiceinfo.vmNetAddr\" data-options=\"width:150\"/>\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                            <tr>\n" +
                    "                                <td class=\"kv-label\">管理地址</td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                    <input class=\"easyui-textbox\" "+isReadOnly+" name=\"idcNetServiceinfo.vmManagerAddr\" value=\""+getVmManagerAddr+"\"  id=\"idcNetServiceinfo.vmManagerAddr\" data-options=\"width:150\"/>\n" +
                    "                                </td>\n" +
                    "                                <td class=\"kv-label\"></td>\n" +
                    "                                <td class=\"kv-content\">\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                        </table>\n" +
                    "                    </fieldset>");
        }
        sb.append("</div>");
    }
    public void domainHTML(StringBuffer sb,String isReadOnly,String openTimeStr){
        sb.append("<div id=\"contract_content\"> \n" +
                "        <fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                "            <legend>&diams;服务信息</legend>\n" +
                "            <table class=\"kv-table\">\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\" style=\"width:215px\">服务开通时间<span style=\"color:red\">&nbsp;*</span></td>\n" +
                "                    <td class=\"kv-content\" colspan=\"3\">\n" +
                "                        <input class=\"easyui-datetimebox\" "+isReadOnly+"  name=\"idcNetServiceinfo.openTime\" value=\""+openTimeStr+"\" data-options=\"showSeconds:true,width:250\">\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </fieldset>\n" +
                "    </div>");
    }

    public void writeACTTag(JspWriter out,StringBuffer sb) throws Exception{
        log.debug("流程图显示");
        sb = new StringBuffer();
        sb.append("<div title=\"流程图\" data-options=\"closable:false\" style=\"overflow:auto;padding:10px;display:none;\">");
        sb.append("<iframe id=\"diagramInfojbpmdiv\"  style=\"width: 100%;  height: 100%; overflow: hidden; border: none;  margin: 10px auto;  margin-top: 80px; \" src=\""+httpSuffix+"/modelController/diagramInfo/"+processInstanceId+"/"+processDefinitionId+"/"+processDefinitionKey+"\"></iframe>");
        sb.append("</div>");
        out.print(sb.toString());
    }

    /*停机原因的html*/
    public void writePauseTag(JspWriter out,StringBuffer sb,IdcRunProcCment idcRunProcCment,
                              String ticketCategoryTo,
                              String ticketCategoryFrom,
                              String ticketInstId,
                              String prodCategory,
                              String prodInstId,
                              String parentId,
                              Date reservestart,
                              Date reserveend,
                              String comment,
                              Integer ticketStatus) throws Exception{
        log.debug("停机申请");

        sb = new StringBuffer();
        sb.append("<div title=\"停机原因\" data-options=\"closable:false\" style=\"overflow:auto;padding:10px;display:none;\"> \n" +
                "        <form method=\"post\" id=\"pauseSingleForm\" action=\"<%=request.getContextPath() %>/actJBPMController/procCmentFormSaveOrUpdate.do\">\n" +
                "            <table class=\"kv-table\">\n" +
                "                <input type=\"hidden\" value=\""+ticketCategoryTo+"\" name=\"idcRunTicket.ticketCategoryTo\"/>\n" +
                "                <input type=\"hidden\" value=\""+ticketCategoryFrom+"\" name=\"idcRunTicket.ticketCategoryFrom\"/>\n" +
                "                <input type=\"hidden\" value=\""+ticketInstId+"\" name=\"idcRunTicket.ticketInstId\"/>\n" +
                "                <input type=\"hidden\" value=\""+prodCategory+"\" name=\"idcRunTicket.prodCategory\"/>\n" +
                "                <input type=\"hidden\" value=\""+prodInstId+"\" name=\"idcRunTicket.prodInstId\"/> \n" +
                "                    <div style=\"position:relative;margin: 0px;\" >\n" +
                "\n" +
                "                        <tr>\n" +
                "                            <td class=\"kv-label\">停机时间<span style=\"color:red\">&nbsp;*</span></td>\n" +
                "                            <td class=\"kv-content\">\n" +
                "                                <!-- contractstart   contractsigndate -->\n" +
                "                                开始时间<input class=\"easyui-datetimebox\"  name=\"reservestart\" value=\""+reservestart+"\" data-options=\"required:true,editable:false,showSeconds:true,width:150\">\n" +
                "                                -结束时间<input class=\"easyui-datetimebox\"  name=\"reserveend\" value=\""+reserveend+"\" data-options=\"required:true,editable:false,showSeconds:true,width:150\">\n" +
                "                                <span style=\"color: red\">注意：停机时间不能超过30天</span>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"kv-label\"style=\"width:100px\" >停机原因<span style=\"color:red\">&nbsp;*</span></td>\n" +
                "                    <td class=\"kv-content\">\n" +
                "                        <input class=\"easyui-textbox\" data-options=\"required:false,width:420,height:100,multiline:true\" name=\"constructComment\" value=\"" + comment + "\" data-options=\"validType:'length[0,255]'\"/>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </form>\n" +
                "    </div>");
        out.print(sb.toString());
    }
    public void writeRackTag(JspWriter out,StringBuffer sb, Long prodInstId,Long ticketInstId) throws Exception{
        IdcReRackModel idcReRackModel = idcReProddefService.getModelRackToBeanByCategory(ServiceApplyEnum.机架.value(), prodInstId,ticketInstId);
        if(idcReRackModel != null){
            String desc = idcReRackModel.getDesc()!=null?idcReRackModel.getDesc():"";
           /*  */
            request.setAttribute("idcReRackModel",idcReRackModel);
            String isReadOnly = isFormEdit?"":"readonly=\"readonly\"";
            sb.append("<div id=\"rack_fieldset_content_Id\">");
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">")
                    .append("<legend>&diams;机架机位</legend>")
                    .append("<table class=\"kv-table\">")
                    .append("<tr>\n" +
                            "                        <td class=\"kv-label\" style=\"width: 120px;\">规格</td>\n" +
                            "                        <td class=\"kv-content\">\n" +
                            "                            <input class=\"easyui-combobox\"\n" +
                            "                     "+isReadOnly+"       " +
                            "                                   data-options=\"valueField: 'value',\n" +
                            "                                   textField: 'label',\n" +
                            "                                   editable:false,\n" +
                            "                                   width:150,\n" +
                            "                                   required:true,\n" +
                            "                                   url:'"+httpSuffix+"/assetBaseinfo/combobox/100' \"\n" +
                            "                                   name=\"idcReRackModel.spec\"\n" +
                            "                                   id=\"idcReRackModel_spec\"\n" +
                            "                                   value=\""+idcReRackModel.getSpec()+"\" />\n" +
                            "                        </td>\n" +
                            "                        <td class=\"kv-label\"  style=\"width: 120px;\">机架(个)/机位数(U)</td>\n" +
                            "                        <td class=\"kv-content\">\n" +
                            "                            <input class=\"easyui-numberbox\" "+isReadOnly+"  name=\"idcReRackModel.rackNum\" value=\""+idcReRackModel.getRackNum()+"\"\n" +
                            "                                   id=\"idcReRackModel_rackNum\" data-options=\"precision:0,width:150,min:0,validType:'length[0,11]'\"/>\n" +
                            "                        </td>\n" +
                            "                        <td class=\"kv-label\" style=\"width: 120px;\">供电类型</td>\n" +
                            "                        <td class=\"kv-content\">\n" +
                            "                            <input class=\"easyui-combobox\"\n" +
                            "                             "+isReadOnly+"   " +
                            "                                   data-options=\"valueField: 'value',\n" +
                            "                                   textField: 'label',\n" +
                            "                                   editable:false,\n" +
                            "                                   width:150,\n" +
                            "                                   required:true,\n" +
                            "                                   url:'"+httpSuffix+"/assetBaseinfo/combobox/200'\n" +
                            "                                   \"\n" +
                            "                                   name=\"idcReRackModel.supplyType\"\n" +
                            "                                   id=\"idcReRackModel_supplyType\"\n" +
                            "                                   value=\""+idcReRackModel.getSupplyType()+"\" />\n" +
                            "                        </td>\n" +
                            "                    </tr>")
                    .append("<tr>\n" +

                            "\n" +
                            "                        <td class=\"kv-label\" style=\"width: 120px;\" >描述</td>\n" +
                            "                        <td class=\"kv-content\" colspan=\"5\">\n" +
                            "                            <input class=\"easyui-textbox\" "+isReadOnly+"  value=\""+desc+"\"\n" +
                            "                                   name=\"idcReRackModel.desc\" id=\"idcReRackModel_desc\"\n" +
                            "                                   data-options=\"multiline:true,height:70,width:525\"/>\n" +
                            "                        </td>\n" +
                            "                    </tr>")
                    .append("</table>\n" +
                            "            </fieldset>");
            sb.append("</div>");
            request.setAttribute("idcReRackModelNum",idcReRackModel.getRackNum());
            /*request.setAttribute("rackOrracunit",idcReRackModel.getRackOrracunit());*/
        }else{
            sb.append("<div id=\"rack_fieldset_content_Id\"></div>");
        }
    }

    public void writeReNewlyTag(JspWriter out,StringBuffer sb,Long prodInstId,Long ticketInstId ) throws Exception{

        IdcReNewlyModel idcReNewlyModel = idcReProddefService.getModelAddNewlyToBeanByCategory(ServiceApplyEnum.增值业务.value(), prodInstId,ticketInstId);
        if(idcReNewlyModel != null){
            request.setAttribute("idcReNewlyModel",idcReNewlyModel);
            String isReadOnly = isFormEdit?"":"readonly=\"readonly\"";
            String desc = idcReNewlyModel.getDesc()!=null?idcReNewlyModel.getDesc():"";
            sb.append("<div id=\"add_fieldset_content_Id\">");
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                <legend>&diams;业务增值</legend>\n" +
                    "                <table class=\"kv-table\">\n" +
                    "                    <tr>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 85px;\">名称</td>\n" +
                    "                        <td class=\"kv-content\">\n" +
                    "                            <input "+isReadOnly+" class=\"easyui-textbox\" data-options=\"width:150\" value=\""+idcReNewlyModel.getName()+"\"\n" +
                    "                                   name=\"idcReNewlyModel.name\" id=\"idcReNewlyModel_name\"\n" +
                    "                            />\n" +
                    "                        </td>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 85px;\">业务分类</td>\n" +
                    "                        <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                            <input class=\"easyui-combobox\" "+isReadOnly+" data-options=\"\n" +
                    "                                       valueField: 'value',\n" +
                    "                                       width:150,\n" +
                    "                                           textField: 'label',\n" +
                    "                                           editable:false,\n" +
                    "                                        url:'"+httpSuffix+"/assetBaseinfo/combobox/800' \" name=\"idcReNewlyModel.category\"    id=\"idcReNewlyModel_category\" value=\""+idcReNewlyModel.getCategory()+"\" />\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 85px;\">描述</td>\n" +
                    "                        <td class=\"kv-content\" colspan=\"4\">\n" +
                    "                            <input "+isReadOnly+" class=\"easyui-textbox\" value=\""+desc+"\"\n" +
                    "                                   name=\"idcReNewlyModel.desc\" id=\"idcReNewlyModel_desc\"\n" +
                    "                                   data-options=\"multiline:true,height:70,width:525\"/>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "\n" +
                    "                </table>\n" +
                    "            </fieldset>");
            sb.append("</div>");
        }else{
            sb.append("<div id=\"add_fieldset_content_Id\"></div>");
        }
    }
    public void writeServerTag(JspWriter out,StringBuffer sb,Long prodInstId,Long ticketInstId) throws Exception{

        IdcReServerModel idcReServerModel = idcReProddefService.getModelServerToBeanByCategory(ServiceApplyEnum.主机租赁.value(), prodInstId,ticketInstId);
        if(idcReServerModel != null){
            request.setAttribute("idcReServerModel",idcReServerModel);
            String isReadOnly = isFormEdit?"":"readonly=\"readonly\"";
            String desc = idcReServerModel.getDesc()!=null?idcReServerModel.getDesc():"";
            sb.append("<div id=\"server_fieldset_content_Id\">");
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                <legend>&diams;主机租赁</legend>\n" +
                    "                <table class=\"kv-table\">\n" +
                    "                    <tr>\n" +
                    "                        <td class=\"kv-label\" style=\"width:120px;\">资源类型</td>\n" +
                    "                        <td class=\"kv-content\">\n" +
                    "                            <input class=\"easyui-combobox\"\n" +
                    "                                   "+isReadOnly+"" +
                    "                                   data-options=\"\n" +
                    "                                   valueField: 'value',\n" +
                    "                                       textField: 'label',\n" +
                    "                                       editable:false,\n" +
                    "                                       required:true,\n" +
                    "                                       width:150,\n" +
                    "                                       url:'"+httpSuffix+"/assetBaseinfo/combobox/500'\" name=\"idcReServerModel.typeMode\" id=\"idcReServerModel_typeMode\" value=\""+idcReServerModel.getTypeMode()+"\" />\n" +
                    "\n" +
                    "                        </td>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 120px;\">设备型号</td>\n" +
                    "                        <td class=\"kv-content\">\n" +
                    "                            <input class=\"easyui-textbox\"\n" +
                    "                                  "+isReadOnly+"" +
                    "                                   data-options=\"required:true,width:150 \" " +
                    "                                       name=\"idcReServerModel.specNumber\" id=\"idcReServerModel_specNumber\" value=\""+idcReServerModel.getSpecNumber()+"\" />\n" +
                    "\n" +
                    "\n" +
                    "                        </td>\n" +
                    "                        <td class=\"kv-label\"  style=\"width: 120px;\">数量(个)</td>\n" +
                    "                        <td class=\"kv-content\">\n" +
                    "                            <input "+isReadOnly+" class=\"easyui-numberbox\"\n" +
                    "                                   name=\"idcReServerModel.num\"\n" +
                    "                                   value=\""+idcReServerModel.getNum()+"\"\n" +
                    "                                   id=\"idcReServerModel_num\"\n" +
                    "                                   data-options=\"precision:0,width:150,min:0,validType:'length[0,11]'\"/>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td class=\"kv-label\"  style=\"width: 120px;\">描述</td>\n" +
                    "                        <td class=\"kv-content\" colspan=\"5\">\n" +
                    "                            <input "+isReadOnly+"  class=\"easyui-textbox\" value=\""+desc+"\"\n" +
                    "                                   name=\"idcReServerModel.desc\" id=\"idcReServerModel_desc\"\n" +
                    "                                   data-options=\"multiline:true,height:70,width:525\"/>\n" +
                    "                        </td>\n" +
                    "\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </fieldset>");
            sb.append("</div>");
            request.setAttribute("idcReServerModelNum",idcReServerModel.getNum());
        }else{
            sb.append("<div id=\"server_fieldset_content_Id\"></div>");
        }

    }
    public void writeIpTag(JspWriter out,StringBuffer sb,Long prodInstId,Long ticketInstId) throws Exception{

        IdcReIpModel idcReIpModel = idcReProddefService.getModelIpToBeanByCategory(ServiceApplyEnum.IP租用.value(), prodInstId,ticketInstId);
        if(idcReIpModel != null){
            request.setAttribute("idcReIpModel",idcReIpModel);
            String desc = idcReIpModel.getDesc()!=null?idcReIpModel.getDesc():"";
            String isReadOnly = isFormEdit?"":"readonly=\"readonly\"";
            sb.append("<div id=\"ip_fieldset_content_Id\">");
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                <legend>&diams;IP租用</legend>\n" +
                    "                <table class=\"kv-table\">\n" +
                    "                    <tr>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 85px;\">IP租用</td>\n" +
                    "                        <td class=\"kv-content\">\n" +
                    "                            <input class=\"easyui-combobox\"\n" +
                    "                              "+isReadOnly+" " +
                    "                                   data-options=\"\n" +
                    "                               valueField: 'value',\n" +
                    "                                   textField: 'label',\n" +
                    "                                   editable:false,\n" +
                    "                                   required:true,\n" +
                    "                                   width:150,\n" +
                    "                                   url:'"+httpSuffix+"/assetBaseinfo/combobox/400'\n" +
                    "                                   \" name=\"idcReIpModel.portMode\" id=\"idcReIpModel_portMode\" value=\""+idcReIpModel.getPortMode()+"\" />\n" +
                    "\n" +
                    "                        </td>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 85px;\">数量(个)</td>\n" +
                    "                        <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                            <input "+isReadOnly+" class=\"easyui-numberbox\"\n" +
                    "                                   name=\"idcReIpModel.num\"\n" +
                    "                                   value=\""+idcReIpModel.getNum()+"\"\n" +
                    "                                   id=\"idcReIpModel_num\"\n" +
                    "                                   data-options=\"precision:0,width:150,min:0,validType:'length[0,5]'\"/>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 85px;\">描述</td>\n" +
                    "                        <td class=\"kv-content\" colspan=\"3\">\n" +
                    "                            <input "+isReadOnly+"  class=\"easyui-textbox\" value=\""+desc+"\"\n" +
                    "                                   name=\"idcReIpModel.desc\" id=\"idcReIpModel_desc\"\n" +
                    "                                   data-options=\"multiline:true,height:70,width:525\"/>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </fieldset>");
            sb.append("</div>");
            request.setAttribute("idcReIpModelNum",idcReIpModel.getNum());
        }else{
            sb.append("<div id=\"ip_fieldset_content_Id\"></div>");
        }
    }
    public void writePortTag(JspWriter out,StringBuffer sb,Long prodInstId,Long ticketInstId) throws Exception{

        IdcRePortModel idcRePortModel = idcReProddefService.getModelPortToBeanByCategory(ServiceApplyEnum.端口带宽.value(),prodInstId,ticketInstId);
        if(idcRePortModel != null){
            request.setAttribute("idcRePortModel",idcRePortModel);
            String isReadOnly = isFormEdit?"":"readonly=\"readonly\"";
            String desc = idcRePortModel.getDesc()!=null?idcRePortModel.getDesc():"";
            sb.append("<div id=\"port_fieldset_content_Id\">");
            sb.append("<fieldset class=\"fieldsetCls fieldsetHeadCls\">\n" +
                    "                <legend>&diams;端口带宽</legend>\n" +
                    "                <table class=\"kv-table\">\n" +
                    "                    <tr>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 120px;\">端口带宽占用方式</td>\n" +
                    "                        <td class=\"kv-content\">\n" +
                    "                            <input class=\"easyui-combobox\"\n" +
                    "                             "+isReadOnly+" " +
                    "                                   data-options=\"\n" +
                    "                                      valueField: 'value',\n" +
                    "                                       textField: 'label',\n" +
                    "                                       editable:false,\n" +
                    "                                       required:true,\n" +
                    "                                       width:150,\n" +
                    "                                        url:'"+httpSuffix+"/assetBaseinfo/combobox/300'\n" +
                    "                                \" name=\"idcRePortModel.portMode\" id=\"idcRePortModel_portMode\" value=\""+idcRePortModel.getPortMode()+"\" />\n" +
                    "                        </td>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 120px;\">带宽总需求(兆)</td>\n" +
                    "                        <td class=\"kv-content\">\n" +
                    "                            <input class=\"easyui-numberbox\" "+isReadOnly+"  data-options=\"precision:0,width:150,min:0,validType:'length[0,11]'\" name=\"idcRePortModel.bandwidth\" id=\"idcRePortModel_bandwidth\" value=\""+idcRePortModel.getBandwidth()+"\" />\n" +
                    "                        </td>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 120px;\">端口数量(个)</td>\n" +
                    "                        <td class=\"kv-content\">\n" +
                    "                            <input "+isReadOnly+" class=\"easyui-numberbox\"\n" +
                    "                                   name=\"idcRePortModel.num\"\n" +
                    "                                   value=\""+idcRePortModel.getNum()+"\"\n" +
                    "                                   id=\"idcRePortModel_num\"\n" +
                    "                                   data-options=\"precision:0,width:150,min:0,validType:'length[0,11]'\"/>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td class=\"kv-label\" style=\"width: 120px;\">描述</td>\n" +
                    "                        <td class=\"kv-content\" colspan=\"5\">\n" +
                    "                            <input "+isReadOnly+"   class=\"easyui-textbox\" value=\""+desc+"\"\n" +
                    "                                   name=\"idcRePortModel.desc\" id=\"idcRePortModel_desc\"\n" +
                    "                                   data-options=\"multiline:true,height:70,width:525\"/>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </fieldset>");
            sb.append("</div>");
            request.setAttribute("idcRePortModelNum",idcRePortModel.getNum());
        }else{
            sb.append("<div id=\"port_fieldset_content_Id\"></div>");
        }
    }


    public String assemblyIcpsrvContentCode(String icpsrvContentCode){

        String[] arr = icpsrvContentCode.split(",");//根据“ ”和“,”区分

        StringBuilder stringBuilder = new StringBuilder(" ");

        if(icpsrvContentCode.contains("1")){
            stringBuilder.append("，即时通讯");
        }
        if(icpsrvContentCode.contains("2")){
            stringBuilder.append("，搜索引擎");
        }
        if(icpsrvContentCode.contains("3")){
            stringBuilder.append("，综合门户");
        }
        if(icpsrvContentCode.contains("4")){
            stringBuilder.append("，网上邮局");
        }
        if(icpsrvContentCode.contains("5")){
            stringBuilder.append("，网络新闻");
        }
        if(icpsrvContentCode.contains("6")){
            stringBuilder.append("，博客/个人空间");
        }
        if(icpsrvContentCode.contains("7")){
            stringBuilder.append("，网络广告/信息");
        }
        if(icpsrvContentCode.contains("8")){
            stringBuilder.append("，单位门户网站");
        }
        if(icpsrvContentCode.contains("9")){
            stringBuilder.append("，网络购物");
        }
        if(icpsrvContentCode.contains("10")){
            stringBuilder.append("，网上支付");
        }
        if(icpsrvContentCode.contains("11")){
            stringBuilder.append("，网上银行");
        }
        if(icpsrvContentCode.contains("12")){
            stringBuilder.append("，网上炒股/股票基金");
        }
        if(icpsrvContentCode.contains("13")){
            stringBuilder.append("，网络游戏");
        }
        if(icpsrvContentCode.contains("14")){
            stringBuilder.append("，网络音乐");
        }
        if(icpsrvContentCode.contains("15")){
            stringBuilder.append("，网络影视");
        }
        if(icpsrvContentCode.contains("16")){
            stringBuilder.append("，网络图片");
        }
        if(icpsrvContentCode.contains("17")){
            stringBuilder.append("，网络软件/下载");
        }
        if(icpsrvContentCode.contains("18")){
            stringBuilder.append("，网上求职");
        }
        if(icpsrvContentCode.contains("19")){
            stringBuilder.append("，网上交友/婚介");
        }
        if(icpsrvContentCode.contains("20")){
            stringBuilder.append("，网上房产");
        }
        if(icpsrvContentCode.contains("21")){
            stringBuilder.append("，网络教育");
        }
        if(icpsrvContentCode.contains("22")){
            stringBuilder.append("，网站建设");
        }
        if(icpsrvContentCode.contains("23")){
            stringBuilder.append("，WAP");
        }
        if(icpsrvContentCode.contains("24")){
            stringBuilder.append("，其他");
        }
        //去掉第一个逗号
        stringBuilder.deleteCharAt(1);
        return stringBuilder.toString();
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getProdInstId() {
        return prodInstId;
    }

    public void setProdInstId(String prodInstId) {
        this.prodInstId = prodInstId;
    }
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public String getTicketInstId() {
        return ticketInstId;
    }

    public void setTicketInstId(String ticketInstId) {
        this.ticketInstId = ticketInstId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IdcReCustomer getIdcReCustomer() {
        return idcReCustomer;
    }

    public void setIdcReCustomer(IdcReCustomer idcReCustomer) {
        this.idcReCustomer = idcReCustomer;
    }

    public IdcReProduct getIdcReProduct() {
        return idcReProduct;
    }

    public void setIdcReProduct(IdcReProduct idcReProduct) {
        this.idcReProduct = idcReProduct;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public IdcRunTicket getIdcRunTicket() {
        return idcRunTicket;
    }

    public void setIdcRunTicket(IdcRunTicket idcRunTicket) {
        this.idcRunTicket = idcRunTicket;
    }

    public Boolean getZQYWFlag() {
        return ZQYWFlag;
    }

    public void setZQYWFlag(Boolean ZQYWFlag) {
        this.ZQYWFlag = ZQYWFlag;
    }

    public Boolean getIs_author_apply_show() {
        return is_author_apply_show;
    }

    public void setIs_author_apply_show(Boolean is_author_apply_show) {
        this.is_author_apply_show = is_author_apply_show;
    }

    public String getToolbar() {
        return toolbar;
    }

    public void setToolbar(String toolbar) {
        this.toolbar = toolbar;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Integer getHasOtherTicket() {
        return hasOtherTicket;
    }

    public void setHasOtherTicket(Integer hasOtherTicket) {
        this.hasOtherTicket = hasOtherTicket;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsShowGridColumnHandlerFlag() {
        return this.isShowGridColumnHandlerFlag;
    }

    public void setIsShowGridColumnHandlerFlag(Boolean isShowGridColumnHandlerFlag) {
        this.isShowGridColumnHandlerFlag = isShowGridColumnHandlerFlag;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Boolean getIsFormEdit() {
        return this.isFormEdit;
    }

    public void setIsFormEdit(Boolean formEdit) {
        this.isFormEdit = formEdit;
    }

    public IdcHisTicket getIdcHisTicket() {
        return idcHisTicket;
    }

    public void setIdcHisTicket(IdcHisTicket idcHisTicket) {
        this.idcHisTicket = idcHisTicket;
    }

    public Object getTicketItem() {
        return ticketItem;
    }

    public void setTicketItem(Object ticketItem) {
        this.ticketItem = ticketItem;
    }

    public Integer getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Integer ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Boolean getIsHasOpenEdit() {
        return isHasOpenEdit;
    }

    public void setIsHasOpenEdit(Boolean isHasOpenEdit) {
        this.isHasOpenEdit = isHasOpenEdit;
    }

    public IdcRunProcCment getIdcRunProcCment() {
        return idcRunProcCment;
    }

    public void setIdcRunProcCment(IdcRunProcCment idcRunProcCment) {
        this.idcRunProcCment = idcRunProcCment;
    }

}

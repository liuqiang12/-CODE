
    package com.bpm;

    import com.idc.model.*;
    import com.idc.service.*;
    import com.idc.utils.CategoryEnum;
    import com.idc.utils.JBPMModelKEY;
    import com.idc.utils.JbpmStatusEnum;
    import com.idc.utils.ServiceApplyEnum;
    import modules.utils.ResponseJSON;
    import net.sf.json.JSONObject;
    import org.apache.commons.logging.Log;
    import org.apache.commons.logging.LogFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.ServletRequestUtils;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.ResponseBody;
    import system.data.page.PageBean;
    import system.data.supper.action.BaseController;
    import system.data.supper.service.JavaSerializer;
    import system.data.supper.service.RedisManager;
    import utils.DevContext;
    import utils.plugins.excel.Guid;

    import javax.servlet.http.HttpServletRequest;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.*;

    /**
 * Created by DELL on 2017/6/6.
 * act的自定义流程引擎控制器 .
 *注入信息全部在此文件底部
 * actJBPMController
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
@Controller
@RequestMapping("/actJBPMController")
public class ActJBPMController extends BaseController {
    /**
     * 所有的代办任务列表
     * @param request
     * @param model
     * @return
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/ticketTaskGridQueryWithGroupId.do")
    public String ticketTaskGridQueryWithGroupId(HttpServletRequest request,org.springframework.ui.Model model) {
        //流程类别情况
        /*变更的业务需要注意,有两种：变更开通和变更预占【暂时不使用该方法，另起其他方法、避免混淆】*/
        String prodCategory = request.getParameter("prodCategory");
        /*String ticket_ctl = request.getParameter("ticket_ctl");// true*/
        String category = request.getParameter("category");// true
        String isCustomerView = request.getParameter("isCustomerView");//

        model.addAttribute("prodCategory",prodCategory);
        /*model.addAttribute("ticket_ctl",ticket_ctl);*/
        model.addAttribute("category",category);
        model.addAttribute("isCustomerView",isCustomerView);

        //删除工单按钮，只有管理员有相应的权限才能有删除按钮
        SysUserinfo sysUserinfo = getPrincipal();
        String roles_user = sysUserinfo.getRoles_user();
        /*if(roles_user.indexOf("系统管理人员") != -1){
            model.addAttribute("isAdministrator","yes");
        }*/
        model.addAttribute("loginUserId",sysUserinfo.getId());   //放入当前登陆用户的ID
        return "jbpm/ticket/ticketTaskGridQueryWithGroupId";
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/ticketTaskGridQuery.do")
    public String ticketTaskGridQuery(HttpServletRequest request,org.springframework.ui.Model model) {
        //流程类别情况
        /*变更的业务需要注意,有两种：变更开通和变更预占【暂时不使用该方法，另起其他方法、避免混淆】*/
        String processDefinitonKey = request.getParameter("processDefinitonKey");
        String prodCategory = request.getParameter("prodCategory");
        String ticket_ctl = request.getParameter("ticket_ctl");// true
        String ticketCategory = request.getParameter("ticketCategory");//工单类型100:预勘 200:开通 400:停机 500:复通 600:下线 700:变更开通 800:临时测试,900:业务变更
        /*通过category 获取title*/
        String tabsTitleName = actJBPMService.getTabsTitleName(ticketCategory,prodCategory);
        model.addAttribute("tabsTitle",tabsTitleName);

        model.addAttribute("processDefinitonKey",processDefinitonKey);
        model.addAttribute("prodCategory",prodCategory);
        model.addAttribute("ticket_ctl",ticket_ctl);
        model.addAttribute("ticketCategory",ticketCategory);
        model.addAttribute("idcName","idcName");
        //判断类型:ticketTaskGridQuery

        //删除工单按钮，只有管理员有相应的权限才能有删除按钮: 可以忽略------------------
        SysUserinfo sysUserinfo = getPrincipal();

        //放入当前登陆人的id，因为流程最后评分一步，只有登陆人和申请人是同一个人才能评分
        Integer loginUserId = sysUserinfo.getId();
        model.addAttribute("loginUserId",loginUserId);

        return "jbpm/ticket/ticketTaskGridQuery";
    }

    /**
     * 预占预勘察管理:页面
     * @param request
     * @return
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/ticketAllTaskGridQuery.do")
    public String ticketAllTaskGridQuery(HttpServletRequest request,org.springframework.ui.Model model) {
        //流程类别情况
        /*变更的业务需要注意,有两种：变更开通和变更预占【暂时不使用该方法，另起其他方法、避免混淆】*/
        String prodCategory = request.getParameter("prodCategory");
        //isCustomerView是判断是否是客户经理试图，客户经理视图要另外的查询
        String isCustomerView = request.getParameter("isCustomerView");
        model.addAttribute("prodCategory",prodCategory);
        model.addAttribute("isCustomerView",isCustomerView);

        SysUserinfo sysUserinfo = getPrincipal();
        Integer loginUserId = sysUserinfo.getId();
        model.addAttribute("loginUserId",loginUserId);
        return "jbpm/ticket/ticketAllTaskGridQuery";
    }

    /**
     * 在服业务[开通单子以后的所有的工单信息；当然需要人员过滤]
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/ticketOpenLastTaskGridQuery.do")
    public String ticketOpenLastTaskGridQuery(HttpServletRequest request,org.springframework.ui.Model model) {
        SysUserinfo sysUserinfo = getPrincipal();
        model.addAttribute("loginUserId",sysUserinfo.getId());
        return "jbpm/ticket/ticketOpenLastTaskGridQuery";
    }

    /**
     * 在服业务[开通单子以后的所有的工单信息；当然需要人员过滤]
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/ticketOpenLastTaskGridQueryData.do")
    @ResponseBody
    public PageBean ticketOpenLastTaskGridQueryData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        String serialNumber = request.getParameter("serialNumber");
        String ticketCategory = request.getParameter("ticketCategory");
        String prodCategory = request.getParameter("prodCategory");
        SysUserinfo sysUserinfo = getPrincipal();

        Map<String,Object> params=new HashMap<>();
        params.put("serialNumber",serialNumber);
        params.put("ticketCategory",ticketCategory);
        params.put("prodCategory",prodCategory);
        params.put("loginUserId",sysUserinfo.getId());

        List<SysRoleinfo> sysRoleinfos = sysUserinfo.getSysRoleinfos();
        for(SysRoleinfo sysRoleinfo : sysRoleinfos){
            String name = sysRoleinfo.getName();
            //如果角色名多包函了  ‘系统管理员'这个字段，那么他就可以查看所有的在服务业务
            if(name.contains("系统管理员")){
                params.remove("loginUserID");
            }
        }
        actJBPMService.ticketOnServer(result,params);
        return result;
    }

    /**
     *
     * @param request
     * @param result
     * @return
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/runTicketTaskData.do")
    @ResponseBody
    public PageBean runTicketTaskData(HttpServletRequest request, PageBean result) throws Exception {
        result = result == null ? new PageBean() : result;

        Map<String,Object> map = new HashMap<String,Object>();
        //添加过滤条件
        String serialNumber = request.getParameter("serialNumber");
        String processDefinitonKey = request.getParameter("processDefinitonKey");
        String prodCategory = request.getParameter("prodCategory");/*区分是否是政企或者自有业务*/
        String isCustomerView = request.getParameter("isCustomerView") ; /*这个是如果是yes  那么自有业务和政企业务都有查询*/
        String busName = request.getParameter("busName") ;
        String ticketCategory = request.getParameter("ticketCategory") ;
        String locationCode = request.getParameter("locationCode") ;  //区域区分

        map.put("prodCategory",prodCategory);
        map.put("busName",busName);
        map.put("ticketCategory",ticketCategory);
        map.put("locationCode",locationCode);

        /*如果是客户经理视图，那么就查询结果就不区分政企和自有业务，所以就要把判断政企、自有的条件prodCategory去掉*/
        if(isCustomerView != null && !isCustomerView.equals("") && isCustomerView.equals("yes")){
            map.remove("prodCategory");
        }

        map.put("processDefinitonKey",processDefinitonKey);
        logger.debug(" processDefinitonKey: "+ processDefinitonKey);
        SysUserinfo sysUserinfo = getPrincipal();

        /* 下面是添加具有审批权限的所有人的id */
        map.put("assignee", sysUserinfo.getId() );
        map.put("serialNumber",serialNumber);

        map.put("lookAllTicketRole",0);  //此字段非常重要，用来判断当前登陆用户是否有查看所有工单的权限

        List<SysRoleinfo> sysRoleinfos = sysUserinfo.getSysRoleinfos();
        for(SysRoleinfo sysRoleinfo : sysRoleinfos){
            String name = sysRoleinfo.getName();
            //如果角色名多包函了  ‘查看所有工单'这个字段，那么他就可以查看所有的工旱
            if(name.contains("查看所有工单")){
                //代办任务里面，有审批权限才能查看
                //map.put("lookAllTicketRole",CategoryEnum.查看所有工单的角色.value());
                break;
            }
        }

        /**
         * 这里也是区分是否冲缓冲中获取
         */
        String PROJECT_QUERY_USE_REDIS = DevContext.PROJECT_QUERY_USE_REDIS;
        /*logger.debug("通过[common.properties]配置文件属性PROJECT_QUERY_USE_REDIS配置是否读取redis数据:[true:从redis读取],当前值是:["+PROJECT_QUERY_USE_REDIS+"]");
        if(1==2 && PROJECT_QUERY_USE_REDIS == null || DevContext.TRUE.equalsIgnoreCase(PROJECT_QUERY_USE_REDIS)){
            logger.debug("==============================[从缓存中获取的参数配置]==============================");
            //当前登录人ID
            String loginID = String.valueOf(sysUserinfo.getId());
            logger.debug("当前登录人ID:"+loginID);
            //匹配的KEY是  JBPM_SYS_USERINFO:USER_125_政企_100_PA_20170831_061
            String redis_key = DevContext.JBPM_SYS_USERINFO+":USER_"+loginID;
            logger.debug("政企和自有业务是否需要过滤。。。。。。。。。。。。。start");
            String GDDL = "*";
            if(map.containsKey("prodCategory")){
                //说明是要区分自有和政企业务
                if(prodCategory != null && "0".equals(prodCategory)){GDDL = "自有";}else{GDDL = "政企";}
            }
            //工单大类，政企还是自有
            redis_key += "_"+GDDL;
            logger.debug("政企和自有业务是否需要过滤。。。。。。。。。。。。。end");
            String ticketCategoryXXX = DevContext.getTicketCategory(processDefinitonKey,null);
            if(ticketCategory != null){
                if(serialNumber != null && !"".equals(serialNumber) && !"".equals(StringUtils.trim(serialNumber))){
                    redis_key += "_"+ticketCategory+":*"+serialNumber+"*";
                }else{
                    redis_key += "_"+ticketCategory+"*";
                }
            }else{
                redis_key += "_*";
            }
            logger.debug("首先判断代办工单的类别，是自有还是政企。是预勘还是开通等。。。。。。。。[prodCategory:"+GDDL+";工单类型:"+ticketCategory+"]");
            logger.debug("==============================[开始从缓存中获取正在代办的工单任务数据]==============================");
            //开始分页首先获取0到20的数据
            *//*获取所有的数据通过模糊获取[写法]:获取总数*//*
            //获取总数信息。
            Map<String,Object> resultMap = actJBPMService.getIdcRunTicketFormRedis(redis_key,result.getPageNo(),15);
            result = result == null ? new PageBean() : result;

            result.setItems((List)resultMap.get("items"));
            result.setTotalRecord((int)resultMap.get("total"));
        }else{
            actJBPMService.jbpmRunTicketTaskListPage(result,map);
        }*/
        actJBPMService.jbpmRunTicketTaskListPage(result,map);

        return result;
    }

    /**
     * 客户经理视视图里面的查询发起的工单
     * @param request
     * @return
     */
    @RequestMapping("/jbpmManagerView.do")
    @ResponseBody
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public PageBean jbpmManagerView(HttpServletRequest request, PageBean page) {
        Map<String,Object> map = new HashMap<String,Object>();

        //获取当前登陆用户的id，user_key_ecmp@id_1
        SysUserinfo mySysUserinfo = getPrincipal();
        map.put("assignee",mySysUserinfo.getId().toString());   //系统当前登陆人的ID
        actJBPMService.jbpmManagerViewListPage(page, map);
        return page;
    }
    /**
     * 客户经理视视图里面的查询发起的工单的更多跳转的页面
     * @param request
     * @return
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/jbpmManagerViewMore.do")
    public String jbpmManagerViewMore(HttpServletRequest request,org.springframework.ui.Model model) {
        model.addAttribute("loginUserId",getPrincipal().getId().toString());  //当前登陆人id
        return "jbpm/ticket/jbpmManagerViewMore";
    }

    //WCG：20180105确定在用，选择机架资源时，确定按机架还是机位分配！！！！！！！！！！！
    @RequestMapping("/rackOrRackUnitChoicePage/{idcNameCode}/{ticketInstId}")
    public String rackOrRackUnitChoicePage(@PathVariable("idcNameCode") String idcNameCode,@PathVariable("ticketInstId") String ticketInstId,HttpServletRequest request,org.springframework.ui.Model model) {
        model.addAttribute("idcNameCode",idcNameCode);
        model.addAttribute("ticketInstId",ticketInstId);
        return "jbpm/ticket/rackOrRackUnitChoicePage";
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/hisTicketTaskData.do")
    @ResponseBody
    public PageBean hisTicketTaskData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> map = new HashMap<String,Object>();
        //添加过滤条件
        String serialNumber = request.getParameter("serialNumber");
        String processDefinitonKey = request.getParameter("processDefinitonKey");
        String prodCategory = request.getParameter("prodCategory");/*区分是否是政企或者自有业务*/
        String isCustomerView = request.getParameter("isCustomerView");//yes代表是客户经理视图，客户经理视图政企自有业务都要查询
        String ticketCategory = request.getParameter("ticketCategory");
        String busName = request.getParameter("busName");
        String locationCode = request.getParameter("locationCode");  //区域区分

        //过滤条件是processDefinitonKey:[这个比较关键是因为可以过滤不同的流程]
        map.put("processDefinitonKey",processDefinitonKey);
        map.put("ticketCategory",ticketCategory);
        map.put("busName",busName);
        map.put("locationCode",locationCode);

        //得到当前登陆用户信息
        SysUserinfo sysUserinfo = getPrincipal();

        map.put("assignee", sysUserinfo.getId());
        map.put("serialNumber",serialNumber);
        map.put("prodCategory",prodCategory);
        map.put("serialNumber",serialNumber);
        //map.put("loginUserArea",loginUserArea);  //此字段非常重要，用来判断当前登陆用户是否有查看所有工单的权限

        map.put("lookAllTicketRole",0);  //此字段非常重要，用来判断当前登陆用户是否有查看所有工单的权限
        //map.put("lookAreaTicketRole",0);  //此字段非常重要，用来判断当前登陆用户是否有查看申请人区域工单的权限

        List<SysRoleinfo> sysRoleinfos = sysUserinfo.getSysRoleinfos();
        for(SysRoleinfo sysRoleinfo : sysRoleinfos){
            String name = sysRoleinfo.getName();
            //如果角色名多包函了  ‘查看所有工单'这个字段，那么他就可以查看所有的工旱
            if(name.contains("查看所有工单")){
                map.put("lookAllTicketRole",CategoryEnum.查看所有工单的角色.value());
                break;
            }
        }
        //判断是否是客户经理视图，客户经理视图是另外的查询，并且客户经理视图是查询可以开通的单子
        if(isCustomerView != null && isCustomerView.equals("yes")){
            map.remove("prodCategory");
            map.put("ticketStatus", JbpmStatusEnum.流程结束.value());   //ticketStatus=2代表工单结束，可以开通或者下线。。。。
            map.put("ticketCategory", CategoryEnum.预堪预占流程.value());   //预堪预占流程结束了就可以进行开通流程
        }
        actJBPMService.jbpmHisTicketTaskListPage(result, map);
        return result;
    }

    /**
     * 代办任务:只是显示用户组的情况。第一步申请就不显示。
     * @param request
     * @param result
     * @return
     */
    @RequestMapping("/ticketTaskQueryGridQueryData.do")
    @ResponseBody
    public PageBean ticketTaskQueryGridQueryData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;

        //获取代办任务信息
        Map<String,Object> map = new HashMap<String,Object>();

        //添加过滤条件
        String serialNumber = request.getParameter("serialNumber");
        String prodName = request.getParameter("prodName");
        String customerName = request.getParameter("customerName");
        String attribute = request.getParameter("attribute");
        String processDefinitonKey = request.getParameter("processDefinitonKey");
        String prodCategory = request.getParameter("prodCategory");/*区分是否是政企或者自有业务*/
        String ticket_ctl = request.getParameter("ticket_ctl");/*主要目的是控制是否需要显示所有的单子*/

        //过滤条件是processDefinitonKey:[这个比较关键是因为可以过滤不同的流程]
        map.put("processDefinitonKey",processDefinitonKey);
        logger.debug(" processDefinitonKey: "+ processDefinitonKey);
        /*通过自有业务特有的标志判断*/
        map.put("prodCategory",prodCategory);
        map.put("serialNumber",serialNumber);
        map.put("ticket_ctl",ticket_ctl);
        actJBPMService.jbpmTaskQueryListPage(result,map);
        return result;
    }
    /**
     * 预占预勘察管理:数据
     * @param request
     * @return
     */
    @RequestMapping("/ticketTaskGridQueryData.do")
    @ResponseBody
    public PageBean ticketTaskGridQueryData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;

        Map<String,Object> map = new HashMap<String,Object>();

        //添加过滤条件
        String serialNumber = request.getParameter("serialNumber");
        String processDefinitonKey = request.getParameter("processDefinitonKey");
        String prodCategory = request.getParameter("prodCategory");/*区分是否是政企或者自有业务*/
        String ticket_ctl = request.getParameter("ticket_ctl");/*主要目的是控制是否需要显示所有的单子*/

        //过滤条件是processDefinitonKey:[这个比较关键是因为可以过滤不同的流程]
        map.put("processDefinitonKey",processDefinitonKey);
        logger.debug(" processDefinitonKey: "+ processDefinitonKey);
        /*通过自有业务特有的标志判断*/
        map.put("prodCategory",prodCategory);
        map.put("serialNumber",serialNumber);
        map.put("ticket_ctl",ticket_ctl);
        List<Map<String, Object>> maps = actJBPMService.jbpmTaskQueryListPage(result, map);
        return result;
    }
    @RequestMapping("/ticketAllTaskGridQueryData.do")
    @ResponseBody
    public PageBean ticketAllTaskGridQueryData(HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> map = new HashMap<String,Object>();
        //添加过滤条件
        String serialNumber = request.getParameter("serialNumber");/* 工单ID */
        String ticketCategory = request.getParameter("ticketCategory");  /* 工单类型 */

        map.put("serialNumber",serialNumber);
        map.put("ticketCategory",ticketCategory);

        SysUserinfo sysUserinfo = getPrincipal();
        map.put("assignee",sysUserinfo.getId().toString());
        /*角色信息 */
        List<String> groupListMap = new ArrayList<String>();
        List<SysRoleinfo> sysRoleinfos = sysUserinfo.getSysRoleinfos();

        actJBPMService.jbpmAllTaskQueryListPage(result,map);
        return result;
    }
    public List<IdcHisProcCment> getIdcHisProcCmentListFromBinaryJedisCache(Long prodInstId,Long ticketInstId) throws Exception{
        List<IdcHisProcCment> hisProcCmentList = new ArrayList<IdcHisProcCment>();
        //通过工单ID获取正在运行的工单
        List<Long> his_proc_ids = idcHisProcCmentService.getIdsByProdIdAndTicketId(prodInstId,ticketInstId);
        if(his_proc_ids != null && !his_proc_ids.isEmpty()){
            for(int i = 0;i < his_proc_ids.size() ;i++){
                byte[] hisProcCmentBinary = redisManager.getBinaryJedisCache(DevContext.IDC_HIS_PROC_CMENT,prodInstId+"||"+ticketInstId+"||"+his_proc_ids.get(i));
                IdcHisProcCment hisProcCment = (IdcHisProcCment)serializableMethod.unserialize(hisProcCmentBinary);
                if(hisProcCment != null){
                    hisProcCmentList.add(hisProcCment);
                }
            }
        }
        return hisProcCmentList;
    }

    public List<IdcRunProcCment> getIdcRunProcCmentListFromBinaryJedisCache(Long prodInstId,Long ticketInstId) throws Exception{
        List<IdcRunProcCment> runProcCmentList = new ArrayList<IdcRunProcCment>();
        //通过工单ID获取正在运行的工单
        List<Long> run_proc_ids = idcRunProcCmentService.getIdsByProdIdAndTicketId(prodInstId,ticketInstId);
        if(run_proc_ids != null && !run_proc_ids.isEmpty()){
            for(int i = 0;i < run_proc_ids.size() ;i++){
                byte[]  runProcCmentBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RUN_PROC_CMENT,prodInstId+"||"+ticketInstId+"||"+run_proc_ids.get(i));
                if(runProcCmentBinary != null){
                    IdcRunProcCment runProcCment = (IdcRunProcCment)serializableMethod.unserialize(runProcCmentBinary);
                    if(runProcCment != null){
                        runProcCmentList.add(runProcCment);
                    }
                }
            }
        }
        return runProcCmentList;
    }

    /*
     * @return
     * @throws Exception
     */

    public List<AssetAttachmentinfo> getAssetAttachmentinfoFromBinaryJedisCache(IdcContract idcContract) throws Exception {
        List<AssetAttachmentinfo> attachmentinfoList = new ArrayList<AssetAttachmentinfo>();
        if (idcContract != null && idcContract.getId() != null) {
            //首先通过合同查询附件的id
            List<Long> attachIds = assetAttachmentinfoService.getIdsByContractId(idcContract.getId());
            if(attachIds != null && !attachIds.isEmpty()){
                for(int i = 0 ;i < attachIds.size(); i++){
                    Long attachId = attachIds.get(i);
                    byte[] attachmentListBinary = redisManager.getBinaryJedisCache(DevContext.ATTACHMENT,
                            "IDC_CONTRACT||" +
                                    "ID||" +
                                    ""+idcContract.getId()+"" +
                                    ""+AssetAttachmentinfo.tableName+"||" +
                                    "ID||" +attachId
                            );
                    AssetAttachmentinfo assetAttachmentinfo = new AssetAttachmentinfo();
                    if (attachmentListBinary != null) {
                        assetAttachmentinfo = (AssetAttachmentinfo) serializableMethod.unserialize(attachmentListBinary);
                    }
                    if(assetAttachmentinfo != null){
                        attachmentinfoList.add(assetAttachmentinfo);
                    }
                }
            }
            return attachmentinfoList;
        }
        return null;
    }
    public IdcNetServiceinfo getIdcNetServiceinfoFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception {
        if (idcRunTicket != null && idcRunTicket.getProdInstId() != null) {
            byte[] idcNetServiceinfoBinary = redisManager.getBinaryJedisCache(DevContext.IDC_NET_SERVICEINFO,
                    String.valueOf(idcRunTicket.getProdInstId()));
            IdcNetServiceinfo idcNetServiceinfo = new IdcNetServiceinfo();
            if (idcNetServiceinfoBinary != null) {
                idcNetServiceinfo = (IdcNetServiceinfo) serializableMethod.unserialize(idcNetServiceinfoBinary);
            }
            return idcNetServiceinfo;
        }
        return null;
    }

    public IdcContract getIdcContractFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getProdInstId() != null){
            byte[] idcContractBinary = redisManager.getBinaryJedisCache(DevContext.IDC_CONTRACT_TICKET_KEY,
                    String.valueOf(idcRunTicket.getTicketInstId()));
            IdcContract idcContract = new IdcContract();
            if(idcContractBinary != null){
                idcContract = (IdcContract)serializableMethod.unserialize(idcContractBinary);
            }
            return idcContract;
        }
        return null;
    }

    public ServiceApplyImgStatus getServiceApplyImgStatusFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getProdInstId() != null){
            byte[] serviceApplyImgStatusBinary = redisManager.getBinaryJedisCache(DevContext.SERVICE_APPLY_CATEGORY,
                    String.valueOf(idcRunTicket.getProdInstId()));
            ServiceApplyImgStatus serviceApplyImgStatus = new ServiceApplyImgStatus();
            if(serviceApplyImgStatusBinary != null){
                serviceApplyImgStatus = (ServiceApplyImgStatus)serializableMethod.unserialize(serviceApplyImgStatusBinary);
            }
            return serviceApplyImgStatus;
        }
        return null;
    }

    public Map<String,Object> getIdcReNewlyModelFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getProdInstId() != null){
            byte[] idcReNewlyModelBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RE_NEWLY_MODEL,
                    ServiceApplyEnum.增值业务.value()+"||"+String.valueOf(idcRunTicket.getProdInstId()));
            Map<String,Object> idcReNewlyModel = new HashMap<String,Object>();
            if(idcReNewlyModelBinary != null){
                idcReNewlyModel = (Map<String,Object>)serializableMethod.unserialize(idcReNewlyModelBinary);
            }
            return idcReNewlyModel;
        }
        return null;
    }

    public Map<String,Object> getIdcReServerModelFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getProdInstId() != null){
            byte[] idcReServerModelBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RE_SERVER_MODEL,
                    ServiceApplyEnum.主机租赁.value()+"||"+String.valueOf(idcRunTicket.getProdInstId()));
            Map<String,Object> idcReServerModel = new HashMap<String,Object>();
            if(idcReServerModelBinary != null){
                idcReServerModel = (Map<String,Object>)serializableMethod.unserialize(idcReServerModelBinary);
            }
            return idcReServerModel;
        }
        return null;
    }

    public Map<String,Object> getIdcReIpModelFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getProdInstId() != null){
            byte[] idcReIpModelBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RE_IP_MODEL,
                    ServiceApplyEnum.IP租用.value()+"||"+String.valueOf(idcRunTicket.getProdInstId()));
            Map<String,Object> idcReIpModelMap = new HashMap<String,Object>();
            if(idcReIpModelBinary != null){
                idcReIpModelMap = (Map<String,Object>)serializableMethod.unserialize(idcReIpModelBinary);
            }
            return idcReIpModelMap;
        }
        return null;
    }

    public Map<String,Object> getIdcRePortModelFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getProdInstId() != null){
            byte[] idcRePortModelBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RE_PORT_MODEL,
                    ServiceApplyEnum.端口带宽.value()+"||"+String.valueOf(idcRunTicket.getProdInstId()));
            Map<String,Object> idcRePortModel = new HashMap<String,Object>();
            if(idcRePortModelBinary != null){
                idcRePortModel = (Map<String,Object>)serializableMethod.unserialize(idcRePortModelBinary);
            }
            return idcRePortModel;
        }
        return null;
    }

    public Map<String,Object> getIdcReRackModelFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getProdInstId() != null){
            byte[] idcReRackModelBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RE_RACK_MODEL,
                    ServiceApplyEnum.机架.value()+"||"+String.valueOf(idcRunTicket.getProdInstId()));
            Map<String,Object> idcReRackModel = new HashMap<String,Object>();
            if(idcReRackModelBinary != null){
                idcReRackModel = (Map<String,Object>)serializableMethod.unserialize(idcReRackModelBinary);
            }
            return idcReRackModel;
        }
        return null;
    }

    public IdcReProduct getIdcReProductFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getProdInstId() != null){
            byte[] idcReProductBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RE_PRODUCT,String.valueOf(idcRunTicket.getProdInstId()));
            IdcReProduct idcReProduct = new IdcReProduct();
            if(idcReProductBinary != null){
                idcReProduct = (IdcReProduct)serializableMethod.unserialize(idcReProductBinary);
            }
            return idcReProduct;
        }
        return null;
    }


    public IdcReCustomer getIdcReCustomerFromBinaryJedisCache(IdcRunTicket idcRunTicket) throws Exception{
        if(idcRunTicket != null && idcRunTicket.getCustomerId() != null){
            byte[] idcReCustomerBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RE_CUSTOMER,String.valueOf(idcRunTicket.getCustomerId()));
            IdcReCustomer idcReCustomer = new IdcReCustomer();
            if(idcReCustomerBinary != null){
                idcReCustomer = (IdcReCustomer)serializableMethod.unserialize(idcReCustomerBinary);
            }
            return idcReCustomer;
        }
        return null;
    }

    /**
     * 20171116确定：申请操作： 这是什么申请？？   ???????????????
     * */
    @RequestMapping("/recoverFormSaveOrUpdateForCreateTicket.do")
    @ResponseBody
    public ResponseJSON recoverFormSaveOrUpdateForCreateTicket(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,IdcRunTicket idcRunTicket,IdcRunTicketRecover idcRunTicketRecover,HttpServletRequest request) throws Exception {

        ResponseJSON result = new ResponseJSON();
        try {
            SysUserinfo sysUserinfo = getPrincipal();
            idcRunProcCment.setAuthorId(String.valueOf(sysUserinfo.getId()));
            idcRunProcCment.setAuthor(sysUserinfo.getNick());
            idcRunTicketRecoverService.handWithRecover(getAppContext(request),idcRunProcCment,idcRunTicketRecover);
            result.setMsg("创建成功!");
            result.setResult("");
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 20171116确定：申请操作： 业务变更
     * */
    @RequestMapping("/reWriteProductFormSaveOrUpdate.do")
    @ResponseBody
    public ResponseJSON reWriteProductFormSaveOrUpdate(HttpServletRequest request) throws Exception {
        /**
         * 创建开通工单。此时先保存合同信息或者修改合同信息。
         * 1:先保存合同信息
         * 2:在创建工单，如果工单创建失败。手动业务回滚合同、附件、业务信息
         */
        ResponseJSON result = new ResponseJSON();
        String ticketCategoryFrom = request.getParameter("idcRunTicket.ticketCategoryFrom");
        String ticketCategoryTo = request.getParameter("idcRunTicket.ticketCategoryTo");
        String prodCategory = request.getParameter("idcRunTicket.prodCategory");
        String prodInstId = request.getParameter("idcRunTicket.prodInstId");
        String ticketInstId = request.getParameter("idcRunTicket.ticketInstId");
        try {
            Map<String,Object> variables=new HashMap<>();
            variables.put("ticketCategoryFrom",ticketCategoryFrom);
            variables.put("ticketCategoryTo",ticketCategoryTo);
            variables.put("ticketInstId",ticketInstId);
            variables.put("prodCategory",prodCategory);
            variables.put("prodInstId",prodInstId);
            variables.put("parentId",ticketInstId);     //!!!!!!! 这个parentId千万不要写错了
            SysUserinfo sysUserinfo = getPrincipal();
            IdcReProduct idcReProduct=new IdcReProduct();
            idcReProductService.handWithIdcReProduct(getAppContext(request),variables,idcReProduct);
            result.setMsg("创建成功!");
            result.setResult("");
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 2017/01/11：业务变更申请或驳回：变更需求信息的修改
     * */
    @RequestMapping("/updateTicketDemand.do")
    @ResponseBody
    public ResponseJSON updateTicketDemand(HttpServletRequest request) throws Exception {
        ResponseJSON result = new ResponseJSON();
        try {
            SysUserinfo sysUserinfo = getPrincipal();
            String idName = String.valueOf(request.getParameter("idName"));  //修改的ID的名字
            String idValue = String.valueOf(request.getParameter("idValue"));  //修改的ID的值
            String ticketInstId = String.valueOf(request.getParameter("ticketInstId"));  //工单id
            String prodInstId = String.valueOf(request.getParameter("prodInstId"));  //产品id

            Map<String,Object> idcTicketDemandMap=new HashMap<>();
            idcTicketDemandMap.put("idName",idName);
            idcTicketDemandMap.put("idValue",idValue);
            idcTicketDemandMap.put("ticketInstId",ticketInstId);
            idcTicketDemandMap.put("prodInstId",prodInstId);

            idcTicketDemandService.updateIdcTicketDemandByTicketId(idcTicketDemandMap);
            result.setMsg("操作成功!");
            result.setResult("");
        } catch (Exception e) {
            logger.error("操作失败!",e);
            result.setSuccess(false);
            result.setMsg("操作失败!"+e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 2017/01/12：需求信息修改，第一次的需求
     * */
    @RequestMapping("/updateTicketFirstDemand.do")
    @ResponseBody
    public ResponseJSON updateTicketFirstDemand(HttpServletRequest request) throws Exception {
        ResponseJSON result = new ResponseJSON();
        try {
            SysUserinfo sysUserinfo = getPrincipal();
            String idName = String.valueOf(request.getParameter("idName"));  //修改的ID的名字
            String idValue = String.valueOf(request.getParameter("idValue"));  //修改的ID的值
            String ticketInstId = String.valueOf(request.getParameter("ticketInstId"));  //工单id
            String prodInstId = String.valueOf(request.getParameter("prodInstId"));  //产品id

            Map<String,Object> idcTicketFirstDemandMap=new HashMap<>();
            idcTicketFirstDemandMap.put("idName",idName);
            idcTicketFirstDemandMap.put("idValue",idValue);
            idcTicketFirstDemandMap.put("ticketInstId",ticketInstId);
            idcTicketFirstDemandMap.put("prodInstId",prodInstId);

            idcReProddefService.updateFirstDemandByTicketId(idcTicketFirstDemandMap);

            result.setMsg("操作成功!");
            result.setResult("");
        } catch (Exception e) {
            logger.error("操作失败!",e);
            result.setSuccess(false);
            result.setMsg("操作失败!"+e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 2018.3.13：测试流程的需求信息修改
     * */
    @RequestMapping("/updateTestJbpmDemand.do")
    @ResponseBody
    public ResponseJSON updateTestJbpmDemand(HttpServletRequest request) throws Exception {
        ResponseJSON result = new ResponseJSON();
        try {
            SysUserinfo sysUserinfo = getPrincipal();
            String idName = String.valueOf(request.getParameter("idName"));  //修改的ID的名字
            String idValue = String.valueOf(request.getParameter("idValue"));  //修改的ID的值
            String ticketInstId = String.valueOf(request.getParameter("ticketInstId"));  //工单id
            String prodInstId = String.valueOf(request.getParameter("prodInstId"));  //产品id

            Map<String,Object> idcTickeTestDemandMap=new HashMap<>();
            idcTickeTestDemandMap.put("idName",idName);
            idcTickeTestDemandMap.put("idValue",idValue);
            idcTickeTestDemandMap.put("ticketInstId",ticketInstId);
            idcTickeTestDemandMap.put("prodInstId",prodInstId);

            idcReProddefService.updateTestJbpmDemand(idcTickeTestDemandMap);

            result.setMsg("操作成功!");
            result.setResult("");
        } catch (Exception e) {
            logger.error("操作失败!",e);
            result.setSuccess(false);
            result.setMsg("操作失败!"+e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/createJbpmEntrance.do")
    @ResponseBody
    public ResponseJSON productFormSaveOrUpdate(HttpServletRequest request, Model model,IdcReProduct idcReProduct) throws Exception {
        /* 传递参数。此时工单入口信息 */
        ResponseJSON result = new ResponseJSON();
        try {
            //这里使用oracle的常用方法[新增和修改相同方法]1111111111
            //随机订单名称
            Integer prodCategory = idcReProduct.getProdCategory();
            if(prodCategory == 1){
                idcReProduct.setProdName("政企业务:"+ Guid.Instance());
            }else{
                idcReProduct.setProdName("自有业务:"+Guid.Instance());
            }
            //用户信息id
            SysUserinfo sysUserinfo = getPrincipal();
            String catalog=idcReProduct.getCatalog();
            idcReProduct.setApplyId(String.valueOf(sysUserinfo.getId()));
            idcReProduct.setApplyName(sysUserinfo.getNick());
            idcReProduct.setApplyerName(sysUserinfo.getNick());
            idcReProduct.setApplyerRoles(sysUserinfo.getRoles_user());
            idcReProduct.setApplyerPhone(sysUserinfo.getPhone());

            /*--------预受理信息表  start--------------*/
            //拿到预勘信息
            String idcReProductbusName = request.getParameter("idcReProductbusName");
            String idcReProductidcName = request.getParameter("idcReProductidcName");
            String idcReProductvalidity = request.getParameter("idcReProductvalidity");
            String customerName = ServletRequestUtils.getStringParameter(request,"customerName",null);
            String lscsStartTime = request.getParameter("lscsStartTime");   //临时测试开始时间
            String lscsEntTime = ServletRequestUtils.getStringParameter(request,"lscsEntTime",null);  //临时测试结束时间
            Boolean isRejectTicket = Boolean.valueOf(request.getParameter("isRejectTicket"));//是否是驳回的单子
            idcReProduct.setIsRejectTicket(isRejectTicket);
            idcReProduct.setBusName(idcReProductbusName);
            idcReProduct.setIdcName(idcReProductidcName);
            idcReProduct.setValidity(idcReProductvalidity);
            idcReProduct.setCustomerName(customerName);

            //如果是临时测试，就有开始临时测试时间和结束临时测试时间
            if(lscsStartTime != null && lscsStartTime != ""){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                idcReProduct.setLscsStartTime(sdf.parse(lscsStartTime));
            }
            if(lscsEntTime != null && lscsEntTime != ""){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                idcReProduct.setLscsEntTime(sdf.parse(lscsEntTime));
            }

            if(idcReProduct.getCustomerId() == null || idcReProduct.getCustomerName() ==null  || idcReProduct.getApplyName()==null ){
                try {
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/html; charset=utf-8");
                    out.print("<script>" +
                            "top.layer.msg('客户信息或申请人丢失,请查看数据', {\n" +
                            "                icon: 2,\n" +
                            "                time: 1500\n" +
                            "          });</script>");
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                throw new Exception("GGGGG客户信息或申请人丢失,请查看数据..");
            }

        /*--------预受理信息表  end--------------*/
            String prodInstId = request.getParameter("prodInstId");//

            String category = request.getParameter("category");//
            Map<String,Object> createTicketMap = new HashMap<String,Object>();
            createTicketMap.put("prodInstId",prodInstId);//
            createTicketMap.put("prodCategory",prodCategory);//asdf
            createTicketMap.put("category",category);//category不能是空
            //这里也需要动态代理,即发起预勘完成后需要将工单的相关信息发布到redis中:此时就需要用到返回值

            Map<String, Object> maps = idcReProductService.createJbpmEntrance(idcReProduct, getAppContext(request));
            Long isEXIST=maps.get("EXIST") != null ? Long.valueOf(maps.get("EXIST").toString()) : null;
            //Long isEXIST = null;
            if(isEXIST != null && DevContext.EXIST == isEXIST){
                result.setMsg("存在相同名称["+idcReProduct.getBusName()+"]");
                result.setSuccess(false);
            }else if(isEXIST != null &&  isEXIST > DevContext.EXIST){

                //如果是临时测试工单，就有临时测试开始时间和结束时间，还是就是服务信息，是否有域名信息等。。。
                if(catalog != null && catalog.equals("800")){
                    IdcNetServiceinfo idcNetServiceinfo = idcReProduct.getIdcNetServiceinfo();

                    Long prodInstIdXXX=maps.get("prodInstId") != null ? Long.valueOf(maps.get("prodInstId").toString()) : null;
                    Long ticketInstIdXXX=maps.get("ticketInstId") != null ? Long.valueOf(maps.get("ticketInstId").toString()) : null;
                    if(prodInstIdXXX != null){
                        idcReProduct.setProdInstId(prodInstIdXXX);
                        idcNetServiceinfo.setProdInstId(prodInstIdXXX);
                    }
                    if(ticketInstIdXXX != null){
                        idcReProduct.setTicketInstId(ticketInstIdXXX);
                        idcNetServiceinfo.setTicketInstId(ticketInstIdXXX);
                    }

                    if(idcNetServiceinfo.getIcpaccesstype() == null){
                        idcNetServiceinfo.setDomainStatus(0L);
                    }else{
                        idcNetServiceinfo.setDomainStatus(1L);
                    }

                    //设置临时测试的开始时间和结束时间
                    idcReProductService.updateLSCS(idcReProduct.getLscsStartTime(),idcReProduct.getLscsEntTime(),prodInstIdXXX);
                    //保存服务信息，域名信息或者服务开通时间等
                    idcNetServiceinfo.setTicketInstId(idcReProduct.getTicketInstId());
                    idcNetServiceinfo.setProdInstId(idcReProduct.getProdInstId());
                    idcNetServiceinfoService.insertByLSCS(idcNetServiceinfo);
                    //保存成功
                    result.setMsg("["+idcReProduct.getBusName()+"],保存成功!");
                }else{
                    //保存成功
                    result.setMsg("["+idcReProduct.getBusName()+"],保存成功!");
                }
            }
        } catch (Exception e) {
            logger.error("保存失败",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 20171116确定：申请操作： 下线流程
     * */
    @RequestMapping("/haltFormSaveOrUpdate.do")
    @ResponseBody
    public ResponseJSON haltFormSaveOrUpdate(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,IdcRunTicketHalt idcRunTicketHalt,HttpServletRequest request) throws Exception {
        /**
         * 创建开通工单。此时先保存合同信息或者修改合同信息。
         * 1:先保存合同信息
         * 2:在创建工单，如果工单创建失败。手动业务回滚合同、附件、业务信息
         */
        ResponseJSON result = new ResponseJSON();
        try {
            SysUserinfo sysUserinfo = getPrincipal();
            idcRunProcCment.setAuthorId(String.valueOf(sysUserinfo.getId()));
            idcRunProcCment.setAuthor(sysUserinfo.getNick());
            idcRunTicketHaltService.handHaltTicket(getAppContext(request),idcRunProcCment,idcRunTicketHalt);
            result.setMsg("创建成功!");
            result.setResult(idcRunProcCment);
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 2018 01 17申请操作： 开通申请、变更开通申请
     * */
    @RequestMapping("/contractFormSaveOrUpdate.do")
    @ResponseBody
    public ResponseJSON contractFormSaveOrUpdate(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,@ModelAttribute("idcContract") IdcContract idcContract,HttpServletRequest request) throws Exception {
        /**
         * 创建开通工单。此时先保存合同信息或者修改合同信息。
         * 1:先保存合同信息
         * 2:在创建工单，如果工单创建失败。手动业务回滚合同、附件、业务信息
         */
        ResponseJSON result = new ResponseJSON();
        try {
            SysUserinfo sysUserinfo = getPrincipal();
            idcRunProcCment.setAuthorId(String.valueOf(sysUserinfo.getId()));
            idcRunProcCment.setAuthor(sysUserinfo.getNick());

            String prodCategory=idcContract.getIdcRunTicket().getProdCategory();  //0:自有业务,1:政企业务
            if(!prodCategory.equals("0")){
                //下面是政企开通申请的方法
                idcContractService.handOpenTicketWithContractInto(getAppContext(request),idcContract,idcRunProcCment,request);
            }else{
                //下面是自有开通申请的方法
                idcContractService.handOpenTicketWithContractInto_Self(getAppContext(request),idcContract,idcRunProcCment,request);
            }
            result.setMsg("创建成功!");
            result.setResult(idcContract);
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg("错误，原因："+e);
            e.printStackTrace();
        }

        return result;
    }

    //2017.12.18确定在使用此方法。严重合同名称是否重复
    @RequestMapping("/verifyContractRepeat.do")
    @ResponseBody
    public ResponseJSON verifyContractRepeat(HttpServletRequest request) throws Exception {
        //验证合同编码不能重复
        ResponseJSON result = new ResponseJSON();
        try {
            String contractno = request.getParameter("contractno");
            String ticketInstId = request.getParameter("idcRunTicket_ticketInstId");
            String prodInstId = request.getParameter("idcRunTicket_prodInstId");

            //下面的查询，是通过工单id和产品id，查询当前工单的子工单是否是删除或者作废，或者没有子工单，如果为null或者“”就说明可以开通
            String str = idcContractService.querySonTicketIsEnd(ticketInstId, prodInstId);

            Long aLong = idcContractService.verifyContractRepeat(contractno);
            if(aLong > 0 && str != null && !str.equals("")){
                result.setSuccess(false);
                result.setMsg("合同编码重复!");
            }
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 20171116确定：停机申请操作： 停机申请
     * */
    @RequestMapping("/pauseFormSaveOrUpdate.do")
    @ResponseBody
    public ResponseJSON pauseFormSaveOrUpdate( IdcRunProcCment idcRunProcCment,IdcRunTicketPause idcRunTicketPause,HttpServletRequest request) throws Exception {

        ResponseJSON result = new ResponseJSON();
        try {
            SysUserinfo sysUserinfo = getPrincipal();
            idcRunProcCment.setAuthorId(String.valueOf(sysUserinfo.getId()));
            idcRunProcCment.setAuthor(sysUserinfo.getNick());
            idcReProductService.pauseFormSaveOrUpdate(getAppContext(request), idcRunTicketPause, idcRunProcCment);

            //result.setMsg("创建成功!");
            //result.setResult(idcContract);
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 申请操作：
     * 			业务变更申请、变更开通申请、停机申请、下线申请、复通申请等。不包括开通申请
     * */
    @RequestMapping("/createTicketApply.do")
    @ResponseBody
    public ResponseJSON createTicketApply(HttpServletRequest request,IdcRunTicket idcRunTicket) throws Exception {

        ResponseJSON result = new ResponseJSON();
        try {
            /**
             * 其他参数利用reqeust
             */
            Map<String,Object> variables = new HashMap<String,Object>();
            variables.put("prodInstId",idcRunTicket.getProdInstId());
            variables.put("ticketCategoryFrom",idcRunTicket.getTicketCategoryFrom());
            variables.put("ticketCategoryTo",idcRunTicket.getTicketCategoryTo());
            variables.put("parentId",idcRunTicket.getTicketInstId());
            variables.put("prodCategory",idcRunTicket.getProdInstId());
            idcContractService.createTicketApply(getAppContext(request),variables);
            result.setMsg("创建成功!");
            //result.setSuccess(true);
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 审批的时候，所需要的界面数据
     * @param request
     * @param model
     * @param ticketInstId
     * @param prodInstId
     * @return
     * @throws Exception
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！ 跳转工单的详细信息页面
    @RequestMapping("/jbpm_apply/{ticketInstId}/{prodInstId}/{ticketStatus}/{ticketCategoryFrom}/{ticketCategoryTo}")
    public String jbpm_apply(HttpServletRequest request, org.springframework.ui.Model model,
                             @PathVariable("ticketInstId") Long ticketInstId,
                             @PathVariable("prodInstId") Long prodInstId,
                             @PathVariable("ticketStatus") Integer ticketStatus,
                             @PathVariable("ticketCategoryFrom") String ticketCategoryFrom,
                             @PathVariable("ticketCategoryTo") String ticketCategoryTo) throws Exception {
        Map<String,Object> procJbpmApplyMap = new HashMap<String,Object>();
        procJbpmApplyMap.put("ticketInstId",ticketInstId);
        procJbpmApplyMap.put("prodInstId",prodInstId);
        logger.debug("*****************ticketStatus["+ticketStatus+"]*****************************");
        //状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束
        Object idcTicket = null;
        Long customerId = null;
        String formKey = null;
        String prodCategory = null;   //0：自有业务，1：政企业务
        Boolean canPermission = false;
        SysUserinfo sysUserinfo = getPrincipal();
        if(ticketStatus == -2 || ticketStatus == -3 || ticketStatus == 2){
            logger.debug("查询历史信息表：");
            idcTicket = idcHisTicketService.getIdcHisTicketByIdTicketInstIdForPerission(ticketInstId,sysUserinfo.getId());
            customerId = ((IdcHisTicket)idcTicket).getCustomerId();
            formKey = ((IdcHisTicket)idcTicket).getFormKey();
            ((IdcHisTicket)idcTicket).setTicketCategoryFrom(ticketCategoryFrom);
            ((IdcHisTicket)idcTicket).setTicketCategory(ticketCategoryTo);
            prodCategory = ((IdcHisTicket) idcTicket).getProdCategory();
            canPermission = ((IdcHisTicket) idcTicket).getCanPermission();
            if(!canPermission){
                ((IdcHisTicket)idcTicket).setTicketResourceHandStatus(false);
            }
        }else{
            /*首先获取工单信息*/
            idcTicket = idcRunTicketService.getIdcRunTicketByIdTicketInstIdForPerission(ticketInstId,sysUserinfo.getId());
            customerId = ((IdcRunTicket)idcTicket).getCustomerId();
            formKey = ((IdcRunTicket)idcTicket).getFormKey();
            ((IdcRunTicket)idcTicket).setTicketCategoryFrom(ticketCategoryFrom);
            ((IdcRunTicket)idcTicket).setTicketCategory(ticketCategoryTo);
            prodCategory = ((IdcRunTicket) idcTicket).getProdCategory();
            canPermission = ((IdcRunTicket) idcTicket).getCanPermission();
            if(!canPermission){
                ((IdcRunTicket)idcTicket).setTicketResourceHandStatus(false);
            }
        }
        applyEasyuiFormModel(ticketCategoryFrom,ticketCategoryTo,ticketInstId,ticketStatus);
        /*获取客户信息*/
        IdcReCustomer idcReCustomer = idcReCustomerService.getFilterModelByReCustomerId(customerId);
        model.addAttribute("idcTicket",idcTicket);
        model.addAttribute("idcReCustomer",idcReCustomer);
        //流程审批form配置
        applyEasyuiTitleModel(formKey,ticketStatus);
        //开通
        openTicketModel(ticketCategoryFrom,ticketCategoryTo,ticketStatus,ticketInstId,prodCategory);
        //停机工单配置
        pauseTicketModel(ticketCategoryFrom,ticketCategoryTo,ticketStatus);
        //复通工单配置
        recoverTicketModel(ticketCategoryFrom,ticketCategoryTo,ticketStatus);
        //下线
        xxTicketModel(ticketCategoryFrom,ticketCategoryTo,ticketStatus);
        model.addAttribute("ticketCategoryTo",ticketCategoryTo);
        model.addAttribute("ticketCategoryFrom",ticketCategoryFrom);
        /** 这里总体控制 **/
        if(!canPermission){
            model.addAttribute("pageQueryDataStatus",false);
            model.addAttribute("isHasOpenEdit",false);
            model.addAttribute("isHaltTicketEdit",false);
            model.addAttribute("isRecoverTicketEdit",false);
            model.addAttribute("isPauseTicketEdit",false);
         }
         if(ticketCategoryFrom.equals(ticketCategoryTo)){
            //凡是第一次申请的步骤，都不需要显示评价里面的信息
             model.addAttribute("notShowEvaluate",true);
         }
         //ticketCategoryFrom,ticketCategoryTo   下面是变更需求
         if(Long.valueOf(ticketCategoryTo)>200){
             //ticketInstId
             IdcTicketDemand idcTicketDemand = null;
             try {
                 idcTicketDemand = idcTicketDemandService.queryAllByTicketInstId(String.valueOf(ticketInstId));
             } catch (Exception e) {
                 e.printStackTrace();
             }
             model.addAttribute("idcTicketDemand",idcTicketDemand);
         }

        try {
            //添加第一次的需求信息
            addFirstDemand(ticketInstId,prodInstId,ticketCategoryFrom);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("需求信息查询出错!!!!");
        }
        //加入预受理信息表信息
        IdcReProduct idcReProduct = idcReProductService.getFilterModelByProductId(prodInstId);

        model.addAttribute("idcReProduct",idcReProduct);  //
        model.addAttribute("demandReadOnly",null);  //控制客户需求只读的属性，为空就可以操作
        model.addAttribute("firstDemandReadOnly",null);  //控制首次需求只读的属性，为空就可以操作
        model.addAttribute("canPermission",canPermission);

        /*首资源需求能否修改  ;只有预占流程 并且 是申请阶段，才能修改首次需求信息     */
        boolean containsFirstDemand = formKey.contains("accept_apply_form");
        if(containsFirstDemand && ticketCategoryTo.equals(CategoryEnum.预堪预占流程.value())){
            model.addAttribute("firstDemand",null);
        }else{
            model.addAttribute("firstDemand","firstDemand");
        }
        /*  控制变更 需求表是否可以随时修改信息。 如果是 申请业务变更 ，那么就可以修改业务变更需求表的信息   */

        boolean containsChangeDemand = formKey.contains("open_comment_form")
                                        || formKey.contains("idc_ticket_business_change")
                                        || formKey.contains("open_change_comment_form")
                                        || formKey.contains("business_change_accept_apply_form") ;
        if(containsChangeDemand){
            model.addAttribute("demandReadOnly",null);
        }else{
            model.addAttribute("demandReadOnly","demandReadOnly");
        }

        String processdefinitonkey="";
        if(idcTicket!= null ){
            IdcRunTicket idcRunTicket = idcTicket instanceof IdcRunTicket ? ((IdcRunTicket) idcTicket) : null;
            IdcHisTicket idcHisTicket = idcTicket instanceof IdcHisTicket ? ((IdcHisTicket) idcTicket) : null;
            if(idcRunTicket != null){
                processdefinitonkey=idcRunTicket.getProcessdefinitonkey();
                model.addAttribute("processdefinitonkey",idcRunTicket.getProcessdefinitonkey());
            }else if(idcHisTicket!= null){
                processdefinitonkey=idcHisTicket.getProcessdefinitonkey();
                model.addAttribute("processdefinitonkey",idcHisTicket.getProcessdefinitonkey());
            }
        }

        try {
            //如果是测试流程，要添加idcNetServiceInfo信息
            if(processdefinitonkey != null && processdefinitonkey.equals(JBPMModelKEY.临时测试流程.value())){
                addIdcNetServiceInfo(model,ticketInstId,prodInstId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*这里都跳转到相同的url*/
        return "jbpm/ticket/jbpmTicket";
    }

    //WCG：2018228确定此方法在使用！！！！！！！！！！！
    public void addIdcNetServiceInfo(Model model, Long ticketInstId, Long prodInstId){

        IdcNetServiceinfo idcNetServiceinfo = idcNetServiceinfoService.getIdcNetServiceinfoByTicketInstId(ticketInstId);
        String icpsrvcontentcode = idcNetServiceinfo.getIcpsrvcontentcode();
        //翻译  服务内容   这个字段
        if(icpsrvcontentcode != null && !icpsrvcontentcode.equals("")){
            idcNetServiceinfo.setIcpsrvcontentcode(assemblyIcpsrvContentCode(icpsrvcontentcode));
        }

        model.addAttribute("idcNetServiceinfo",idcNetServiceinfo);

    }
    //WCG：2018114确定此方法在使用！！！！！！！！！！！  分配资源的 申请数量
    public void changeDemandWarns(IdcTicketDemand idcTicketDemand){
        String rackUsed="";

        if(idcTicketDemand != null && idcTicketDemand.getRack_Num()!=null && Long.valueOf(idcTicketDemand.getRack_Num())>0){
            rackUsed=rackUsed+"机架增加："+idcTicketDemand.getRack_Num()+"个  ";
        }else if(idcTicketDemand != null && idcTicketDemand.getRack_Num()!=null && Long.valueOf(idcTicketDemand.getRack_Num())<0){
            rackUsed=rackUsed+"机架减少："+idcTicketDemand.getRack_Num()+"个  ";
        }
        if(idcTicketDemand != null && idcTicketDemand.getOther_Msg()!=null && Long.valueOf(idcTicketDemand.getOther_Msg())>0){
            rackUsed=rackUsed+"   机位增加： "+idcTicketDemand.getOther_Msg()+"个  ";
        }else if(idcTicketDemand != null && idcTicketDemand.getOther_Msg()!=null && Long.valueOf(idcTicketDemand.getOther_Msg())<0){
            rackUsed=rackUsed+"   机位减少： "+idcTicketDemand.getOther_Msg()+"个  ";
        }
        model.addAttribute("changeRackNum",rackUsed);


        if(idcTicketDemand != null && idcTicketDemand.getPort_num()!=null && Long.valueOf(idcTicketDemand.getPort_num())>0){
            model.addAttribute("changePortNum","(需求增加) "+idcTicketDemand.getPort_num());
        }else if(idcTicketDemand != null && idcTicketDemand.getPort_num()!=null && Long.valueOf(idcTicketDemand.getPort_num())<0){
            model.addAttribute("changePortNum","(需求减少) "+idcTicketDemand.getPort_num());
        }
        if(idcTicketDemand != null && idcTicketDemand.getIp_num()!=null && Long.valueOf(idcTicketDemand.getIp_num())>0){
            model.addAttribute("changeIPNum","(需求增加) "+idcTicketDemand.getIp_num());
        }else if(idcTicketDemand != null && idcTicketDemand.getIp_num()!=null && Long.valueOf(idcTicketDemand.getIp_num())<0){
            model.addAttribute("changeIPNum","(需求减少) "+idcTicketDemand.getIp_num());
        }
        if(idcTicketDemand != null && idcTicketDemand.getServer_num()!=null && Long.valueOf(idcTicketDemand.getServer_num())>0){
            model.addAttribute("changeServerNum","(需求增加) "+idcTicketDemand.getServer_num());
        }else if(idcTicketDemand != null && idcTicketDemand.getServer_num()!=null && Long.valueOf(idcTicketDemand.getServer_num())<0){
            model.addAttribute("changeServerNum","(需求减少) "+idcTicketDemand.getServer_num());
        }
    }
    //WCG：2018114确定此方法在使用！！！！！！！！！！！    变更需求的提示信息
    public void firstDemandWarns(Long ticketInstId,Long prodInstId){
        IdcReRackModel idcReRackModel = idcReProddefService.getModelRackToBeanByCategory(ServiceApplyEnum.机架.value(), prodInstId,ticketInstId);
        IdcRePortModel idcRePortModel = idcReProddefService.getModelPortToBeanByCategory(ServiceApplyEnum.端口带宽.value(),prodInstId,ticketInstId);
        IdcReIpModel idcReIpModel = idcReProddefService.getModelIpToBeanByCategory(ServiceApplyEnum.IP租用.value(), prodInstId,ticketInstId);
        IdcReServerModel idcReServerModel = idcReProddefService.getModelServerToBeanByCategory(ServiceApplyEnum.主机租赁.value(), prodInstId,ticketInstId);
        IdcReNewlyModel idcReNewlyModel = idcReProddefService.getModelAddNewlyToBeanByCategory(ServiceApplyEnum.增值业务.value(), prodInstId,ticketInstId);


        Long rackAndRackunitCout=0L;

        if(idcReRackModel.getRackNum() != null){
            rackAndRackunitCout += idcReRackModel.getRackNum();
        }
        if(idcReRackModel.getSeatNum() != null){
            rackAndRackunitCout += idcReRackModel.getSeatNum();
        }

        model.addAttribute("idcReRackModelNum","(需求增加) "+ rackAndRackunitCout);
        model.addAttribute("idcRePortModelNum","(需求增加) "+idcRePortModel.getNum());
        model.addAttribute("idcReIpModelNum","(需求增加) "+idcReIpModel.getNum());
        model.addAttribute("idcReServerModelNum","(需求增加) "+idcReServerModel.getNum());
    }

    //WCG：2018112确定此方法在使用！！！！！！！！！！！  添加首次需求的 需求信息
    public void addFirstDemand(Long ticketInstId,Long prodInstId,String ticketCategoryFrom){
        IdcReRackModel idcReRackModel = idcReRackModelService.getModelByTicketInstId(ticketInstId);
        IdcRePortModel idcRePortModel = idcRePortModelService.getModelByTicketInstId(ticketInstId);
        IdcReIpModel idcReIpModel = idcReIpModelService.getModelByTicketInstId(ticketInstId);
        IdcReServerModel idcReServerModel = idcReServerModelService.getModelByTicketInstId(ticketInstId);
        IdcReNewlyModel idcReNewlyModel = idcReNewlyModelService.getModelByTicketInstId(ticketInstId);

        model.addAttribute("idcReRackModel",idcReRackModel);
        model.addAttribute("idcRePortModel",idcRePortModel);
        model.addAttribute("idcReIpModel",idcReIpModel);
        model.addAttribute("idcReServerModel",idcReServerModel);
        model.addAttribute("idcReNewlyModel",idcReNewlyModel);

        //添加申请资源的数量信息
        if(ticketCategoryFrom.equals(CategoryEnum.业务变更流程.value())){
            IdcTicketDemand idcTicketDemand = idcTicketDemandService.queryAllByTicketInstId(String.valueOf(ticketInstId));
            //分配资源时，提示信息
            changeDemandWarns(idcTicketDemand);
        }else{
            firstDemandWarns(ticketInstId,prodInstId);
        }
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public void applyEasyuiFormModel(String ticketCategoryFrom,String ticketCategoryTo,Long ticketInstId,Integer ticketStatus){
        model.addAttribute("pageQueryDataStatus",false);
        if(ticketStatus == -2 || ticketStatus == -3 || ticketStatus == 2){
            if(ticketCategoryFrom != null && ticketCategoryTo != null && "600".equals(ticketCategoryTo)){
                model.addAttribute("idcTicketHalt",idcHisTicketHaltService.queryIdcTicketHalt(ticketInstId));
            }
            if(ticketCategoryFrom != null && ticketCategoryTo != null && "400".equals(ticketCategoryTo)){
                model.addAttribute("idcTicketPause",idcHisTicketPauseService.queryByTicketInstId(ticketInstId));
            }
            if(ticketCategoryFrom != null && ticketCategoryTo != null && "500".equals(ticketCategoryTo)){
                model.addAttribute("idcTicketRecover",idcHisTicketRecoverService.queryByTicketInstId(ticketInstId));
            }
            model.addAttribute("pageQueryDataStatus",true);
            //这里需要查询满意度和评价
            model.addAttribute("idcTicketCommnet",idcHisTicketCommnetService.queryHisTikectCommentByTicketInstId(ticketInstId));
        }else{
            if(ticketCategoryFrom != null && ticketCategoryTo != null && "600".equals(ticketCategoryTo)){
                model.addAttribute("idcTicketHalt",idcHisTicketHaltService.queryIdcTicketHalt(ticketInstId));
            }
            if(ticketCategoryFrom != null && ticketCategoryTo != null && "400".equals(ticketCategoryTo)){
                model.addAttribute("idcTicketPause",idcRunTicketPauseService.queryByTicketInstId(ticketInstId));
            }
            if(ticketCategoryFrom != null && ticketCategoryTo != null && "500".equals(ticketCategoryTo)){
                model.addAttribute("idcTicketRecover",idcRunTicketRecoverService.queryByTicketInstId(ticketInstId));
            }
            //这里需要查询满意度和评价
            model.addAttribute("idcTicketCommnet", idcRunTicketCommnetService.queryTikectCommentByTicketInstId(ticketInstId));
        }

    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public void applyEasyuiTitleModel(String formKey,Integer ticketStatus){
    /*设置审批的tabs  title*/
    /*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束
    * 如果单子结束了 或者 最后一步且是通过状态：显示状态是评价
    * */
        /************* 是否显示评价。只要是最后一步，就显示评价 start****************/
        Boolean isLastStepTicket = idcRunTicketService.getIsLastStepTicket(formKey);//true表示不是最后一步，false表示是最后一步
        /************* 是否显示评价。只要是最后一步，就显示评价 end****************/
        String tabsApplyTitle = "审批";
        if( !isLastStepTicket || ticketStatus == 2){
            tabsApplyTitle = "评价";
        }
        model.addAttribute("commentFieldName",!isLastStepTicket?"备注":"备注");//意见名称
        model.addAttribute("isLastStepTicket",!isLastStepTicket);
        model.addAttribute("tabsApplyTitle",tabsApplyTitle);
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public void openTicketModel(String ticketCategoryFrom,String ticketCategoryTo,Integer ticketStatus,Long ticketInstId,String prodCategory){
        logger.debug("【开通信息】查询是否存在开通工单。。。。。。。。。。。。。。。start");
        //如果是预勘预占状态，不能查看工单信息:前提是直接查看的情况
        model.addAttribute("isOpenTicket",false);
        if(ticketCategoryFrom != null && ticketCategoryTo != null && (( !"100".equals(ticketCategoryTo) || !"100".equals(ticketCategoryFrom)))  ){
            /*model.addAttribute("isOpenTicket",true);*/
            model.addAttribute("isHasOpenEdit",false);
            Boolean isOpenTicket = idcRunTicketService.getIsHasOpenTicket(ticketInstId,ticketStatus);
            model.addAttribute("isOpenTicket",isOpenTicket);
            if(isOpenTicket){
                logger.debug("开通业务表单属于是否属于编辑状态....");

                Boolean isHasOpenEdit = idcRunTicketService.getIsHasOpenTicketEdit(ticketInstId,ticketStatus);
                if(ticketCategoryTo.equals("700") && !ticketCategoryTo.equals(ticketCategoryFrom)){
                    isHasOpenEdit = true;
                }
                model.addAttribute("isHasOpenEdit",isHasOpenEdit);
            }
        }
        logger.debug("【开通信息】查询是否存在开通工单。。。。。。。。。。。。。。。end");
    }

    /*
        下线
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public void xxTicketModel(String ticketCategoryFrom,String ticketCategoryTo,Integer ticketStatus){
        if(ticketCategoryFrom != null && ticketCategoryTo != null && "600".equals(ticketCategoryTo)){
            /*获取是否停机属于编辑状态:
            * 1:如果 ticketCategoryFrom 和 ticketCategoryTo不同则需要编辑
            * 2:如果 相同，则如果是驳回状态，则需要修改
            * */
            model.addAttribute("isHaltTicket",true);
            model.addAttribute("isHaltTicketEdit",false);
            model.addAttribute("isCreateTicketFun",false);
            Boolean isAddEasyuiTab = false;
            if(ticketCategoryFrom.equals(ticketCategoryTo)){
                model.addAttribute("isHaltTicketEdit", ticketStatus == -1);

            }else{
                //是否需要增加一个tabs
                isAddEasyuiTab = true;
                model.addAttribute("isHaltTicketEdit",true);
            }
            model.addAttribute("addEasyuiTab",isAddEasyuiTab);

            if(!isAddEasyuiTab){
                //model.addAttribute("commentFieldName","下线原因");//意见名称
            }
        }
        logger.debug("【业务下线】查询是否存在下线工单。。。。。。。。。。end】");
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public void recoverTicketModel(String ticketCategoryFrom,String ticketCategoryTo,Integer ticketStatus){
        logger.debug("【复通】查询是否存在复通工单。。idc_run_ticket_recover。。。。。。。。start】");
        if(ticketCategoryFrom != null && ticketCategoryTo != null && "500".equals(ticketCategoryTo)){
            //判断是否显示复通信息
            model.addAttribute("isRecoverTicket",true);
            model.addAttribute("isRecoverTicketEdit",false);
            //如果是创建工单则需要新增
            Boolean isAddRecoverEasyuiTab = false;
            if(ticketCategoryFrom.equals(ticketCategoryTo)){
                model.addAttribute("isRecoverTicketEdit", ticketStatus == -1);
            }else{
                isAddRecoverEasyuiTab = true;
                model.addAttribute("isRecoverTicketEdit",true);
            }
            model.addAttribute("isAddRecoverEasyuiTab",isAddRecoverEasyuiTab);
        }
        logger.debug("【复通】查询是否存在复通工单。。idc_run_ticket_recover。。。。。。。。start】");
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public void pauseTicketModel(String ticketCategoryFrom,String ticketCategoryTo,Integer ticketStatus){
        logger.debug("【停机】查询是否存在停机工单。。IDC_RUN_TICKET_PAUSE。。。。。。。。start】");
        if(ticketCategoryFrom != null && ticketCategoryTo != null && "400".equals(ticketCategoryTo)){
            //判断是否显示复通信息
            model.addAttribute("isPauseTicket",true);
            model.addAttribute("isPauseTicketEdit",false);
            //如果是创建工单则需要新增
            Boolean isAddPauseEasyuiTab = false;
            if(ticketCategoryFrom.equals(ticketCategoryTo)){
                model.addAttribute("isPauseTicketEdit", ticketStatus == -1);
            }else{
                isAddPauseEasyuiTab = true;
                model.addAttribute("isPauseTicketEdit",true);
            }

            model.addAttribute("isAddPasueEasyuiTab",isAddPauseEasyuiTab);
        }
        logger.debug("【停机】查询是否存在停机工单。。IDC_RUN_TICKET_PAUSE。。。。。。。。end】");
    }


    /**
     * ggg
     * 审批意见信息列表
     * @return
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/runProcCommentQueryData/{ticketStatus}/{ticketInstId}")
    @ResponseBody
    public Map<String,Object> runProcCommentQueryData(@PathVariable("ticketStatus") Integer ticketStatus,@PathVariable("ticketInstId") String ticketInstId) throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        /*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束*/
        if(ticketStatus == -2 || ticketStatus == -3 || ticketStatus == 2){
            //查询历史表
            List<IdcHisProcCment> lists = idcHisProcCmentService.queryHisProcCommentQueryData(ticketInstId);
            result.put("rows",lists);
            result.put("total",lists!=null&&!lists.isEmpty()?lists.size():0);
        }else{
            List<IdcRunProcCment> lists = idcRunProcCmentService.queryRunProcCommentQueryData(ticketInstId);
            result.put("rows",lists);
            result.put("total",lists!=null&&!lists.isEmpty()?lists.size():0);
        }
        return result;
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！  分配资源这一步获取是否具有资源，如果没有分配资源则不能则不能走流程
    @RequestMapping("/getTicketResourceCount.do")
    @ResponseBody
    public ResponseJSON getTicketResourceCount(Long ticketInstId,HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        result.setSuccess(true);
        try {
            /**************************** 获取是否具有资源，如果没有资源则提示不能评价******************************/
            Long ticketResourceCount = idcRunTicketService.getTicketResourceCount(ticketInstId);
            /*过程已经结束*/
            if(ticketResourceCount == null || ticketResourceCount == 0){
                result.setSuccess(false);
                result.setMsg("至少选择一个资源");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //WCG：20171226确定此方法在使用！！！！！！！！！！！  分配资源这一步，如果分配了端口，那么必须手动输入端口带宽
    @RequestMapping("/portHaveAssignation.do")
    @ResponseBody
    public ResponseJSON portHaveAssignation(String ticketInstId,HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            Boolean aBoolean = idcRunTicketResourceService.portHaveAssignation(ticketInstId);
            result.setSuccess(!aBoolean);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/decideExistsOtherTicket.do")
    @ResponseBody
    public Map<String,Object> decideExistsOtherTicket(Long ticketInstId,HttpServletRequest request) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success",false);
        map.put("iExistsOtherTicket",false);
        try {
             Boolean iExistsOtherTicket = actJBPMService.getIExistsOtherTicket(ticketInstId);
             if(iExistsOtherTicket){
                 /* 如果存在其他的单子则需要返回其他单子的工单号。。。 */
                 String existsOhterTicket = actJBPMService.getExistsOtherTicket(ticketInstId);
                 map.put("iExistsOtherTicket",iExistsOtherTicket);
                 map.put("existsOhterTicket",existsOhterTicket);
             }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 20171201 ：这个就是唯一的走流程审批的方法!!！
     */
    @RequestMapping("/handlerRunTikcetProcess.do")
    @ResponseBody
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public ResponseJSON handlerRunTikcetProcess(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,IdcReProduct idcReProduct,HttpServletRequest request) throws IOException {
        /*try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        ResponseJSON result = new ResponseJSON();
        try {
            SysUserinfo sysUserinfo = getPrincipal();
            idcRunProcCment.setAuthor(sysUserinfo.getNick());
            idcRunProcCment.setAuthorId(String.valueOf(sysUserinfo.getId()));
            idcReProduct.setApplyId(String.valueOf(sysUserinfo.getId()));
            if(idcReProduct!=null && idcReProduct.getIdcRunTicket()!=null && idcReProduct.getIdcRunTicket().getProdInstId() != null){
                idcReProduct.setProdInstId(idcReProduct.getIdcRunTicket().getProdInstId());
            }
            idcRunProcCmentService.handlerRunTikcetProcess(getAppContext(request),idcRunProcCment,idcReProduct);
            result.setMsg("提交成功!");
            result.setResult(idcRunProcCment);
        } catch (Exception e) {
            logger.error("[" + idcRunProcCment.getActName()+"],提交失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        result.setSuccess(true);
        return result;
    }

    /*   作废单子跳转的评分页面  */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("rubbishTicketPage/{ticketInstId}/{prodInstId}")
    public String saveAccedfptAdjunct(HttpServletRequest request,@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("prodInstId") Long prodInstId, org.springframework.ui.Model model) throws IOException {
        model.addAttribute("prodInstId",prodInstId);
        model.addAttribute("ticketInstId",ticketInstId);
        return "jbpm/rubbish/rubbishScorePage";
    }

    /*   删除的单子，跳转的删除警告页面  */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("rubbishTicketDeletePage/{ticketInstId}/{prodInstId}")
    public String rubbishTicketDeletePage(HttpServletRequest request,@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("prodInstId") Long prodInstId, org.springframework.ui.Model model) throws IOException {
        model.addAttribute("prodInstId",prodInstId);
        model.addAttribute("ticketInstId",ticketInstId);
        return "jbpm/rubbish/rubbishTicketDeletePage";
    }

    /*   删除工单之前，要验证此工单是否在在父工单  */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("deleteTicketQuerySon/{ticketInstId}/{prodInstId}")
    @ResponseBody
    public ResponseJSON deleteTicketQuerySon(HttpServletRequest request,@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("prodInstId") Long prodInstId, org.springframework.ui.Model model) throws IOException {
        ResponseJSON result = new ResponseJSON();

        try {
            //res如果为true，有子工单，不能删除父工单，否则就是没有子工单，可以删除
            Boolean res = idcRunProcCmentService.deleteTicketQuerySon(ticketInstId, prodInstId);
            result.setSuccess(res);
            if(!res){
                result.setMsg("当前工单有子工单，此工单不能删除！");
            }else{
                result.setMsg("没有子工单，此工单可以删除！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
    return result;

    }

    //作废  、  删除单子
    @RequestMapping("rubbishOrDeleteTicket.do")
    @ResponseBody
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public ResponseJSON rubbishOrDeleteTicket(IdcRunProcCment idcRunProcCment,HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();

        String prodInstId = ServletRequestUtils.getStringParameter(request,"prodInstId",null);
        String ticketInstId = ServletRequestUtils.getStringParameter(request,"ticketInstId",null);
        String operationSign = ServletRequestUtils.getStringParameter(request,"operationSign",null);
        Long starNum = ServletRequestUtils.getLongParameter(request,"starNum",0);
        String ticketCategory = idcHisTicketService.getIdcHisTicketCategoryById(ticketInstId);
        //获取该工单的类型
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ticketInstId",ticketInstId);
        paramMap.put("prodInstId",prodInstId);
        paramMap.put("ticketCategory",ticketCategory);
        paramMap.put("operationSign",operationSign);  //操作方式 0 代表删除工单操作， 1代表作废工单操作
        paramMap.put("satisfaction",starNum);  //评分

        //idcRunProcCment  操作人，操作人ID
        SysUserinfo sysUserinfo = getPrincipal();
        idcRunProcCment.setAuthorId(String.valueOf(sysUserinfo.getId()));
        idcRunProcCment.setAuthor(sysUserinfo.getNick());
        idcRunProcCment.setProdInstId(Long.valueOf(prodInstId));
        idcRunProcCment.setTicketInstId(Long.valueOf(ticketInstId));
        idcRunProcCment.setOperationSign(operationSign);

        //下面是删除作废的信息
        try {
            StringBuilder rubbishOrDeleteMaker=new StringBuilder();
            rubbishOrDeleteMaker.append("删除/作废操作人名称:"+sysUserinfo.getNick());
            rubbishOrDeleteMaker.append(";删除/作废操作人ID:"+String.valueOf(sysUserinfo.getId()));
            Date nowTime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
            String nowTimeStr = dateFormat.format(nowTime);
            rubbishOrDeleteMaker.append(";删除/作废操时间:"+nowTimeStr);
            paramMap.put("rubbishOrDeleteMSG",rubbishOrDeleteMaker);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(starNum != null && starNum != 0){
            idcRunProcCment.setStarNum(Integer.valueOf(starNum.intValue()));
        }
        try {
             //删除或者作废单子。他们都需要释放资源，存储过程
            Map<String,Object> resultMap = idcRunProcCmentService.rubbishOrDeleteTicket(idcRunProcCment,getAppContext(request),paramMap);
            if(resultMap !=  null && String.valueOf(resultMap.get("resultFlag")).equals(String.valueOf(DevContext.EXIST))){
                /*弹出框*/
                try {
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/html; charset=utf-8");
                    out.print("<script>" +
                                "top.layer.alert('"+resultMap.get("msg")+"', {\n" +
                            //"                skin: 'layui-layer-lan',\n" +
                            "                closeBtn: 0\n" +
                            "          });</script>");
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(resultMap !=  null && resultMap.get("resultFlag").equals(String.valueOf(DevContext.ERROR))){
                result.setSuccess(false);
                result.setMsg("删除失败");
            }else{
                result.setSuccess(true);
                result.setMsg("操作成功");
            }
        } catch (Exception e) {
            //logger.error("保存失败：id={}", id, e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/ticketRackResourceGridQueryData/{ticketInstId}/{ticketStatus}/{category}")
    @ResponseBody
    public PageBean ticketRackResourceGridQueryData(@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("category") String category,@PathVariable("ticketStatus") Integer ticketStatus, HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ticketInstId",ticketInstId);
        map.put("category",category);
        //自行分页
        //是否有查询条件
        String resourcename = request.getParameter("resourcename");
        if(resourcename != null && !"".equals(resourcename)){
            map.put("resourcename",resourcename);
        }
        result.setPageSize(7);
        model.addAttribute("pageQueryDataStatus",false);
        if(ticketStatus == -2 || ticketStatus == -3 || ticketStatus == 2){
            logger.debug("查询历史信息表：");
            idcHisTicketResourceService.queryTicketResourceListExtraPage(result,map);
            model.addAttribute("pageQueryDataStatus",true);//页面的所有数据只做查询处理
        }else{
            idcRunTicketResourceService.queryTicketResourceListExtraPage(result,map);
        }
        return result;
    }
    /**
     * 资源分配  start
     */
    @RequestMapping("/ticketRackResourceQueryData/{ticketInstId}/{category}")
    @ResponseBody
    public PageBean ticketRackResourceQueryData(@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("category") String category, HttpServletRequest request, PageBean result,IdcHisTicket idcHisTicket) {
        result = result == null ? new PageBean() : result;
        /*
        * 获取额外的参数信息
        * */
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ticketInstId",ticketInstId);
        map.put("category",category);
        //自行分页
        //是否有查询条件
        String rackName = request.getParameter("rackName");
        if(rackName != null && !"".equals(rackName)){
            map.put("rackName",rackName);
        }
        result.setPageSize(7);
        String is_author_apply_show = request.getParameter("is_author_apply_show");

        return result;
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/loadHisTicketGridData/{prodInstId}/{ticketInstId}")
    @ResponseBody
    public PageBean loadHisTicketGridData(@PathVariable("prodInstId") Long prodInstId,@PathVariable("ticketInstId") Long ticketInstId,HttpServletRequest request, PageBean result) {
        result = result == null ? new PageBean() : result;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("prodInstId",prodInstId);
        map.put("ticketInstId",ticketInstId);
        String serialNumber = request.getParameter("serialNumber");
        map.put("serialNumber",serialNumber);
        //相关工单
        actJBPMService.jbpmLinkedHisTicketTaskListPage(result,map);
        return result;
    }

    @RequestMapping("/ticketRackmcbResourceQueryData/{ticketInstId}/{category}")
    @ResponseBody
    public PageBean ticketRackmcbResourceQueryData(@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("category") String category, HttpServletRequest request, PageBean result,IdcHisTicket idcHisTicket) {
        result = result == null ? new PageBean() : result;
        /*
        * 获取额外的参数信息
        * */
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ticketInstId",ticketInstId);
        map.put("category",category);/**/

        //是否有查询条件
        String resourcename = request.getParameter("resourcename");
        if(resourcename != null && !"".equals(resourcename)){
            map.put("resourcename",resourcename);
        }
        //自行分页
        result.setPageSize(7);
        String is_author_apply_show = request.getParameter("is_author_apply_show");
        if(is_author_apply_show != null && !"".equals(is_author_apply_show) && "true".equals(is_author_apply_show)){
            idcHisTicketResourceService.queryTicketResourceListExtraPage(result,map);
        }else{
            idcHisTicketResourceService.queryTicketResourceListExtraPage(result,map);
        }
        return result;
    }

    @RequestMapping("/ticketServerResourceQueryData/{ticketInstId}/{category}")
    @ResponseBody
    public PageBean ticketServerResourceQueryData(@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("category") String category, HttpServletRequest request, PageBean result,IdcHisTicket idcHisTicket) {
        result = result == null ? new PageBean() : result;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ticketInstId",ticketInstId);
        map.put("category",category);
        //是否有查询条件
        String serverDeviceName = request.getParameter("serverDeviceName");
        if(serverDeviceName != null && !"".equals(serverDeviceName)){
            map.put("serverDeviceName",serverDeviceName);
        }
        //自行分页
        result.setPageSize(7);
        /*if(isProcessEnd == null || isProcessEnd != 1){
            idcRunTicketResourceService.queryTicketServerResourceListExtraPage(result,map);
        }else{
            idcHisTicketResourceService.queryTicketServerResourceListExtraPage(result,map);
        }*/
        return result;
    }

    @RequestMapping("/ticketIpResourceQueryData/{ticketInstId}/{category}")
    @ResponseBody
    public PageBean ticketIpResourceQueryData(@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("category") String category, HttpServletRequest request, PageBean result,IdcHisTicket idcHisTicket) {
        result = result == null ? new PageBean() : result;

        Map<String,Object> map = new HashMap<String,Object>();
        List<String> ipTypeList = new ArrayList<String>();
        ipTypeList.add("ipsubnet");
        ipTypeList.add("ipaddress");
        map.put("ticketInstId",ticketInstId);
        map.put("category",category);
        map.put("ipTypeList",ipTypeList);
        //是否有查询条件
        String resourcename = request.getParameter("resourcename");
        if(resourcename != null && !"".equals(resourcename)){
            map.put("resourcename",resourcename);
        }
        //自行分页
        result.setPageSize(7);
        String is_author_apply_show = request.getParameter("is_author_apply_show");
        if(is_author_apply_show != null && !"".equals(is_author_apply_show) && "true".equals(is_author_apply_show)){
            idcHisTicketResourceService.queryTicketResourceListExtraPage(result,map);
        }else{
            idcHisTicketResourceService.queryTicketResourceListExtraPage(result,map);
        }

        return result;
    }

    /*
        资源配置整改刘强
     */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/resourceSetting.do")
    @ResponseBody
    public ResponseJSON resourceSetting(String resourceSettingData, HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            ResponseJSON responseJSON = actJBPMService.resourceSetting(resourceSettingData);

            if(responseJSON != null && !responseJSON.isSuccess()){
                PrintWriter out = response.getWriter();
                out.print("<script type='text/javascript'>alert('添加失败，资源已经存在！');</script>");
                out.flush();
                out.close();
                //result.setMsg("此机架已经被选择，请选择其他机架!");
                return null;
            }
            result.setMsg("添加成功!");
        } catch (Exception e) {
            logger.error("添加失败!",e);
            result.setSuccess(false);
            String message = e.getMessage();
            if(message == null){
                message="添加失败！";
            }
            result.setMsg(message);
            e.printStackTrace();
        }

        return result;
    }

    /*
        一键删除资源的提示页面
     */
    //WCG：2018/1/9确定此方法在使用！！！！！！！！！！！　
    @RequestMapping("/removeAllResourcePage/{resourceCategory}/{ticketInstId}")
    public String removeAllResourcePage(@PathVariable("resourceCategory") String resourceCategory,
                                        @PathVariable("ticketInstId") String ticketInstId,
                                        HttpServletRequest request,
                                        org.springframework.ui.Model model) throws IOException {
        model.addAttribute("ticketInstId",ticketInstId);
        model.addAttribute("resourceCategory",resourceCategory);
        return "jbpm/deleteAllResourcePage";
    }

    /*
        一键删除资源的确定按钮，删除资源
     */
    //WCG：2018/1/9确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/removeAllResourceMake.do")
    @ResponseBody
    public ResponseJSON removeAllResourceMake(String ticketInstId, String resourceCategory, HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            actJBPMService.removeAllResourceMake(getAppContext(request),ticketInstId,resourceCategory);
            result.setSuccess(true);
            result.setMsg("操作成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("操作失败");
            e.printStackTrace();
        }
        return result;
    }

    /*
        分配端口资源的时候，分配带宽 和 ip的网关  手动输入的保存方法
     */
    //WCG：20171226确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/editPortAssignation.do")
    @ResponseBody
    public ResponseJSON editPortAssignation(HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            String portMSG=ServletRequestUtils.getStringParameter(request,"portMSG");   //端口信息
            String valuesChange=ServletRequestUtils.getStringParameter(request,"portChanges");    //端口的分配带宽或者网关 手动输入的信息

            //转成map类型的
            JSONObject portMSGJSONObject = JSONObject.fromObject(portMSG);
            Map<String, Object> editMSGMap = (Map<String, Object>)portMSGJSONObject;  //端口信息

            JSONObject editChangesJSONObject = JSONObject.fromObject(valuesChange);
            Map<String, Object>valuesChangesMap = (Map<String, Object>)editChangesJSONObject;

            String ticketInstId=String.valueOf(editMSGMap.get("ticketInstId"));   //端口信息
            //拿到formkey
            String formkey = idcRunTicketService.getFormkeyByTicketId(ticketInstId);

            //判断，如果是自有业务或者政企业务的  预占流程中的 预占堪查 或者 是业务变更流程中的资源上架这四步，才允许修改 分配带宽和网关地址
            //formkey !="temporary_shelveuping_form"意思是测试流程可以修改
            if( ticketInstId !=null && formkey !=null && !formkey.equals("temporary_shelveuping_form") && (!(formkey.contains("business_change_shelveuping_form") || formkey.contains("occupy_relonnissanle_form")))){
                result.setMsg("当前审批不允许修改！");
            }else {
                if(valuesChangesMap != null && valuesChangesMap.get("portassigation") != null){
                    Map<String,Object> netPortMaps=new HashMap<>();
                    netPortMaps.put("resourceId",String.valueOf(editMSGMap.get("resourceid")));   //资源id
                    netPortMaps.put("ticketInstId",String.valueOf(editMSGMap.get("ticketInstId")));   //工单id
                    netPortMaps.put("resourceCategory",String.valueOf(editMSGMap.get("category")));   //资源类型
                    netPortMaps.put("inputValues",String.valueOf(valuesChangesMap.get("portassigation")));  //端口的分配带宽手动输入的信息
                    idcRunTicketResourceService.editPortAssignation(netPortMaps);
                }else if(valuesChangesMap != null && valuesChangesMap.get("usedRackUnIt") != null){
                    Map<String,Object> ipMaps=new HashMap<>();
                    ipMaps.put("resourceId",String.valueOf(editMSGMap.get("resourceid")));   //资源id
                    ipMaps.put("ticketInstId",String.valueOf(editMSGMap.get("ticketInstId")));   //工单id
                    ipMaps.put("resourceCategory",String.valueOf(editMSGMap.get("category")));   //资源类型
                    ipMaps.put("inputValues",String.valueOf(valuesChangesMap.get("usedRackUnIt")));  //端口的分配带宽手动输入的信息
                    idcRunTicketResourceService.editPortAssignation(ipMaps);
                }
                result.setMsg("设置成功！");
            }
        } catch (Exception e) {
            logger.error("失败!",e);
            result.setSuccess(false);
            String message = "请输入数字！";
            if(message == null){
                message="添加失败！";
            }
            result.setMsg(message);
            e.printStackTrace();
        }
        return result;
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    /*此是删除资源的方法*/
    @RequestMapping("/deleteTicketResource.do")
    @ResponseBody
    public ResponseJSON deleteTicketResource(HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            Long primaryId=ServletRequestUtils.getLongParameter(request,"primaryId");
            Long ticketInstId= ServletRequestUtils.getLongParameter(request,"ticketInstId");
            Long resourceid=ServletRequestUtils.getLongParameter(request,"resourceid");
            String resourceCategory=ServletRequestUtils.getStringParameter(request,"resourceCategory");
            String ticketCategory=ServletRequestUtils.getStringParameter(request,"category");
            String category=ServletRequestUtils.getStringParameter(request,"category");
            String rackOrracunit=ServletRequestUtils.getStringParameter(request,"rackOrracunit");
            String ipType=ServletRequestUtils.getStringParameter(request,"ipType",null);
            String rackOrRackUnit=ServletRequestUtils.getStringParameter(request,"rackOrRackUnit",null);
            //如果是删除的是IP。则还需要传递IP的的类型

            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("primaryId",primaryId);
            paramMap.put("ticketInstId",ticketInstId);
            paramMap.put("resourceid",resourceid);
            paramMap.put("resourceCategory",resourceCategory);
            paramMap.put("category",category);
            paramMap.put("ipType",ipType);
            paramMap.put("ticketCategory",ticketCategory);
            paramMap.put("rackOrRackUnit",rackOrRackUnit);
            /*paramMap.put("rackOrracunit",rackOrracunit);*/

             //调用开始保存资源信息
             idcRunTicketResourceService.deleteTicketResource(paramMap,getAppContext(request));

            result.setMsg("操作成功!");
        } catch (Exception e) {
            logger.error("添加失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/loadRoomsWithTicket.do")
    @ResponseBody
    public ResponseJSON loadRoomsWithTicket(Long ticketInstId, HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            String roomidStr = idcRunTicketResourceService.loadRoomsWithTicket(ticketInstId);

            //通过工单ID查询此工单已经选择了的机架的ID，选择端口的时候传给端口
            List<String> rackIDList = idcRunTicketResourceService.loadRackIDWithTicket(ticketInstId);
            StringBuilder rackIDStr=new StringBuilder();
            //把数据库查出来的机架ID拼接成(12,23,24,24)这种格式
            if(rackIDList != null && rackIDList.size()>0){
                for (String str : rackIDList) {
                    if(rackIDStr.length()>0){
                        rackIDStr.append("_");
                    }
                    rackIDStr.append(str);
                }
            }

            Map<String,Object> params=new HashMap<>();
            params.put("roomidStr",roomidStr);
            params.put("rackIDStr",rackIDStr);
            result.setResult(params);
            result.setMsg("查询成功!");
        } catch (Exception e) {
            logger.error("查询失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //资源操作：这个是查询所选择的端口的方法
    @RequestMapping("/ticketPortResourceQueryData/{ticketInstId}/{category}")
    @ResponseBody
    public PageBean ticketPortResourceQueryData(@PathVariable("ticketInstId") Long ticketInstId,@PathVariable("category") String category, HttpServletRequest request, PageBean result,IdcHisTicket idcHisTicket) {
        result = result == null ? new PageBean() : result;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ticketInstId",ticketInstId);
        map.put("category",category);
        //是否有查询条件
        String resourcename = request.getParameter("resourcename");
        if(resourcename != null && !"".equals(resourcename)){
            map.put("resourcename",resourcename);
        }
        //自行分页
        result.setPageSize(7);
        String is_author_apply_show = request.getParameter("is_author_apply_show");
        if(is_author_apply_show != null && !"".equals(is_author_apply_show) && "true".equals(is_author_apply_show)){
            idcHisTicketResourceService.queryTicketResourceListExtraPage(result,map);
        }else{
            idcHisTicketResourceService.queryTicketResourceListExtraPage(result,map);
        }
        return result;
    }

    @RequestMapping("/xxFormSaveOrUpdate.do")
    @ResponseBody
    public ResponseJSON xxFormSaveOrUpdate(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            String prodInstId = request.getParameter("prodInstId");//
            String prodCategory = request.getParameter("prodCategory");//
            String category = request.getParameter("category");//
            Map<String,Object> createTicketMap = new HashMap<String,Object>();
            createTicketMap.put("prodInstId",prodInstId);
            createTicketMap.put("prodCategory",prodCategory);
            createTicketMap.put("category",category);
            //创建停机工单

            //把当前用户信息放入map中,然后保存到历史审批信息里面去
            SysUserinfo sysUserinfo = getPrincipal();
            Map map=new HashMap();
            map.put("author",sysUserinfo.getUsername());

            idcRunTicketHaltService.mergeDataInto(getAppContext(request),map,idcRunProcCment,createTicketMap);
            result.setMsg("创建成功!");
            result.setResult(idcRunProcCment);
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/recoverFormSaveOrUpdate.do")
    @ResponseBody
    public ResponseJSON recoverFormSaveOrUpdate(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            String prodInstId = request.getParameter("prodInstId");//
            String prodCategory = request.getParameter("prodCategory");//
            String category = request.getParameter("category");//
            Map<String,Object> createTicketMap = new HashMap<String,Object>();
            createTicketMap.put("prodInstId",prodInstId);
            createTicketMap.put("prodCategory",prodCategory);
            createTicketMap.put("category",category);

            //把当前用户信息放入map中,然后保存到历史审批信息里面去
            SysUserinfo sysUserinfo = getPrincipal();
            Map map=new HashMap();
            map.put("author",sysUserinfo.getUsername());

            //创建停机工单
            idcRunTicketPauseService.mergeDataInto(getAppContext(request),map,idcRunProcCment,createTicketMap);
            result.setMsg("创建成功!");
            result.setResult(idcRunProcCment);
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/bussnessChangeFormSaveOrUpdate.do")
    @ResponseBody
    public ResponseJSON bussnessChangeFormSaveOrUpdate(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            String prodInstId = request.getParameter("prodInstId");//
            String prodCategory = request.getParameter("prodCategory");//
            String category = request.getParameter("category");//
            Map<String,Object> createTicketMap = new HashMap<String,Object>();
            createTicketMap.put("prodInstId",prodInstId);
            createTicketMap.put("prodCategory",prodCategory);
            createTicketMap.put("category",category);
            //创建停机工单

            //把当前用户信息放入map中,然后保存到历史审批信息里面去
            SysUserinfo sysUserinfo = getPrincipal();
            Map map=new HashMap();
            map.put("author",sysUserinfo.getUsername());

            idcRunProcCmentService.mergeDataInto(getAppContext(request),map,idcRunProcCment,createTicketMap);
            result.setMsg("创建成功!");
            result.setResult(idcRunProcCment);
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/procPauseFormSaveOrUpdate.do")
    @ResponseBody
    public ResponseJSON procPauseFormSaveOrUpdate(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,IdcRunTicketPause idcRunTicketPause,HttpServletRequest request) throws IOException {
       ResponseJSON result = new ResponseJSON();
        try {
            String prodInstId = request.getParameter("prodInstId");//
            String prodCategory = request.getParameter("prodCategory");//
            String category = request.getParameter("category");//
            Map<String,Object> createTicketMap = new HashMap<String,Object>();
            createTicketMap.put("prodInstId",prodInstId);
            createTicketMap.put("prodCategory",prodCategory);
            createTicketMap.put("category",category);

            Boolean isRejectTicket = idcRunProcCment.getIsRejectTicket();

            //把当前用户信息放入map中,然后保存到历史审批信息里面去
            SysUserinfo sysUserinfo = getPrincipal();
            Map map=new HashMap();
            map.put("author",sysUserinfo.getUsername());

            //创建停机工单
            idcRunProcCmentService.mergeDataInto(getAppContext(request),map,idcRunProcCment,createTicketMap);

            result.setMsg("创建成功!");
            result.setResult(idcRunProcCment);
        } catch (Exception e) {
            logger.error("创建失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/historyDemand.do")
    @ResponseBody
    public Map<String,Object>  historyDemand(HttpServletRequest request) throws IOException {
        request.getParameter("");
        Map<String,Object> map = new HashMap<String,Object>();
        List<Map<String, Object>> maps = null;
        try {
            String prodInstId = request.getParameter("prodInstId");
            maps= idcTicketDemandService.queryHisDemand(prodInstId);

        } catch (Exception e) {
        }
        map.put("rows",maps);
        map.put("total",maps.size());
        return map;
    }

    /**
     * 停机:页面
     * @param request
     * @return
     */
    @RequestMapping("/pauseTaskGridQuery.do")
    public String  pauseTaskGridQuery(HttpServletRequest request,org.springframework.ui.Model model) {
        //流程类别情况
        String processDefinitonKey = request.getParameter("processDefinitonKey");
        model.addAttribute("processDefinitonKey",processDefinitonKey);
        return "jbpm/ticket/pauseTaskGridQuery";
    }

    /**
     * 查看按照机位分配的工单，没有选择机位的资源。安装机位分配，选择了机架必须分配机位
     */
    @RequestMapping("/findRackIsHaveUnit.do")
    @ResponseBody
    public Map<String,Object>  findRackIsHaveUnit(HttpServletRequest request,org.springframework.ui.Model model) throws Exception{
        String ticketInstId=request.getParameter("ticketInstId");
        if(ticketInstId == null || "".equals(ticketInstId)){
            throw new Exception("工单ID,不能为null，请查看程序....");
        }
        /*通过工单获取工单类型*/
        String ticketCategory = idcRunTicketService.getTicketCategoryById(ticketInstId);
        Map<String,Object> params=new HashMap<>();
        params.put("ticketInstId",ticketInstId);
        params.put("ticketCategory",ticketCategory);

        //count代表按照机位分配的机架，它们分配的机位的个数
        Integer count = actJBPMService.queryRackIsHaveUnitNEW(Long.valueOf(ticketInstId));
        Map<String,Object> map = new HashMap<String,Object>();

        /*下面的有问题*/
        //Long isHaveUnit = actJBPMService.queryRackIsHaveUnit(params);//按照u位分，还有没有分配的的机架


        map.put("count",count);
        map.put("success",true);
        return map;
    }

    /**
     *
     *
     * @return
     */
    public IdcReNewlyModel newLyMaptoBean(Map<String,Object> idcReNewlyModelMap){
        IdcReNewlyModel idcReNewlyModel = new IdcReNewlyModel();
        if(idcReNewlyModelMap != null && !idcReNewlyModelMap.isEmpty()) {
            Set<Map.Entry<String, Object>> mapSet = idcReNewlyModelMap.entrySet();
            Iterator<Map.Entry<String, Object>> it = mapSet.iterator();
            while(it.hasNext()){
                Map.Entry<String, Object> tmp = it.next();
                if(tmp.getKey().equals("ID")){
                    idcReNewlyModel.setId(Long.valueOf(String.valueOf(tmp.getValue())));
                }else if(tmp.getKey().equals("NAME")){
                    String name = String.valueOf(tmp.getValue());
                    if(name != null && !"".equals(name)){
                        idcReNewlyModel.setName(name);
                    }
                }else if(tmp.getKey().equals("CATEGORY")){
                    String category = String.valueOf(tmp.getValue());
                    if(category != null && !"".equals(category)){
                        idcReNewlyModel.setCategory(category);
                    }
                }else if(tmp.getKey().equals("DESC")){
                    String desc = String.valueOf(tmp.getValue());
                    if(desc != null && !"".equals(desc)){
                        idcReNewlyModel.setDesc(desc);
                    }
                }else if(tmp.getKey().equals("CREATETIME")){
                    String createtime = String.valueOf(tmp.getValue());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        idcReNewlyModel.setCreateTime(sdf.parse(createtime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return idcReNewlyModel;
    }


    /*
    *满意度查询页面
     *  */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/ticketSatisfactionReport.do")
    public String ticketSatisfactionReport(HttpServletRequest request) {
        return "jbpm/report/ticketSatisfactionReport";
    }

    /*
    * 满意度查询加载数据
    * */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/ticketSatisfactionReportData.do")
    @ResponseBody
    public List<Map<String, Object>> ticketSatisfactionReportData(HttpServletRequest request) {

        String createTime=request.getParameter("createTime");
        String endTime=request.getParameter("endTime");
        //String prodCategory=request.getParameter("prodCategory");   //1政企业务，0自有业务
        //String ticketCtegory=request.getParameter("ticketCtegory");   //工单类型   !_工单类型100:预勘 200:开通 400:停机 500:复通 600:下线 700:变更开通 800:临时测试,900:业务变更
        Map<String,Object> queryCondition=new HashMap<>();

        createTime=createTime != null ? createTime.substring(0,10) : "2017-1-1";
        endTime=endTime != null ? endTime.substring(0,10) : "2099-1-1";

        queryCondition.put("createTime",createTime);
        queryCondition.put("endTime",endTime);
        /*queryCondition.put("ticketCtegory",ticketCtegory);
        queryCondition.put("prodCategory",prodCategory);*/
        List<Map<String, Object>> maps = actJBPMService.ticketSatisfactionReportData(queryCondition);
        return maps;
    }

    /*
    *工单统计页面
     *  */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/allTicketCountPage.do")
    public String allTicketCountPage(HttpServletRequest request) {
        return "jbpm/report/allTicketCountPage";
    }

    /*
    * 工单统计页面加载数据
    * */
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/allTicketCountPageData.do")
    @ResponseBody
    public List<Map<String, Object>> allTicketCountPageData(HttpServletRequest request) {
        String createTime=request.getParameter("createTime");   //查询的开始时间
        String endTime=request.getParameter("endTime");     //查询的结束时间
        /*String prodCategory=request.getParameter("prodCategory");   //业务类型，政企业务还是自有业务
        String ticketActiviti=request.getParameter("ticketActiviti");  //流程的状态，是正在审批还是已经结束
        String ticketCtegory=request.getParameter("ticketCtegory");   //工单类型，开通流程还是下线流程。。。。*/
        /*createTime=createTime.substring(0,10);
        endTime=endTime.substring(0,10);*/

        Map<String,Object> params=new HashMap<>();
        params.put("createTime",createTime.substring(0,10));
        params.put("endTime",endTime.substring(0,10));
        /*params.put("prodCategory",prodCategory);
        params.put("ticketActiviti",ticketActiviti);
        params.put("ticketCtegory",ticketCtegory);*/

        List<Map<String,Object>> list = actJBPMService.allTicketCountPageData(params);
        return list;
    }

    /* */
    @RequestMapping("/buildingEChartsPage.do")
    public String buildingECharts(HttpServletRequest request) {
        return "jbpm/ECharts/buildingEChartsPage";
    }

     /**/

    @RequestMapping("/buildingEChartsTree.do")
    @ResponseBody
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    public List<Map<String, Object>> buildingEChartsTree(HttpServletRequest request) {
        //得到菜单树
        List<Map<String, Object>> dataCheckTree = actJBPMService.getDataCheckTree();
        return dataCheckTree;
    }

    /**/
    //WCG：20171208确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/buildingEChartsData.do")
    @ResponseBody
    public Map<String, Object> buildingEChartsData(HttpServletRequest request) {
        String dataCenterID = request.getParameter("dataCenterID");   //数据中心ID
        String buildingID = request.getParameter("buildingID");   //机楼ID
        String roomIDStr = request.getParameter("roomID");   //机房ID
        //得到机房的id
        Long dataCenterIDLong =(dataCenterID != null && dataCenterID !="") ? Long.valueOf(dataCenterID) : null;
        Long buildingIDLong =(buildingID != null && buildingID !="") ? Long.valueOf(buildingID) : null;
        Long roomIDLong =(roomIDStr != null && roomIDStr !="") ? Long.valueOf(roomIDStr) : null;

        Map<String, Object> statusMap = actJBPMService.getResourceStatusByRoomID(roomIDLong);

        return statusMap;
    }

    //WCG：20171213确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/getIdcNameByTicketID.do")
    @ResponseBody
    public ResponseJSON getIdcNameByTicketID(@ModelAttribute("idcRunProcCment") IdcRunProcCment idcRunProcCment,IdcRunTicketPause idcRunTicketPause,HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            String ticketInstId = request.getParameter("ticketInstId");

            String idcNameByTicketID = idcReProductService.getIdcNameByTicketID(ticketInstId);
            result.setMsg("查询成功!");
            result.setResult(idcNameByTicketID);
        } catch (Exception e) {
            logger.error("查询失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    //WCG：20171218确定此方法在使用！！！！！！！！！！！
    @RequestMapping("/customerViewData.do")
    @ResponseBody
    public ResponseJSON customerViewData(HttpServletRequest request) throws IOException {
        ResponseJSON result = new ResponseJSON();
        try {
            String loginUserID = request.getParameter("loginUserID");
            //通过当前登陆用户查询此用户的发起的工单合计和待办工单合计数量
            Map<String, Object> countMap = actJBPMService.customerViewData(loginUserID);

            String pending = String.valueOf(countMap.get("PENDING"));
            String founding = String.valueOf(countMap.get("FOUNDING"));
            result.setMsg("查询成功!");
            result.setResult(countMap);
        } catch (Exception e) {
            logger.error("查询失败!",e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

        /**
     *
     *
     * @return
     */
    public IdcReServerModel serverMaptoBean(Map<String,Object> idcReServerModelMap){
        IdcReServerModel idcReServerModel = new IdcReServerModel();
        if(idcReServerModelMap != null && !idcReServerModelMap.isEmpty()) {
            Set<Map.Entry<String, Object>> mapSet = idcReServerModelMap.entrySet();
            Iterator<Map.Entry<String, Object>> it = mapSet.iterator();
            while(it.hasNext()){
                Map.Entry<String, Object> tmp = it.next();
                if(tmp.getKey().equals("ID")){
                    idcReServerModel.setId(Long.valueOf(String.valueOf(tmp.getValue())));
                }else if(tmp.getKey().equals("TYPEMODE")){
                    String typeMode = String.valueOf(tmp.getValue());
                    if(typeMode != null && !"".equals(typeMode)){
                        idcReServerModel.setTypeMode(typeMode);
                    }
                }else if(tmp.getKey().equals("BRAND")){
                    String brand = String.valueOf(tmp.getValue());
                    if(brand != null && !"".equals(brand)){
                        idcReServerModel.setBrand(brand);
                    }
                }else if(tmp.getKey().equals("CODE")){
                    String code = String.valueOf(tmp.getValue());
                    if(code != null && !"".equals(code)){
                        idcReServerModel.setCode(code);
                    }
                }else if(tmp.getKey().equals("SPECNUMBER")){
                    String specNumber = String.valueOf(tmp.getValue());
                    if(specNumber != null && !"".equals(specNumber)){
                        idcReServerModel.setSpecNumber(specNumber);
                    }
                }else if(tmp.getKey().equals("RATEDPOWER")){
                    String ratedPower = String.valueOf(tmp.getValue());
                    if(ratedPower != null && !"".equals(ratedPower)){
                        idcReServerModel.setRatedPower(Integer.valueOf(ratedPower));
                    }
                }else if(tmp.getKey().equals("NUM")){
                    String num = String.valueOf(tmp.getValue());
                    if(num != null && !"".equals(num)){
                        idcReServerModel.setNum(Long.valueOf(num));
                    }
                }else if(tmp.getKey().equals("DESC")){
                    String desc = String.valueOf(tmp.getValue());
                    if(desc != null && !"".equals(desc)){
                        idcReServerModel.setDesc(desc);
                    }
                }else if(tmp.getKey().equals("CREATETIME")){
                    String createtime = String.valueOf(tmp.getValue());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        idcReServerModel.setCreateTime(sdf.parse(createtime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return idcReServerModel;
    }

    /**
     *
     *
     * @return
     */
    public IdcReIpModel ipMaptoBean(Map<String,Object> idcReIpModelMap){
        IdcReIpModel idcReIpModel = new IdcReIpModel();
        if(idcReIpModelMap != null && !idcReIpModelMap.isEmpty()) {
            Set<Map.Entry<String, Object>> mapSet = idcReIpModelMap.entrySet();
            Iterator<Map.Entry<String, Object>> it = mapSet.iterator();
            while(it.hasNext()){
                Map.Entry<String, Object> tmp = it.next();
                if(tmp.getKey().equals("ID")){
                    idcReIpModel.setId(Long.valueOf(String.valueOf(tmp.getValue())));
                }else if(tmp.getKey().equals("PORTMODE")){
                    String portMode = String.valueOf(tmp.getValue());
                    if(portMode != null && !"".equals(portMode)){
                        idcReIpModel.setPortMode(portMode);
                    }
                }else if(tmp.getKey().equals("NUM")){
                    String num = String.valueOf(tmp.getValue());
                    if(num != null && !"".equals(num)){
                        idcReIpModel.setNum(Long.valueOf(num));
                    }
                }else if(tmp.getKey().equals("DESC")){
                    String desc = String.valueOf(tmp.getValue());
                    if(desc != null && !"".equals(desc)){
                        idcReIpModel.setDesc(desc);
                    }
                }else if(tmp.getKey().equals("CREATETIME")){
                    String createtime = String.valueOf(tmp.getValue());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        idcReIpModel.setCreateTime(sdf.parse(createtime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return idcReIpModel;
    }

    /**
     *
     *
     * @return
     */
    public IdcRePortModel portMaptoBean(Map<String,Object> idcRePortModelMap){
            IdcRePortModel idcRePortModel = new IdcRePortModel();
            if(idcRePortModelMap != null && !idcRePortModelMap.isEmpty()) {
                Set<Map.Entry<String, Object>> mapSet = idcRePortModelMap.entrySet();
                Iterator<Map.Entry<String, Object>> it = mapSet.iterator();
                while(it.hasNext()){
                    Map.Entry<String, Object> tmp = it.next();
                    if(tmp.getKey().equals("ID")){
                        idcRePortModel.setId(Long.valueOf(String.valueOf(tmp.getValue())));
                    }else if(tmp.getKey().equals("PORTMODE")){
                        String portMode = String.valueOf(tmp.getValue());
                        if(portMode != null && !"".equals(portMode)){
                            idcRePortModel.setPortMode(portMode);
                        }
                    }else if(tmp.getKey().equals("BANDWIDTH")){
                        String bandwidth = String.valueOf(tmp.getValue());
                        if(bandwidth != null && !"".equals(bandwidth)){
                            idcRePortModel.setBandwidth(Long.valueOf(String.valueOf(tmp.getValue())));
                        }
                    }else if(tmp.getKey().equals("NUM")){
                        String num = String.valueOf(tmp.getValue());
                        if(num != null && !"".equals(num)){
                            idcRePortModel.setNum(Long.valueOf(num));
                        }
                    }else if(tmp.getKey().equals("DESC")){
                        String desc = String.valueOf(tmp.getValue());
                        if(desc != null && !"".equals(desc)){
                            idcRePortModel.setDesc(desc);
                        }
                    }else if(tmp.getKey().equals("CREATETIME")){
                        String createtime = String.valueOf(tmp.getValue());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            idcRePortModel.setCreateTime(sdf.parse(createtime));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return idcRePortModel;
        }

    /**
     *
     *
     * @return
     */
    public IdcReRackModel rackMaptoBean(Map<String,Object> idcReRackModelMap){
        IdcReRackModel idcReRackModel = new IdcReRackModel();
        if(idcReRackModelMap != null && !idcReRackModelMap.isEmpty()) {
            Set<Map.Entry<String, Object>> mapSet = idcReRackModelMap.entrySet();
            Iterator<Map.Entry<String, Object>> it = mapSet.iterator();
            while(it.hasNext()){
                Map.Entry<String, Object> tmp = it.next();
                if(tmp.getKey().equals("ID")){
                    idcReRackModel.setId(Long.valueOf(String.valueOf(tmp.getValue())));
                }else if(tmp.getKey().equals("SPEC")){
                    String spec = String.valueOf(tmp.getValue());
                    if(spec != null && !"".equals(spec)){
                        idcReRackModel.setSpec(spec);
                    }
                }else if(tmp.getKey().equals("RACKNUM")){
                    String rackNum = String.valueOf(tmp.getValue());
                    if(rackNum != null && !"".equals(rackNum)){
                        idcReRackModel.setRackNum(Long.valueOf(String.valueOf(tmp.getValue())));
                    }
                }else if(tmp.getKey().equals("SEATNUM")){
                    String seatNum = String.valueOf(tmp.getValue());
                    if(seatNum != null && !"".equals(seatNum)){
                        idcReRackModel.setSeatNum(Long.valueOf(seatNum));
                    }
                }else if(tmp.getKey().equals("RATEDPOWER")){
                    String ratedPower = String.valueOf(tmp.getValue());
                    if(ratedPower != null && !"".equals(ratedPower)){
                        idcReRackModel.setRatedPower(Integer.valueOf(ratedPower));
                    }
                }else if(tmp.getKey().equals("DESC")){
                    String desc = String.valueOf(tmp.getValue());
                    if(desc != null && !"".equals(desc)){
                        idcReRackModel.setDesc(desc);
                    }
                }else if(tmp.getKey().equals("SUPPLYTYPE")){
                    String suppllytype = String.valueOf(tmp.getValue());
                    if(suppllytype != null && !"".equals(suppllytype)){
                        idcReRackModel.setSupplyType(suppllytype);
                    }
                }else if(tmp.getKey().equals("RACKORRACUNIT")){
                    String rackoru = String.valueOf(tmp.getValue());
                    if(rackoru != null && !"".equals(rackoru)){
                        idcReRackModel.setRackOrracunit(rackoru);
                    }
                }else if(tmp.getKey().equals("CREATETIME")){
                    String createtime = String.valueOf(tmp.getValue());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        idcReRackModel.setCreateTime(sdf.parse(createtime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return idcReRackModel;
    }

    public String assemblyIcpsrvContentCode(String icpsrvContentCode){

        String[] arr = icpsrvContentCode.split(",");//根据“ ”和“,”区分

        StringBuilder stringBuilder = new StringBuilder(" ");

        if(icpsrvContentCode.contains("50001")){
            stringBuilder.append("，即时通讯");
        }
        if(icpsrvContentCode.contains("50002")){
            stringBuilder.append("，搜索引擎");
        }
        if(icpsrvContentCode.contains("50003")){
            stringBuilder.append("，综合门户");
        }
        if(icpsrvContentCode.contains("50004")){
            stringBuilder.append("，网上邮局");
        }
        if(icpsrvContentCode.contains("51001")){
            stringBuilder.append("，网络新闻");
        }
        if(icpsrvContentCode.contains("51002")){
            stringBuilder.append("，博客/个人空间");
        }
        if(icpsrvContentCode.contains("51003")){
            stringBuilder.append("，网络广告/信息");
        }
        if(icpsrvContentCode.contains("51004")){
            stringBuilder.append("，单位门户网站");
        }
        if(icpsrvContentCode.contains("52001")){
            stringBuilder.append("，网络购物");
        }
        if(icpsrvContentCode.contains("52002")){
            stringBuilder.append("，网上支付");
        }
        if(icpsrvContentCode.contains("52003")){
            stringBuilder.append("，网上银行");
        }
        if(icpsrvContentCode.contains("52004")){
            stringBuilder.append("，网上炒股/股票基金");
        }
        if(icpsrvContentCode.contains("53001")){
            stringBuilder.append("，网络游戏");
        }
        if(icpsrvContentCode.contains("53002")){
            stringBuilder.append("，网络音乐");
        }
        if(icpsrvContentCode.contains("53003")){
            stringBuilder.append("，网络影视");
        }
        if(icpsrvContentCode.contains("53004")){
            stringBuilder.append("，网络图片");
        }
        if(icpsrvContentCode.contains("53005")){
            stringBuilder.append("，网络软件/下载");
        }
        if(icpsrvContentCode.contains("54001")){
            stringBuilder.append("，网上求职");
        }
        if(icpsrvContentCode.contains("54002")){
            stringBuilder.append("，网上交友/婚介");
        }
        if(icpsrvContentCode.contains("54003")){
            stringBuilder.append("，网上房产");
        }
        if(icpsrvContentCode.contains("54004")){
            stringBuilder.append("，网络教育");
        }
        if(icpsrvContentCode.contains("54005")){
            stringBuilder.append("，网站建设");
        }
        if(icpsrvContentCode.contains("54006")){
            stringBuilder.append("，WAP");
        }
        if(icpsrvContentCode.contains("54007")){
            stringBuilder.append("，其他");
        }
        stringBuilder.deleteCharAt(1);
        return stringBuilder.toString();
    }


        /* 注入信息等全部放最下面 */
    private static final Log logger = LogFactory.getLog(ActJBPMController.class);
    @Autowired
    private IdcReProductService idcReProductService;
    @Autowired
    private IdcRunTicketService idcRunTicketService;
    @Autowired
    private ActJBPMService actJBPMService;
    @Autowired
    private IdcReCustomerService idcReCustomerService;
    @Autowired
    private IdcReProddefService idcReProddefService;//产品模型
    @Autowired
    private IdcRunTicketRecoverService idcRunTicketRecoverService;
    @Autowired
    private IdcRunProcCmentService idcRunProcCmentService;//流程的当前审批意见信息
    @Autowired
    private IdcHisProcCmentService idcHisProcCmentService;//流程的当前审批意见信息
    @Autowired
    private IdcRunTicketCommnetService idcRunTicketCommnetService;//工单描述
    @Autowired
    private IdcHisTicketCommnetService idcHisTicketCommnetService;//工单描述
    @Autowired
    private IdcRunTicketResourceService idcRunTicketResourceService;//工单资源服务
    @Autowired
    private IdcHisTicketResourceService idcHisTicketResourceService;//工单资源服务
    @Autowired
    private IdcHisTicketService idcHisTicketService;//idc的历史工单信息
    @Autowired
    private IdcContractService idcContractService;//合同服务
    @Autowired
    private AssetAttachmentinfoService assetAttachmentinfoService;
    @Autowired
    private IdcRunTicketPauseService idcRunTicketPauseService;
    @Autowired
    private IdcRunTicketHaltService idcRunTicketHaltService;
    @Autowired
    private IdcHisTicketHaltService idcHisTicketHaltService;
    @Autowired
    private IdcHisTicketPauseService idcHisTicketPauseService ;
    @Autowired
    private IdcHisTicketRecoverService idcHisTicketRecoverService;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private JavaSerializer serializableMethod;//序列化方式
    @Autowired
    private IdcTicketDemandService idcTicketDemandService;
    @Autowired
    private IdcReRackModelService idcReRackModelService;
    @Autowired
    private IdcRePortModelService idcRePortModelService;
    @Autowired
    private IdcReIpModelService idcReIpModelService;
    @Autowired
    private IdcReServerModelService idcReServerModelService;
    @Autowired
    private IdcReNewlyModelService idcReNewlyModelService;
    @Autowired
    private IdcNetServiceinfoService idcNetServiceinfoService;

}

package com.idc.action;

import com.idc.model.IdcRunTicket;
import com.idc.model.SysUserinfo;
import com.idc.service.ActJBPMService;
import com.idc.service.IdcReCustomerService;
import com.idc.service.SysMenuinfoService;
import com.idc.service.SysRegionService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import system.Global;
import utils.strategy.code.utils.CodeResourceUtil;
import utils.tree.TreeNode;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by mylove on 2016/11/28.
 */
@Controller
public class Index {
    private static final Logger logger = LoggerFactory.getLogger(Index.class);
    @Autowired
    private SysMenuinfoService sysMenuinfoService;
    @Autowired
    private SysRegionService sysRegionServce;
    @Autowired
    private IdcReCustomerService idcReCustomerService;
    @Autowired
    private ActJBPMService actJBPMService;

    @RequestMapping("/main_manager.do")
    public String main_manager(HttpServletRequest request) {
    	//管理者视图
        return "/mainpage/main_manager";
    }
    
    @RequestMapping("/main_manager_sitepowerchart.do")
    public String main_manager_sitepowerchart(HttpServletRequest request) {
    	request.setAttribute("sitepowertype", request.getAttribute("sitepowertype"));
    	//管理者视图
        return "/mainpage/main_manager_sitepowerchart";
    }
    
    @RequestMapping("/main_maintainer.do")
    public String main_maintainer(HttpServletRequest request, ModelMap map) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> list = idcReCustomerService.queryAllCustomer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> param = new HashedMap();
                param.put("value1", list.get(i).get("ID"));
                param.put("label", list.get(i).get("NAME"));
                param.put("value2", list.get(i).get("ADDR"));
                result.add(param);
            }
        }
        //String json = JSONArray.fromObject(result).toString();
        map.put("cusList", com.alibaba.fastjson.JSONObject.toJSON(result));
        //维护人员视图
        return "/mainpage/main_maintainer";
    }

    @RequestMapping("/main_cus_manager.do")
    public String main_cus_manager(HttpServletRequest request,ModelMap map,org.springframework.ui.Model model) {
        /*List<IdcRunTicket> idcRunTickets = actJBPMService.managerViewTips();*/

        SysUserinfo sysUserinfo = getPrincipal();
        Integer loginUserID = sysUserinfo.getId();  //当前登陆用户的ID

        /*model.addAttribute("idcRunTickets",idcRunTickets);*/
        model.addAttribute("loginUserID",loginUserID);

        //客户经理视图
        return "/mainpage/main_cus_manager";
    }

    @RequestMapping("/indexview.do")
    public String indexview(HttpServletRequest request) {
       String url = getView();
        request.setAttribute("viewtype",url);
        return "redirect:/"+url;
    }
    private String getView(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String url = "" ;
        if (authorities.contains(new SimpleGrantedAuthority(CodeResourceUtil.getRolePrefix() + "idc_customer_manager_group"))) {
                /*客户经理  idc_customer_manager_group  */
            url = "main_cus_manager.do";
        }else if(authorities.contains(new SimpleGrantedAuthority(CodeResourceUtil.getRolePrefix() + "idc_province_government_group"))||
                authorities.contains(new SimpleGrantedAuthority(CodeResourceUtil.getRolePrefix() + "idc_uesr_group"))){
            /*管理者视图*/
            url = "main_manager.do";
        }else{
            /*维护视图*/
            url = "main_maintainer.do";
        }
        return url;
    }
    @RequestMapping("/index.do")
    public String index(HttpServletRequest request) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            String view = getView();
            request.setAttribute("viewtype",view);
            List<TreeNode> urlAuthoritiesTree = sysMenuinfoService.getUrlAuthoritiesTree(userDetails.getUsername());
            request.getSession().setAttribute("menus", urlAuthoritiesTree);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.do";
        }
        String toUrl = request.getParameter("tourl");
        if(StringUtils.isNotEmpty(toUrl)){
            request.setAttribute("firstpage",request.getContextPath()+toUrl);
//            return "redirect:/index.do?tourl=" +toUrl;
        }else{
            request.setAttribute("firstpage",request.getContextPath()+"/first.do");
        }
        //直接顶级布局
        String wel = Global.getConfig("wel");
        return wel;
    }

    @RequestMapping("/redirect.do")
    public String redirect(String url, String fromindex, RedirectAttributes attrs) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            Map map = new HashMap();
            attrs.addFlashAttribute("fromindex", fromindex); return "redirect:" + url;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:" + url;
        }
    }

    @RequestMapping("/bindlink.do")
    public String BindResource(String type, String ids, ModelMap modelMap) {
        if (StringUtils.isNotEmpty(type))
            modelMap.put("type", type);
        if (StringUtils.isNotEmpty(ids))
            modelMap.put("ids", ids);
        return "resourcelink/createlink";
    }

    @RequestMapping("/first.do")
    public String frist(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        //直接顶级布局
        return "/first/index";
    }

    public SysUserinfo getPrincipal(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof SysUserinfo) {
            return (SysUserinfo) principal;
        }else{
            return null;
        }
    }



//
//    @RequestMapping(value = "/top5.do", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String Top(HttpServletRequest request) {
//        try {
//            List<RptCapMachroom> currPue = reportService.getCurrPueByRoom(null);
//            if (currPue != null) {
//                List<RptCapMachroom> top5 = null, last5 = null;
//                if (currPue.size() > 5) {
//                    top5 = currPue.subList(0, 5);
//                    last5 = currPue.subList(currPue.size() - 5, currPue.size());
//                } else {
//                    top5 = currPue;
//                    last5 = currPue;
//                }
////                List<ResMachroom> resMachrooms = machroomService.queryList();
////                Map<String, String> rommName = new HashMap<>();
////                for (ResMachroom resMachroom : resMachrooms) {
////                    rommName.put(resMachroom.getMachroomid().toString(), resMachroom.getMachname());
////                }
////                JSONArray top5arr = new JSONArray();
//                JSONObject o;
////                for (RptCapMachroom rptCapMachroom : top5) {
////                    o = JSONObject.fromObject(rptCapMachroom);
//////                    o.put("roomName", rommName.get(rptCapMachroom.getMachroomid().toString()));
////                    top5arr.add(o);
////                }
////                JSONArray top5last = new JSONArray();
////                for (RptCapMachroom rptCapMachroom : last5) {
////                    o = JSONObject.fromObject(rptCapMachroom);
////                    o.put("roomName", rommName.get(rptCapMachroom.getMachroomid().toString()));
////                    System.out.println(rptCapMachroom.getMachroomid().toString());
////                    top5last.add(o);
////                }
//                o = new JSONObject();
//                o.put("top5", JSONArray.fromObject(top5));
//                o.put("last5", JSONArray.fromObject(last5));
//                return o.toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("获取TOP5失败", e);
//        }
//        //直接顶级布局
//        return new JSONObject().toString();
//    }
//
//    /***
//     * 能耗趋势
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/usagetrend.do", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String usageTrend(HttpServletRequest request) {
//        try {
//            Map<String, List<Double>> yeadDate = new HashMap<>();//年能耗总数据
//            List<Map<String, Object>> datas = new ArrayList<>();
//
//            List<Map<String, Object>> list = reportService.getAllUsege();
//            List<String> leg = new ArrayList<>();//X轴数据
//            for (Map<String, Object> objectMap : list) {
//                String year = objectMap.get("yearstr").toString();//日期
//                String[] split = year.split("-");//[0]//year [1] month
//                String s = split[0] + "总能耗";
//                String month = split[1];//月份
//                List<Double> floats = yeadDate.get(s);
//                if (floats == null) {
//                    floats = new ArrayList<>();
//                    for (int i = 0; i < 12; i++) {
//                        floats.add(0D);
//                    }
//                }
//                Double totalUsage = (Double) objectMap.get("totalUsage");
//                floats.set(Integer.parseInt(month.replace("月", "")) - 1, totalUsage);
//                yeadDate.put(s, floats);
//                if (!leg.contains(s)) {//添加X轴
//                    leg.add(s);
//                }
//            }
//            Map<String, List<Double>> yeadroomDate = new HashMap<>();//年能耗总数据
//            List<Map<String, Object>> list1 = reportService.getAllUsegeByRoomAndMonth();
//            for (Map<String, Object> objectMap : list1) {
//                String year = objectMap.get("yearstr").toString();//日期
//                String[] split = year.split("-");//[0]//year [1] month
//                String s = split[0] + "机楼能耗";
//                String month = split[1];//月份
//                List<Double> floats = yeadroomDate.get(s);
//                if (floats == null) {
//                    floats = new ArrayList<>();
//                    for (int i = 0; i < 12; i++) {
//                        floats.add(0D);
//                    }
//                }
//                Double totalUsage = (Double) objectMap.get("totalUsage");
//                floats.set(Integer.parseInt(month.replace("月", "")) - 1, totalUsage);
//                yeadroomDate.put(s, floats);
//                if (!leg.contains(s)) {//添加X轴
//                    leg.add(s);
//                }
//            }
////            Map<String, List<Map<String, Object>>> yeadroomDate = new HashMap<>();//年能耗总数据
////
////            for (Map<String, Object> objectMap : list1) {
////                String year = objectMap.get("yearstr").toString();//日期
////                String[] split = year.split("-");//[0]//year [1] month
////                String s = split[0] + "机楼能耗";
////                String month = split[1];//月份
////                List<Map<String, Object>> maps = yeadroomDate.get(s);
////                Double totalUsage = (Double) objectMap.get("totalUsage");
////                Double mainUsage = (Double) objectMap.get("mainUsage");
////                Double airconUsage = (Double) objectMap.get("airconUsage");
////                Double otherUsage = (Double) objectMap.get("otherUsage");
////                if (maps == null) {
////                    maps = new ArrayList<>();
////                    for (int i = 0; i < 12; i++) {
////                        Map<String, Object> valuemap = new HashMap<>();
////                        valuemap.put("value", 0);
////                        valuemap.put("mainUsage", 0);
////                        valuemap.put("airconUsage", 0);
////                        valuemap.put("otherUsage", 0);
////                        maps.add(valuemap);
//////                            floats.add(0D);
////                    }
////                }
////                int indexmonth = Integer.parseInt(month.replace("月", "")) - 1;
////                Map<String, Object> tmap = maps.get(indexmonth);
////                tmap.put("value", totalUsage);
////                tmap.put("mainUsage", mainUsage);
////                tmap.put("airconUsage", airconUsage);
////                tmap.put("otherUsage", otherUsage);
////                yeadroomDate.put(s, maps);
////            }
//            JSONObject o = new JSONObject();
//            o.put("alldata", yeadDate);
//            o.put("allroomdata", yeadroomDate);
////            o.put("xAxis",xAxis);
//            return o.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("获取TOP5失败", e);
//        }
//        //直接顶级布局
//        return new JSONObject().toString();
//    }
//
//    /***
//     * 分类饼图
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/usageGroup.do", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String usageGroup(HttpServletRequest request) {
//        try {
//            List<Map<String, Object>> BuildTypeData = reportService.groupByType(0);
//            List<Map<String, Object>> usetypeData = reportService.groupByType(1);
//            List<Map<String, Object>> tmp = new ArrayList<>();
//            for (Map<String, Object> objectMap : usetypeData) {
//                if(objectMap==null)continue;
//                Double mainusage = (Double) objectMap.get("mainusage");
//                Double airconusage = (Double) objectMap.get("airconusage");
//                Double otherusage = (Double) objectMap.get("otherusage");
//                Map<String, Object> map = new HashMap<>();
//                map.put("name", "主设备");
//                map.put("value", mainusage);
//                tmp.add(map);
//                map = new HashMap<>();
//                map.put("name", "空调设备");
//                map.put("value", airconusage);
//                tmp.add(map);
//                map = new HashMap<>();
//                map.put("name", "其他设备");
//                map.put("value", otherusage);
//                tmp.add(map);
//            }
//            JSONObject o = new JSONObject();
//            o.put("buildtypedata", BuildTypeData);
//            o.put("usetypedata", tmp);
//            return o.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("获取TOP5失败", e);
//        }
//        //直接顶级布局
//        return new JSONObject().toString();
//    }
//
//    @RequestMapping("/index/res_group")
//    @ResponseBody
//    public List<Map<String, Object>> resGroup(EasyUIPageBean pageBean) {
//        List<Map<String, Object>> list = reportService.resGroup();
//        if (list != null)
//            return list;
//        return new ArrayList<>();
//    }
//
//    /***
//     * 分类能耗
//     * @param pageBean
//     * @return
//     */
//    @RequestMapping("/index/usagebytype.do")
//    @ResponseBody
//    public List<Map<String, Object>> usageByType(EasyUIPageBean pageBean,Boolean lastmonth) {
//        List<Map<String, Object>> list = reportService.usageByType(lastmonth==null?false:lastmonth);
//        if (list != null)
//            return list;
//        return new ArrayList<>();
//    }
//    /***
//     * 获取各地市 能耗
//     * @return
//     */
//    @RequestMapping(value = "/puebyregion.do",produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public List<Map<String,Object>> puebyregion(){
//        List<Map<String,Object>> result = reportService.puebyregion();
////        List<TreeNode> tree = sysRegionServce.getTree(true, 0, null,false);
////        for (int i = 0; i < 10; i++) {
////            Map<String,Object> map = new HashMap<>();
////            map.put("name","丽江市");
////            map.put("value",10);
////            result.add(map);
////        }
////        for (TreeNode treeNode : tree.get(0).getChildren()) {
////            Map<String,Object> map = new HashMap<>();
////            map.put("name",treeNode.getName());
////            map.put("value",Math.round(Math.random()*100));
////            result.add(map);
////        }
////        sysRegionServce.getTree()
////        Map<String,Object> map = new HashMap<>();
////        map = new HashMap<>();
////        map.put("name","丽江市");
////        map.put("value",10);
////        result.add(map);
////
////        map = new HashMap<>();
////        map.put("name","普洱市");
////        map.put("value",20);
////        result.add(map);
//        return result;
//    }
//
//
//
//    /***
//     * 单端口
//     * @param
//     * @return
//     */
//    @RequestMapping("/index/usageline.do")
//    @ResponseBody
//    public List<Map<String, Object>> usageLine() {
//        List<Map<String, Object>> list = reportService.AvgArrierAndWBport();
//        if (list != null)
//            return list;
//        return new ArrayList<>();
//    }
}

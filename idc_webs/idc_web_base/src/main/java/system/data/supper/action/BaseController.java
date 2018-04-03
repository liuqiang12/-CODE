package system.data.supper.action;

import com.idc.model.SysUserinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import system.data.cache.web.RequestCache;
import system.data.cache.web.WebCache;
import system.data.datetool.RegionSettingCfg;
import system.data.msg.ajax.ResultMsg;
import system.data.msg.ajax.ResultMsg.ResultData;
import utils.typeHelper.CustomStringEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * BaseController:提供一些初始化服务<br/>
 * <b>描述</b>：<br/>
 * 属性说明：<br/>
 * <b>request</b>，<b>response</b>，<b>session</b><br/>
 * <b>model</b>：如果要往jsp传递值，请使用model.addAttribute设置；因为el表达式会先从model->request->session查找，
 * 如果model中开始就有a键的话那么你存入request的a就取不到，所以建议把返回值存入model中<br/>
 * <b>ids</b>：页面传递过来的id数组；<br/>
 * <b>viewFlag</b>：1：=编辑，2：查看<br/>
 */
@Controller
@Scope("prototype")
public class BaseController extends AbstractController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    protected ModelMap model;

    protected String[] ids;

    protected Integer viewFlag;//表单的读写标志：1表示编辑：2表示查看

    protected Integer zgid;//只管id
    protected String viewQuery;//详情标志1

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);

       /* SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH24:mm:ss");
        timestampFormat.setLenient(false);*/
        /*dateFormat.setLenient(false);*/
        /*binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));*/

        /*binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(timestampFormat, true));*/

        binder.registerCustomEditor(String.class, new CustomStringEditor());
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
        /*binder.registerCustomEditor(Integer.TYPE, null, new CustomNativeEditor(Integer.class, null, true));*/
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
        /*binder.registerCustomEditor(Long.TYPE, null, new CustomNativeEditor(Long.class, null, true));*/
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));


    }
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
    protected ApplicationContext getAppContext(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
        return ctx;
    }

    /**
     * 使用了ModelAttribute：作用
     * 1)放置在方法的形参上：表示引用Model中的数据
     * 2)放置在方法上面：表示请求该类的每个Action前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面。
     * */
    @ModelAttribute
    protected void setReqAndRes(HttpServletRequest request, HttpServletResponse response,
                                ModelMap model,String[] ids,Integer viewFlag,Integer zgid,String viewQuery ){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.model=model;

        if(ids != null && ids.length==0){
            this.ids=null;
        }else{
            this.ids=ids;
        }
        this.viewFlag = viewFlag;
        this.zgid = zgid;
        this.viewQuery = viewQuery;
        this.model.addAttribute("viewFlag", viewFlag);
        this.model.addAttribute("zgid", zgid);
        this.model.addAttribute("viewQuery", viewQuery);
        RequestCache.setRequest(request);
        RequestCache.setResponse(response);
    }

    protected HttpServletRequest getRequest() {
        return request;
    }

    protected void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    protected HttpServletResponse getResponse() {
        return response;
    }

    protected void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    protected Map<String, String> getRegionParamSetting() {
    	// 先获取顶级节点编码
    	RegionSettingCfg cfgHelper = RegionSettingCfg.getInstance().load();
		String rootRegion = cfgHelper.getKeyValue("rootRegion");
		String nextLevelAppendLen = cfgHelper.getKeyValue("nextLevelAppendLen");
		Map<String, String> map = new HashMap<String, String>();
		map.put("rootRegion", rootRegion);
		map.put("nextLevelAppendLen",nextLevelAppendLen);
		return map;
	}

    /**
     * 登录的用户信息：如果还需要其他的数据，请保存在用户信息实体类中
     * @return
     */
     public SysUserinfo getPrincipal(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof SysUserinfo) {
            return (SysUserinfo) principal;
        }else{
            return null;
        }
    }

    //List 去重
    public List removeDuplicate(List<String> list) {
        HashSet h = new HashSet(list);
        List newList = new ArrayList();
        newList.addAll(h);
        return newList;
    }
//    /**
//     * 获取登录用户名称
//     * @return
//     */
/*    protected String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }*/

    /**
     * 在request中设置值；内部转发必须使用setAttr才能在跳转的方法中获取到值
     * @param attributeName
     * @param attributeValues
     */
    protected void setAttr(String attributeName,Object attributeValues){
        if(this.request!=null){
            this.request.setAttribute(attributeName, attributeValues);
        }
    }
    /**
     * 返回ajax请求的数据
     */
    protected ResultMsg responseAjaxData(Boolean success,Map<String, Object> map){
        if(map == null){
            map = new HashMap<String, Object>();
        }
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccess(success);
        List<ResultData> list = new ArrayList<ResultData>();
        if (map != null && !map.isEmpty()) {
            for(String key : map.keySet()){
                ResultData resultData = resultMsg.new ResultData();
                resultData.setAttribute(key);
                resultData.setAttrvaule(map.get(key));
                list.add(resultData);
            }
        }
        if (list != null && !list.isEmpty()) {
            resultMsg.setItems(list);
        }

        return resultMsg;
    }


    /**
     * 获取request中的值：内部转发必须使用getAttr才能获取到值
     * @param attributeName
     * @return 如果没有值则返回null
     */
    protected Object getAttr(String attributeName){
        if(this.request!=null){
            return this.request.getAttribute(attributeName);
        }
        return null;
    }

    /**
     * 获取request中的值：内部转发必须使用getParameter才能获取到值
     * @param attributeName
     * @return 如果没有值则返回null
     */
    protected String getParameter(String attributeName){
        if(this.request!=null){
            return this.request.getParameter(attributeName);
        }
        return null;
    }
    /**
     * easyui固定的返回格式 rows 和total
     * @param list
     * @return
     */
    protected Map<String, Object> responseList(List list){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", list);
        map.put("total", (list!=null&&!list.isEmpty())?list.size():0);
        return map;
    }
    /**
     * 项目当前路径
     * @return
     */
    protected String getCurWebAppPath() {
        return WebCache.getCurWebAppPath();
    }

    protected String forward(String url){
        if(!url.startsWith("/")){
            url="/"+url;
        }
        return "forward:"+url;
    }

    protected String redirect(String url){
        if(!url.startsWith("/")){
            url="/"+url;
        }
        return "redirect:"+url;
    }
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}

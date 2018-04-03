package com.bpm;

import com.idc.model.AssetAttachmentinfo;
import com.idc.model.IdcHisTicketResource;
import com.idc.model.SysUserinfo;
import com.idc.service.AssetAttachmentinfoService;
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
import utils.DevContext;
import utils.plugins.excel.Guid;
import utils.typeHelper.FTPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/7/5.
 */
@Controller
@RequestMapping("/assetAttachmentinfoController")
public class AssetAttachmentinfoController {
    private Logger logger = LoggerFactory.getLogger(AssetAttachmentinfoController.class);
    @Autowired
    private AssetAttachmentinfoService assetAttachmentinfoService;
    //下载附件信息:下载采用另外一种方式
    @RequestMapping("/downLoadFile/{id}")
    public void downLoadFile(HttpServletResponse response, @PathVariable("id") Long id) {
        AssetAttachmentinfo assetAttachmentinfo = assetAttachmentinfoService.getAttachmentById(id);
        String ftpAttachName = assetAttachmentinfo.getFtpAttachName();
        /**
         * 读取文件流情况
         */
        FTPUtils ftpUtils = null;
        if(DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)){
            logger.debug("需要通过ftp方式传递:[一般是跨主机的情况:这种情况少用.......]");
            ftpUtils = FTPUtils.getInstance();
            //这个是文件名称:
            String ftp_next_dir = Guid.getKhPreChar(ftpAttachName);
            if(ftp_next_dir != null && !"".equalsIgnoreCase(ftp_next_dir)){
                ftp_next_dir = ftp_next_dir.toLowerCase();
            }

            /*下载附件   lq   --------start------*/
            //InputStream in = ftpUtils.downLoadFile(ftp_next_dir,ftpAttachName); //LQ
            //downFile(in,response,assetAttachmentinfo.getAttachName());
            /*下载附件   lq   --------end------*/


            //wcg ：下面的ftpUtils.downloadFileNew是把附件保存到指定的路径比如 D: 比如 D:\ftp 绝对路径
            //Boolean outFile = ftpUtils.downloadFileNew(response,DevContext.FTP_PATH_NAME,assetAttachmentinfo.getFtpAttachName(),DevContext.FTP_DOWNLOAD_LOCAL_PATH,assetAttachmentinfo.getAttachName());  //WCG

            //wcg ：下面的ftpUtils.downloadFileNewXXX是把附件保存到固定的路径，相对路径
            Boolean outFile = ftpUtils.downloadFileNewXXX(response,DevContext.FTP_PATH_NAME,assetAttachmentinfo.getFtpAttachName(),ftp_next_dir,assetAttachmentinfo.getAttachName());  //WCG

            /**
             * 附件信息
             */
        }else{
            logger.debug("然后获取本地的附件流[.......]");
            String localFilePath = Guid.getFirstDkhPreChar(assetAttachmentinfo.getFtpInfo());
            if(localFilePath == null || "".equalsIgnoreCase(localFilePath)){
                /*文件缺失*/
                logger.error("附件ID:["+assetAttachmentinfo.getId()+"],对应的ftpInfo的数据为空或者格式不对，请检查ERROR!ERROR!ERROR!");
            }else{
                File file = new File(localFilePath);
                if(!file.exists()){
                    logger.error("---------------文件缺失---------------");
                    //返回文件缺失的消息
                    try {
                        PrintWriter out = response.getWriter();
                        response.setContentType("text/html; charset=utf-8");
                        out.print("<script>" +
                        "top.layer.msg('文件缺失,请联系管理员', {\n" +
                        "                icon: 2,\n" +
                        "                time: 1500\n" +
                        "          });</script>");
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    logger.debug("开始下载相应的文件:["+assetAttachmentinfo.getAttachName()+"]");
                    try{
                        FileInputStream in = new FileInputStream(file);
                        response.reset(); // 非常重要
                        downFile(in,response,assetAttachmentinfo.getAttachName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //删除附件removeAttach
    @RequestMapping("removeAttach/{id}")
    @ResponseBody
    public ResponseJSON removeAttach(HttpServletResponse response,  @PathVariable("id") Long id) throws IOException {
        AssetAttachmentinfo assetAttachmentinfo = assetAttachmentinfoService.getAttachmentById(id);
        String ftpAttachName = assetAttachmentinfo.getFtpAttachName();

        ResponseJSON result = new ResponseJSON();
        FTPUtils ftpUtils = null;

        logger.debug("需要通过ftp方式传递:[一般是跨主机的情况:这种情况少用.......]");
        ftpUtils = FTPUtils.getInstance();
        //删除附件
        //这个是文件名称:
        String ftp_next_dir = Guid.getKhPreChar(ftpAttachName);
        if(ftp_next_dir != null && !"".equalsIgnoreCase(ftp_next_dir)){
            ftp_next_dir = ftp_next_dir.toLowerCase();
        }

        //Boolean xxxx = ftpUtils.deleteFile(ftp_next_dir,ftpAttachName);//FTP删除附件信息 LQ

        Boolean delSuccess = ftpUtils.deleteFileNew(ftp_next_dir,ftpAttachName);//WCG
        if(delSuccess){
            logger.debug("FTP服务器的文件删除成功！");
            try {
                assetAttachmentinfoService.removeAttachById(id);
                result.setMsg("删除成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.setMsg("失败！！！");
            }
        }else{
            result.setMsg("删除成功");
        }

        return result;

        /*try {
            FTPUtils ftpUtils = null;
            if(DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)){
                logger.debug("需要通过ftp方式传递:[一般是跨主机的情况:这种情况少用.......]");
                ftpUtils = FTPUtils.getInstance();
                //删除附件
                //这个是文件名称:
                String ftp_next_dir = Guid.getKhPreChar(ftpAttachName);
                if(ftp_next_dir != null && !"".equalsIgnoreCase(ftp_next_dir)){
                    ftp_next_dir = ftp_next_dir.toLowerCase();
                }

                //Boolean xxxx = ftpUtils.deleteFile(ftp_next_dir,ftpAttachName);//FTP删除附件信息 LQ

                Boolean delSuccess = ftpUtils.deleteFileNew(ftp_next_dir,ftpAttachName);//WCG
                if(delSuccess){
                    logger.debug("FTP服务器的文件删除成功！");
                    assetAttachmentinfoService.removeAttachById(id);
                    result.setMsg("删除成功");
                }
            }else{
                logger.debug("删除本地文件信息");
                String localFilePath = Guid.getFirstDkhPreChar(assetAttachmentinfo.getFtpInfo());
                if(localFilePath == null || "".equalsIgnoreCase(localFilePath)){
                *//*文件缺失*//*
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/html; charset=utf-8");
                    out.print("<script>" +
                            "top.layer.msg('对应的ftpInfo的数据为空或者格式不对', {\n" +
                            "                icon: 2,\n" +
                            "                time: 1500\n" +
                            "          });</script>");
                    out.flush();
                    out.close();
                    logger.error("附件ID:["+assetAttachmentinfo.getId()+"],对应的ftpInfo的数据为空或者格式不对，请检查ERROR!ERROR!ERROR!");
                }else{
                    File file = new File(localFilePath);
                    if(!file.exists()){
                        logger.error("---------------文件缺失---------------");
                        PrintWriter out = response.getWriter();
                        response.setContentType("text/html; charset=utf-8");
                        out.print("<script>" +
                                "top.layer.msg('文件缺失,请联系管理员', {\n" +
                                "                icon: 2,\n" +
                                "                time: 1500\n" +
                                "          });</script>");
                        out.flush();
                        out.close();
                    }else{
                        logger.debug("开始删除本地文件:["+assetAttachmentinfo.getAttachName()+"]");
                        try{
                            file.delete();
                            assetAttachmentinfoService.removeAttachById(id);
                            result.setMsg("删除成功");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("删除失败：id={}", id, e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }*/
    }
    //添加资源附件的页面
    @RequestMapping("assetAttachmentinfoPage/{prodInstId}/{ticketInstId}/{attachmentType}")
    public String saveAccedfptAdjunct(@PathVariable("prodInstId")Long prodInstId,@PathVariable("ticketInstId")Long ticketInstId,@PathVariable("attachmentType")Long attachmentType,Model model) throws IOException {
        model.addAttribute("prodInstId",prodInstId);
        model.addAttribute("ticketInstId",ticketInstId);
        model.addAttribute("attachmentType",attachmentType);
        return "jbpm/attachment/resourceAttachment";
    }
    //保存资源附件
    @RequestMapping("saveAcceptAdjunct/{prodInstId}/{ticketInstId}/{attachmentType}")
    @ResponseBody
    public ResponseJSON saveAcceptAdjunct(@PathVariable("prodInstId")Long prodInstId,@PathVariable("ticketInstId")Long ticketInstId,@PathVariable("attachmentType")Long attachmentType,HttpServletRequest request) throws Exception {
        ResponseJSON result = new ResponseJSON();
        //Long ticketInstId = ServletRequestUtils.getLongParameter(request,"ticketInstId");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ticketInstId",ticketInstId);
        paramMap.put("prodInstId",prodInstId);
        paramMap.put("attachmentType",attachmentType);  //附件类型，
        paramMap.put("logicTablename", IdcHisTicketResource.tableName);
        try {
            //保存附件
            assetAttachmentinfoService.saveAcceptAdjunct(request,paramMap);
            result.setMsg("保存成功");
        } catch (Exception e) {
            //logger.error("保存失败：id={}", id, e);
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    /*//加载资源附件的表格
    @RequestMapping("loadAttachmetTable/{prodInstId}")
    @ResponseBody
    public List<AssetAttachmentinfo> loadAttachmetTable(HttpServletRequest request,@PathVariable("prodInstId")Long prodInstId) throws Exception {
        if(prodInstId == null){
            throw new Exception("prodInstId【工单id不能为空】");
        }
        List<AssetAttachmentinfo> list = assetAttachmentinfoService.getAttachmentinfoByTicketInstIdList(IdcHisTicketResource.tableName,prodInstId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("total",(list == null || list.isEmpty())?0:list.size());
        map.put("rows",list);
        return list;
    }*/

    //加载资源附件的表格
    @RequestMapping("loadAttachmetTable.do")
    @ResponseBody
    public PageBean loadAttachmetTable(HttpServletRequest request, PageBean result) throws Exception {
        result = result == null ? new PageBean() : result;

        SysUserinfo sysUserinfo = getPrincipal();
        Map<String,Object> queryParams=new HashMap<String,Object>();
        Map<String, Object> handleSecurity = new HashMap<>();
        String prodInstId = request.getParameter("prodInstId");
        String ticketInstId = request.getParameter("ticketInstId");
        String attachmentType = request.getParameter("attachmentType");
        queryParams.put("prodInstId",prodInstId);
        queryParams.put("ticketInstId",ticketInstId);
        queryParams.put("attachmentType",attachmentType);
        queryParams.put("loginUserID",sysUserinfo.getId());
        try {
            //下面没有处理完，后面处理
            //handleSecurity = assetAttachmentinfoService.call_AttachmentHandleSecurity(queryParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean handleResource= handleSecurity.get("handleResource") != null && String.valueOf(handleSecurity.get("handleResource")).equals("1");  //操作资源附件的权限
        Boolean handleIDCISP= handleSecurity.get("handleIDCISP") != null && String.valueOf(handleSecurity.get("handleIDCISP")).equals("1");   //操作IDC_ISP附件的权限
        Boolean handleOther= handleSecurity.get("handleOther") != null && String.valueOf(handleSecurity.get("handleOther")).equals("1");    //操作其他附件的权限
        Boolean handleContract= handleSecurity.get("handleContract") != null && String.valueOf(handleSecurity.get("handleContract")).equals("1");   //操作合同附件的权限
        Boolean handleMoney= handleSecurity.get("handleDelete") != null && String.valueOf(handleSecurity.get("handleDelete")).equals("1");    //查看合同金额的权限
        Boolean handleDelete= handleSecurity.get("handleMoney") != null && String.valueOf(handleSecurity.get("handleMoney")).equals("1");    //操作删除资源附件的权限

        if(prodInstId == null){
            throw new Exception("prodInstId【工单id不能为空】");
        }
        //List<AssetAttachmentinfo> list = assetAttachmentinfoService.getAttachmentinfoByTicketInstIdList(IdcHisTicketResource.tableName,Long.valueOf(prodInstId));

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("attachmentType",attachmentType);
        params.put("prodInstId",prodInstId);
        //params.put("ticketInstId",ticketInstId);
        //params.put("logicColumn",IdcHisTicketResource.tableName);
        assetAttachmentinfoService.queryAttachmentListPage(result,params);

        return result;
    }

    public void downFile(InputStream in,HttpServletResponse response,String fileName){
        if(fileName == null || "".equalsIgnoreCase(fileName)){
            logger.error("附件名称没有"+fileName);
        }
        if(in != null){

            OutputStream out = null;
            try {
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ));
                BufferedInputStream br = new BufferedInputStream(in);
                byte[] buf = new byte[1024];
                int len = 0;
                out = response.getOutputStream();
                while ((len = br.read(buf)) > 0)
                    out.write(buf, 0, len);
                //IOUtils.copy(in, response.getOutputStream());
                //response.flushBuffer();
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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

}

package utils;

/**
 * Created by DELL on 2017/9/1.
 * 环境上下文
 */
public class DevContext {
    //前面的url地址是传递过来的
    public static String ISP_WSDL_URL="idc/webService/isp_publish?wsdl";
    //默认是这个地址
    public static String REST_URL="http://localhost:8080/idc/web_rs/";
    //***redis的key
    public static String IDC_CONTRACT="IDC_CONTRACT";

    public static String IDC_CONTRACT_TICKET_KEY="IDC_CONTRACT_TICKET_KEY";
    //到期前一周、到期前两周、到期后未处理的合同
    public static String HAND_IDC_CONTRACT="HAND_IDC_CONTRACT";//已经处理过了的合同
    public static String NOT_HAND_IDC_CONTRACT="NOT_HAND_IDC_CONTRACT";//还没有处理的合同

    public static String PRE_3_EXPIRE_IDC_CONTRACT="PRE_3_EXPIRE_IDC_CONTRACT";//到期前三天:egnore
    public static String PRE_7_EXPIRE_IDC_CONTRACT="PRE_7_EXPIRE_IDC_CONTRACT";//到期前七天:egnore
    public static String PRE_14_EXPIRE_IDC_CONTRACT="PRE_14_EXPIRE_IDC_CONTRACT";//到期前十四天:egnore
    public static String SUFFIX_3_EXPIRE_IDC_CONTRACT="SUFFIX_3_EXPIRE_IDC_CONTRACT";//到期后三天:egnore
    public static String SUFFIX_7_EXPIRE_IDC_CONTRACT="SUFFIX_7_EXPIRE_IDC_CONTRACT";//到期后七天:egnore
    public static String SUFFIX_10UPER_EXPIRE_IDC_CONTRACT="SUFFIX_10UPER_EXPIRE_IDC_CONTRACT";//到期后十天、十天以上:egnore

    /** 即将过期的工单表  start **/
    public static String COMMING_3_DAY_EXPIRE_TICKET="COMMING_3_DAY_EXPIRE_TICKET";//即将过期的工单
    public static String COMMING_7_DAY_EXPIRE_TICKET="COMMING_7_DAY_EXPIRE_TICKET";//即将过期的工单
    public static Integer COMMING_EXPIRE_TICKET_COUNT=20;//过期条数默认值

    public static String EGNORE_REDIS_SYNCH="true";
    public static String PROJECT_QUERY_USE_REDIS="true";
    public static String PROJECT_MANAGER_USE_REDIS="true";


    public static String COMMING_14_DAY_EXPIRE_TICKET="COMMING_14_DAY_EXPIRE_TICKET";//即将过期的工单

    public static String COMMING_1_MONTH_EXPIRE_TICKET="COMMING_1_MONTH_EXPIRE_TICKET";//即将过期的工单
    public static String COMMING_2_MONTH_EXPIRE_TICKET="COMMING_2_MONTH_EXPIRE_TICKET";//即将过期的工单
    public static String COMMING_3_MONTH_EXPIRE_TICKET="COMMING_3_MONTH_EXPIRE_TICKET";//即将过期的工单
    /** 即将过期的工单表  end **/

    public static String IDC_HIS_PROC_CMENT="IDC_HIS_PROC_CMENT";

    public static String IDC_HIS_TICKET="IDC_HIS_TICKET";


    public static String IDC_RUN_TICKET_EXPIRE="IDC_HIS_TICKET_EXPIRE";//该工单过期了

    public static String IDC_HIS_TICKET_RESOURCE="IDC_HIS_TICKET_RESOURCE";

    public static String IDC_RE_CUSTOMER="IDC_RE_CUSTOMER";
    public static String IDC_RE_CUSTOMER_EXT="IDC_RE_CUSTOMER_EXT";
    public static String JBPM_SYS_USERINFO="JBPM_SYS_USERINFO";
    /*--------- ASSET_BASEINFO start ------------*/
    public static String ASSET_BASEINFO="ASSET_BASEINFO";

    public static String JBPM_SYS_USERINFO_FORHIS="JBPM_SYS_USERINFO_FORHIS";
    public static String IDC_NET_SERVICEINFO="IDC_NET_SERVICEINFO";

    public static String IDC_RE_PRODUCT="IDC_RE_PRODUCT";
    public static String IDC_RE_PRODUCT_EXT="IDC_RE_PRODUCT_EXT";
    public static String IDC_RE_RACK_MODEL="IDC_RE_RACK_MODEL";
    public static String IDC_RE_PORT_MODEL="IDC_RE_PORT_MODEL";
    public static String IDC_RE_IP_MODEL="IDC_RE_IP_MODEL";
    public static String IDC_RE_SERVER_MODEL="IDC_RE_SERVER_MODEL";
    public static String IDC_RE_NEWLY_MODEL="IDC_RE_NEWLY_MODEL";
    public static String SERVICE_APPLY_CATEGORY="SERVICE_APPLY_CATEGORY";

    public static String IDC_RUN_PROC_CMENT="IDC_RUN_PROC_CMENT";

    public static String IDC_RUN_TICKET="IDC_RUN_TICKET";
    public static String IDC_RUN_TICKET_TASK="IDC_RUN_TICKET_TASK";
    public static String IDC_HIS_TICKET_TASK="IDC_HIS_TICKET_TASK";

    public static String IDC_RUN_TICKET_EXT="IDC_RUN_TICKET_EXT";
    public static String IDC_HIS_TICKET_EXT="IDC_HIS_TICKET_EXT";
    public static String IDC_RUN_TICKET_1="IDC_RUN_TICKET_1";
    public static String IDC_RUN_TICKET_2="IDC_RUN_TICKET_2";
    public static String IDC_RUN_TICKET_PAGE="IDC_RUN_TICKET_PAGE";

    public static String IDC_RUN_TICKET_RESOURCE="IDC_RUN_TICKET_RESOURCE";

    /* 流程中单独处理业务的状态 */
    public static String OPEN_CONTRACT="OPEN_CONTRACT";//开通合同标志
    public static String ATTACHMENT="ASSET_ATTACHMENTINFO";//附件信息
    public static String TRUE="true";
    public static String FALSE="false";

    public static String FTP_CONTRACT_PATH="contract";
    public static String FTP_IDC_RE_PRODUCT_PATH="idc_re_product";
    public static String FTP_IDC_RUN_TICKET_PATH="idc_run_ticket";

    public static String FTP_DIR="/opt/ftp";
    public static String FTP_ADDR="192.168.0.240";
    public static int FTP_PORT=21;
    public static String FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE="false";

    public static String FTP_ONLINE_ADDR="";   //线上FTP地址  wcg
    public static int FTP_ONLINE_PORT=0;                     //线上FTP端口  wcg
    public static String FTP_ONLINE_USERNAME="";   //线上FTP账号  wcg
    public static String FTP_ONLINE_PASSWORD="";   //线上FTP密码  wcg
    public static String FTP_PATH_NAME="";   //线上FTP服务器文件目录  wcg
    public static String FTP_DOWNLOAD_PATH_NAME="";   // 下载的路径windows写 D:/    linux写 idc/attachment    wcg

    public static String EMAIL_ADRESS_FROM = "";    //邮件发件人地址  wcg
    public static String EMAIL_USER = "";    //邮件发件人账号  wcg
    public static String EMAIL_PASSWORD = "";    //邮件发件人密码  wcg
    public static String SERVER_MAIL_SMTP_HOST = "";    //邮件服务器地址  wcg
    public static String SERVER_MAIL_SMTP_AUTH = "";    //邮件服务器  wcg
    public static String SERVER_MAIL_SMTP_PORT = "";    //邮件服务器端口  wcg
    public static String SERVER_MAIL_SMTP_SSL_ENABLE = "";    //邮件服务器  wcg

    public static String REMOTE_FTP_ADDR="192.168.0.240";
    public static int REMOTE_FTP_PORT =21;
    public static Long EXIST =-1l;//已经存在
    public static Long ERROR =-2L;//失败
    public static Integer SUCCESS =1;//chenggong
    public static String REMOTE_FTP_USERNAME="idcOptFtp2017";
    public static String REMOTE_FTP_DIR="/opt/ftp";
    public static String REMOTE_FTP_PASSWORD="123456";
    public static String LOCAL_FTP_TEMPFIELPATH="D:\\ftp\\tmp";
    public static String LOCAL_ISP_TEMPFIELPATH="D:\\ftp\\tmp";

    public static String ISP_TEMPFIELPATH="D:\\ftp\\tmp";

    public static String LOCAL_FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE="true";



    /*用户名和密码都可以为空*/
    public static String FTP_USERNAME="idcOptFtp2017";
    public static String FTP_PASSWORD="123456";
    //上传ftp的文件目录:[合同有关的数据]
    public static String CONTRACT = "CONTRACT";
    public static String FTP_TEMPFIELPATH = "D:\\ftp\\tmp"; //这个是上传文件时，临时保存在本地的文件，上传FTP成功后就会把本地的删除
    public static String IDC_UPLOADFILE_USE_FTP = "true";
    public static String FTP_TEMPFIELPATH_ABSOLUTE = "true";
    public static String JBPM_DEFAULT_COMMON = "同意";
    /********* isp调用地址 *********/
    public static String FTP_ISP_IP = "183.221.251.229";
    public static String FTP_ISP_USERNAME = "idcftp";
    public static String FTP_ISP_PWD = "scydftp2014!!";
    public static String FTP_ISP_PORT = "21";
    public static String FTP_ISP_REMOTE_PATH = "28004/1";
    public static String FTP_ISP_TEMPLATE_FILE = "idcisp.ftl";
    public static String FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE="false";
    /* FTP_ISP_IP */



    public static void main(String[] args) {

    }
    public static String getTicketCategory(String processDefinitonKey,String category){
        if("idc_ticket_pre_accept".equalsIgnoreCase(processDefinitonKey) || "100".equals(category) ){
            return "PA";
        }else if("idc_ticket_open".equalsIgnoreCase(processDefinitonKey) || "200".equals(category) ){
            return "KT";
        }else if("idc_ticket_pause".equalsIgnoreCase(processDefinitonKey) || "400".equals(category) ){
            return "TJ";
        }else if("idc_ticket_recover".equalsIgnoreCase(processDefinitonKey) || "500".equals(category) ){
            return "FT";
        }else if("idc_ticket_pre_change".equalsIgnoreCase(processDefinitonKey) || "300".equals(category) ){
            return "BGYZ";//变更预占
        }else if("idc_ticket_business_change".equalsIgnoreCase(processDefinitonKey) || "900".equals(category) ){
            return "BA";//业务变更
        }else if("idc_ticket_open_change".equalsIgnoreCase(processDefinitonKey) || "700".equals(category) ){
            return "BK";//开通变更
        }else if("idc_ticket_halt".equalsIgnoreCase(processDefinitonKey) || "600".equals(category) ){
            return "XX";
        }else if("idc_ticket_temporary".equalsIgnoreCase(processDefinitonKey) || "800".equals(category) ){
            return "CS";
        }else {
            return null;
        }
    }
    public static String getLastSplitText(String serialNumber){
        if(serialNumber != null && !"".equals(serialNumber)){
            return serialNumber.substring(serialNumber.lastIndexOf(":")+1);
        }
        return null;
    }


}


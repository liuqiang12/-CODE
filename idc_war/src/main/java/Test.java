import com.idc.model.IdcJbpmTaskNode;
import com.idc.model.IdcReProduct;
import com.idc.utils.TASKNodeURL;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.data.springEvent.EmailEvent;
import utils.strategy.code.utils.JdbcHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/8/23.
 */
public class Test {
    private static Logger log=Logger.getLogger(Test.class);
    public static void mainasfd(String[] args) {
        Logger logger = Logger.getLogger(Test.class);
        logger.debug("开始");

        logger.debug("结束");

    }
    public static void callArryProc(){
        Connection conn = null;
        /**
         * 传递数组 批量保存数据的存储过程
         */
        JdbcHelper jdbcHelper = JdbcHelper.getInstance();
        conn = jdbcHelper.load("config/jdbc.properties").getConn();
        CallableStatement cstmt = null;
        try {
            StructDescriptor recDesc = StructDescriptor.createDescriptor("IDC_RE_PRODUCT_META", conn);
            ArrayList<STRUCT> pstruct = new ArrayList<STRUCT>();
            List<IdcReProduct> productList = new ArrayList<IdcReProduct>();
            for(int i = 0 ;i < 5 ;i++){
                IdcReProduct product = new IdcReProduct();
                product.setId(Long.valueOf(i));
                productList.add(product);
            }
            for(IdcReProduct u : productList){
                Object[] objs = new Object[1];
                objs[0] = u.getId();
                STRUCT struct = new STRUCT(recDesc, conn, objs);
                pstruct.add(struct);
            }

            ArrayDescriptor tabDesc =  ArrayDescriptor.createDescriptor("IDC_RE_PRODUCT_ARRAY", conn);
            ARRAY vArray = new ARRAY(tabDesc, conn, pstruct.toArray());
            cstmt = conn.prepareCall("call jbpm_process_back.proc_save_product_arry(?,?,?,?)");
            /**
             * 注册类型
             */
            cstmt.setArray(1, vArray);
            cstmt.registerOutParameter(2, Types.INTEGER);
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.registerOutParameter(4, Types.VARCHAR);

            cstmt.execute();
            /**
             * 获取返回值
             */
            System.out.println(cstmt.getString(2));
            System.out.println(cstmt.getString(3));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcHelper.close();
        }
    }


    public static void main(String[] args) {
		/*遍历*/
        List<IdcJbpmTaskNode> list = new ArrayList<IdcJbpmTaskNode>();
        log.debug("先删除表中的数据，然后保存基础数据");
        String delSql = "delete from idc_jbpm_tasknode";
        JdbcHelper jdbcHelper = JdbcHelper.getInstance();
        PreparedStatement pst = jdbcHelper.load(delSql,"config/jdbc.properties").getPst();
        try {
            pst.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcHelper.close();
        }
        for (TASKNodeURL e : TASKNodeURL.values()) {

            IdcJbpmTaskNode idcJbpmTaskNode = new IdcJbpmTaskNode();
            idcJbpmTaskNode.setProcessdefinitonkey(e.processKey());
            idcJbpmTaskNode.setTasknodeName(e.taskNode());
            idcJbpmTaskNode.setFormKey(e.formkey());
            idcJbpmTaskNode.setFormUrl(e.url());
            idcJbpmTaskNode.setJbpmStep(e.number());
            idcJbpmTaskNode.setIsUpdateHandler(e.isUpdateHandler()?1:0);
            idcJbpmTaskNode.setIsShowHandbar(e.isShowHandBar()?1:0);
            System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(idcJbpmTaskNode));
            jdbcHelper = JdbcHelper.getInstance();
            String parentFormKey = (e.parentFormKey()!=null&& !"".equals( e.parentFormKey()!=null))?"'"+e.parentFormKey()+"'":null;
            String sql = " insert into idc_jbpm_tasknode\n" +
                    "   (processdefinitonkey, tasknode_name, form_key, form_url, jbpm_step, RESOURCE_ALLOCATION_STATUS, TICKET_RESOURCE_HAND_STATUS,PARENT_FORM_KEY,TICKET_CATEGORY,PROD_CATEGORY,JBPM_NAME)\n" +
                    " values ('"+e.processKey()+"','"+e.taskNode()+"','"+e.formkey()+"','" +
                    e.url()+"',"+e.number()+",'"+(e.resourceAllocationStatus()?1:0)+"',"+(e.ticketResourceHandStatus()?1:0)+","+parentFormKey+","+e.ticketCategory()+","+e.prodCategory()+",'"+e.jbpmName()+"')";
            System.out.println(sql);
            PreparedStatement pst2 = jdbcHelper.load(sql,"config/jdbc.properties").getPst();
            try {
                pst2.execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                jdbcHelper.close();
            }

            list.add(idcJbpmTaskNode);
        }
		/*利用jdbc方式保存*/



        System.out.println(list.toArray().toString());
    }
    /**
     * 批量保存数组情况:[明天利用mybatis方式存储数据]]
     * @param args
     */
    public static void main1111(String[] args) {
        callArryProc();
    }
    public static void main11(String[] args) {

        log.info("this is info message");
        log.debug("this is debug message");
        log.warn("this is warn message");
        log.error("this is error message");

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/spring-applicationContext.xml");

        //HelloBean hello = (HelloBean) context.getBean("helloBean");
        //hello.setApplicationContext(context);
        EmailEvent event = new EmailEvent("hello","boylmx@163.com","this is a email text!");
        context.publishEvent(event);

    }

}

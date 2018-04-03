package com.idc.utils;

import com.idc.model.IdcRunProcCment;
import utils.ISPEventInvoke;
import utils.strategy.code.utils.JdbcHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/8/23.
 */
/*
* 动态代理情况
* */
public class IspDynamicProxy implements InvocationHandler {

    public Object originalObj;

    public Object bind(Object originalObj) {
        this.originalObj = originalObj;
        return Proxy.newProxyInstance(
                // 被代理类的ClassLoader
                originalObj.getClass().getClassLoader(),
                // 要被代理的接口,本方法返回对象会自动声称实现了这些接口
                originalObj.getClass().getInterfaces(),
                // 代理处理器对象
                this);
    }

    /**
     * 动态代理信息
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //目前只针对预getIsHaveRackChangeNum 和 getIsHaveIpChangeNum方法
        if(!"toString".equals(method.getName()) && (
                "getUpdateHouseFun".equals(method.getName())
                ||
                "getDeleteUserFun".equals(method.getName())
                ||
                "getAddUserFun".equals(method.getName())
                ||
                "getAddHouseFun".equals(method.getName())
                ||
                "getDeleteHouseFun".equals(method.getName())  )
                ){
            System.out.println("[[[[[[[[[[[com.idc.service.impl.BasicInfoEventListener[onApplicationEvent]方法调用]]]]]]]]]");
            IdcRunProcCment idcRunProcCment = getIdcRunProcCmentFromDynamicProxy(args);
            System.out.println("》》》》》》》》》》》》》》工单ID:"+idcRunProcCment.getTicketInstId()+":调用的方法是:"+method.getName());

            String result = String.valueOf(method.invoke(originalObj, args));
            result = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<basicInfo>\n" +
                    "    <provID>280</provID>\n" +
                    "    <newInfo>\n" +
                    "        <idcId>A2.B1.B2-20100001</idcId>\n" +
                    "        <idcName>中国（西部）云计算中心</idcName>\n" +
                    "        <idcAdd>四川成都双流区物联二路中国西部云计算中心</idcAdd>\n" +
                    "        <idcZip>610000</idcZip>\n" +
                    "        <corp>赵大春</corp>\n" +
                    "        <idcOfficer>\n" +
                    "            <name>郭智</name>\n" +
                    "            <idType>2</idType>\n" +
                    "            <id>210302198709063020</id>\n" +
                    "            <tel>0000-0000000</tel>\n" +
                    "            <mobile>13709016370</mobile>\n" +
                    "            <email>13709016370@139.com</email>\n" +
                    "        </idcOfficer>\n" +
                    "        <emergencyContact>\n" +
                    "            <name>黄晨炜</name>\n" +
                    "            <idType>2</idType>\n" +
                    "            <id>510105198602123010</id>\n" +
                    "            <tel>0000-0000000</tel>\n" +
                    "            <mobile>13730842162</mobile>\n" +
                    "            <email>13730842162@139.com</email>\n" +
                    "        </emergencyContact>\n" +
                    "        <houseCount>1</houseCount>\n" +
                    "        <houseInfo>\n" +
                    "            <houseId>5000065004</houseId>\n" +
                    "            <houseName>中国移动（四川成都）数据中心</houseName>\n" +
                    "            <houseType>2</houseType>\n" +
                    "            <houseProvince>510000</houseProvince>\n" +
                    "            <houseCity>510000</houseCity>\n" +
                    "            <houseCounty>510122</houseCounty>\n" +
                    "            <houseAdd>公兴镇物联西路</houseAdd>\n" +
                    "            <houseZip>610000</houseZip>\n" +
                    "            <houseOfficer>\n" +
                    "                <name>郭智</name>\n" +
                    "                <idType>2</idType>\n" +
                    "                <id>210302198709063020</id>\n" +
                    "                <tel>0000-0000000</tel>\n" +
                    "                <mobile>13709016370</mobile>\n" +
                    "                <email>13709016370@139.com</email>\n" +
                    "            </houseOfficer>\n" +
                    "            <gatewayInfo>\n" +
                    "                <id>5000001260</id>\n" +
                    "                <bandWidth>800000</bandWidth>\n" +
                    "                <linkType>3</linkType>\n" +
                    "                <accessUnit>中国移动通信集团四川分公司</accessUnit>\n" +
                    "                <gatewayIp>183.223.118.137</gatewayIp>\n" +
                    "            </gatewayInfo> \n" +
                    "            </gatewayInfo>\n" +
                    "            <ipSegInfo>\n" +
                    "                <id>5000001261</id>\n" +
                    "                <startIp>183.223.116.0</startIp>\n" +
                    "                <endIp>183.223.116.255</endIp>\n" +
                    "                <type>0</type>\n" +
                    "                <useTime>2017-06-25</useTime>\n" +
                    "                <sourceUnit>中国移动通信集团四川有限公司</sourceUnit>\n" +
                    "                <allocationUnit>中国移动通信集团四川有限公司</allocationUnit>\n" +
                    "            </ipSegInfo>\n" +
                    "            <frameInfo>\n" +
                    "                <id>5000002497</id>\n" +
                    "                <useType>2</useType>\n" +
                    "                <distribution>2</distribution>\n" +
                    "                <occupancy>2</occupancy>\n" +
                    "                <frameName>YD-SC-CD-XY-B04-101-A03</frameName>\n" +
                    "            </frameInfo>\n" +
                    "        </houseInfo>\n" +
                    "        <userInfo>\n" +
                    "            <idcId>5000000081</idcId>\n" +
                    "            <nature>1</nature>\n" +
                    "            <info>\n" +
                    "                <unitName>四川省铁路产业投资集团有限责任公司</unitName>\n" +
                    "                <unitNature>4</unitNature>\n" +
                    "                <idType>999</idType>\n" +
                    "                <idNumber>915100006841545284</idNumber>\n" +
                    "                <officer>\n" +
                    "                    <name>陈湘</name>\n" +
                    "                    <idType>2</idType>\n" +
                    "                    <id>511303198103010010</id>\n" +
                    "                    <tel>02812345678</tel>\n" +
                    "                    <mobile>13612345678</mobile>\n" +
                    "                    <email>2497120949@qq.com</email>\n" +
                    "                </officer>\n" +
                    "                <add>四川省成都市高新区九兴大道12号</add>\n" +
                    "                <zipCode>610000</zipCode>\n" +
                    "                <serviceInfo>\n" +
                    "                    <serviceId>5000000188</serviceId>\n" +
                    "                    <serviceContent>1</serviceContent>\n" +
                    "                    <regType>3</regType>\n" +
                    "                    <regId>2</regId>\n" +
                    "                    <setMode>2</setMode>\n" +
                    "                    <business>2</business>\n" +
                    "                    <domain>\n" +
                    "                        <id>5000000188</id>\n" +
                    "                        <name>2</name>\n" +
                    "                    </domain>\n" +
                    "                    <housesHoldInfo>\n" +
                    "                        <hhId>5000001259</hhId>\n" +
                    "                        <houseId>5000065004</houseId>\n" +
                    "                        <bandWidth>1000</bandWidth>\n" +
                    "                        <ipSeg>\n" +
                    "                            <ipId>4432</ipId>\n" +
                    "                            <startIp>223.87.178.132</startIp>\n" +
                    "                            <endIp>223.87.178.132</endIp>\n" +
                    "                        </ipSeg>\n" +
                    "                        <distributeTime>2017-11-14 11:18:16</distributeTime>\n" +
                    "                    </housesHoldInfo>\n" +
                    "                </serviceInfo>\n" +
                    "                <registerTime>2017-08-02 17:42:14</registerTime>\n" +
                    "            </info>\n" +
                    "        </userInfo>\n" +
                    "    </newInfo>\n" +
                    "    <timeStamp>2017-11-14 11:18:16</timeStamp>\n" +
                    "</basicInfo>\n";
            Map<String,String> logMap = new HashMap<String,String>();
            Map<String,String> logISPMap = new HashMap<String,String>();
            //idcRunProcCment [id = null,ticketInstId = 1188,prodInstId = 713,procInstId = 985005,executionid = 985005,taskId = 985029,actName = 评价,author = 超级管理员,authorId = 1,comment = asdfasdfa,status = 1,rev = null,createTime = null ]

            if("getUpdateHouseFun".equals(method.getName()) ){
                System.out.println("######################修改数据中心######################");
            }else if("getDeleteUserFun".equals(method.getName()) ){
                System.out.println("######################删除用户######################");
            }else if("getAddUserFun".equals(method.getName()) ){
                System.out.println("######################新增用户######################");
            }else if("getAddHouseFun".equals(method.getName()) ){
                System.out.println("######################新增数据中心######################");
            }else if("getDeleteHouseFun".equals(method.getName()) ){
                System.out.println("######################删除数据中心######################");
            }
            //同时先生成对应的原始文件作为备份使用
            System.out.println("######################同时生成原始数据文件---备份使用start######################");
            //创建的状态是
            logMap.put("HAND_METHOD",method.getName());
            logMap.put("XML_CONTENT","<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<basicInfo>\n" +
                    "    <provID>280</provID>\n" +
                    "    <newInfo>\n" +
                    "        <idcId>A2.B1.B2-20100001</idcId>\n" +
                    "        <idcName>中国（西部）云计算中心</idcName>\n" +
                    "        <idcAdd>四川成都双流区物联二路中国西部云计算中心</idcAdd>\n" +
                    "        <idcZip>610000</idcZip>\n" +
                    "        <corp>赵大春</corp>\n" +
                    "        <idcOfficer>\n" +
                    "            <name>郭智</name>\n" +
                    "            <idType>2</idType>\n" +
                    "            <id>210302198709063020</id>\n" +
                    "            <tel>0000-0000000</tel>\n" +
                    "            <mobile>13709016370</mobile>\n" +
                    "            <email>13709016370@139.com</email>\n" +
                    "        </idcOfficer>\n" +
                    "        <emergencyContact>\n" +
                    "            <name>黄晨炜</name>\n" +
                    "            <idType>2</idType>\n" +
                    "            <id>510105198602123010</id>\n" +
                    "            <tel>0000-0000000</tel>\n" +
                    "            <mobile>13730842162</mobile>\n" +
                    "            <email>13730842162@139.com</email>\n" +
                    "        </emergencyContact>\n" +
                    "        <houseCount>1</houseCount>\n" +
                    "        <houseInfo>\n" +
                    "            <houseId>5000065004</houseId>\n" +
                    "            <houseName>中国移动（四川成都）数据中心</houseName>\n" +
                    "            <houseType>2</houseType>\n" +
                    "            <houseProvince>510000</houseProvince>\n" +
                    "            <houseCity>510000</houseCity>\n" +
                    "            <houseCounty>510122</houseCounty>\n" +
                    "            <houseAdd>公兴镇物联西路</houseAdd>\n" +
                    "            <houseZip>610000</houseZip>\n" +
                    "            <houseOfficer>\n" +
                    "                <name>郭智</name>\n" +
                    "                <idType>2</idType>\n" +
                    "                <id>210302198709063020</id>\n" +
                    "                <tel>0000-0000000</tel>\n" +
                    "                <mobile>13709016370</mobile>\n" +
                    "                <email>13709016370@139.com</email>\n" +
                    "            </houseOfficer>\n" +
                    "            <gatewayInfo>\n" +
                    "                <id>5000001260</id>\n" +
                    "                <bandWidth>800000</bandWidth>\n" +
                    "                <linkType>3</linkType>\n" +
                    "                <accessUnit>中国移动通信集团四川分公司</accessUnit>\n" +
                    "                <gatewayIp>183.223.118.137</gatewayIp>\n" +
                    "            </gatewayInfo>\n" +
                    "            <gatewayInfo>\n" +
                    "                <id>5000001261</id>\n" +
                    "                <bandWidth>800000</bandWidth>\n" +
                    "                <linkType>3</linkType>\n" +
                    "                <accessUnit>中国移动通信集团四川分公司</accessUnit>\n" +
                    "                <gatewayIp>183.223.118.141</gatewayIp>\n" +
                    "            </gatewayInfo>\n" +
                    "            <gatewayInfo>\n" +
                    "                <id>5000001262</id>\n" +
                    "                <bandWidth>800000</bandWidth>\n" +
                    "                <linkType>3</linkType>\n" +
                    "                <accessUnit>中国移动通信集团四川分公司</accessUnit>\n" +
                    "                <gatewayIp>221.183.50.57</gatewayIp>\n" +
                    "            </gatewayInfo>\n" +
                    "            <ipSegInfo>\n" +
                    "                <id>5000001261</id>\n" +
                    "                <startIp>183.223.116.0</startIp>\n" +
                    "                <endIp>183.223.116.255</endIp>\n" +
                    "                <type>0</type>\n" +
                    "                <useTime>2017-06-25</useTime>\n" +
                    "                <sourceUnit>中国移动通信集团四川有限公司</sourceUnit>\n" +
                    "                <allocationUnit>中国移动通信集团四川有限公司</allocationUnit>\n" +
                    "            </ipSegInfo>\n" +
                    "            <ipSegInfo>\n" +
                    "                <id>5000001262</id>\n" +
                    "                <startIp>183.223.117.0</startIp>\n" +
                    "                <endIp>183.223.117.0</endIp>\n" +
                    "                <type>0</type>\n" +
                    "                <useTime>2017-06-25</useTime>\n" +
                    "                <sourceUnit>中国移动通信集团四川有限公司</sourceUnit>\n" +
                    "                <allocationUnit>中国移动通信集团四川有限公司</allocationUnit>\n" +
                    "            </ipSegInfo>\n" +
                    "            <ipSegInfo>\n" +
                    "                <id>5000001263</id>\n" +
                    "                <startIp>183.223.118.0</startIp>\n" +
                    "                <endIp>183.223.118.0</endIp>\n" +
                    "                <type>0</type>\n" +
                    "                <useTime>2017-06-25</useTime>\n" +
                    "                <sourceUnit>中国移动通信集团四川有限公司</sourceUnit>\n" +
                    "                <allocationUnit>中国移动通信集团四川有限公司</allocationUnit>\n" +
                    "            </ipSegInfo>\n" +
                    "            <ipSegInfo>\n" +
                    "                <id>5000001264</id>\n" +
                    "                <startIp>183.223.119.0</startIp>\n" +
                    "                <endIp>183.223.119.0</endIp>\n" +
                    "                <type>0</type>\n" +
                    "                <useTime>2017-06-25</useTime>\n" +
                    "                <sourceUnit>中国移动通信集团四川有限公司</sourceUnit>\n" +
                    "                <allocationUnit>中国移动通信集团四川有限公司</allocationUnit>\n" +
                    "            </ipSegInfo>\n" +
                    "            <frameInfo>\n" +
                    "                <id>5000002497</id>\n" +
                    "                <useType>2</useType>\n" +
                    "                <distribution>2</distribution>\n" +
                    "                <occupancy>2</occupancy>\n" +
                    "                <frameName>YD-SC-CD-XY-B04-101-A03</frameName>\n" +
                    "            </frameInfo>\n" +
                    "            <frameInfo>\n" +
                    "                <id>5000002499</id>\n" +
                    "                <useType>1</useType>\n" +
                    "                <distribution>2</distribution>\n" +
                    "                <occupancy>2</occupancy>\n" +
                    "                <frameName>YD-SC-CD-XY-B04-101-A05</frameName>\n" +
                    "            </frameInfo>\n" +
                    "        </houseInfo>\n" +
                    "        <userInfo>\n" +
                    "            <idcId>5000000081</idcId>\n" +
                    "            <nature>1</nature>\n" +
                    "            <info>\n" +
                    "                <unitName>四川省铁路产业投资集团有限责任公司</unitName>\n" +
                    "                <unitNature>4</unitNature>\n" +
                    "                <idType>999</idType>\n" +
                    "                <idNumber>915100006841545284</idNumber>\n" +
                    "                <officer>\n" +
                    "                    <name>陈湘</name>\n" +
                    "                    <idType>2</idType>\n" +
                    "                    <id>511303198103010010</id>\n" +
                    "                    <tel>02812345678</tel>\n" +
                    "                    <mobile>13612345678</mobile>\n" +
                    "                    <email>2497120949@qq.com</email>\n" +
                    "                </officer>\n" +
                    "                <add>四川省成都市高新区九兴大道12号</add>\n" +
                    "                <zipCode>610000</zipCode>\n" +
                    "                <serviceInfo>\n" +
                    "                    <serviceId>5000000188</serviceId>\n" +
                    "                    <serviceContent>1</serviceContent>\n" +
                    "                    <regType>3</regType>\n" +
                    "                    <regId>2</regId>\n" +
                    "                    <setMode>2</setMode>\n" +
                    "                    <business>2</business>\n" +
                    "                    <domain>\n" +
                    "                        <id>5000000188</id>\n" +
                    "                        <name>2</name>\n" +
                    "                    </domain>\n" +
                    "                    <housesHoldInfo>\n" +
                    "                        <hhId>5000001259</hhId>\n" +
                    "                        <houseId>5000065004</houseId>\n" +
                    "                        <bandWidth>1000</bandWidth>\n" +
                    "                        <ipSeg>\n" +
                    "                            <ipId>4432</ipId>\n" +
                    "                            <startIp>223.87.178.132</startIp>\n" +
                    "                            <endIp>223.87.178.132</endIp>\n" +
                    "                        </ipSeg>\n" +
                    "                        <distributeTime>2017-11-14 11:18:16</distributeTime>\n" +
                    "                    </housesHoldInfo>\n" +
                    "                </serviceInfo>\n" +
                    "                <registerTime>2017-08-02 17:42:14</registerTime>\n" +
                    "            </info>\n" +
                    "        </userInfo>\n" +
                    "    </newInfo>\n" +
                    "    <timeStamp>2017-11-14 11:18:16</timeStamp>\n" +
                    "</basicInfo>\n");
            logMap.put("TICKET_INST_ID",String.valueOf(idcRunProcCment.getTicketInstId()) );
            try {
                String filePath = ISPEventInvoke.getInstance().xmlToFile(String.valueOf(result),"xml/repository",method.getName());
                logMap.put("RELATIVE_PATH",filePath);
                logMap.put("CREATE_XML_STATUS","成功创建了该文件"+method.getName());
            } catch (Exception e) {
                logMap.put("CREATE_XML_STATUS","原始文件创建失败"+method.getName());
                e.printStackTrace();
            } finally {
                createIspEventLog(logMap);
            }
            System.out.println("######################同时生成原始数据文件---备份使用end######################");
            System.out.println("######################call ISP interface:生成压缩文件  start######################");
            //调用
            logISPMap.putAll(logMap);
            try {
                /*压缩文件*/
                String encodeXmlContent = ISPEventInvoke.getInstance().stubEventForISPXMLUtils(String.valueOf(result),method.getName());
                logISPMap.put("ENCODE_XML_CONTENT",encodeXmlContent);
                logMap.put("CREATE_XML_STATUS","成功创建压缩文件创建"+method.getName());
            } catch (Exception e) {
                logISPMap.put("CREATE_XML_STATUS","压缩文件创建失败"+method.getName());
                e.printStackTrace();
            } finally {
                //logISPMap
                createIspEventLog(logMap);
            }
            System.out.println("######################call ISP interface:生成压缩文件  end######################");
            return result;
        }else{
            Object result = method.invoke(originalObj, args);
            return result;
        }
    }
    /*创建有关数据ISP_EVENT_LOG:如果代码要写得漂亮请用发射*/
    public void createIspEventLog(Map<String,String> map){
        JdbcHelper jdbcHelper = JdbcHelper.getInstance();
        String RELATIVE_PATH = ( map.get("RELATIVE_PATH")==null||"".equals(map.get("RELATIVE_PATH")) )?"xml/repository":map.get("RELATIVE_PATH");
        String ENCODE_XML_CONTENT = ( map.get("ENCODE_XML_CONTENT")==null||"".equals(map.get("ENCODE_XML_CONTENT")) )?"无":map.get("ENCODE_XML_CONTENT");
        String ENCODE_XML_STATUS = ( map.get("ENCODE_XML_STATUS")==null||"".equals(map.get("ENCODE_XML_STATUS")) )?"无":map.get("ENCODE_XML_STATUS");
        String HAND_METHOD = ( map.get("HAND_METHOD")==null||"".equals(map.get("HAND_METHOD")) )?"无":map.get("HAND_METHOD");
        String XML_CONTENT = ( map.get("XML_CONTENT")==null||"".equals(map.get("XML_CONTENT")) )?"无":map.get("XML_CONTENT");
        String CREATE_XML_STATUS = ( map.get("CREATE_XML_STATUS")==null||"".equals(map.get("CREATE_XML_STATUS")) )?"无":map.get("CREATE_XML_STATUS");
        String LINKED_XML = ( map.get("LINKED_XML")==null||"".equals(map.get("LINKED_XML")) )?"无":map.get("LINKED_XML");
        String LOCAL_STATUS = ( map.get("LOCAL_STATUS")==null||"".equals(map.get("LOCAL_STATUS")) )?"LOCAL":map.get("LOCAL_STATUS");
        String RETURN_XML = ( map.get("RETURN_XML")==null||"".equals(map.get("RETURN_XML")) )?"无":map.get("RETURN_XML");
        String TICKET_INST_ID = ( map.get("TICKET_INST_ID")==null||"".equals(map.get("TICKET_INST_ID")) )?"":map.get("TICKET_INST_ID");

        String sql = "insert into " +
                "ISP_EVENT_LOG(" +
                "RELATIVE_PATH," +
                "HAND_METHOD," +
                "XML_CONTENT," +
                "CREATE_XML_STATUS," +
                "ENCODE_XML_CONTENT," +
                "ENCODE_XML_STATUS," +
                "LINKED_XML," +
                "\"LOCAL_STATUS\"," +
                "RETURN_XML," +
                "TICKET_INST_ID" +
                ") values ('"+RELATIVE_PATH+"','"+HAND_METHOD+"',?,'" +
                CREATE_XML_STATUS+"',?,'"+ENCODE_XML_STATUS+"',?,'"+LOCAL_STATUS+"',?,?)";
        PreparedStatement pst = jdbcHelper.load(sql,"config/jdbc.properties").getPst();
        try {

            InputStream inputStream1 = new ByteArrayInputStream(XML_CONTENT.getBytes("UTF-8"));
            InputStream inputStream2 = new ByteArrayInputStream(ENCODE_XML_CONTENT.getBytes("UTF-8"));
            InputStream inputStream3 = new ByteArrayInputStream(LINKED_XML.getBytes("UTF-8"));
            InputStream inputStream4 = new ByteArrayInputStream(RETURN_XML.getBytes("UTF-8"));

            pst.setBinaryStream(1,inputStream1);
            pst.setBinaryStream(2,inputStream2);
            pst.setBinaryStream(3,inputStream3);
            pst.setBinaryStream(4,inputStream4);
            pst.setInt(5,Integer.valueOf(TICKET_INST_ID));
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcHelper.close();
        }
    }
    public IdcRunProcCment getIdcRunProcCmentFromDynamicProxy(Object[] params){
        for(int i = 0 ;i < params.length ; i++){
            if( params[i] instanceof IdcRunProcCment ){
                return (IdcRunProcCment)params[i];
            }
        }
        return null;
    }


}

/*
package com.core.jaxb.utils;
import com.idc.model.*;
import utils.typeHelper.FileHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by DELL on 2017/8/15.
 * 1:mcb
 *//*

public class JaxbDataMain {
    */
/**
     *
     * @throws Exception
     *//*

    public static String preFTPHandler() {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setProvID("280");
        //------------newInfo--------------start;
        InterfaceInfo newInfo = new InterfaceInfo();
        newInfo.setIdcId("A2.B1.B2-20100001");
        newInfo.setIdcName("中国（西部）云计算中心");
        newInfo.setIdcAdd("四川成都双流区物联二路中国西部云计算中心");
        newInfo.setIdcZip("610000");
        newInfo.setCorp("赵大春");
        newInfo.setHouseCount(1l);
        //idcOfficer
        IdcOfficer idcOfficer = new IdcOfficer();
        idcOfficer.setName("郭智");
        idcOfficer.setIdType(2);
        idcOfficer.setId("210302198709063020");
        idcOfficer.setTel("0000-0000000");
        idcOfficer.setMobile("13709016370");
        idcOfficer.setEmail("13709016370@139.co");
        newInfo.setIdcOfficer(idcOfficer);
        //emergencyContact
        IdcOfficer emergencyContact = new IdcOfficer();
        emergencyContact.setName("黄晨炜");
        emergencyContact.setIdType(2);
        emergencyContact.setId("210302198709063020");
        emergencyContact.setTel("0000-0000000");
        emergencyContact.setMobile("13709016370");
        emergencyContact.setEmail("13709016370@139.co");
        newInfo.setEmergencyContact(emergencyContact);
        basicInfo.setNewInfo(newInfo);
        //houseInfo
        List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
        for(int i = 0 ; i < 3; i++){
            HouseInfo houseInfo = new HouseInfo();
            houseInfo.setHouseId(28004l);
            houseInfo.setHouseName("中国（西部）云计算中心");
            houseInfo.setHouseType(2);
            houseInfo.setHouseProvince(510000l);
            houseInfo.setHouseCity(510000l);
            houseInfo.setHouseAdd("双流公兴镇物联二路");
            houseInfo.setHouseZip("610000");
            //------ houseOfficer --------
            IdcOfficer houseOfficer = new IdcOfficer();
            houseOfficer.setName("郭智");
            houseOfficer.setIdType(2);
            houseOfficer.setId("210302198709063020");
            houseOfficer.setTel("0000-0000000");
            houseOfficer.setMobile("13709016370");
            houseOfficer.setEmail("13709016370@139.co");
            //houseInfo.setIdcOfficer(houseOfficer);
            //gatewayInfos
            List<GatewayInfo> gatewayInfos = new ArrayList<GatewayInfo>();
            for(int g = 0 ; g < 4; g++){
                GatewayInfo gatewayInfo = new GatewayInfo();
                gatewayInfo.setId(5000000001l);
                gatewayInfo.setBandWidth(800000l);
                gatewayInfo.setLinkType(3);
                gatewayInfo.setAccessUnit("中国移动通信集团四川分公司");
                gatewayInfo.setGatewayIp("183.223.118.137");
                gatewayInfos.add(gatewayInfo);
            }
            //houseInfo.setGatewayInfos(gatewayInfos);
            //ipSegInfos
            List<IpSegInfo> ipSegInfos = new ArrayList<IpSegInfo>();
            for(int s = 0 ; s < 5; s++){
                IpSegInfo ipSegInfo = new IpSegInfo();
                ipSegInfo.setId(5000000001l);
                ipSegInfo.setStartIp("183.223.116.0");
                ipSegInfo.setEndIp("183.223.116.255");
                ipSegInfo.setType(0);
                ipSegInfo.setUseTime("2017-06-25");
                ipSegInfo.setSourceUnit("中国移动通信集团四川有限公司");
                ipSegInfo.setAllocationUnit("中国移动通信集团四川有限公司");
                ipSegInfos.add(ipSegInfo);
            }
            //houseInfo.setIpSegInfos(ipSegInfos);
            //frameInfos
            List<FrameInfo> frameInfos = new ArrayList<FrameInfo>();
            for(int f = 0  ; f < 3 ; f++){
                FrameInfo frameInfo = new FrameInfo();
                frameInfo.setId(5000000001l);
                frameInfo.setUseType("2");
                frameInfo.setDistribution("1");
                frameInfo.setOccupancy("1");
                frameInfo.setFrameName("YD_SC_CD_XY_B04_101_A01");
                frameInfos.add(frameInfo);
            }
            //houseInfo.setFrameInfos(frameInfos);
            houseInfos.add(houseInfo);
        }
        //newInfo.setHouseInfos(houseInfos);
        //userinfo
        List<UserInfo> userInfos= new ArrayList<UserInfo>();
        for(int i = 0 ; i < 3; i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setId(5000000001l);
            userInfo.setNature(1);
            Info info = new Info();
            info.setUnitName("中国移动通信集团四川有限公司");
            info.setUnitNature(4);
            info.setIdType(1);
            info.setIdNumber("91510000735866286J");
            info.setAdd("四川省成都市高新区高鹏大道10号");
            info.setZipCode("610000");
            info.setRegisterTime("2017-06-25");
            //officer
            IdcOfficer officer = new IdcOfficer();
            officer.setName("郭智");
            officer.setIdType(2);
            officer.setId("210302198709063020");
            officer.setTel("0000-0000000");
            officer.setMobile("13709016370");
            officer.setEmail("13709016370@139.co");
            info.setIdcOfficer(officer);
            //serviceInfo
            List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>();
            for(int s = 0 ;s < 4; s++){
                ServiceInfo serviceInfo = new ServiceInfo();
                serviceInfo.setServiceId(5000000001l);
                serviceInfo.setServiceContent(24);
                serviceInfo.setRegType(2);
                serviceInfo.setRegId("N/A");
                serviceInfo.setSetMode("2");
                serviceInfo.setBusiness("1");
                //domain
                Domain domain = new Domain();
                domain.setId(5000000001l);
                domain.setName("www");
                serviceInfo.setDomain(domain);
                //housesHoldInfo
                List<HousesHoldInfo> housesHoldInfos = new ArrayList<HousesHoldInfo>();
                HousesHoldInfo housesHoldInfo = new HousesHoldInfo();
                housesHoldInfo.setHhId(5000000001l);
                housesHoldInfo.setHouseId(28004L);
                housesHoldInfo.setDistributeTime("2017-06-25");
                housesHoldInfo.setFrameInfoId("测试数据");
                housesHoldInfo.setBandWidth(100l);
                List<IpTran> ipTrans = new ArrayList<IpTran>();
                for(int p = 0 ; p < 4; p++){
                    IpTran ipTran = new IpTran();
                    if(p%2 == 0){
                        IpAttr internetIp = new IpAttr();
                        internetIp.setStartIp("183.223.116.35");
                        internetIp.setEndIp("183.223.116.35");
                        ipTran.setInternetIp(internetIp);
                    }else{
                        if(p == 1){
                            IpAttr internetIp = new IpAttr();
                            internetIp.setStartIp("183.223.116.35");
                            internetIp.setEndIp("183.223.116.35");
                            ipTran.setInternetIp(internetIp);
                            IpAttr netIp = new IpAttr();
                            netIp.setStartIp("33333333");
                            netIp.setEndIp("333333333");
                            ipTran.setNetIp(netIp);
                        }
                    }
                    ipTrans.add(ipTran);
                }
                housesHoldInfo.setIpTrans(ipTrans);
                //问题
                housesHoldInfos.add(housesHoldInfo);
                serviceInfo.setHousesHoldInfos(housesHoldInfos);
                serviceInfos.add(serviceInfo);
            }
            info.setServiceInfos(serviceInfos);
            userInfo.setInfo(info);
            userInfos.add(userInfo);
        }
        */
/**
         * IDC_BASIC_INFO
         *//*

        newInfo.setUserInfos(userInfos);
        //------------newInfo--------------  end;
        basicInfo.setTimeStamp("2017-07-05 10:37:47");
       // String xml = JaxbUtil.convertToXml(basicInfo);
        String result = XMLParser.marshal(basicInfo, BasicInfo.class,"UTF-8");
        //System.out.println(xml);
        return result;
    }

    public static void main(String[] args) throws Exception{
       String text =  preFTPHandler();

//       FileHelper.getInstance().textToFile("D:/360驱动大师目录/下载保存目录/sssss.xml",text);
    }
}
*/

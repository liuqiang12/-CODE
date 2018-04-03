package com.idc.scheduler;

import com.idc.model.IdcContract;
import com.idc.model.IdcRunTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import system.data.supper.service.JavaSerializer;
import system.data.supper.service.RedisManager;
import utils.DevContext;
import utils.typeHelper.DateHelper;
import utils.typeHelper.GsonUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/***************************************************************************************
 * *定时说明：
 * *【秒（0~59）、分钟（0~59）、 小时（0~23）、天（月）（0~31，但是你需要考虑你月的天数）、
 * *月（0~11） 、天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
 * *、7.年份（1970－2099）】
 * *如0 0 0 * * ? 每天上午10点，下午2点，4点
 * *0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
 * *0 0 12 ? * WED 表示每个星期三中午12点
 * *"0 0 12 * * ?" 每天中午12点触发
 * *"0 15 10 ? * *" 每天上午10:15触发
 * *"0 15 10 * * ?" 每天上午10:15触发
 * *"0 15 10 * * ? *" 每天上午10:15触发
 * *"0 15 10 * * ? 2005" 2005年的每天上/午10:15触发
 * *"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
 * *"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
 * *"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
 * *"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
 * *"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
 * *"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
 * *"0 15 10 15 * ?" 每月15日上午10:15触发
 * *"0 15 10 L * ?" 每月最后一日的上午10:15触发
 * *"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
 * *"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
 * *"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
 * ***************************************************************************************/
@Component
public class JbpmJob {
    @Autowired
	private RedisManager redisManager;
    @Autowired
    private JavaSerializer serializableMethod;//序列化方式

    public JbpmJob(){
        System.out.println("------------------JbpmJob创建成功------------------");
    }

    /**
     *  每隔1秒执行一次
     */
    @Scheduled(cron = "59 59 23 * * ? ")//每天晚上凌晨计算一次 合同过期了的  工单
    public void run(){
        /*统计出来的数据都是取决于缓存*/
        /*获取正在运行的工单*/
        /**/
        try {
            List<String> list = redisManager.getListJedisCache(DevContext.NOT_HAND_IDC_CONTRACT);
            Iterator<String> it = list.iterator();
            while(it.hasNext()){
                String contractStr = it.next();
                IdcContract idcContract = GsonUtil.json2Object(contractStr,IdcContract.class);
                Date contractEnd = idcContract.getContractEnd();
                Date current = new Date();
                Date currentSuffix3Month = DateHelper.addDataWithMonth(current,3);
                Date currentSuffix2Month = DateHelper.addDataWithMonth(current,2);
                Date currentSuffix1Month = DateHelper.addDataWithMonth(current,1);

                Date currentSuffix14Day = DateHelper.addDataWithDay(current,14);
                Date currentSuffix7Day = DateHelper.addDataWithDay(current,7);
                Date currentSuffix3Day = DateHelper.addDataWithDay(current,3);
                //判断该事件是否大于了当前时间
                if(contractEnd != null && contractEnd.getTime() < current.getTime()){
                    System.out.println("================已经过期了.......但没有处理的工单================");
                    comingExpireTicketIntoRedis(idcContract,DevContext.IDC_RUN_TICKET_EXPIRE);
                }else if(contractEnd != null && contractEnd.getTime() >= current.getTime()
                        && currentSuffix3Month.getTime() > current.getTime()
                        ){
                    System.out.println("================三个月之内即将过期的缓存下来================");
                    comingExpireTicketIntoRedis(idcContract,DevContext.COMMING_3_MONTH_EXPIRE_TICKET);
                }else if(contractEnd != null && contractEnd.getTime() >= current.getTime()
                        && currentSuffix2Month.getTime() > current.getTime()
                        ){
                    //两个月之内即将过期的缓存下来
                    System.out.println("================两个月之内即将过期的缓存下来================");
                    comingExpireTicketIntoRedis(idcContract,DevContext.COMMING_2_MONTH_EXPIRE_TICKET);
                }else if(contractEnd != null && contractEnd.getTime() >= current.getTime()
                        && currentSuffix1Month.getTime() > current.getTime()
                        ){
                    //一个月之内即将过期的缓存下来
                    System.out.println("================一个月之内即将过期的缓存下来================");
                    comingExpireTicketIntoRedis(idcContract,DevContext.COMMING_1_MONTH_EXPIRE_TICKET);
                }else if(contractEnd != null && contractEnd.getTime() >= current.getTime()
                        && currentSuffix14Day.getTime() > current.getTime()
                        ){
                    //两周之内即将过期的缓存下来
                    System.out.println("================两周之内即将过期的缓存下来================");
                    comingExpireTicketIntoRedis(idcContract,DevContext.COMMING_14_DAY_EXPIRE_TICKET);
                }else if(contractEnd != null && contractEnd.getTime() >= current.getTime()
                        && currentSuffix7Day.getTime() > current.getTime()
                        ){
                    //一周之内即将过期的缓存下来
                    System.out.println("================一周之内即将过期的缓存下来================");
                    comingExpireTicketIntoRedis(idcContract,DevContext.COMMING_7_DAY_EXPIRE_TICKET);
                }else if(contractEnd != null && contractEnd.getTime() >= current.getTime()
                        && currentSuffix3Day.getTime() > current.getTime()
                        ){
                    //三天之内即将过期的缓存下来
                    System.out.println("================三天之内即将过期的缓存下来================");
                    comingExpireTicketIntoRedis(idcContract,DevContext.COMMING_3_DAY_EXPIRE_TICKET);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void comingExpireTicketIntoRedis(IdcContract idcContract,String redisKey) throws Exception{
        //查询该合同对应的工单:[]
        System.out.println("合同名称["+idcContract.getContractname()+"],合同编号["+idcContract.getContractno()+"],对应的工单过期了。需要保存到过期表中");
        //保存过期工单到redis中:[正在运行的工单]
        //根据合同ID所对应的工单id得到对应工单
        if(idcContract.getTicketInstId() != null){
            byte[] runTicketBinary = redisManager.getBinaryJedisCache(DevContext.IDC_RUN_TICKET,String.valueOf(idcContract.getTicketInstId()));
            if(runTicketBinary != null){
                IdcRunTicket idcRunTicket = (IdcRunTicket)serializableMethod.unserialize(runTicketBinary);
                if(idcRunTicket != null){
                    //将该工单保存到过期工单中
                    System.out.println("过期工单号:["+idcRunTicket.getId()+"]");
                    redisManager.putIntoListJedisCache(redisKey,idcRunTicket);
                }
            }

        }
    }


}

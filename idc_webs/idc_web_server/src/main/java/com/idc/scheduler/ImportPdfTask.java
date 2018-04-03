package com.idc.scheduler;


import com.idc.service.ImportPdfPowerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.DateFormate;
import utils.DateUtil;

@Component
public class ImportPdfTask {
    @Autowired
   ImportPdfPowerDataService importPdfPowerDataService;

   // @Scheduled(fixedRate = 1000 * 10)
   @Scheduled(cron = "00 00 01 * * ? ")//每天晚上凌晨计算一次 合同过期了的  工单
    public void importPdfFileTask() throws Exception {
        System.out.println("开始导入");
        String date= DateFormate.dateToStr(DateFormate.addDate(new java.util.Date(), -1), "yyyyMMdd");
       importPdfPowerDataService.importDataAndSave(date);
    }


}

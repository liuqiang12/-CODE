package com.commer.app.task;


import com.comer.util.DateFormate;
import com.comer.util.DateUtil;
import com.comer.util.PropertyConfig;
import com.commer.app.service.ImportPdfPowerDataService;
import com.commer.app.service.ImportPowerRoomDataService;
import com.commer.app.service.impl.ImportPdfPowerDataServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class ImportPdfTask {
    Logger logger = Logger.getLogger(ImportPdfTask.class);
    @Autowired
    ImportPdfPowerDataService importPdfPowerDataService;

    @Autowired
    ImportPowerRoomDataService importPowerRoomDataService;

  //  @Scheduled(fixedRate = 1000 * 30)
   @Scheduled(cron = "00 00 02 * * ? ")//每天凌晨1点导入PDF架电量
    public void importPdfFileTask() throws Exception {
        logger.info("PDF开始导入");
        // Date startdate= DateFormate.strToDate("2017-12-22","yyyy-MM-dd");
        Date startdate = DateFormate.strToDate(DateFormate.dateToStr(DateFormate.addDate(new java.util.Date(), -1), "yyyy-MM-dd"), "yyyy-MM-dd");
        for (int i = 0; i < 1; i++) {
            String startDate = DateFormate.dateToStr(DateFormate.addDate(startdate, i), "yyyy-MM-dd");
            String enddate = DateFormate.dateToStr(DateFormate.addDate(startdate, i + 1), "yyyy-MM-dd");
            //  String date1=DateUtil.getDate();
            logger.info("PDF:"+startDate + "#" + enddate);
            importPdfPowerDataService.importDataAndSave(startDate + "#" + enddate);
            logger.info(startDate + "#" + enddate + "PDF完成导入!");
        }

    }

  //  @Scheduled(fixedRate = 1000 * 30)
    @Scheduled(cron = "00 00 01 * * ? ")//每天凌晨1点导入机房电量
    public void importRoomFileTask() throws Exception {
        logger.info("ROOM开始导入");
        Date startdate = DateFormate.strToDate(DateFormate.dateToStr(DateFormate.addDate(new java.util.Date(), -1), "yyyy-MM-dd"), "yyyy-MM-dd");
        for (int i = 0; i < 1; i++) {
            String startDate = DateFormate.dateToStr(DateFormate.addDate(startdate, i), "yyyy-MM-dd");
            String enddate = DateFormate.dateToStr(DateFormate.addDate(startdate, i + 1), "yyyy-MM-dd");
            String date1 = DateUtil.getDate();
            //  importPdfPowerDataService.importDataAndSave(startDate+"#"+enddate);
            logger.info("PDF:"+startDate + "#" + enddate);
            importPowerRoomDataService.importDataAndSave(startDate + "#" + enddate);
            logger.info(startDate + "#" + enddate + "room完成导入!");
        }
/*
        String date= DateFormate.dateToStr(DateFormate.addDate(new java.util.Date(), -1), "yyyy-MM-dd");
        String date1=DateUtil.getDate();

        importPowerRoomDataService.importDataAndSave(date1);*/

    }

}

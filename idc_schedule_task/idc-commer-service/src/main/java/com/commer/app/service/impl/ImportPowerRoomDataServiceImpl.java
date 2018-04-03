package com.commer.app.service.impl;


import au.com.bytecode.opencsv.CSVReader;

import com.comer.util.*;
import com.commer.app.mapper.IdcPdfDayPowerInfoMapper;
import com.commer.app.mapper.IdcPowerRoomInfoMapper;
import com.commer.app.mode.IdcPdfDayPowerInfo;
import com.commer.app.mode.IdcPowerRoomInfo;
import com.commer.app.service.ImportPowerRoomDataService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


@Service("importPowerRoomService")
public class ImportPowerRoomDataServiceImpl implements ImportPowerRoomDataService {
    Logger logger = Logger.getLogger(ImportPowerRoomDataServiceImpl.class);
    @Autowired
    private IdcPowerRoomInfoMapper mapper;

    @Autowired
    private IdcPdfDayPowerInfoMapper idcPdfDayPowerInfoMapper;
    private String filePre = "MACHROOM-1440-";

    @Override
    public CSVReader loadRoomData(String date) throws Exception {
        CSVReader csvReader_ams;
        String filepath = GloabVar.appDir;

        //String curDate = DateFormate.currDateToStr();
       /* String begData = DateFormate.dateToStr(DateFormate.addDate(new Date(), -1), "yyyyMMdd") + "0000";
        String enddate = DateFormate.dateToStr(new Date(), "yyyyMMdd") + "0000";*/
        String se[]=date.split("#");

        //String curDate = DateFormate.currDateToStr();
        String begData = se[0].replace("-","") + "0000";
        String enddate = se[1].replace("-","") + "0000";
     //   String zxfilepath = filepath + "/amsfile/";
        String zxfilepath = PropertyConfig.getInstance().getPropertyValue("LOCAL_FILE_PATH");
       // String amsfilepath = filepath + "/pdffile/";
        String filename = this.filePre + begData + "-" + enddate + "-" + ".*" + "\\.csv";
        //通过文件过滤器过滤需要的文件
        FilenameFilterExt fileNameFilte = new FilenameFilterExt(filename);
        File zxfile = new File(zxfilepath);
        File[] fileList = null;
        if (!zxfile.exists()) {
            zxfile.exists();

        }
        fileList = zxfile.listFiles(fileNameFilte);
        if (fileList == null || fileList.length == 0)
            throw new Exception("文件未生成");
        csvReader_ams = new CSVReader(new InputStreamReader(new FileInputStream(fileList[0]), "GBK"), ' ');
        if (csvReader_ams == null) {
            throw new Exception("文件加载失败");
        }
//        File amsfile = new File(amsfilepath);
//        fileList = amsfile.listFiles(fileNameFilte);

        return csvReader_ams;
    }

    @Override
    public void downRoomFile(String date) throws Exception {
        String ftpServerIp = "";
        int ftpServerPort = 21;
        String ftpServerUser = "";
        String ftpServerPassword = "";
        ftpServerIp = PropertyConfig.getInstance().getPropertyValue("FTP_ADDR");
        String FTP_PORT = PropertyConfig.getInstance().getPropertyValue("FTP_PORT");
        ftpServerPort = Integer.parseInt(FTP_PORT);
        ftpServerUser = PropertyConfig.getInstance().getPropertyValue("FTP_USERNAME");
        ftpServerPassword = PropertyConfig.getInstance().getPropertyValue("FTP_PASSWORD");
        String FTP_DIR = PropertyConfig.getInstance().getPropertyValue("FTP_DIR");
        String remoteRootDir = "/";//PropertiesHandle.getResuouceInfo("amsdonghuan.ftp.server.remotepath");

        FTPClientUpfile amsftp = new FTPClientUpfile(ftpServerIp, ftpServerPort, ftpServerUser,
                ftpServerPassword);
        ;
        if (!amsftp.isConnected()) {
            logger.error("初始化FTP客户端失败，任务执行失败!");
            throw new Exception("初始化FTP客户端失败");
        }
        String amsfilepath =PropertyConfig.getInstance().getPropertyValue("LOCAL_FILE_PATH");// GloabVar.appDir + "//amsfile//";
        String se[]=date.split("#");
       /* String begData = DateFormate.dateToStr(DateFormate.addDate(new Date(), -1), "yyyyMMdd") + "0000";
        String enddate = DateFormate.dateToStr(new Date(), "yyyyMMdd") + "0000";*/
        String begData = se[0].replace("-","") + "0000";
        String enddate = se[1].replace("-","") + "0000";
        String filename = this.filePre + begData + "-" + enddate + "-" + ".*" + "\\.csv";
        try {
            amsftp.downFiles(remoteRootDir, filename, amsfilepath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件上传失败！");
        } finally {
            amsftp.disconnect();
        }

    }

    @Transactional
    @Override
    public void roomPwoerSave(List<IdcPowerRoomInfo> list) {
        try {
            mapper.insertList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<IdcPowerRoomInfo> calculationPowerDifference(Map map) {
        Map<String, Double> amountMap = new HashMap();
        //  String date= DateFormate.dateToStr(DateFormate.addDate(new java.util.Date(), -1), "yyyy-MM-dd");
        //  map.put("idcCreateTime", date);
        List<IdcPowerRoomInfo> list = mapper.queryListByMap(map);
        for (int i = 0; i < list.size(); i++) {
            IdcPowerRoomInfo room = list.get(i);
            amountMap.put(room.getIdcPowerRoomCode(), room.getIdcAmout());
            amountMap.put("air_" + room.getIdcPowerRoomCode(), room.getIdcAirAdjustAmout());
            amountMap.put("other_" + room.getIdcPowerRoomCode(), room.getIdcOtherAmout());
            amountMap.put("dev_" + room.getIdcPowerRoomCode(), room.getIdcDeviceAmout());

        }

        List<IdcPowerRoomInfo> currList = (List<IdcPowerRoomInfo>) map.get("roomList");
        for (IdcPowerRoomInfo info : currList) {
            if (amountMap.get(info.getIdcPowerRoomCode()) != null) {
                double diff1 = Double.parseDouble(amountMap.get(info.getIdcPowerRoomCode()).toString());
                double diff = info.getIdcAmout() - diff1;
                double air_diff1 = Double.parseDouble(amountMap.get("air_" + info.getIdcPowerRoomCode()).toString());
                double air_diff = info.getIdcAirAdjustAmout() - air_diff1;
                double other_diff1 = Double.parseDouble(amountMap.get("other_" + info.getIdcPowerRoomCode()).toString());
                double other_diff = info.getIdcOtherAmout() - other_diff1;
                double dev_diff1 = Double.parseDouble(amountMap.get("dev_" + info.getIdcPowerRoomCode()).toString());
                double dev_diff = info.getIdcDeviceAmout() - dev_diff1;
                info.setIdcBeforeDiff(diff);
                info.setIdcOtherAmout(other_diff);
                info.setIdcAirAdjustBeforeDiff(air_diff);
                info.setIdcDeviceBeforeDiff(dev_diff);
            } else {
                info.setIdcBeforeDiff(0.0);
                info.setIdcOtherAmout(0.0);
                info.setIdcAirAdjustBeforeDiff(0.0);
                info.setIdcDeviceBeforeDiff(0.0);

            }
        }
        return currList;
    }

    @Override
    public List<IdcPowerRoomInfo> generateRoomPowerDataList(CSVReader reader, String date) throws IOException {
        List<IdcPowerRoomInfo> list = new ArrayList<IdcPowerRoomInfo>();
        String se[] = date.split("#");
        String[] csvRow = null;
        int i = 1;
        String zgid;
        reader.readNext();
        IdcPowerRoomInfo idcPowerRoomInfo;
        while ((csvRow = reader.readNext()) != null) {
            if (csvRow.length <= 1) {
                continue;
            }
            i++;
            idcPowerRoomInfo = new IdcPowerRoomInfo();
            try {
                String csr0[] = csvRow[0].split("\\s+");//下标2为机房名称
                String csr1[] = csvRow[1].split("\\s+");//下标1为机房编码2区域
                String csr2[] = csvRow[2].split("\\s+");//下标2为机房名称
                String csr3[] = csvRow[3].split("\\s+");//下标1总电表读数2状态位3主设备电量4主设备状态位5空调电量6空调电量状态7其他电量
                if (Double.parseDouble(csr3[3].trim()) == 0) {
                    continue;
                }
                idcPowerRoomInfo.setIdcPowerRoomCode(csr1[1].trim());
                idcPowerRoomInfo.setIdcPowerRoomId(UuidUtil.get32UUID().toString());
                idcPowerRoomInfo.setIdcPowerRoomName(csr0[2]);
                // idcPowerRoomInfo.setIdcCreateTime(new Date());
                idcPowerRoomInfo.setIdcCreateTime(DateUtil.parseDate(se[1]));

                idcPowerRoomInfo.setIdcAirAdjustAmout((Double.parseDouble(csr3[5].trim())));
                idcPowerRoomInfo.setIdcDeviceAmout(Double.parseDouble(csr3[3].trim()));
                if (Double.parseDouble(csr3[1].trim()) == 0) {
                    double amout = (Double.parseDouble(csr3[5].trim())) + Double.parseDouble(csr3[3].trim()) + (Double.parseDouble(csr3[7].trim()));
                    idcPowerRoomInfo.setIdcAmout(amout);
                }else
                {
                    idcPowerRoomInfo.setIdcAmout(Double.parseDouble(csr3[1].trim()));
                }
                ;
             // idcPowerRoomInfo.setIdcStartTime(DateUtil.parseDateTime(DateFormate.strToDate(csr0[3].trim()+" 00:00:00","yyyy-MM-dd HH:mm:ss")));
              //idcPowerRoomInfo.setIdcEndTime(DateUtil.parseDateTime(DateFormate.strToDate(csr0[3].trim()+" 23:59:59","yyyy-MM-dd HH:mm:ss")));
                idcPowerRoomInfo.setIdcOtherAmout((Double.parseDouble(csr3[7].trim())));

                idcPowerRoomInfo.setIdcStatus("0");

                list.add(idcPowerRoomInfo);
            } catch (Exception e) {
                logger.error(e.getMessage() + "第" + i + "行数据异常." + e.getMessage());
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importDataAndSave(String date) throws Exception {
        Map<String, Object> map = new HashMap();
        String se[] = date.split("#");
        map.put("idcCreateTime", se[1]);
        List<IdcPowerRoomInfo> roomlist = mapper.queryListByMap(map);
        if (roomlist != null && roomlist.size() == 0) {
            Map<String, Object> map1 = new HashMap();
            downRoomFile(date);
            CSVReader cSVReader = loadRoomData(date);
            List<IdcPowerRoomInfo> list = generateRoomPowerDataList(cSVReader, date);

            map1.put("roomList", list);
            map1.put("idcCreateTime", se[0]);
            List<IdcPowerRoomInfo> currList = calculationPowerDifference(map1);
            if (currList != null && currList.size() > 0) {
                mapper.insertList(currList);
                logger.info(date + "导入动环机房数据成功!");
            }
        }

    }

    private class FilenameFilterExt implements FilenameFilter {
        private Pattern pattern;

        public FilenameFilterExt(String regex) {
            pattern = Pattern.compile(regex);
        }

        @Override
        public boolean accept(File dir, String name) {

            return pattern.matcher(name).matches();
        }

    }

}

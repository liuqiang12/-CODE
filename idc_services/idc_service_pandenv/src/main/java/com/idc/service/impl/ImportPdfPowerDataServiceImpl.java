package com.idc.service.impl;


import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Strings;
import com.idc.mapper.IdcPdfDayPowerInfoMapper;
import com.idc.model.IdcPdfDayPowerInfo;

import com.idc.service.ImportPdfPowerDataService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utils.*;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


@Service("importPdfPowerDataService")
public class ImportPdfPowerDataServiceImpl implements ImportPdfPowerDataService {
    Logger logger = Logger.getLogger(ImportPdfPowerDataServiceImpl.class);
    @Autowired
    private IdcPdfDayPowerInfoMapper mapper;
    private String filePre = "PDF-1440-";

    @Override
    public CSVReader loadPdfData() throws Exception {
        CSVReader csvReader_ams;
        String filepath = GloabVar.appDir;

        //String curDate = DateFormate.currDateToStr();
        String begData = DateFormate.dateToStr(DateFormate.addDate(new java.util.Date(), -1), "yyyyMMdd") + "0000";
        String enddate = DateFormate.dateToStr(new java.util.Date(), "yyyyMMdd") + "0000";
        String zxfilepath = filepath + "/amsfile/";
        String amsfilepath = filepath + "/pdffile/";
        String filename = this.filePre + begData + "-" + enddate + "-" + ".*" + "\\.csv";
        //通过文件过滤器过滤需要的文件
        FilenameFilterExt fileNameFilte = new FilenameFilterExt(filename);
        File zxfile = new File(zxfilepath);
        File[] fileList = zxfile.listFiles(fileNameFilte);
        if (fileList==null || fileList.length == 0)
            throw new Exception("文件未生成");
        csvReader_ams = new CSVReader(new InputStreamReader(new FileInputStream(fileList[0]), "UTF-8"), ',');
        if (csvReader_ams == null)
            throw new Exception("文件加载失败");
        File amsfile = new File(amsfilepath);
        fileList = amsfile.listFiles(fileNameFilte);

        return csvReader_ams;
    }

    @Override
    public void downPdfFile() throws Exception {
        String ftpServerIp = "";
        int ftpServerPort = 21;
        String ftpServerUser = "";
        String ftpServerPassword = "";
        String remoteRootDir = "/pdffile";//PropertiesHandle.getResuouceInfo("amsdonghuan.ftp.server.remotepath");
        ftpServerIp = PropertiesHandle.getResuouceInfo("amsdonghuan.ftp.server.ip");
        if (ftpServerIp == null
                || ftpServerIp.trim().equals("")
                || !IPUtil.isIPFormat(ftpServerIp/**/
                .trim())) {
            logger.error("FTP IP地址没有配置或者配置不合法，請配置amsdonghuan.ftp.server.ip!");
            throw new Exception("FTP IP地址没有配置或者配置不合法，請配置amsdonghuan.ftp.server.ip!");
        }
        ftpServerIp = ftpServerIp.trim();
        String str = PropertiesHandle.getResuouceInfo("amsdonghuan.ftp.server.port");
        if (str != null && !str.trim().equals("")) {
            try {
                ftpServerPort = Integer.parseInt(str.trim());
            } catch (Exception e) {
                logger.error("FTP PORT配置不合法，請配置amsdonghuan.ftp.server.port!");
                throw new Exception("FTP PORT配置不合法，請配置amsdonghuan.ftp.server.port!");
            }
        }
        str = PropertiesHandle.getResuouceInfo("amsdonghuan.ftp.server.user");
        if (str != null) {
            ftpServerUser = str.trim();
        }
        str = PropertiesHandle.getResuouceInfo("amsdonghuan.ftp.server.password");
        if (str != null) {
            ftpServerPassword = str.trim();
        }
        str = PropertiesHandle.getResuouceInfo("amsdonghuan.ftp.server.remotepath");
        if (str != null && !str.trim().equals("")) {
            remoteRootDir = str.trim();
        }

        FTPClientUpfile amsftp = new FTPClientUpfile(ftpServerIp, ftpServerPort, ftpServerUser,
                ftpServerPassword);
        ;
        if (!amsftp.isConnected()) {
            logger.error("初始化FTP客户端失败，任务执行失败!");
            throw new Exception("初始化FTP客户端失败");
        }
        String amsfilepath = GloabVar.appDir + "//amsfile//";
        String begData = DateFormate.dateToStr(DateFormate.addDate(new java.util.Date(), -1), "yyyyMMdd") + "0000";
        String enddate = DateFormate.dateToStr(new java.util.Date(), "yyyyMMdd") + "0000";
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
    public void pdfPwoerSave(List<IdcPdfDayPowerInfo> list) {
        try {
            mapper.insertList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<IdcPdfDayPowerInfo> calculationPowerDifference(Map map) {
        Map<String, Double> amountMap = new HashedMap();
        List<IdcPdfDayPowerInfo> list = mapper.queryListByMap(map);
        for (IdcPdfDayPowerInfo pdf : list) {
            amountMap.put(pdf.getIdcOnlyCode(), pdf.getIdcAmout());
        }

        List<IdcPdfDayPowerInfo> currList = (List<IdcPdfDayPowerInfo>) map.get("pdfList");
        for (IdcPdfDayPowerInfo info : currList) {
            if(amountMap.get(info.getIdcOnlyCode())!=null) {
                double diff1 =Double.parseDouble(amountMap.get(info.getIdcOnlyCode()).toString()) ;
                double diff = info.getIdcAmout() - diff1;
                info.setIdcBeforeDiff(diff);
            }else
            {
                info.setIdcBeforeDiff(0.00);
            }
        }
        return currList;
    }

    @Override
    public List<IdcPdfDayPowerInfo> generatePdfPowerDataList(CSVReader reader) throws IOException {
        List<IdcPdfDayPowerInfo> list = new ArrayList<IdcPdfDayPowerInfo>();
        String[] csvRow = null;
        int i = 1;
        String zgid;
        reader.readNext();
        IdcPdfDayPowerInfo idcPdfDayPowerInfo;
        while ((csvRow = reader.readNext()) != null) {
            if (csvRow.length <= 1)
                continue;
            i++;
            idcPdfDayPowerInfo = new IdcPdfDayPowerInfo();
            try {


                idcPdfDayPowerInfo.setIdcPdfDayPowerInfoId(UuidUtil.get32UUID().toString());
                idcPdfDayPowerInfo.setIdcAmout(Double.parseDouble(csvRow[7].trim()));
               // idcPdfDayPowerInfo.setIdcCreateTime(DateUtil.getNow());
                idcPdfDayPowerInfo.setIdcRoomCode(csvRow[2].trim());
                idcPdfDayPowerInfo.setIdcPdfCode(csvRow[3].trim());
                idcPdfDayPowerInfo.setIdcMcbCode(Objects.toString(csvRow[4],""));
                idcPdfDayPowerInfo.setIdcStatus(csvRow[8].trim());
                idcPdfDayPowerInfo.setIdcOnlyCode(csvRow[3].trim() + "_" + Objects.toString(csvRow[4],""));
                list.add(idcPdfDayPowerInfo);
            } catch (Exception e) {
                logger.error(e.getMessage() + "第" + i + "行数据异常." + e.getMessage());
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    @Transactional
    public void importDataAndSave(String date) throws Exception {
        Map<String, Object> map = new HashedMap();
        downPdfFile();
        CSVReader cSVReader = loadPdfData();
        List<IdcPdfDayPowerInfo> list = generatePdfPowerDataList(cSVReader);
        map.put("pdfList", list);
        map.put("idcCreateTime", date);
        List<IdcPdfDayPowerInfo> currList = calculationPowerDifference(map);
        mapper.insertList(list);

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

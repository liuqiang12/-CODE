package com.commer.app.service.impl;


import au.com.bytecode.opencsv.CSVReader;
import com.comer.util.*;
import com.commer.app.mapper.IdcPdfDayPowerInfoMapper;
import com.commer.app.mode.IdcPdfDayPowerInfo;
import com.commer.app.service.ImportPdfPowerDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public CSVReader loadPdfData(String filePath) throws Exception {
        CSVReader csvReader_ams;
        String filepath = GloabVar.appDir;
        String se[]=filePath.split("#");

        //String curDate = DateFormate.currDateToStr();
        String begData = se[0].replace("-","") + "0000";
        String enddate = se[1].replace("-","") + "0000";
       // String zxfilepath = filepath + "/amsfile/";
        String zxfilepath = PropertyConfig.getInstance().getPropertyValue("LOCAL_FILE_PATH");
        String filename = this.filePre + begData + "-" + enddate + "-" + ".*" + "\\.csv";
        //通过文件过滤器过滤需要的文件
        FilenameFilterExt fileNameFilte = new FilenameFilterExt(filename);
        File zxfile = new File(zxfilepath);
        if(!zxfile.exists())
        {
            zxfile.mkdirs();
        }
        File[] fileList = zxfile.listFiles(fileNameFilte);
        if (fileList==null || fileList.length == 0) {
            throw new Exception("文件未生成");
        }
        csvReader_ams = new CSVReader(new InputStreamReader(new FileInputStream(fileList[0]), "GBK"), ' ');
        if (csvReader_ams == null) {
            throw new Exception("文件加载失败");
        }
   //     File amsfile = new File(amsfilepath);
     //   fileList = amsfile.listFiles(fileNameFilte);

        return csvReader_ams;
    }

    @Override
    public void downPdfFile(String filepath) throws Exception {
        String ftpServerIp = "";
        int ftpServerPort = 21;
        String ftpServerUser = "";
        String ftpServerPassword = "";
        ftpServerIp =  PropertyConfig.getInstance().getPropertyValue("FTP_ADDR");
        String FTP_PORT =  PropertyConfig.getInstance().getPropertyValue("FTP_PORT");
        ftpServerPort=Integer.parseInt(FTP_PORT);
        ftpServerUser =  PropertyConfig.getInstance().getPropertyValue("FTP_USERNAME");
        ftpServerPassword =  PropertyConfig.getInstance().getPropertyValue("FTP_PASSWORD");
        String FTP_DIR= PropertyConfig.getInstance().getPropertyValue("FTP_DIR");
        String remoteRootDir = "/";//PropertiesHandle.getResuouceInfo("amsdonghuan.ftp.server.remotepath");
        String se[]= filepath.split("#");

        FTPClientUpfile amsftp = new FTPClientUpfile(ftpServerIp, ftpServerPort, ftpServerUser,
                ftpServerPassword);
        ;
        if (!amsftp.isConnected()) {
            logger.error("初始化FTP客户端失败，任务执行失败!");
            throw new Exception("初始化FTP客户端失败");
        }
        String amsfilepath = PropertyConfig.getInstance().getPropertyValue("LOCAL_FILE_PATH");//GloabVar.appDir + "//amsfile//";
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
    public void pdfPwoerSave(List<IdcPdfDayPowerInfo> list) {
        try {
            mapper.insertList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<IdcPdfDayPowerInfo> calculationPowerDifference(Map map) {
        Map<String, Double> amountMap = new HashMap();
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
    public List<IdcPdfDayPowerInfo> generatePdfPowerDataList(CSVReader reader,String filePath) throws IOException {
        List<IdcPdfDayPowerInfo> list = new ArrayList<IdcPdfDayPowerInfo>();
        Set<String> set=new HashSet<>();
        String[] csvRow = null;
        int i = 1;
        String zgid;
        String se[]=filePath.split("#");
        reader.readNext();
     //   IdcPdfDayPowerInfo idcPdfDayPowerInfo;
        while ((csvRow = reader.readNext()) != null) {
            if (csvRow.length <= 1) {
                continue;
            }
            i++;
            IdcPdfDayPowerInfo idcPdfDayPowerInfo = new IdcPdfDayPowerInfo();
            try {
                String[] row1=csvRow[0].split("\\s+");
                String[] row2=csvRow[1].split("\\s+");
                String[] row3=csvRow[2].split("\\s+");

                if(Double.parseDouble(row3[1].trim())==0)
                    continue;
                String key=row1[1] + "_" +Objects.toString(Integer.parseInt(row1[2]))+"_"+se[1];
                if(!set.contains(key)) {


                    String bnum = row1[0].split("_")[1];
                    String rnum = row1[0].split("_")[2];
                    String prefix = "";
                    if (bnum.equals("B04")) {
                        prefix = PropertyConfig.getInstance().getPropertyValue("B04");
                    } else if (bnum.equals("B01")) {
                        prefix = PropertyConfig.getInstance().getPropertyValue("B01");
                    } else if (bnum.equals("A01")) {
                        prefix = PropertyConfig.getInstance().getPropertyValue("A01");
                    }
                    String roomcode = prefix + row1[0].split("_")[1] + "-" + row1[0].split("_")[2];
                    //  if(row1[2].)
                    // String mcbcode="";
                    idcPdfDayPowerInfo.setIdcPdfDayPowerInfoId(UuidUtil.get32UUID().toString());
                    idcPdfDayPowerInfo.setIdcAmout(Double.parseDouble(row3[1].trim()));
                    // idcPdfDayPowerInfo.setIdcCreateTime(DateUtil.getNow());
                    idcPdfDayPowerInfo.setIdcRoomCode(roomcode);
                    idcPdfDayPowerInfo.setIdcPdfCode((prefix + row1[1]));
                    idcPdfDayPowerInfo.setIdcMcbCode(Objects.toString(Integer.parseInt(row1[2]), "0"));
                    idcPdfDayPowerInfo.setIdcStatus(row3[2]);
                    idcPdfDayPowerInfo.setIdcOnlyCode(row1[1] + "_" + Objects.toString(Integer.parseInt(row1[2]), "0"));
                    //   idcPdfDayPowerInfo.setIdcCreateTime(new Date());
                    idcPdfDayPowerInfo.setIdcCreateTime(DateUtil.parseDate(se[1]));

                    set.add(key);
                    list.add(idcPdfDayPowerInfo);
                }
            } catch (Exception e) {
                logger.error(e.getMessage() + "第" + i + "行数据异常." + e.getMessage());
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    @Transactional( rollbackFor =Exception.class )
    public void importDataAndSave(String date) throws Exception {
        Map m=new HashMap();
        String se[]=date.split("#");
        m.put("idcCreateTime", se[1]);
        List<IdcPdfDayPowerInfo> pdflist = mapper.queryListByMap(m);
        if(pdflist!=null && pdflist.size()==0) {
            Map<String, Object> map = new HashMap();
            downPdfFile(date);
            CSVReader cSVReader = loadPdfData(date);
            List<IdcPdfDayPowerInfo> list = generatePdfPowerDataList(cSVReader,date);
            map.put("pdfList", list);
            map.put("idcCreateTime", se[0]);
            List<IdcPdfDayPowerInfo> currList = calculationPowerDifference(map);
            if (list != null && list.size() > 0) {
                mapper.insertList(currList);
                System.out.println(se[1]+"导入完成");
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

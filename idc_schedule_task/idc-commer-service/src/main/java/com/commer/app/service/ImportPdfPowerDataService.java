package com.commer.app.service;



import au.com.bytecode.opencsv.CSVReader;
import com.commer.app.mode.IdcPdfDayPowerInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 54074 on 2017-10-18.
 */
public interface ImportPdfPowerDataService {
    /**
     * 读取本地pdf数据
     * @return
     * @throws Exception
     */
    public CSVReader loadPdfData(String filePath) throws Exception;

    /**
     * 从FTP服务器下载文件
     */
    public void  downPdfFile(String filePath) throws Exception;

    /**
     * 保存计算解析后的数据
     * @param list
     * @throws Exception
     */
    public void  pdfPwoerSave(List<IdcPdfDayPowerInfo> list) throws Exception;

    /**
     * 计算PDF电量差值
     * @param map
     * @return
     */
    public List<IdcPdfDayPowerInfo> calculationPowerDifference(Map map);

    /**
     *生成pdf用电列表
     * @param reader
     * @return
     */
    public List<IdcPdfDayPowerInfo>  generatePdfPowerDataList(CSVReader reader,String filePath) throws IOException;

    /**
     * 导入保存前一天的数据
     * @param filePath
     */
    public void importDataAndSave(String filePath) throws Exception;

}

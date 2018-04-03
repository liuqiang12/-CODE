package com.commer.app.service;



import au.com.bytecode.opencsv.CSVReader;
import com.commer.app.mode.IdcPowerBuidingInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 54074 on 2017-10-18.
 */
public interface ImportPowerBuidingDataService {
    /**
     * 读取本地pdf数据
     * @return
     * @throws Exception
     */
    public CSVReader loadPdfData() throws Exception;

    /**
     * 从FTP服务器下载文件
     */
    public void  downPdfFile() throws Exception;

    /**
     * 保存计算解析后的数据
     * @param list
     * @throws Exception
     */
    public void  buidingPwoerSave(List<IdcPowerBuidingInfo> list) throws Exception;

    /**
     * 计算楼栋电量差值
     * @param map
     * @return
     */
    public List<IdcPowerBuidingInfo> calculationPowerDifference(Map map);

    /**
     *生成楼栋用电列表
     * @param reader
     * @return
     */
    public List<IdcPowerBuidingInfo>  generateBuidingPowerDataList(CSVReader reader) throws IOException;

    /**
     * 导入保存前一天的数据
     * @param date
     */
    public void importDataAndSave(String date) throws Exception;

}

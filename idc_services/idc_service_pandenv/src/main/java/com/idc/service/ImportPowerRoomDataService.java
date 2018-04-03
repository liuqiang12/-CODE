package com.idc.service;

import au.com.bytecode.opencsv.CSVReader;
import com.idc.model.IdcPdfDayPowerInfo;
import com.idc.model.IdcPowerRoomInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 54074 on 2017-10-18.
 */
public interface ImportPowerRoomDataService {
    /**
     * 读取本地pdf数据
     * @return
     * @throws Exception
     */
    public CSVReader loadRoomData() throws Exception;

    /**
     * 从FTP服务器下载文件
     */
    public void  downRoomFile() throws Exception;

    /**
     * 保存计算解析后的数据
     * @param list
     * @throws Exception
     */
    public void  roomPwoerSave(List<IdcPowerRoomInfo> list) throws Exception;

    /**
     * 计算房间电量差值
     * @param map
     * @return
     */
    public List<IdcPowerRoomInfo> calculationPowerDifference(Map map);

    /**
     *生成房间用电列表
     * @param reader
     * @return
     */
    public List<IdcPowerRoomInfo>  generateRoomPowerDataList(CSVReader reader) throws IOException;

    /**
     * 导入保存前一天的数据
     * @param date
     */
    public void importDataAndSave(String date) throws Exception;

}

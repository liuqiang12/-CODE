package com.idc.mapper;

import com.idc.model.CostAnalysisRoom;
import com.idc.model.CostAnalysisRoomExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CostAnalysisRoomMapper {
    long countByExample(CostAnalysisRoomExample example);

    int deleteByExample(CostAnalysisRoomExample example);

    int deleteByPrimaryKey(String costAnalysisRoomId);

    int insert(CostAnalysisRoom record);

    int insertSelective(CostAnalysisRoom record);

    List<CostAnalysisRoom> selectByExample(CostAnalysisRoomExample example);

    CostAnalysisRoom selectByPrimaryKey(String costAnalysisRoomId);

    int updateByExampleSelective(@Param("record") CostAnalysisRoom record, @Param("example") CostAnalysisRoomExample example);

    int updateByExample(@Param("record") CostAnalysisRoom record, @Param("example") CostAnalysisRoomExample example);

    int updateByPrimaryKeySelective(CostAnalysisRoom record);

    int updateByPrimaryKey(CostAnalysisRoom record);
}
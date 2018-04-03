package com.idc.mapper;

import com.idc.model.CostAnalysisRack;
import com.idc.model.CostAnalysisRackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CostAnalysisRackMapper {
    long countByExample(CostAnalysisRackExample example);

    int deleteByExample(CostAnalysisRackExample example);

    int deleteByPrimaryKey(String costAnalysisRackId);

    int insert(CostAnalysisRack record);

    int insertSelective(CostAnalysisRack record);

    List<CostAnalysisRack> selectByExample(CostAnalysisRackExample example);

    CostAnalysisRack selectByPrimaryKey(String costAnalysisRackId);

    int updateByExampleSelective(@Param("record") CostAnalysisRack record, @Param("example") CostAnalysisRackExample example);

    int updateByExample(@Param("record") CostAnalysisRack record, @Param("example") CostAnalysisRackExample example);

    int updateByPrimaryKeySelective(CostAnalysisRack record);

    int updateByPrimaryKey(CostAnalysisRack record);
}
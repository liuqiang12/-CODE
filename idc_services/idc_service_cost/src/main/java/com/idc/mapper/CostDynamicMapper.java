package com.idc.mapper;

import com.idc.model.CostDynamic;
import com.idc.model.CostDynamicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CostDynamicMapper {
    long countByExample(CostDynamicExample example);

    int deleteByExample(CostDynamicExample example);

    int deleteByPrimaryKey(String costRoomId);

    int insert(CostDynamic record);

    int insertSelective(CostDynamic record);

    List<CostDynamic> selectByExample(CostDynamicExample example);

    CostDynamic selectByPrimaryKey(String costRoomId);

    int updateByExampleSelective(@Param("record") CostDynamic record, @Param("example") CostDynamicExample example);

    int updateByExample(@Param("record") CostDynamic record, @Param("example") CostDynamicExample example);

    int updateByPrimaryKeySelective(CostDynamic record);

    int updateByPrimaryKey(CostDynamic record);
}
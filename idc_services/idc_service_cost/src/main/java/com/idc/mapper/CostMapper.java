package com.idc.mapper;

import com.idc.vo.CostRackVO;
import com.idc.vo.CostRoomVO;

/**
 * Created by mylove on 2017/10/27.
 */
public interface CostMapper {
    CostRackVO getCostRack(long rackid);

    CostRoomVO getCostByRoom(long roomid);

    String getBuilding(long rackid);
}

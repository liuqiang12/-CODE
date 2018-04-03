package com.idc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.bo.RoomPE;
import com.idc.mapper.PowerEnvMapper;
import com.idc.service.PowerEnvService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/7/17.
 */
@Service
public class PowerEnvServiceImpl implements PowerEnvService {
    @Autowired
    private PowerEnvMapper mapper;

    @Override
    public Map<Long, RoomPE> getPEByRoom(String startTime, String endTime) {
        return Maps.uniqueIndex(getPE(startTime, endTime), new Function<RoomPE, Long>() {
            @Override
            public Long apply(RoomPE roomPE) {
                return roomPE.getRoomid();
            }
        });
    }

    @Override
    public List<RoomPE> getPE(String startTime, String endTime) {
        Calendar calendar = Calendar.getInstance();
        if (endTime == null) {
            calendar.setTime(new Date());
            endTime = DateFormatUtils.format(calendar, "yyyy-MM-dd");
        }
        if (startTime == null) {
            try {
                calendar.setTime(DateUtils.parseDate(endTime, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            startTime = DateFormatUtils.format(calendar, "yyyy-MM-dd");
        }
        return mapper.getPEByRoom(startTime, endTime, null);
    }

    @Override
    public List<RoomPE> getLastPEByBuild(String buildid) {
        return mapper.getLastPEByBuild(buildid);
    }

    @Override
    public Map<Long, RoomPE> getLastPEByBuildToMap(String buildid) {
        return Maps.uniqueIndex(getLastPEByBuild(buildid), new Function<RoomPE, Long>() {
            @Override
            public Long apply(RoomPE roomPE) {
                return roomPE.getRoomid();
            }
        });
    }

    @Override
    public List<RoomPE> getLastPEByRoom(String roomid) {
        return mapper.getLastPEByRoom(roomid);
    }

    @Override
    public List<RoomPE> getPETopRoom(Long TopN) {
        return mapper.getPETopRoom(TopN);
    }

    @Override
    public Map<Long, RoomPE> getLastPEByRoomToMap(String roomid) {
        return Maps.uniqueIndex(getLastPEByRoom(roomid), new Function<RoomPE, Long>() {
            @Override
            public Long apply(RoomPE roomPE) {
                return roomPE.getRoomid();
            }
        });
    }

    @Override
    public List<RoomPE> getRoomPEByBuildid(String type, String buildid) {
        return mapper.getRoomPEByBuildid(type, buildid);
    }

    @Override
    public List<RoomPE> getHisPE(String type, String roomid) {
        Calendar calendar = Calendar.getInstance();
        String endTime = DateFormatUtils.format(calendar, "yyyy-MM-dd");
        calendar.add(Calendar.MONTH, -1);
        String startTime = DateFormatUtils.format(calendar, "yyyy-MM-dd");
        List<RoomPE> peByRoom = mapper.getHisPE(type, startTime, endTime, Long.parseLong(roomid));
        return peByRoom;
    }

    @Override
    public Map<String, RoomPE> getLastPEMap() {
        List<RoomPE> lists = mapper.getLastPeByRack();
        ImmutableMap<String, RoomPE> stringRoomPEImmutableMap = Maps.uniqueIndex(lists, new Function<RoomPE, String>() {
            @Override
            public String apply(RoomPE roomPE) {
                return roomPE.getRoomid()+"";
            }
        });
        return stringRoomPEImmutableMap;
    }

}

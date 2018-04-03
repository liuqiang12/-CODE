package com.idc.action.capmon;

import com.idc.model.ExecuteResult;
import com.idc.model.IdcDevice;
import com.idc.model.NetDevice;
import com.idc.service.DeviceCapService;
import com.idc.service.IdcDeviceService;
import com.idc.service.NetDeviceService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备监视
 * Created by mylove on 2017/8/1.
 */
@RequestMapping("/devicecap")
@Controller
public class DeviceCap {
    @Autowired
    private NetDeviceService netDeviceService;
    @Autowired
    private IdcDeviceService idcDeviceService;

    @Autowired
    private DeviceCapService deviceCapService;

    @RequestMapping("/{deviceId}")
    public String getDeviceCap(@PathVariable long deviceId, IdcDevice idcDevice, ModelMap map) {
        IdcDevice _idcDevice = idcDeviceService.getModelById(deviceId);
        NetDevice _netDevice = netDeviceService.getModelById(deviceId);
        if(_idcDevice!=null&&_netDevice!=null){
            map.put("model", _idcDevice.getModel());
            map.put("name", _idcDevice.getName());
            map.put("deviceid", _idcDevice.getDeviceid());
            map.put("ip", _netDevice.getIpaddress());
        }
        return "devicecap/devicecap";
    }
    @RequestMapping("/syncDevice/{deviceid}")
    @ResponseBody
    public ExecuteResult syncDevice(@PathVariable Long deviceid){
        return deviceCapService.syncDevice(deviceid);
    }
    @RequestMapping("/hiscap/{type}/{deviceid}")
    public String showHis(@PathVariable String type, @PathVariable long deviceid) {
        return "devicecap/hiscap";
    }

    @RequestMapping("/gethis")
    @ResponseBody
    public Map<String, Object> getHis(String type, String deviceid,String startTime, String endTime) {
          List<Map<String,Object>> datas = deviceCapService.getHis(type,deviceid,startTime,endTime);
        List<Map<String,Object>> results =new ArrayList<>();
        Map<String,Object>  tmap;
        for (Map<String, Object> data : datas) {
            tmap = new HashMap<>();
            Timestamp timestr = (Timestamp) data.get("TIMESTR");
            tmap.put("name", DateFormatUtils.format(timestr,"yy/MM/dd HH:mm:ss"));
            tmap.put("value",data.get("VALUE"));
            results.add(tmap);
        }
        HashMap<String, Object> map = new HashMap<>();
//        List<Map<String,Object>> datas = new ArrayList<>();
//        Map<String,Object>  tmap;
//        for (int j = 0; j < 12; j++) {
//            for (int i = 0; i < 28; i++) {
//                tmap = new HashMap<>();
//                tmap.put("name","2017-"+j+"-"+i);
//                tmap.put("value",Math.random()*80);
//                datas.add(tmap);
//            }
//        }
//
        map.put("data",results);
        return map;
    }
}

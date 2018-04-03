package com.idc.action.racklayout;

import com.idc.model.IdcInternetexport;
import com.idc.model.IdcRack;
import com.idc.model.IdcRackunit;
import com.idc.service.IdcDeviceService;
import com.idc.service.IdcInternetexportService;
import com.idc.service.IdcRackService;
import com.idc.service.IdcRackunitService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/5/26.
 */
@Controller
public class RackLayoutAction {
    @Autowired
    private IdcRackService idcRackService;
    @Autowired
    private IdcRackunitService idcRackunitService;
    @Autowired
    private IdcInternetexportService idcInternetexportService;
    @Autowired
    private IdcDeviceService idcDeviceService;

    @RequestMapping("/racklayout/in")
    public String in() {
        return "racklayout/index2";
    }

    @RequestMapping("/racklayout/{rackid}")
    public String index(@PathVariable int rackid, ModelMap map, Long deviceId, String type) {
        IdcRack idcRack = idcRackService.getModelById(rackid);
        IdcRackunit idcRackunit = new IdcRackunit();
        idcRackunit.setRackid(Long.valueOf(rackid));

        if (idcRack != null) {
            JSONObject jsonObject = JSONObject.fromObject(idcRack);
            map.put("rack", jsonObject);
            map.put("rackid", idcRack.getId());
            if (idcRack.getInternetexportId() != null) {
                IdcInternetexport idcInternetexport = idcInternetexportService.getModelById(Long.valueOf(idcRack.getInternetexportId().toString()));
                if (idcInternetexport != null) {
                    map.put("idcinternetexport", idcInternetexport);
                }
            }
            List<Map<String, Object>> idcRackunits = idcRackunitService.queryListByRackID(rackid);
            if (idcRackunits != null) {
                map.put("rackunits", idcRackunits);
                map.put("rackunitsJson", JSONArray.fromObject(idcRackunits));
                map.put("rackunitsize", idcRackunits.size());
            }
            //选中的要上架的设备
            if (deviceId != null) {
                Map<String, Object> mapQ = new HashedMap();
                mapQ.put("deviceid", deviceId);
                List<Map<String, Object>> idcDevices = idcDeviceService.queryListByObjectMap(mapQ);
                if (idcDevices != null && idcDevices.size() > 0) {
                    map.put("device", JSONObject.fromObject(idcDevices.get(0)));
                }
            }
        }
        if (type != null && "view".equals(type)) {
            return "racklayout/selectRackunit";
        } else {
            return "racklayout/index";
        }
    }

    /***
     * 获取机架的设备
     *
     * @param rackid
     * @param map
     * @return
     */
    @RequestMapping("/racklayout/devicelist/{rackid}")
    public String deviceList(@PathVariable int rackid, ModelMap map) {
//        List<Map<String, Object>> list = idcRackService.queryRackDevice(rackid);
//        map.put("list", list);
        return "racklayout/devicelist";
    }
    @RequestMapping("/racklayout/add")
    @ResponseBody
    public String add(int startu,int uheight,long rackid ,long deviceid){
        try {
            idcRackunitService.online(startu, uheight, rackid, deviceid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
    @RequestMapping("/racklayout/down/{rackid}/{deviceid}")
    @ResponseBody
    public String down(@PathVariable int rackid,@PathVariable int deviceid){
        try {
            idcRackunitService.down(rackid, deviceid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}

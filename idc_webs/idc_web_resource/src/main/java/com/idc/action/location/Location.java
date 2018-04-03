package com.idc.action.location;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/5/8.
 */

/***
 * 数据中心
 */
@Controller
@RequestMapping("/location")
public class Location {
//    @RequestMapping("/")
//    public String Index(ModelMap map) {
//        map.put("type","location");
//        return "location/index";
//    }
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData List(EasyUIPageBean pageBean) {
        List<Map<String, Object>> rows = new ArrayList<>();
        int page = pageBean.getPage() - 1;
        int pageSize = pageBean.getPageSize();
        Map<String, Object> objectMap = new HashMap<>();
        for (int i = page * pageSize; i < page * pageSize + pageSize; i++) {
            objectMap = new HashMap<>();
            objectMap.put("id", i);
            objectMap.put("device", "设备01");
            objectMap.put("name", "端口" + i);
            objectMap.put("bandWidth", "100MB");
            objectMap.put("ip", "192.168.0." + i);
            objectMap.put("mac", "fe80::951c:2481:bc3d:4ea1");
            objectMap.put("inflow", 6000);
            objectMap.put("outflow", 4000);
            objectMap.put("status", 1);
            rows.add(objectMap);
        }
        EasyUIData easyUIData = new EasyUIData();
        easyUIData.setRows(rows);
        easyUIData.setTotal(200);
        return easyUIData;
    }
}

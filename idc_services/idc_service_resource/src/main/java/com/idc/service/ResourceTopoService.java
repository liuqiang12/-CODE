package com.idc.service;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/5/31.
 */
public interface ResourceTopoService {
    /***
     * 通过类型和id 获取资源树
     *
     * @param type
     * @param id
     * @return
     */
    List<Map<String, Object>> getTree(String type, int id);
}

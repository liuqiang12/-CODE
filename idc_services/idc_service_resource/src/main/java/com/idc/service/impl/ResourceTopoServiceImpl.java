package com.idc.service.impl;

import com.idc.service.ResourceTopoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/5/31.
 */
@Service
public class ResourceTopoServiceImpl implements ResourceTopoService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceTopoServiceImpl.class);

    public static void main(String[] args) {
        //ToDo
    }

    @Override
    public List<Map<String, Object>> getTree(String type, int id) {
        return null;
    }
}

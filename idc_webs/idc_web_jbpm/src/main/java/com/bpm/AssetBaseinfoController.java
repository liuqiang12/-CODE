package com.bpm;

/**
 * Created by DELL on 2017/8/2.
 */

import com.idc.model.AssetBaseinfo;
import com.idc.service.AssetBaseinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.DevContext;
import utils.plugins.excel.Guid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务字段的请求路径
 */
@Controller
@RequestMapping("/assetBaseinfo")
public class AssetBaseinfoController {
    private Logger logger = LoggerFactory.getLogger(AssetBaseinfoController.class);
    @Autowired
    private AssetBaseinfoService assetBaseinfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    /*请求的对象*/
    @RequestMapping("/combobox/{parentCode}")
    @ResponseBody
    public List<AssetBaseinfo> completeByTaskId(@PathVariable String parentCode) throws IOException {
        List<AssetBaseinfo> list = new ArrayList<AssetBaseinfo>();

        list = assetBaseinfoService.queryComboboxData(parentCode);
        return list;
    }

    /*请求的对象*/
    @RequestMapping("/getIDC/{IDCCode}")
    @ResponseBody
    public AssetBaseinfo getIDC(@PathVariable String IDCCode) throws IOException {
        AssetBaseinfo idc = assetBaseinfoService.getIDCName(IDCCode);
        return idc;
    }

    /*请求的对象*/
    @RequestMapping("/getIntentionIdcCode.do")
    @ResponseBody
    public List<AssetBaseinfo> getIntentionIdcCode() throws IOException {
        List<AssetBaseinfo> list = new ArrayList<AssetBaseinfo>();

        //此查询的是 idc_location表
        list = assetBaseinfoService.getIntentionIdcCode();
        return list;
    }

    @RequestMapping("/checkboxOpenMSG/{parentCode}")
    @ResponseBody
    public Object getTreeNode(@PathVariable String parentCode) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        if(parentCode.equals("9999")){
            /*result=assetBaseinfoService.checkboxOpenMSG();

            Map<String, Object> param=new HashMap<>();
            param.put("value","16,17,141,147,17,141,155");
            param.put("label","asfdasffsda");
            result.add(param);
            System.out.println("------result--------");*/
        }else{
            List<AssetBaseinfo> root = assetBaseinfoService.getRootByParentCodeLike_("50");
            for (AssetBaseinfo organBuss : root) {
                Map<String, Object> node = new HashMap<String, Object>();
                node.put("id", organBuss.getId());
                node.put("text", organBuss.getLabel());

                //返回的是childs
                List<Map<String, Object>> childs = insertChilddrens(assetBaseinfoService.getChildrenByParend(organBuss.getValue()));
                node.put("children", childs);

                result.add(node);
            }
        }

        return result;
    }
    public List<Map<String, Object>> insertChilddrens(List<AssetBaseinfo> childrens) {
        List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();

        for (AssetBaseinfo children : childrens) {
            Map<String, Object> child = new HashMap<String, Object>();
            child.put("id", children.getId());
            child.put("text", children.getLabel());

            //在里面放一个child
            //child.put("children", insertChilddrens(assetBaseinfoService.queryComboboxData( children.getId().toString() ) ) );
            childs.add(child);
        }
        return childs;
    }



}

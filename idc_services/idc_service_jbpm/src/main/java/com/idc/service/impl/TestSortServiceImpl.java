package com.idc.service.impl;

import com.idc.service.TestSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.connection.RedisSetCommands;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import system.data.supper.service.impl.RedisSortQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("testSortService")
public class TestSortServiceImpl implements TestSortService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List testSort() {
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection redisConnection = RedisConnectionUtils.getConnection(factory);
        //开启管道
        RedisHashCommands redisHashCommands = factory.getConnection();
        RedisSetCommands redisSetCommands = factory.getConnection();
        ((RedisConnection) redisSetCommands).openPipeline();
        ((RedisConnection) redisHashCommands).openPipeline();
        //设置redisTemplate的ValueSerializer为StringSerializer
        redisTemplate.setValueSerializer(redisTemplate.getStringSerializer());
        //以下为序列化下面用到的信息
        //关联关系
        byte[]p103=redisTemplate.getStringSerializer().serialize("price:103");
        //关联关系
        byte[]s4=redisTemplate.getStringSerializer().serialize("star:4");
        //关联关系
        byte[]cb=redisTemplate.getStringSerializer().serialize("city:北京");
        //价格  key  value【ticket】
        byte[]price=redisTemplate.getStringSerializer().serialize("price");
        byte[]price103=redisTemplate.getStringSerializer().serialize("103");
        byte[]star=redisTemplate.getStringSerializer().serialize("starRarting");
        byte[]star4=redisTemplate.getStringSerializer().serialize("4");
        byte[]hotelId=redisTemplate.getStringSerializer().serialize("hotelId");
        byte[]hotelId10=redisTemplate.getStringSerializer().serialize("10");
        byte[]hotelName=redisTemplate.getStringSerializer().serialize("hotelName");
        byte[]hotelNameValue=redisTemplate.getStringSerializer().serialize("测试酒店0");
        byte[]city=redisTemplate.getStringSerializer().serialize("city");
        byte[]cityB=redisTemplate.getStringSerializer().serialize("北京");

        //工单key值
        byte[]hotelKey0 = redisTemplate.getStringSerializer().serialize("hotelId:10");

        //增加第一个hotel map为价格103,4星级，id=10，name=“测试酒店0”
        Map map = new HashMap();
        map.put(price, price103);
        map.put(star,star4);
        map.put(hotelId, hotelId10);
        map.put(hotelName, hotelNameValue);
        map.put(city,cityB );
//存储hotel信息为hash类型
        redisHashCommands.hMSet(hotelKey0, map);
//存储相关的键值
        redisSetCommands.sAdd(p103, hotelId10);
        redisSetCommands.sAdd(s4,hotelId10);
        redisSetCommands.sAdd(cb,hotelId10);
//取出country为中国，city为北京的hotelId存储到searchResult
        redisTemplate.opsForSet().intersectAndStore( "star:4","city:北京","searchResult");
//按照价格升序取出从0下标开始的10条数据 返回hotelList
        RedisSortQuery rsq=new RedisSortQuery(0, 10, "searchResult", "price", "asc");
        List hotelList=redisTemplate.sort(rsq);
//关闭管道
        ((RedisConnection) redisSetCommands).closePipeline();
        ((RedisConnection) redisSetCommands).close();
        ((RedisConnection) redisHashCommands).closePipeline();
        ((RedisConnection) redisHashCommands).close();
        return hotelList;
    }
}
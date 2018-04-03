package system.data.supper.service.impl;

import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.connection.SortParameters.Range;
import org.springframework.data.redis.core.query.SortQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/9/19.
 */
public class RedisSortQuery implements SortQuery {
    private int star;//从第几条开始 显示
    private int count;//每页显示的条数
    private String key=null;//redis键
    private String sortBy;//排序依据（星级，价格，）
    private String order;//排序方式（升序降序）
    public RedisSortQuery(int star, int count, String key, String sortBy,
                          String order) {
        super();
        this.star = star;
        this.count = count;
        this.key = key;
        this.sortBy = sortBy;
        this.order = order;
    }
    public RedisSortQuery() {
        super();
    }
    //排序方式  按照hash方式存储的value的map的key取值比如starRating，price
    @Override
    public String getBy() {
        String by=null;
         if("serialNumber".equals(sortBy)){
            by="ticketInstId:*->serialNumber";
        }
        /*if("price".equals(sortBy)){
            by="hotelId:*->price";
        }*/
        return by;
    }
    //排序方式
    @Override
    public Order getOrder() {
        Order orderBy=null;
//升序
        if("asc".equalsIgnoreCase(order)){
            orderBy=SortParameters.Order.ASC;
        }
        //降序
        if("desc".equalsIgnoreCase(order)){
            orderBy=SortParameters.Order.DESC;
        }
        return orderBy;
    }
    //排序是数值（默认值）或字母（辞书）。如果返回值为true则按照字母方式排序
    @Override
    public Boolean isAlphabetic() {
        return false;
    }
    //返回排序限制（范围或分页），从0开始。
    @Override
    public Range getLimit() {
        return new Range(star, count);
    }
    //返回目标键进行排序。
    @Override
    public Object getKey() {
        return key;
    }
    //返回用于排序外键的方式。
//返回外部密钥（多个），它们的值被排序被返回。
    @Override
    public List getGetPattern() {
//设置如下信息可返回指定列，sort返回的list的值为在此设置的值，如果返回值为null，则返回的list为key按照规则排序分页后的值
        List list=new ArrayList();
 list.add("ticketInstId:*->ticketInstId");
        list.add("ticketInstId:*->prodInstId");

        //list.add("ticketInstId:*->serialNumber");
        return null;
        //return list;
    }
    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getSortBy() {
        return sortBy;
    }
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setOrder(String order) {
        this.order = order;
    }
}

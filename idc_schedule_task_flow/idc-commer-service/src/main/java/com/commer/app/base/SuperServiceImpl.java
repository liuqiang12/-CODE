package com.commer.app.base;

import com.comer.util.MapHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 公共的业务层方法
 * @author Administrator
 * @param <T>
 * @param <PK>
 */
public abstract class SuperServiceImpl<T, PK extends Serializable> implements SuperService<T, PK> {
	@Autowired
	private SuperMapper<T, PK> mapper;
	/**
	 * @return T  通过主键获取实体类
	 * @param PK 主键
	 * @exception Exception
	 */
	@Override
	public T getModelById(PK pk) {
		return mapper.getModelById(pk);
	}
	/**
	 * @param map 
	 * @return 通过map条件 查询
	 * @
	 */
	@Override
	public T getModelByMap(Map map) {
		return mapper.getModelByMap(map);
	}
	/**
	 * @param entity
	 * @return 通过实体类 查询实体
	 * @
	 */
	@Override
	public T getModelByObject(T t) {
		return mapper.getModelByObject(t);
	}
	/**
	 * @return 所有数据集
	 * @
	 */
	@Override
	public List<T> queryList() {
		return mapper.queryList();
	}
	/**
	 * @param map 
	 * @return 通过map过滤数据集
	 * @
	 */
	@Override
	public List<T> queryListByMap(Map map) {
		return mapper.queryListByMap(map);
	}
	/**
	 * 
	 * @param entity
	 * @return 通过实体类过滤数据集
	 * @
	 */
	@Override
	public List<T> queryListByObject(T t) {
		return mapper.queryListByObject(t);
	}
	/**
	 * 
	 * @param page
	 * @param t
	 * @return 通过实体过滤分页数据集
	 * @
	 */
	@Override
	public List<T> queryListPage(PageBean<T> page,Object param) {
		//这里讲查询条件进行处理
		page.setParams(MapHelper.queryCondition(param));
		//真正执行查询分页
		return mapper.queryListPage(page);
	}
	/**
	 * <br> 查询结束
	 */
	/**
	 * 
	 * @param pk
	 * @return 根据主键修改实体类 返回实体类
	 * @throws Exception
	 */
	@Override
	public T updateById(PK pk) throws Exception {
		return mapper.updateById(pk);
	}
	/**
	 * 
	 * @param map
	 * @return 根据map更新实体类
	 * @throws Exception
	 */
	@Override
	public void updateByObject(T t) throws Exception {
		 mapper.updateByObject(t);
	}
	/**
	 * 
	 * @param map
	 * @return 根据map更新实体类
	 * @throws Exception
	 */
	@Override
	public T updateByMap(Map map) throws Exception {
		return mapper.updateByMap(map);
	}
	/**
	 * <br> 修改结束
	 */
	/**
	 * 删除所有的数据
	 * @throws Exception
	 */
	@Override
	public void deleteAll() throws Exception {
		mapper.deleteAll();
	}
	/**
	 * 根据主键 删除记录
	 * @param pk
	 * @throws Exception
	 */
	@Override
	public void deleteById(PK pk) throws Exception {
		mapper.deleteById(pk);
	}
	/**
	 * 根据实体类过滤 删除记录
	 * @param entity
	 * @throws Exception
	 */
	@Override
	public void deleteByObject(T entity) throws Exception{
		mapper.deleteByObject(entity);
	}
	/**
	 * 根据map过滤删除记录
	 * @param map
	 * @throws Exception
	 */
	@Override
	public void deleteByMap(Map map) throws Exception {
		mapper.deleteByMap(map);
	}
	/**
	 * 批量删除
	 * @param list
	 * @throws Exception
	 */
	@Override
	public void deleteByList(List list) throws Exception {
		mapper.deleteByList(list);
	}
	/**
	 * <br> 删除结束
	 */
	/**
	 * 保存数据
	 * @param t
	 * @throws Exception
	 */
	@Override
	public void insert(T t) throws Exception {
		mapper.insert(t);
	}
	/**
	 * 批量保存数据
	 * @param list
	 * @throws Exception
	 */
	@Override
	public void insertList(List<T> list) throws Exception {
		mapper.insertList(list);
	}

}

package com.commer.app.base;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <b>描述<b>：共性service
 * supperMapper
 * @author Administrator
 */
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public interface SuperService<T, PK extends Serializable> {
	/**
	 * <h1> 通用性的方法：增、删、改、查[insert,delete,update,query]<h1>
	 */
	/**
	 * @return T  通过主键获取实体类
	 * @param PK 主键
	 * @exception Exception
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public T getModelById(PK pk) ;
	/**
	 * @param map 
	 * @return 通过map条件 查询
	 * @
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public T getModelByMap(Map map) ;
	/**
	 * @param entity
	 * @return 通过实体类 查询实体
	 * @
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public T getModelByObject(T t) ;
	/**
	 * @return 所有数据集
	 * @
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<T> queryList() ;
	/**
	 * @param map 
	 * @return 通过map过滤数据集
	 * @
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<T> queryListByMap(Map map) ;
	/**
	 * 
	 * @param entity
	 * @return 通过实体类过滤数据集
	 * @
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<T> queryListByObject(T t) ;
	/**
	 * 
	 * @param page:分页的实体类
	 * @param param:任意格式的参数
	 * @return 通过实体过滤分页数据集
	 * @
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<T> queryListPage(PageBean<T> page, Object param) ;
	/**
	 * <br> 查询结束
	 */
	/**
	 * 
	 * @param pk
	 * @return 根据主键修改实体类 返回实体类
	 * @throws Exception
	 */
	public T updateById(PK pk) throws Exception;
	/**
	 * 
	 * @param entity
	 * @return 修改 返回实体类
	 * @throws Exception
	 */
	public void updateByObject(T t) throws Exception;
	/**
	 * 
	 * @param map
	 * @return 根据map更新实体类
	 * @throws Exception
	 */
	public T updateByMap(Map map) throws Exception;
	/**
	 * <br> 修改结束
	 */
	/**
	 * 删除所有的数据
	 * @throws Exception
	 */
	public void deleteAll() throws Exception;
	/**
	 * 根据主键 删除记录
	 * @param pk
	 * @throws Exception
	 */
	public void deleteById(PK pk) throws Exception;
	/**
	 * 根据实体类过滤 删除记录
	 * @param entity
	 * @throws Exception
	 */
	public void deleteByObject(T entity) throws Exception;
	/**
	 * 根据map过滤删除记录
	 * @param map
	 * @throws Exception
	 */
	public void deleteByMap(Map map) throws Exception;
	/**
	 * 批量删除
	 * @param list
	 * @throws Exception
	 */
	public void deleteByList(List list) throws Exception;
	/**
	 * <br> 删除结束
	 */
	/**
	 * 保存数据
	 * @param t
	 * @throws Exception
	 */
	public void insert(T t) throws Exception;
	/**
	 * 批量保存数据
	 * @param list
	 * @throws Exception
	 */
	public void insertList(List<T> list) throws Exception;

}

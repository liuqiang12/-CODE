package system.data.supper.mapper;

import system.data.page.PageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <b>描述<b>：共性mapper
 * supperMapper
 * @author Administrator
 */
public interface SuperMapper<T, PK extends Serializable> {
	/**
	 * <h1> 通用性的方法：增、删、改、查[insert,delete,update,query]<h1>
	 */
	/**
	 * @return T  通过主键获取实体类
	 * @exception Exception
	 */
    T getModelById(PK pk) ;
	/**
	 * @param map 
	 * @return 通过map条件 查询
	 * @
	 */
    T getModelByMap(Map map) ;
	/**
	 * @return 通过实体类 查询实体
	 * @
	 */
    T getModelByObject(T t) ;
	/**
	 * @return 所有数据集
	 * @
	 */
    List<T> queryList() ;
	/**
	 * @param map 
	 * @return 通过map过滤数据集
	 * @
	 */
    List<T> queryListByMap(Map map) ;
	/**
	 *
	 * @return 通过实体类过滤数据集
	 * @
	 */
    List<T> queryListByObject(T t) ;
	/**
	 * 
	 * @param page
	 * @return 分页查询
	 * @
	 */
    List<T> queryListPage(PageBean<T> page) ;
	/**
	 * <br> 查询结束
	 */
	/**
	 * 
	 * @param pk
	 * @return 根据主键修改实体类 返回实体类
	 * @throws Exception
	 */
    T updateById(PK pk) throws Exception;
	/**
	 *
	 * @return 修改 返回实体类
	 * @throws Exception
	 */
    void updateByObject(T t) throws Exception;
	/**
	 * 
	 * @param map
	 * @return 根据map更新实体类
	 * @throws Exception
	 */
    T updateByMap(Map map) throws Exception;
	/**
	 * <br> 修改结束
	 */
	/**
	 * 删除所有的数据
	 * @throws Exception
	 */
    void deleteAll() throws Exception;
	/**
	 * 根据主键 删除记录
	 * @param pk
	 * @throws Exception
	 */
    void deleteById(PK pk) throws Exception;
	/**
	 * 根据实体类过滤 删除记录
	 * @param entity
	 * @throws Exception
	 */
    void deleteByObject(Object entity) throws Exception;
	/**
	 * 根据map过滤删除记录
	 * @param map
	 * @throws Exception
	 */
    void deleteByMap(Map map) throws Exception;
	/**
	 * 批量删除
	 * @param list
	 * @throws Exception
	 */
    void deleteByList(List list) throws Exception;
	/**
	 * <br> 删除结束
	 */
	/**
	 * 保存数据
	 * @param t
	 * @throws Exception
	 */
    void insert(T t) throws Exception;
	/**
	 * 批量保存数据
	 * @param list
	 * @throws Exception
	 */
    void insertList(List<T> list) throws Exception;
}

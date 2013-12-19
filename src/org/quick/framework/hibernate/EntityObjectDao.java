package org.quick.framework.hibernate;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.quick.framework.hibernate.bean.EntityObject;
import org.quick.framework.hibernate.support.PaginationMapSupport;
import org.quick.framework.hibernate.support.PaginationSupport;


/**
 * 
 * @title:EntityObjectDao
 * @describe: 泛型数据操作接口
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 13, 201311:54:54 AM
 * @version V1.0
 * @param <T>
 */
public interface EntityObjectDao<T extends EntityObject> {


    public Class<T> getEntityType();

    public String getEntityName();

    public T loadObject(String id);

    public String createObject(T entity);
    
    public void updateObject(T entity);
    
    public void deleteObject(String id);
    
    public void deleteObject(T entity);

    public void deleteObjects(String[] ids);

    public void deleteObjects(T[] entities);

    public void deleteObjects(Collection<T> entities);

    public void deleteAll();

    /**
     * 这里根据属性返回唯一的对象。
     * 
     * @param propName
     * @param propValue
     * @return
     * @throws NotUniqueException 当有多条结果的时候抛出。
     */
    public T findObject(final String propName, final Object propValue);

	public List<T> findAll();
	
	/**
	 * 提供给Hibernate使用的查询。直接通过hql查询。对于mongo可以忽略参数values,直接通过json查询
	 * @param query
	 * @param values
	 * @return
	 */
	public List<?> findByQuery(String query, Object... values);
	
	public List<?> findByQuery(String query);

    public List<T> findByProperty(String propName, Object propValue);

	public PaginationSupport<T> findByCondition(Condition condition, Range range, Sorter sorter);

	public List<T> findAllByCondition(Condition condition);

    public List<T> findAllByCondition(Condition condition, Sorter sorter);
    
    /**
     * 根据条件查询有限记录列表
     * @param condition 查询条件
     * @param size 查询条数
     * @return 记录列表
     */
    public List<T> findByCondition(Condition condition, int size);
    
    /**
     * 根据条件、排序查询有限记录列表
     * @param condition 查询条件
     * @param sorter 排序规则
     * @param size 查询条数
     * @return 记录列表
     */
    public List<T> findByCondition(Condition condition, Sorter sorter, int size);

    /**
     * 根据条件统计记录数
     * @param condition 统计条件
     * @return 统计记录数
     */
    public int countByCondition(Condition condition);

    
    /**
     * 查询总数
     * @param sql sql语句
     * @return 统计记录数
     */
    public int countBySql(String sql);
    
    /**
     * 根据条件查询总数
     * @param sql sql语句
     * @param agrs sql参数
     * @return 统计记录数
     */
    public int countBySql(String sql, Object ...agrs);
    
    /**
     * 查询符合条件的数据
     * @param sql
     * @return 记录列表
     */
    public List<Map<String,Object>> findAllBySql(String sql);
    
   
    /**
     * 查询符合条件的数据
     * @param sql sql语句
     * @param agrs 查询参数
     * @return 记录列表
     */
    public List<Map<String,Object>> findAllBySql(String sql, Object ...agrs);
    
    /**
     * 根据条件、有限记录列表
     * @param countSql 查询总数sql
     * @param sql sql语句
     * @param range 分页参数
     * @param agrs 查询参数
     * @return 记录列表
     */
    public PaginationMapSupport findAllBySql(String countSql,String sql, Range range, Object ...agrs);
    
    /**
     * 根据条件、有限记录列表
     * @param sql sql语句
     * @param size 查询数量
     * @param agrs 查询参数
     * @return 记录列表
     */
    public List<Map<String,Object>> findAllBySql(String sql, int size, Object ...agrs);
}

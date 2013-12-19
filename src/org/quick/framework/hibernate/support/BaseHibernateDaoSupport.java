package org.quick.framework.hibernate.support;


import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.quick.framework.hibernate.Condition;
import org.quick.framework.hibernate.EntityObjectDao;
import org.quick.framework.hibernate.Range;
import org.quick.framework.hibernate.Sorter;
import org.quick.framework.hibernate.Sorter.Sort;
import org.quick.framework.hibernate.bean.EntityObject;
import org.quick.framework.hibernate.util.HibernateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * @title:BaseHibernateDaoSupport
 * @describe: hibernate支持
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 5, 20132:13:34 PM
 * @version V1.0
 * @param <T>
 */
public class BaseHibernateDaoSupport <T extends EntityObject> extends HibernateDaoSupport implements EntityObjectDao<T> {

	/**
	 * 实体 class
	 */
	protected Class<T> entityClass;
	
	/**
	 * 实体名
	 */
	private String entityName;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 注入sessionFactory
	 * @param sessionFactory
	 */
	@Autowired
	public final void setHibernateSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	public BaseHibernateDaoSupport(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntityType() {
		if (entityClass == null) {
			ParameterizedType pmType = (ParameterizedType) getClass().getGenericSuperclass();
			entityClass = (Class<T>) pmType.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	public String getEntityName() {
		if (entityName == null) {
			entityName = getEntityType().getSimpleName();
		}
		return entityName;
	}

	public int countByCondition(Condition condition) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		criteria.setProjection(Projections.rowCount());
		List<?> result = getHibernateTemplate().findByCriteria(criteria);
		return ((Integer) result.get(0)).intValue();
	}
	
	public String createObject(T entity) {
		String entityId = (String) getHibernateTemplate().save(entity);
		return entityId;
	}
	
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void deleteAll() {
		List<T> entities = findAll();
		getHibernateTemplate().deleteAll(entities);
	}

	public void deleteObject(String id) {
		deleteObject(loadObject(id));
	}

	public void deleteObject(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public void deleteObjects(String[] ids) {
		for (String id : ids) {
			deleteObject(id);
		}
	}

	public void deleteObjects(T[] entities) {
		getHibernateTemplate().deleteAll(Arrays.asList(entities));
	}

	public void deleteObjects(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(getEntityType()));
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllByCondition(Condition condition) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllByCondition(Condition condition, Sorter sorter) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		doSort(criteria, sorter);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport<T> findByCondition(Condition condition, Range range, Sorter sorter) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		doSort(criteria, sorter);
		return HibernateUtils.findByCriteria(getHibernateTemplate(), criteria, range.getStart(), range.getLimit());
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCondition(Condition condition, int size) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		return getHibernateTemplate().findByCriteria(criteria, -1, size);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCondition(Condition condition, Sorter sorter, int size) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		doSort(criteria, sorter);
		return getHibernateTemplate().findByCriteria(criteria, -1, size);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propName, Object propValue) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityType());
		criteria.add(Restrictions.eq(propName, propValue));
		return (List<T>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public T findObject(String propName, Object propValue) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityType());
		criteria.add(Restrictions.eq(propName, propValue));
		List<?> list = getHibernateTemplate().findByCriteria(criteria, -1, 1);
		Object entity = list.isEmpty() ? null : list.get(0);
		return (T) entity;
	}

	public T loadObject(String id) {
		return (T) getHibernateTemplate().get(getEntityType(), id);
	}

	public void updateObject(T entity) {
		getHibernateTemplate().merge(entity);
	}

	protected void applyPropertyFilterToDetachedCriteria(DetachedCriteria criteria, String paramName, Object propValue) throws HibernateException {
		if (propValue instanceof Collection<?>) {
			criteria.add(Restrictions.in(paramName, (Collection<?>) propValue));
		} else if (propValue instanceof Object[]) {
			criteria.add(Restrictions.in(paramName, (Object[]) propValue));
		} else {
			criteria.add(Restrictions.eq(paramName, propValue));
		}
	}

	protected DetachedCriteria populateDetachedCriteria(Condition condition) {
		return populateDetachedCriteria(condition, DetachedCriteria.forClass(getEntityType()));
	}

	protected DetachedCriteria populateDetachedCriteria(Condition condition, DetachedCriteria criteria) {
		if (criteria == null) {
			criteria = DetachedCriteria.forClass(getEntityType());
		}
		HibernateCondition hibernateCondition = (HibernateCondition) condition;
		hibernateCondition.populateDetachedCriteria(criteria);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	protected void doSort(DetachedCriteria criteria, Sorter sorter) {
		for (Sort sort : sorter.getSorts()) {
			criteria.addOrder(sort.isAscending() ? Order.asc(sort.property) : Order.desc(sort.property));
		}
	}

	public List<?> findByQuery( String query, Object... values) {
		return getHibernateTemplate().find(query, values);
	}

	public List<?> findByQuery(String query) {
		return getHibernateTemplate().find(query);
	}

	public int countBySql(String sql) {
		return jdbcTemplate.queryForInt(sql);
	}

	public int countBySql(String sql, Object... agrs) {
		return jdbcTemplate.queryForInt(sql,agrs);
	}

	public List<Map<String, Object>> findAllBySql(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> findAllBySql(String sql, Object... agrs) {
		return jdbcTemplate.queryForList(sql,agrs);
	}

	public PaginationMapSupport findAllBySql(String countSql, String sql, Range range, Object... agrs) {
		int totalCount =  countBySql(countSql,agrs);
		PaginationMapSupport page = new PaginationMapSupport();
		page.setTotalCount(totalCount);
		page.setOffset(range.getStart()); 
		List<Map<String,Object>> items = findAllBySql(sql+" limit "+(page.getOffset()>0?page.getOffset():1-1)* range.getLimit()+","+range.getLimit(),agrs);
		page.setItems(items);
		page.setPageSize(range.getLimit());
		return page;
	}

	public List<Map<String, Object>> findAllBySql(String sql, int size, Object... agrs) {
		sql = sql+" limit "+size;
		return findAllBySql(sql,agrs);
	}
}

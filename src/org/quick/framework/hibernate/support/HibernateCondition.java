package org.quick.framework.hibernate.support;


import org.hibernate.criterion.DetachedCriteria;
import org.quick.framework.hibernate.Condition;

/**
 * @title:HibernateCondition
 * @describe: hibernate构造条件
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 3, 20132:59:51 PM
 * @version V1.0
 */
public interface HibernateCondition extends Condition {

	public void populateDetachedCriteria(DetachedCriteria criteria);
}

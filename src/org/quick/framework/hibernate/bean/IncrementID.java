package org.quick.framework.hibernate.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @title:IncrementID
 * @describe: 主键(自动生成)
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 5, 20132:08:09 PM
 * @version V1.0
 */
@MappedSuperclass
public abstract class IncrementID {

	@Id
	@GeneratedValue(generator = "hibernate-increment")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	public Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

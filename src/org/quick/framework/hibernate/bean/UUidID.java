package org.quick.framework.hibernate.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @title:UuidID
 * @describe: uuid主键
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 5, 20132:08:34 PM
 * @version V1.0
 */
@MappedSuperclass
public abstract class UUidID {

	private String id;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

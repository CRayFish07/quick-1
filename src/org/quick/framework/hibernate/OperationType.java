package org.quick.framework.hibernate;


/**
 * 操作标志
 * @title:OperationType
 * @describe: TODO(这里用一句话描述这个方法的作用)
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 3, 20134:08:41 PM
 * @version V1.0
 */
public enum OperationType {

	CREATE("create"), UPDATE("update"), DELETE("delete");

	private String type;

	OperationType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
}

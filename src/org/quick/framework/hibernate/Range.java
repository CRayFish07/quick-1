package org.quick.framework.hibernate;


import java.io.Serializable;

/**
 * @title:Range
 * @describe: 分页
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 3, 20134:08:25 PM
 * @version V1.0
 */
public class Range implements Serializable{

	private static final long serialVersionUID = -3472933788362913672L;

	//当前页
	private int start;
	
	//每页多少记录
	private int limit;
	
	public Range(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getEnd() {
		return start + limit;
	}
	
}

package org.quick.framework.hibernate;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.quick.framework.hibernate.util.StringUtils;


/**
 * @title:Sorter
 * @describe: 排序对象
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 3, 20133:31:48 PM
 * @version V1.0
 */
public class Sorter implements Serializable{

	private static final long serialVersionUID = -6373394706784489139L;

	/**
     * 排序类型
     */
    private static enum Type {

        /**
         * 升序
         */
        ASCENDING,

        /**
         * 降序
         */
        DESCENDING
    }

    /**
     * 排序指示对象
     */
    public static class Sort implements Serializable {

		private static final long serialVersionUID = -1599386645244047497L;

		public String property;

		public Type type;

		/**
		 * 用默认构造器
		 * @param property
		 * @param type
		 */
        private Sort(String property, Type type) {
            this.property = property;
            this.type = type;
        }

        public boolean isAscending() {
            return type == Type.ASCENDING;
        }

        @Override
        public String toString() {
            return property + " " + (type == Type.ASCENDING ? "asc" : "desc");
        }
    }

    private List<Sort> sorts = new ArrayList<Sort>();

    public List<Sort> getSorts() {
		return sorts;
	}

	/**
     * 升序
     * @param property sort property
     * @return this
     */
    public Sorter asc(String property) {
    	for (Sort sort: sorts) {
    		if (StringUtils.compare(property, sort.property)) {
    			sorts.remove(sort);
    			break;
    		}
    	}
        sorts.add(new Sort(property, Type.ASCENDING));
        return this;
    }

    /**
     * 降序
     *
     * @param property sort property
     * @return this
     */
    public Sorter desc(String property) {
    	for (Sort sort: sorts) {
    		if (StringUtils.compare(property, sort.property)) {
    			sorts.remove(sort);
    			break;
    		}
    	}
        sorts.add(new Sort(property, Type.DESCENDING));
        return this;
    }

}

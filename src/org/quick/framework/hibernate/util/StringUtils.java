package org.quick.framework.hibernate.util;

public class StringUtils {

	public static boolean compare(String arg0, String arg1) {
		return arg0 == null ? arg1 == null : arg0.equals(arg1);
	}
}

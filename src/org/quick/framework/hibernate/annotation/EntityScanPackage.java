package org.quick.framework.hibernate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title:EntityScanPackage
 * @describe: 用来标记那些包下面实体需要初始化数据库
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 13, 20139:27:51 AM
 * @version V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EntityScanPackage {

	 String name();		//整个项目标识符名称
	
	 String basePackage();		//实体包
	
	 String[] basePackages() default {};	//实体包数组
}

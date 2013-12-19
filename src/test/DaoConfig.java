package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quick.framework.hibernate.EntityObjectDao;
import org.quick.framework.hibernate.annotation.EntityScanPackage;
import org.quick.framework.hibernate.support.BaseHibernateDaoSupport;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import test.entity.User;

/**
 * 
 * @title:DaoApplication
 * @describe: 用来配置dao层
 * @author: yong.runningboy@gmail.com
 * @datetime: Dec 13, 20139:28:39 AM
 * @version V1.0
 */
@EntityScanPackage(name="quick",basePackage="test.entity")	//扫描包下面实体
@Configuration	//标记为bean管理组件 类似xml
@ComponentScan(basePackages={"org.quick.framework.hibernate.support"})	//扫描数据层扩展
public class DaoConfig {
	
	@Bean(name="userDao")
	public EntityObjectDao<User> makeUserDao(){
		return new BaseHibernateDaoSupport<User>(User.class);
	}
	
	@Autowired
	ApplicationContext applicationContext;
	
	//管理扫描包配置到hibernate下进行扫描
	@Bean(name="hibernate.packagesToScan")
	public String [] packagesToScan(){
		Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(EntityScanPackage.class);
		if(beansWithAnnotation == null || beansWithAnnotation.size() == 0){
			return new String[]{};
		}
		
		List<String> packages = new ArrayList<String>();
		for(Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()){
			Class<?> claz = AopUtils.getTargetClass(entry.getValue());
			if(ClassUtils.isCglibProxy(entry.getValue())){
				claz = entry.getValue().getClass().getSuperclass();
			}
			EntityScanPackage entityScanPackage = claz.getAnnotation(EntityScanPackage.class);
			if(entityScanPackage != null){
				String basePackage = entityScanPackage.basePackage();
				if(StringUtils.isNotBlank(basePackage) || !packages.contains(basePackage)){
					packages.add(basePackage);
				}
				String[] basePackages = entityScanPackage.basePackages();
				for(String _basePackage_ : basePackages){
					if(!packages.contains(_basePackage_)){
						packages.add(_basePackage_);
					}
				}
			}
		}
		return packages.toArray(new String[packages.size()]);
	}
}

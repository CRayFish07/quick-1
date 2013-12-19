package test;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.quick.framework.hibernate.EntityObjectDao;
import org.springframework.beans.factory.annotation.Autowired;

import test.entity.User;
import test.spring.SpringTxTestCase;

public class UserTest extends SpringTxTestCase {

	@Resource(name="userDao")
	EntityObjectDao<User> userUserDao;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Test
	public void save(){
		User user= new User();
		user.setAge(16);
		user.setUsername("张三");
		userUserDao.createObject(user);
	} 
	
	
	/*@Test
	public void query(){
		
		UserCondition condition = new UserCondition();
		condition.setUsername("admin");
		//List<User> list = userUserDao.findAllByCondition(condition);
		
		UserSqlCondition condition2 = new UserSqlCondition();
		condition2.setAge(16);
		List<Map<String,Object>> list = userUserDao.queryForList(condition2);
		for(Map<String,Object> map : list){
			for(Map.Entry<String,Object> entry : map.entrySet()){
				System.out.println(entry.getKey()+"--"+entry.getValue());
			}
		}
		
		Sorter sorter = new Sorter();
		Range range = new Range(0,10);
		PaginationSupport<User> page = userUserDao.findByCondition(condition, range, sorter);
		List<User> list = page.getItems();
		for(User user : list){
			System.out.println(user.getId());
		}
		
		PaginationMapSupport page = userUserDao.findAllBySql(UserCondition.user_count_sql,UserCondition.user_select_sql, new Range(0,10));
		List<Map<String,Object>> items = page.getItems();
		for(Map<String,Object> map : items){
			for(Entry<String, Object> entry : map.entrySet()){
				System.out.println(entry.getKey()+"="+entry.getValue());
			}
		}
	}*/
}

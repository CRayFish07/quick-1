package test.condition;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quick.framework.hibernate.support.HibernateCondition;

public class UserCondition implements HibernateCondition {

	
	private String username;
	
	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if(StringUtils.isNotBlank(username)){
			criteria.add(Restrictions.eq("username", username));
		}
		if(StringUtils.isNotBlank(password)){
			criteria.add(Restrictions.eq("password", password));
		}
	}

	public static final String user_count_sql = "select count(1) from t_user";
	
	public static final String user_select_sql = "select * from t_user";
}

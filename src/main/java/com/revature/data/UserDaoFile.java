package com.revature.data;

import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.revature.beans.User;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class UserDaoFile implements UserDao {
	
	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	

}

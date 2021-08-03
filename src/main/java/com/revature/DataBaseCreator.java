package com.revature;

import com.revature.beans.User;
import com.revature.data.UserDao;
import com.revature.util.CassandraUtil;
import com.revature.data.UserDaoImpl;
import com.revature.beans.UserType;

public class DataBaseCreator {
	private static UserDao userDao = new UserDaoImpl();
	
	public static void dropTables() {
		StringBuilder stringBuild = new StringBuilder("DROP TABLE IF EXISTS User;");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild = new StringBuilder("DROP TABLE IF EXISTS ReceivedNotif;");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		// add more tables as they are needed
		
		
	}
	
	public static void createTables() {
		StringBuilder stringBuild = new StringBuilder("CREATE TABLE IF NOT EXISTS User (")
				.append("username text PRIMARY KEY, email text, type text, ")
				.append("supervisor text, dephead text, inbox list<uuid> );");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild = new StringBuilder("CREATE TABLE IF NOT EXISTS ReceivedNotif (")
				.append("sender text, receiver text, id uuid, message text, sentDate date, ")
				.append("primary key (id, receiver));");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
	}
	
	public static void populateUserTable() {
		User user = new User("Jason", "redhood@bat.com", "Bruce" );
		user.setType(UserType.SUPERVISOR);
		userDao.addUser(user);
		User user2 = new User("Barbara", "batgirl@bat.com", "Bruce" );
		user2.setType(UserType.SUPERVISOR);
		userDao.addUser(user2);
		userDao.addUser(new User("Tim", "nightwing@bat.com", "Jason", "Bruce"));
		userDao.addUser(new User("Damian", "robin@bat.com", "Barbara", "Bruce"));
		userDao.addUser(new User("Cassandra", "blackbat@bat.com", "Barbara", "Bruce"));
		userDao.addUser(new User("Helena", "huntress@bat.com", "Jason", "Bruce"));
		userDao.addUser(new User("Stephanie", "spoiler@bat.com", "Bruce", "Bruce"));
		
	}
	
	

}

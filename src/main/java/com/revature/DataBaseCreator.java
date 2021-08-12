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
		
		stringBuild = new StringBuilder("DROP TABLE IF EXISTS Notification;");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild = new StringBuilder("DROP TABLE IF EXISTS Reimbursement;");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild= new StringBuilder("DROP TABLE IF EXISTS FinalForm;");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild= new StringBuilder("DROP TABLE IF EXISTS ExceedFunds;");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
	}
	
	public static void createTables() {
		StringBuilder stringBuild = new StringBuilder("CREATE TABLE IF NOT EXISTS User (")
				.append("username text PRIMARY KEY, email text, type text, ")
				.append("supervisor text, dephead text, inbox list<uuid> );");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild = new StringBuilder("CREATE TABLE IF NOT EXISTS Notification (")
				.append("receiver text, id uuid, message text, sentDate date, ")
				.append("primary key (receiver, id));");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild = new StringBuilder("CREATE TABLE IF NOT EXISTS Reimbursement (")
				.append("id uuid, employee text, reimburseForm text, approvedEmail text, ")
				.append("submissionDate date, lastApprovalDate date, superApproval boolean, ")
				.append("headApproval boolean, bencoApproval boolean, urgent Boolean, ")
				.append("requestAmount bigint, approvedAmount bigint, ")
				.append("primary key(employee, id));");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild = new StringBuilder("CREATE TABLE IF NOT EXISTS ExceedFunds (")
				.append("id uuid PRIMARY KEY, amount bigint, reason text, bencoName text);");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		stringBuild = new StringBuilder("CREATE TABLE IF NOT EXISTS FinalForm (")
				.append("id uuid, employee text, approved boolean, submissionDate date,")
				.append(" formType text, urgent boolean, filename text, primary key(employee, id));");
		CassandraUtil.getInstance().getSession().execute(stringBuild.toString());
		
		
		
	}
	
	public static void populateUserTable() {
		// SUPERVISORS
		User user = new User("Jason", "redhood@bat.com", "Bruce" );
		user.setType(UserType.SUPERVISOR);
		userDao.addUser(user);
		User user2 = new User("Barbara", "batgirl@bat.com", "Bruce" );
		user2.setType(UserType.SUPERVISOR);
		userDao.addUser(user2);
		
		// DEPARTMENT HEAD USERS
		User user3 = new User("Bruce", "thebat@bat.com", "Alfred" );
		user3.setType(UserType.HEAD);
		userDao.addUser(user3);
		User userCEO = new User("Alfred", "butler@bat.com");
		userCEO.setType(UserType.HEAD);
		userDao.addUser(userCEO);
		
		// BENCO USERS
		User user4 = new User("Gordon", "cop@bat.com", "Dolores" );
		user4.setType(UserType.BENCO);
		userDao.addUser(user4);
		User user5 = new User("Dolores", "holy@bat.com", "Alfred");
		user5.setType(UserType.BENCO);
		userDao.addUser(user5);
		
		// STANDARD EMPLOYEES
		userDao.addUser(new User("Tim", "nightwing@bat.com", "Jason", "Bruce"));
		userDao.addUser(new User("Damian", "robin@bat.com", "Barbara", "Bruce"));
		userDao.addUser(new User("Cassandra", "blackbat@bat.com", "Barbara", "Bruce"));
		userDao.addUser(new User("Helena", "huntress@bat.com", "Jason", "Bruce"));
		userDao.addUser(new User("Stephanie", "spoiler@bat.com", "Bruce", "Bruce"));
		
	}
	
	

}

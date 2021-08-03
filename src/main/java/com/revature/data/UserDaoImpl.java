package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.User;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;
import com.revature.beans.UserType;

@Log
public class UserDaoImpl implements UserDao {
	
	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	@Override
	public void addUser(User user) {
		String query = "Insert into user (username, email, type, supervisor, dephead) values (?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(user.getUsername(), user.getEmail(), user.getType().toString(), user.getSupervisor(), user.getDephead());
		session.execute(bound);
	}
	
	@Override
	public List<User> getUsers() {
		String query = "Select username, email, type, supervisor, dephead from user";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<User> users = new ArrayList<>();
		rs.forEach(row -> {
			User user = new User();
			user.setUsername(row.getString("username"));
			user.setEmail(row.getString("email"));
			user.setType(UserType.valueOf(row.getString("type")));
			user.setSupervisor(row.getString("supervisor"));
			user.setDephead(row.getString("dephead"));
			
			users.add(user);
		});
		 
		return users;
		
	}
	
	@Override
	public User getUser(String username) {
		String query = "Select username, email, type, supervisor, dephead from user where username=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if (row == null) {
			return null;
		}
		User user = new User();
		user.setUsername(row.getString("username"));
		user.setEmail(row.getString("email"));
		user.setType(UserType.valueOf(row.getString("type")));
		user.setSupervisor(row.getString("supervisor"));
		user.setDephead(row.getString("dephead"));
		
		return user;
		
		
	}
	
	@Override
	public void updateUser(User user) {
		String query = "Update user set type = ?, email = ?, inbox?, supervisor = ?, dephead = ? where username = ?";
		List<UUID> inbox = user.getInbox()
				.stream()
				.filter(notification -> notification!=null)
				.map(notification -> notification.getId())
				.collect(Collectors.toList());
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(user.getType().toString(), user.getEmail(), inbox, user.getSupervisor(), user.getDephead());
		session.execute(bound);
	}
	
	@Override
	public List<UUID> getUserInbox(String username) {
		
		// CHANGE TO THE GACHADAOIMPL GETGACHA RULES
		String query = "Select inbox from user where username=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			return null;
		}
		List<UUID> inbox = row.getList("inbox", UUID.class);
		return inbox;
	}

}

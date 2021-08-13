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
import com.revature.beans.Notification;
import com.revature.beans.User;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;
import com.revature.beans.UserType;

@Log
public class UserDaoImpl implements UserDao {
	
	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	@Override
	public void addUser(User user) {
		String query = "Insert into user (username, email, type, supervisor, dephead, inbox, availableFunds, usedFunds, pendingFunds) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(user.getUsername(), user.getEmail(), user.getType().toString(), user.getSupervisor(), user.getDephead(), user.getInbox(),
						1000l, 0l, 0l);
		session.execute(bound);
	}
	
	@Override
	public List<User> getUsers() {
		String query = "Select * from user";
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
			user.setAvailableFunds(row.getLong("availableFunds"));
			user.setUsedFunds(row.getLong("usedFunds"));
			user.setPendingFunds(row.getLong("pendingFunds"));
			
			users.add(user);
		});
		 
		return users;
		
	}
	
	@Override
	public User getUser(String username) {
		String query = "Select * from user where username=?";
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
		user.setAvailableFunds(row.getLong("availableFunds"));
		user.setUsedFunds(row.getLong("usedFunds"));
		user.setPendingFunds(row.getLong("pendingFunds"));
		
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
	public List<Notification> getUserInbox(String username) {
		
		List<Notification> notifs = new ArrayList<Notification>();
		String query = "Select id, receiver, message, sentDate from notification where receiver=?";
		SimpleStatement simple = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(simple).bind(username);
		
		ResultSet resultSet = session.execute(bound);
		
		resultSet.forEach(row -> {
			Notification notif = new Notification();
			notif.setId(row.getUuid("id"));
			notif.setReciever(row.getString("receiver"));
			notif.setMessage(row.getString("message"));
			notif.setSentDate(row.getLocalDate("sentDate"));
			
			notifs.add(notif);
		});
		
		return notifs;
		
	}
	
	@Override
	public void changeUsedAmount(User user) {
		String query = "Update user set usedFunds = ? where username = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(user.getUsedFunds(), user.getUsername());
		session.execute(bound);
	}
	
	@Override
	public void changePendingAmount(User user) {
		String query = "Update user set pendingFunds = ? where username = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(user.getPendingFunds(), user.getUsername());
		session.execute(bound);
	}
	
	@Override
	public void changeAvailableAmount(User user) {
		String query = "Update user set availableFunds = ? where username = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(user.getAvailableFunds(), user.getUsername());
		session.execute(bound);
	}
	
	

}

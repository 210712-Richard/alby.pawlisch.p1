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
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class NotificationDaoImpl implements NotificationDao {

	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	@Override
	public void addNotif(Notification notif) {
		String query = "Insert into notification (receiver, message, sentDate) values (?, ?, ?);";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple)
				.bind(notif.getReciever(), notif.getMessage(), notif.getSentDate());
		session.execute(bound);
		
	}
	
	@Override
	public List<Notification> getNotifs() {
		List<Notification> notifs = new ArrayList<Notification>();
		String query = "Select id, reciever, message, sentDate from notification";
		ResultSet resultSet = session.execute(new SimpleStatementBuilder(query).build());
		
		resultSet.forEach(row -> {
			Notification notif = new Notification();
			notif.setId(row.getUuid("id"));
			notif.setReciever(row.getString("receiver"));
			notif.setMessage(row.getString("message"));
			notif.setSentDate(row.getLocalDate("sentdate"));
			
			notifs.add(notif);
			
		});
		return notifs;
		
	}
	
	@Override
	public Notification getNotifById(UUID id) {
		String query = "Select id, reciever, message, sentDate from notification where id = ?";
		BoundStatement bound = session.prepare(new SimpleStatementBuilder(query).build()).bind(id);
		ResultSet resultSet = session.execute(bound);
		
		Row row = resultSet.one();
		if (row == null) {
			return null;
		}
		Notification notif = new Notification();
		notif.setId(row.getUuid("id"));
		notif.setReciever(row.getString("receiver"));
		notif.setMessage(row.getString("message"));
		notif.setSentDate(row.getLocalDate("sentdate"));
		
		return notif;
		
	}
	
	
	public void deleteNotif(Notification notif) {
		String query = "Delete from notification where id = ?";
		BoundStatement bound = session
				.prepare(new SimpleStatementBuilder(query)
						.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
						.build())
				.bind(notif.getId());
		session.execute(bound);
	}
	
	
}

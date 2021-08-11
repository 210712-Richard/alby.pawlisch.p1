package com.revature.data;

import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.ExceedFunds;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class ExceedFundsDaoImpl implements ExceedFundsDao {
	
	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	// add method
	@Override
	public void addExceedFunds(ExceedFunds exceed) {
		String query = "Insert into exceedFunds (id, reimburseId, amount, reason, bencoName)"
				+ " values (?, ?, ?, ?, ?);";
		UUID id = UUID.randomUUID();
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple)
				.bind(id, exceed.getReimburseId(), exceed.getAmount(), exceed.getReason(), exceed.getBencoName());
		session.execute(bound);
	}
	
	// delete method
	@Override
	public void delete(UUID id) {
		String query = "Delete from exceedFunds where id = ?";
		BoundStatement bound = session
				.prepare(new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build())
				.bind(id);
		session.execute(bound);
		
		
	}
	
	// view/get method
	@Override
	public ExceedFunds viewExceedFunds(UUID id) {
		String query = "Select * from exceedFunds where id = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple).bind(id);
		ResultSet results = session.execute(bound);
		Row row = results.one();
		if (row == null) {
			return null;
		}
		
		ExceedFunds exceed = new ExceedFunds();
		exceed.setId(row.getUuid("id"));
		exceed.setReimburseId(row.getUuid("reimburseId"));
		exceed.setAmount(row.getLong("amount"));
		exceed.setReason(row.getString("reason"));
		exceed.setBencoName(row.getString("bencoName"));
		
		return exceed;
	}
	

}

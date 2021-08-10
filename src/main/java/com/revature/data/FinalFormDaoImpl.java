package com.revature.data;

import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.FinalForm;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class FinalFormDaoImpl implements FinalFormDao {
	
	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	// creation method
	@Override
	public void addFinalForm(FinalForm finalForm) {
		String query = "Insert into finalForm (id, employee, approved, submissionDate,"
				+ " formType, urgent, filename) values (?, ?, ?, ?, ?, ?, ?);";
		UUID id = UUID.randomUUID();
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple)
				.bind(id, finalForm.getEmployee(), finalForm.getApproved(), finalForm.getSubmissionDate(),
						finalForm.getFormType(), finalForm.getUrgent(), finalForm.getFilename());
		session.execute(bound);
	}
	
	// get by id method
	@Override
	public void getFinalById(FinalForm finalForm) {
		String query = "Select * from finalForm where id = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(finalForm.getId());
		session.execute(bound);
	}
	
	// upload file/update method
	@Override
	public void updateFile(FinalForm finalForm) {
		String query = "update finalForm set filename = ? where id = ?;";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(finalForm.getFilename(), finalForm.getId());
		session.execute(bound);
	}
	
	// approval/update method
	@Override
	public void updateApproval(FinalForm finalForm) {
		String query = "update finalForm set approved = ? where id = ?;";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(finalForm.getApproved(), finalForm.getId());
		session.execute(bound);
	}
	
	@Override
	public void deleteFinalForm(UUID id) {
		String query = "Delete from finalForm where id = ?";
		BoundStatement bound = session
				.prepare(new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build())
				.bind(id);
		session.execute(bound);
	}

}

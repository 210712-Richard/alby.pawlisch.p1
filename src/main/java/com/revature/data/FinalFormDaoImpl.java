package com.revature.data;

import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.FinalForm;
import com.revature.util.CassandraUtil;

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
	
	// upload file/update method
	
	// approval/update method

}

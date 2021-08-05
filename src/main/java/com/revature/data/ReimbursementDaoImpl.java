package com.revature.data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;
import com.revature.beans.Reimbursement;

import io.javalin.http.Context;

@Log
public class ReimbursementDaoImpl implements ReimbursementDao {

	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	@Override
	public void addReimbursement(Reimbursement reimbursement) {
		/*
		String query = "Insert into user (username, email, type, supervisor, dephead, inbox) values (?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(user.getUsername(), user.getEmail(), user.getType().toString(), user.getSupervisor(), user.getDephead(), user.getInbox());
		session.execute(bound);
		*/
		
		StringBuilder bigQuery = new StringBuilder ("Insert into reimbursement (employee, reimburseForm, approvedEmail, ")
				.append("submissionDate, lastApprovalDate, superApproval, headApproval, ")
				.append("bencoApproval, urgent, requestAmount, approvedAmount) ")
				.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		String query = bigQuery.toString();
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple)
				.bind(reimbursement.getEmployee(), reimbursement.getReimburseForm(), reimbursement.getApprovedEmail(),
						reimbursement.getSubmissionDate(), reimbursement.getLastApprovalDate(), reimbursement.getSuperApproval(),
						reimbursement.getHeadApproval(), reimbursement.getBencoApproval(), reimbursement.getUrgent(),
						reimbursement.getRequestAmount(), reimbursement.getApprovedAmount());
		session.execute(bound);
		
		
	}
	

}

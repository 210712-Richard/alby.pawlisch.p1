package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
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
	
	@Override
	public List<Reimbursement> getEmployeeReimbursements(String employeeName) {
		String query = "Select employee, reimburseForm, id from reimbursement where employee = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple).bind(employeeName);
		ResultSet results = session.execute(bound);
		
		List<Reimbursement> reimbursements = new ArrayList<>();
		results.forEach(row ->{
			Reimbursement reimbursement = new Reimbursement();
			reimbursement.setEmployee(row.getString("employee"));
			reimbursement.setReimburseForm(row.getString("reimburseForm"));
			reimbursement.setId(row.getUuid("id"));
			
			reimbursements.add(reimbursement);
			
		});
		
		return reimbursements;
		
	}
	
	@Override
	public Boolean checkFileNameAvailability(String key) {
		Boolean available;
		
		String query = "Select employee, reimburseForm, id from reimbursement where reimburseForm = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple).bind(key);
		ResultSet results = session.execute(bound);
		Row row = results.one();
		if(row == null) {
			available = true;
			
		} else {
			available = false;
		}
		
		return available;
	}
	
	public Reimbursement getReimbursementById(UUID id) {
		String query = "Select * from reimbursement where id = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple).bind(id);
		ResultSet results = session.execute(bound);
		Row row = results.one();
		if (row == null) {
			return null;
		}
		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setEmployee(row.getString("employee"));
		reimbursement.setReimburseForm(row.getString("reimburseForm"));
		reimbursement.setApprovedEmail(row.getString("approvedEmail"));
		reimbursement.setSubmissionDate(row.getLocalDate("submissionDate"));
		reimbursement.setLastApprovalDate(row.getLocalDate("lastApprovalDate"));
		reimbursement.setSuperApproval(row.getBoolean("superApproval"));
		reimbursement.setHeadApproval(row.getBoolean("headApproval"));
		reimbursement.setBencoApproval(row.getBoolean("bencoApproval"));
		reimbursement.setUrgent(row.getBoolean("urgent"));
		reimbursement.setRequestAmount(row.getLong("requestAmount"));
		reimbursement.setApprovedAmount(row.getLong("approvedAmount"));
		
		return reimbursement;
		
	}
	

}

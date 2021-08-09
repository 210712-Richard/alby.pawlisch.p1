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


@Log
public class ReimbursementDaoImpl implements ReimbursementDao {

	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	@Override
	public void addReimbursement(Reimbursement reimbursement) {
		String query = "Insert into reimbursement (id, employee, reimburseForm, approvedEmail, "
				+ "submissionDate, lastApprovalDate, superApproval, headApproval, bencoApproval, urgent, requestAmount, approvedAmount) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		UUID id = UUID.randomUUID();
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple)
				.bind(id, reimbursement.getEmployee(), reimbursement.getReimburseForm(), reimbursement.getApprovedEmail(),
						reimbursement.getSubmissionDate(), reimbursement.getLastApprovalDate(), reimbursement.getSuperApproval(),
						reimbursement.getHeadApproval(), reimbursement.getBencoApproval(), reimbursement.getUrgent(),
						reimbursement.getRequestAmount(), reimbursement.getApprovedAmount());
		session.execute(bound);
		
	}
	
	@Override
	public List<Reimbursement> getEmployeeReimbursements(String employeeName) {
		String query = "Select * from reimbursement where employee = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple).bind(employeeName);
		ResultSet results = session.execute(bound);
		
		List<Reimbursement> reimbursements = new ArrayList<>();
		results.forEach(row ->{
			Reimbursement reimbursement = new Reimbursement();
			reimbursement.setId(row.getUuid("id"));
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
			
			reimbursements.add(reimbursement);
			
		});
		
		return reimbursements;
		
	}
	
	
	@Override
	public Reimbursement getReimbursementById(UUID id, String employee) {
		String query = "Select * from reimbursement where employee = ? and id = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(simple).bind(employee, id);
		ResultSet results = session.execute(bound);
		Row row = results.one();
		if (row == null) {
			return null;
		}
		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setId(row.getUuid("id"));
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
	
	@Override
	public void deleteReimbursement(UUID id, String employee) {
		String query = "Delete from reimbursement where employee = ? and id = ?";
		BoundStatement bound = session
				.prepare(new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build())
				.bind(employee, id);
		session.execute(bound);

	}
	
	@Override
	public void updateEmail(Reimbursement reimbursement) {
		// changes/adds approved email
		// sets superApproval to True
		String query = "update reimbursement set approvedEmail = ?, superApproval = ? where employee =? and id=?;";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(reimbursement.getApprovedEmail(), reimbursement.getSuperApproval(), reimbursement.getEmployee(), reimbursement.getId());
		session.execute(bound);
	}
	
	@Override
	public void updateSuperApproval(Reimbursement reimbursement) {
		// sets superApproval
		// sets lastApprovalDate
		String query = "update reimbursement set superApproval = ?, lastApprovalDate = ? where employee =? and id=?;";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(reimbursement.getSuperApproval(), reimbursement.getLastApprovalDate(),
				reimbursement.getEmployee(), reimbursement.getId());
		session.execute(bound);
				
	}
	
	@Override
	public void updateDepheadApproval(Reimbursement reimbursement) {
		// sets headApproval
		// sets lastApprovalDate
		String query = "update reimbursement set headApproval = ?, lastApprovalDate = ? where employee =? and id=?;";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(reimbursement.getHeadApproval(), reimbursement.getLastApprovalDate(),
				reimbursement.getEmployee(), reimbursement.getId());
		session.execute(bound);
	}
	
	@Override
	public void updateBencoApproval(Reimbursement reimbursement) {
		// sets bencoApproval
		// sets lastApprovalDate
		// sets approvedAmount
		String query = "update reimbursement set bencoApproval = ?, lastApprovalDate = ?, approvedAmount = ? where employee =? and id=?;";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(reimbursement.getBencoApproval(), reimbursement.getLastApprovalDate(),
				reimbursement.getApprovedAmount(), reimbursement.getEmployee(), reimbursement.getId());
		session.execute(bound);
	}
	
	
	

}

package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.FinalForm;
import com.revature.beans.FormType;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class FinalFormDaoImpl implements FinalFormDao {
	
	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	// creation method
	@Override
	public void addFinalForm(FinalForm finalForm) {
		String query = "Insert into finalForm (id, reimburseId, employee, approved, submissionDate,"
				+ " formType, urgent, filename) values (?, ?, ?, ?, ?, ?, ?, ?);";
		UUID id = UUID.randomUUID();
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple)
				.bind(id, finalForm.getReimburseId(), finalForm.getEmployee(), finalForm.getApproved(), finalForm.getSubmissionDate(),
						finalForm.getFormType(), finalForm.getUrgent(), finalForm.getFilename());
		session.execute(bound);
	}
	
	// get by id method
	@Override
	public FinalForm getFinalById(FinalForm finalForm) {
		String query = "Select * from finalForm where employee =? and id = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(finalForm.getEmployee(), finalForm.getId());
		ResultSet results = session.execute(bound);
		Row row = results.one();
		if (row == null) {
			return null;
		}
		
		FinalForm form = new FinalForm();
		form.setId(row.getUuid("id"));
		form.setReimburseId(row.getUuid("reimburseId"));
		form.setEmployee(row.getString("employee"));
		form.setApproved(row.getBoolean("approved"));
		form.setSubmissionDate(row.getLocalDate("submissionDate"));
		form.setFormType(FormType.valueOf(row.getString("formType")));
		form.setUrgent(row.getBoolean("urgent"));
		form.setFilename(row.getString("filename"));
		
		return form;
	}
	
	// get by id and employee
	@Override
	public List<FinalForm> getFinalsByEmployee(String employee) {
		String query = "Select * from finalForm where employee = ?";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(employee);
		ResultSet results = session.execute(bound);
		
		List<FinalForm> forms = new ArrayList<>();
		results.forEach(row ->{
			FinalForm form = new FinalForm();
			form.setId(row.getUuid("id"));
			form.setReimburseId(row.getUuid("reimburseId"));
			form.setEmployee(row.getString("employee"));
			form.setApproved(row.getBoolean("approved"));
			form.setSubmissionDate(row.getLocalDate("submissionDate"));
			form.setFormType(FormType.valueOf(row.getString("formType")));
			form.setUrgent(row.getBoolean("urgent"));
			form.setFilename(row.getString("filename"));
			
			forms.add(form);
		});
		
		return forms;
		
	}
	
	// upload file/update method
	@Override
	public void updateFile(FinalForm finalForm) {
		String query = "update finalForm set filename = ? where employee = ? and id = ?;";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(finalForm.getFilename(), finalForm.getEmployee(), finalForm.getId());
		session.execute(bound);
	}
	
	// approval/update method
	@Override
	public void updateApproval(FinalForm finalForm) {
		String query = "update finalForm set approved = ? where employee = ? and id = ?;";
		SimpleStatement simple = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(simple).bind(finalForm.getApproved(), finalForm.getEmployee(), finalForm.getId());
		session.execute(bound);
	}
	
	@Override
	public void deleteFinalForm(FinalForm finalForm) {
		String query = "Delete from finalForm where employee = ? and id = ?";
		BoundStatement bound = session
				.prepare(new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build())
				.bind(finalForm.getEmployee(), finalForm.getId());
		session.execute(bound);
	}

}

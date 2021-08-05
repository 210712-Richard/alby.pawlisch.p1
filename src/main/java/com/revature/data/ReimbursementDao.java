package com.revature.data;

import java.util.List;

import com.revature.beans.Reimbursement;

import io.javalin.http.Context;

public interface ReimbursementDao {
	

	void addReimbursement(Reimbursement reimbursement);

	List<Reimbursement> getEmployeeReimbursements(String employeeName);

	Boolean checkFileNameAvailability(String key);

}

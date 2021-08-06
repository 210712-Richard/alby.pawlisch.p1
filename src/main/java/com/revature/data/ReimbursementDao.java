package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Reimbursement;


public interface ReimbursementDao {
	

	void addReimbursement(Reimbursement reimbursement);

	List<Reimbursement> getEmployeeReimbursements(String employeeName);

	Boolean checkFileNameAvailability(String key);

	Reimbursement getReimbursementById(UUID id, String employee);


}

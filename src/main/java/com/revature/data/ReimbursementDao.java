package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Reimbursement;


public interface ReimbursementDao {
	

	void addReimbursement(Reimbursement reimbursement);

	List<Reimbursement> getEmployeeReimbursements(String employeeName);

	Reimbursement getReimbursementById(UUID id, String employee);

	void deleteReimbursement(UUID id, String employee);
	


}

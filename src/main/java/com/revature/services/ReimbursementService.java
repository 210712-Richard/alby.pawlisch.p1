package com.revature.services;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Reimbursement;

public interface ReimbursementService {

	Reimbursement apply(String reimburseForm, String loggedUser, Long requestAmount);

	List<Reimbursement> viewReimbursementsFromEmployee(String employee);

	Reimbursement viewOneReimbursement(UUID id, String employee);

	void deleteReimbursement(UUID id, String employee);

	void emailApprove(Reimbursement reimbursement);

	void updateSuperApproval(Reimbursement reimbursement, String employee, UUID id);

	void updateDepheadApproval(Reimbursement reimbursement, String employee, UUID id);
	
	void depheadIsSuper(Reimbursement reimbursement, String employee, UUID id);

	void updateBencoApproval(Reimbursement reimbursement, String employee, UUID id);

	

}

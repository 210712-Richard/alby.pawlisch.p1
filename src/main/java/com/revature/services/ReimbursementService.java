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

	void updateSuperApproval(Reimbursement reimbursement);


	void updateDepheadApproval(Reimbursement reimbursement);


	void updateBencoApproval(Reimbursement reimbursement);

}

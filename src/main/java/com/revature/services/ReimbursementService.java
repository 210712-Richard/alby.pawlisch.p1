package com.revature.services;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Reimbursement;

public interface ReimbursementService {

	Reimbursement apply(String reimburseForm, String loggedUser, Long requestAmount);

	Reimbursement emailApprove();

	List<Reimbursement> viewReimbursementsFromEmployee(String employee);

	Reimbursement viewOneReimbursement(UUID id, String employee);


}

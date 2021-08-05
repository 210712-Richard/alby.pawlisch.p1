package com.revature.services;

import com.revature.beans.Reimbursement;

public interface ReimbursementService {

	Reimbursement apply(String reimburseForm, String loggedUser, Long requestAmount);

	Reimbursement emailApprove();

}

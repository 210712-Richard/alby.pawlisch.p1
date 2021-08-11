package com.revature.services;

import java.util.UUID;

import com.revature.beans.ExceedFunds;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;

public interface ExceedFundsService {

	void add(Reimbursement reimbursement, String reason, User loggedUser);

	ExceedFunds getExceed(UUID id);

	void delete(UUID id);
	
	

}

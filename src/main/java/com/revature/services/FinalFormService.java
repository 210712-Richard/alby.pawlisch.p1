package com.revature.services;

import java.util.List;
import java.util.UUID;

import com.revature.beans.FinalForm;
import com.revature.beans.FormType;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;

public interface FinalFormService {

	FinalForm add(Reimbursement reimbursement, FormType formType);

	List<FinalForm> getByEmployee(String employee);

	FinalForm getById(String employee, UUID id);
	
	void updateFile(FinalForm finalForm);

	void updateApproval(FinalForm finalForm);

	void deleteFinalForm(FinalForm finalForm);

	Boolean isAllowed(FinalForm finalForm, User loggedUser);

	

}

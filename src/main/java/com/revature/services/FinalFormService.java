package com.revature.services;

import java.util.List;

import com.revature.beans.FinalForm;
import com.revature.beans.FormType;
import com.revature.beans.Reimbursement;

public interface FinalFormService {

	FinalForm add(Reimbursement reimbursement, FormType formType);

	List<FinalForm> getByEmployee(String employee);

	FinalForm getById(FinalForm form);
	
	void updateFile(FinalForm finalForm);

	void updateApproval(FinalForm finalForm);

	void deleteFinalForm(FinalForm finalForm);

	

}

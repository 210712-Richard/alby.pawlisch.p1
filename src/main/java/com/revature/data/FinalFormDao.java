package com.revature.data;


import java.util.List;
import java.util.UUID;

import com.revature.beans.FinalForm;

public interface FinalFormDao {

	void addFinalForm(FinalForm finalForm);

	FinalForm getFinalById(String employee, UUID id);
	
	List<FinalForm> getFinalsByEmployee(String employee);

	void updateFile(FinalForm finalForm);

	void updateApproval(FinalForm finalForm);

	void deleteFinalForm(FinalForm finalForm);


}

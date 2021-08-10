package com.revature.data;


import java.util.List;

import com.revature.beans.FinalForm;

public interface FinalFormDao {

	void addFinalForm(FinalForm finalForm);

	FinalForm getFinalById(FinalForm finalForm);
	
	 List<FinalForm> getFinalsByEmployee(String employee);

	void updateFile(FinalForm finalForm);

	void updateApproval(FinalForm finalForm);

	void deleteFinalForm(FinalForm finalForm);


}

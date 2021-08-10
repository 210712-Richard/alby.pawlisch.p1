package com.revature.data;

import java.util.UUID;

import com.revature.beans.FinalForm;

public interface FinalFormDao {

	void addFinalForm(FinalForm finalForm);

	void getFinalById(FinalForm finalForm);

	void updateFile(FinalForm finalForm);

	void updateApproval(FinalForm finalForm);

	void deleteFinalForm(UUID finalId);

}

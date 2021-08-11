package com.revature.data;

import java.util.UUID;

import com.revature.beans.ExceedFunds;

public interface ExceedFundsDao {

	void addExceedFunds(ExceedFunds exceed);

	ExceedFunds viewExceedFunds(UUID id);
	
	void delete(UUID id);

}

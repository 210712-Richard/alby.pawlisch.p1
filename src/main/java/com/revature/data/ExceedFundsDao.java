package com.revature.data;

import java.util.UUID;

import com.revature.beans.ExceedFunds;

public interface ExceedFundsDao {

	void addExceedFunds(ExceedFunds exceed);

	void delete(UUID id);

	ExceedFunds viewExceedFunds(UUID id);

}

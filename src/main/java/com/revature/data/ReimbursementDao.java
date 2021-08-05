package com.revature.data;

import com.revature.beans.Reimbursement;

import io.javalin.http.Context;

public interface ReimbursementDao {
	

	void addReimbursement(Reimbursement reimbursement);

}

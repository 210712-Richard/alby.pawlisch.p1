package com.revature.controllers;

import io.javalin.http.Context;

public interface ReimbursementController {

	void addReimbursement(Context ctx);

	void getOneReimbursement(Context ctx);

	void getEmployeeReimbursements(Context ctx);

	void getForm(Context ctx);

	void deleteReimbursement(Context ctx);

}

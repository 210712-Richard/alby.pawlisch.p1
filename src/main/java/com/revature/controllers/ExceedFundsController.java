package com.revature.controllers;

import io.javalin.http.Context;

public interface ExceedFundsController {

	void getExceedFunds(Context ctx);

	void getAllExceedFunds(Context ctx);

}

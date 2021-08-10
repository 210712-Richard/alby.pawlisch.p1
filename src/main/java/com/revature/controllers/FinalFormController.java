package com.revature.controllers;

import io.javalin.http.Context;

public interface FinalFormController {

	void employeeForms(Context ctx);

	void getForm(Context ctx);

	void submitFile(Context ctx);

	void getFile(Context ctx);

	void approval(Context ctx);

}

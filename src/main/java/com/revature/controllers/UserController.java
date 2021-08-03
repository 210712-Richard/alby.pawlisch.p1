package com.revature.controllers;

import io.javalin.http.Context;

public interface UserController {
	
	public void login(Context ctx);

	void logout(Context ctx);

	void inbox(Context ctx);

}

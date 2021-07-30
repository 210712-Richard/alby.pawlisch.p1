package com.revature;

import io.javalin.Javalin;

public class Driver {
	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(8080);
		
		app.get("/", (ctx)->ctx.html("Hello Project 1"));
	}
	
	

}

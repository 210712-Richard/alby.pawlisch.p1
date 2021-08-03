package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.UserController;
import com.revature.controllers.UserControllerImpl;
import com.revature.factory.BeanFactory;
import com.revature.util.CassandraUtil;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

public class Driver {
	public static void main(String[] args) {
		
		//dbtest();
		//dbtestBoogaloo();
		//instantiateDatabase();
		javalin();
		
	}
	
	private static void dbtestBoogaloo() {
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS Test;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
	}
	
	private static void dbtest() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS Test(")
				.append("username text PRIMARY KEY, type text, id int, currency varint, ")
				.append("birthday date, lastCheckIn date, email text );");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
	}
	
	public static void instantiateDatabase() {
		DataBaseCreator.dropTables();
		try {
			Thread.sleep(20000); // wait 20 seconds
		} catch(Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.createTables();
		try {
			Thread.sleep(10000); // wait 10 seconds
		} catch(Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.populateUserTable();
		System.exit(0);
	}
	
	public static void javalin() {
		
		// set up LocalDates
		ObjectMapper jackson = new ObjectMapper();
		jackson.registerModule(new JavaTimeModule());
		jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavalinJackson.configure(jackson);
		
		// start javalin
		Javalin app = Javalin.create().start(8080);
		
		UserController userControl = (UserController) BeanFactory.getFactory().get(UserController.class, UserControllerImpl.class);
		app.get("/", (ctx)->ctx.html("Hello Project 1"));
		
		app.post("/users", userControl::login);
		app.delete("/users", userControl::logout);
		app.get("/users/inbox", userControl::inbox);

	}
	
	

}

package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.ReimbursementController;
import com.revature.controllers.ReimbursementControllerImpl;
import com.revature.controllers.UserController;
import com.revature.controllers.UserControllerImpl;
import com.revature.factory.BeanFactory;
import com.revature.util.CassandraUtil;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

public class Driver {
	public static void main(String[] args) {
		
		//instantiateDatabase();
		//DataBaseCreator.dropTables();
		//DataBaseCreator.createTables();
		//DataBaseCreator.populateUserTable();
		javalin();
		
	}
	
	
	public static void instantiateDatabase() {
		DataBaseCreator.dropTables();
		try {
			Thread.sleep(30000); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.createTables();
		try {
			Thread.sleep(30000); 
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
		ReimbursementController reimbursementControl = (ReimbursementController) BeanFactory.getFactory().get(ReimbursementController.class, ReimbursementControllerImpl.class);
		app.get("/", (ctx)->ctx.html("Hello Project 1"));
		
		// login
		app.post("/login", userControl::login);
		
		// logout
		app.delete("/login", userControl::logout);
		
		// check inbox
		app.get("/inbox", userControl::inbox);
		
		// submit file
		// put requested amount into Reimburse-Amount header
		// file into body - binary
		// put extension name into Extension header
		app.put("/reimbursement", reimbursementControl::addReimbursement);

		// get reimbursements from a specific employee
		app.get("/reimbursement/:employee", reimbursementControl::getEmployeeReimbursements);

		// get a specific reimbursement
		app.get("/reimbursement/:employee/:reimburseId", reimbursementControl::getOneReimbursement);
		
		// delete a reimbursement
		app.delete("/reimbursement/:employee/:reimburseId", reimbursementControl::deleteReimbursement);
		
		// get a form
		app.get("/reimbursement/download/:employee/:reimburseId", reimbursementControl::getForm);
		

	}
	
	

}

package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.ExceedFundsController;
import com.revature.controllers.ExceedFundsControllerImpl;
import com.revature.controllers.FinalFormController;
import com.revature.controllers.FinalFormControllerImpl;
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
			Thread.sleep(40000); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.createTables();
		try {
			Thread.sleep(40000); 
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
		FinalFormController finalControl = (FinalFormController) BeanFactory.getFactory().get(FinalFormController.class, FinalFormControllerImpl.class);
		ExceedFundsController exceedControl = (ExceedFundsController) BeanFactory.getFactory().get(ExceedFundsController.class, ExceedFundsControllerImpl.class);
		//app.get("/", (ctx)->ctx.html("Hello Project 1"));
		
		// login
		app.post("/users", userControl::login);
		
		// logout
		app.delete("/users", userControl::logout);
		
		// check inbox
		app.get("/notifications", userControl::inbox);
		
		// submit file
		// put requested amount into Reimburse-Amount header
		// file into body - binary
		// put extension name into Extension header
		app.put("/reimbursements", reimbursementControl::addReimbursement);

		// get reimbursements from a specific employee
		app.get("/reimbursements/:employee", reimbursementControl::getEmployeeReimbursements);

		// get a specific reimbursement
		app.get("/reimbursements/:employee/:reimburseId", reimbursementControl::getOneReimbursement);
		
		// delete a reimbursement
		app.delete("/reimbursements/:employee/:reimburseId", reimbursementControl::deleteReimbursement);
		
		// get a form
		app.get("/reimbursements/:employee/:reimburseId/files", reimbursementControl::getForm);

		// submit an approval email
		app.put("/reimbursements/:employee/:reimburseId/email", reimbursementControl::approvalEmail);
		
		// submit supervisor or dephead approval
		app.put("/reimbursements/:employee/:reimburseId/approval", reimbursementControl::regularApproval);
		
		// submit benco approval
		// header for formtype (grade or presentation)
		// body has approval status and approved amount
		// header for reason if approved amount exceeds requested
		app.put("/reimbursements/:employee/:reimburseId/approval/benefits", reimbursementControl::bencoApproval);
		
		// view final forms from an employee
		app.get("/finalforms/:employee", finalControl::employeeForms);
		
		// view one final form
		app.get("/finalforms/:employee/:formId", finalControl::getForm);
		
		// submit a file to a final form
		app.put("/finalforms/:employee/:formId/files", finalControl::submitFile);
		
		// get/download a file
		app.get("/finalforms/:employee/:formId/files", finalControl::getFile);
		
		// change approval status on final
		app.put("/finalforms/:employee/:formId/approval", finalControl::approval);
		
		// get an exceed funds thing
		app.get("/exceedsfunds/:fundsId", exceedControl::getExceedFunds);
		
		// get all exceeded funds
		app.get("/exceedsfunds", exceedControl::getAllExceedFunds);
		
	}
	
	

}

package com.revature.controllers;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.util.S3Util;

import io.javalin.http.Context;

@Log
public class ReimbursementControllerImpl implements ReimbursementController {
	
	private ReimbursementService reimburseService = (ReimbursementService) BeanFactory.getFactory().get(ReimbursementService.class,
			ReimbursementServiceImpl.class);
	private UserService userService = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
	private static Logger log = LogManager.getLogger(ReimbursementControllerImpl.class);
	
	
	@Override
	public void addReimbursement(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		// LATER
		// CHECK THAT EMPLOYEE HASNT SUBMITTED OTHER REIMBURSEMENTS
		String employee = loggedUser.getUsername();
		Long requestAmount = Long.parseLong(ctx.header("Reimburse-Amount"));
		
		// file time
		String filetype = ctx.header("Extension");
		if (filetype == null) {
			ctx.status(400);
			return;
		}
		
		String formName = employee + "Reimbursement";
		String key = formName + "." + filetype;
		
		S3Util.getInstance().uploadToBucket(key, ctx.bodyAsBytes());
		
		Reimbursement newReimbursement = reimburseService.apply(key, employee, requestAmount);
		
		if(newReimbursement != null) {
			ctx.status(201);
			ctx.json(newReimbursement);
		} else {
			ctx.html("Unable to make reimbursement request");
		}
		
	}
	
	@Override
	public void getOneReimbursement(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		String employee = ctx.pathParam("employee");
		UUID reimburseId = UUID.fromString(ctx.pathParam("reimburseId"));
		String loggedUsername = loggedUser.getUsername();
		
		
		Reimbursement reimbursement = reimburseService.viewOneReimbursement(reimburseId, employee);
		Boolean allowedAccess = userService.allowedAccess(loggedUsername, employee);
		if(employee == null) {
			ctx.status(404);
			ctx.html("Employee does not exist");
		}
		if(reimbursement == null) {
			ctx.status(404);
			ctx.html("Reimbursement does not exist.");
		}
		
		if(loggedUsername.equals(reimbursement.getEmployee()) || allowedAccess == true){
			ctx.json(reimbursement);
		} else {
			ctx.status(403);
		}
				
	
	}
	
	@Override
	public void getEmployeeReimbursements(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		String employee = ctx.pathParam("employee");
		String loggedUsername = loggedUser.getUsername();
		
		
		List<Reimbursement> reimbursements = reimburseService.viewReimbursementsFromEmployee(employee);
		Boolean allowedAccess = userService.allowedAccess(loggedUsername, employee);
		
		if(userService.getUser(employee) == null) {
			ctx.status(404);
			ctx.html("Employee does not exist");
		}
		
		if(loggedUsername.equals(employee) || allowedAccess == true){
			ctx.json(reimbursements);
		} else {
			ctx.status(403);
		}
	}
	
	@Override
	public void getForm(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		String employee = ctx.pathParam("employee");
		UUID reimburseId = UUID.fromString(ctx.pathParam("reimburseId"));
		String loggedUsername = loggedUser.getUsername();
		
		
		Reimbursement reimbursement = reimburseService.viewOneReimbursement(reimburseId, employee);
		Boolean allowedAccess = userService.allowedAccess(loggedUsername, employee);
		if(employee == null) {
			ctx.status(404);
			ctx.html("Employee does not exist");
		}
		if(reimbursement == null) {
			ctx.status(404);
			ctx.html("Reimbursement does not exist.");
		}
		
		if(loggedUsername.equals(employee) || allowedAccess.equals(true)){
			try {
				InputStream form = S3Util.getInstance().getObject(reimbursement.getReimburseForm());
				ctx.result(form);
			} catch (Exception e) {
				ctx.status(500);
			}
		} else {
			ctx.status(403);
		}
	}
	
	@Override
	public void deleteReimbursement(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		String employee = ctx.pathParam("employee");
		UUID reimburseId = UUID.fromString(ctx.pathParam("reimburseId"));
		
		if(employee.equals(loggedUser.getUsername())){
			reimburseService.deleteReimbursement(reimburseId, employee);
		} else {
			ctx.status(403);
		}
	}
	

}

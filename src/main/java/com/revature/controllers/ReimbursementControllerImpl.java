package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementServiceImpl;
import com.revature.util.S3Util;

import io.javalin.http.Context;

@Log
public class ReimbursementControllerImpl implements ReimbursementController {
	
	private ReimbursementService reimburseService = (ReimbursementService) BeanFactory.getFactory().get(ReimbursementService.class,
			ReimbursementServiceImpl.class);
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
		String key = formName+"."+filetype;
		S3Util.getInstance().uploadToBucket(key, ctx.bodyAsBytes());
		
		Reimbursement newReimbursement = reimburseService.apply(formName, employee, requestAmount);
		
		if(newReimbursement != null) {
			ctx.status(201);
			ctx.json(newReimbursement);
		} else {
			ctx.html("Unable to make reimbursement request");
		}
		
	}

}

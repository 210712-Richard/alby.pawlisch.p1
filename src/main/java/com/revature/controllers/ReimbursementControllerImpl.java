package com.revature.controllers;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.FinalForm;
import com.revature.beans.FormType;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.FinalFormService;
import com.revature.services.FinalFormServiceImpl;
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
	private FinalFormService finalService = (FinalFormService) BeanFactory.getFactory().get(FinalFormService.class, FinalFormServiceImpl.class);
	private static Logger log = LogManager.getLogger(ReimbursementControllerImpl.class);
	
	
	@Override
	public void addReimbursement(Context ctx) {
		log.trace("AddReimbursement method called");
		log.debug(ctx.body());
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		String employee = loggedUser.getUsername();
		Long requestAmount = Long.parseLong(ctx.header("Reimburse-Amount"));
		
		// file time
		String filetype = ctx.header("Extension");
		if (filetype == null) {
			ctx.status(400);
			return;
		}
		
		List<Reimbursement> employeeReimbursements = reimburseService.viewReimbursementsFromEmployee(employee);
		Integer count = employeeReimbursements.size() + 1;
		
		String formName = employee + "Reimbursement" + count;
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
		log.trace("Called getOneReimbursement");
		log.debug("Attempting to view reimbursement: " + ctx.pathParam("reimburseId"));
		
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
		log.trace("Called getEmployeeReimbursements");
		log.debug("Viewing reimbursements from: " + ctx.pathParam("employee"));
		
		
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
		log.trace("Called getForm");
		log.debug("Attempting to get reimbursement form: " + ctx.pathParam("reimburseId"));
		
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
		log.trace("Called deleteReimbursement");
		log.debug("Deleting a reimbursement from: " + ctx.pathParam("employee"));
		
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		String employee = ctx.pathParam("employee");
		UUID reimburseId = UUID.fromString(ctx.pathParam("reimburseId"));
		
		if(employee.equals(loggedUser.getUsername())){
			reimburseService.deleteReimbursement(reimburseId, employee);
		} else {
			ctx.status(403);
		}
	}
	
	@Override
	public void approvalEmail(Context ctx) {
		// send email to s3 w/ key
		// build reimbursement and send to service
		log.trace("Called approveEmail");
		
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String employee = ctx.pathParam("employee");
		UUID reimburseId = UUID.fromString(ctx.pathParam("reimburseId"));
		Reimbursement reimbursement = reimburseService.viewOneReimbursement(reimburseId, employee);
		String[] formParts = reimbursement.getReimburseForm().split(".");
		String reimburseFormName = formParts[0];
		
		if(employee.equals(loggedUser.getUsername())){
			
			String filetype = ctx.header("Extension");
			if (filetype == null) {
				ctx.status(400);
				return;
			}
		
			String formName = reimburseFormName + "Email";
			String key = formName + "." + filetype;
			
			S3Util.getInstance().uploadToBucket(key, ctx.bodyAsBytes());
			
			Reimbursement updateReimbursement = new Reimbursement();
			updateReimbursement.setApprovedEmail(key);
			updateReimbursement.setSuperApproval(true);
			updateReimbursement.setEmployee(employee);
			updateReimbursement.setId(reimburseId);
			
			reimburseService.emailApprove(updateReimbursement);
			
			if (updateReimbursement != null) {
				ctx.status(201);
				ctx.json(updateReimbursement);
			}
			
			
		} else {
			ctx.status(403);
		}
		
	}
	
	@Override
	public void regularApproval(Context ctx) {
		// find if loggedUser is supervisor or head
		// build reimbursement and refer to appropriate service
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String employee = ctx.pathParam("employee");
		UUID reimburseId = UUID.fromString(ctx.pathParam("reimburseId"));
		String loggedUsername = loggedUser.getUsername();
		String superNull = userService.getUser(employee).getSupervisor();
		Reimbursement reimburseApprove = ctx.bodyAsClass(Reimbursement.class);
		
		Boolean supervisor = userService.isSupervisor(loggedUsername, employee);
		Boolean dephead = userService.isDephead(loggedUsername, employee);
		
		if (supervisor.equals(true)) {
			// body has approval status
			
			log.trace("Supervisor " + loggedUsername + "edited approval status");
			log.debug(reimburseApprove.getSuperApproval());
			
			reimburseService.updateSuperApproval(reimburseApprove, employee, reimburseId);
			ctx.status(201);
			return;
		}
		if (dephead.equals(true)) {
			
			Reimbursement calledReimbursement = reimburseService.viewOneReimbursement(reimburseId, employee);
			Boolean superApproval = calledReimbursement.getSuperApproval();
			
			if (superApproval.equals(true)) {
				
				log.trace("Department Head " + loggedUsername + "approved reimbursement");
				log.debug(reimburseApprove.getHeadApproval());
				
				reimburseService.updateDepheadApproval(reimburseApprove, employee, reimburseId);
				ctx.status(201);
				return;
				
			} if (superNull.equals(null)) {
				reimburseService.depheadIsSuper(reimburseApprove, employee, reimburseId);
				ctx.status(201);
				return;
			}
			else {
				ctx.status(403);
				if (superApproval.equals(false)) {
					ctx.html("Supervisor has denied reimbursement");
				}
			}
			
			return;
			
		} else {
			ctx.status(403);
		}
		
		
	}
	
	@Override
	public void bencoApproval(Context ctx) {
		// fine if loggedUser is benco
		// build reimbursement and send
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String employee = ctx.pathParam("employee");
		UUID reimburseId = UUID.fromString(ctx.pathParam("reimburseId"));
		
		Boolean benco = userService.isBenco(loggedUser.getUsername());
		if(benco.equals(true)) {
			// calls both finalForm AND reimbursement
			// and eventually, add for exceedingFunds if necessary
			
			// reimbursement setup
			Reimbursement updateReimbursement = ctx.bodyAsClass(Reimbursement.class);
			reimburseService.updateBencoApproval(updateReimbursement, employee, reimburseId);
			
			// final form part, approved amount in body
			Reimbursement reimbursement = reimburseService.viewOneReimbursement(reimburseId, employee);
			FormType formType = FormType.valueOf(ctx.header("FormType"));
			finalService.add(reimbursement, formType);
			
			ctx.status(201);
			
		} else {
			ctx.status(403);
		}
		
	}
	

}

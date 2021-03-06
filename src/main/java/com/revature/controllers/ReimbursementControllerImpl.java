package com.revature.controllers;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.FinalForm;
import com.revature.beans.FormType;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.ExceedFundsService;
import com.revature.services.ExceedFundsServiceImpl;
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
	private ExceedFundsService exceedService = (ExceedFundsService) BeanFactory.getFactory().get(ExceedFundsService.class, ExceedFundsServiceImpl.class);
	private static Logger log = LogManager.getLogger(ReimbursementControllerImpl.class);
	
	
	@Override
	public void addReimbursement(Context ctx) {
		log.trace("AddReimbursement method called");
		log.debug(ctx.body());
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		String employee = loggedUser.getUsername();
		Long requestAmount = Long.parseLong(ctx.header("Reimburse-Amount"));
		Boolean urgent = Boolean.valueOf(ctx.header("Urgent"));
		String classType = ctx.header("ClassType");
		User employeeUser = userService.getUser(employee);
		
		if (classType.equals(null)) {
			ctx.status(400);
			return;
		}
		
		Long amount = employeeUser.getPendingFunds() + requestAmount;
		userService.changePendingAmount(employee, amount);
		
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
		
		Reimbursement newReimbursement = reimburseService.apply(key, employee, requestAmount, urgent, classType);
		
		
		
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
		
		if(loggedUsername.equals(reimbursement.getEmployee()) || allowedAccess == true || loggedUser.getType().equals(UserType.BENCO)){
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
		
		if(loggedUsername.equals(employee) || allowedAccess == true || loggedUser.getType().equals(UserType.BENCO)){
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
			return;
		}
		if(reimbursement == null) {
			ctx.status(404);
			ctx.html("Reimbursement does not exist.");
			return;
		}
		
		if(loggedUsername.equals(employee) 
				|| allowedAccess.equals(true) 
				|| loggedUser.getType().equals(UserType.BENCO)){
			
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
			
			Reimbursement reimbursement = reimburseService.viewOneReimbursement(reimburseId, employee);
			
			// check if an exceed funds one exists
			// if yes, delete it too
			if(reimbursement.getBencoApproval().equals(true)) {
				exceedService.delete(reimburseId);
			}
			
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
		String fullForm = reimbursement.getReimburseForm();
		log.debug(fullForm);
		String reimburseFormName = fullForm.substring(0, fullForm.length() - 5);
		log.debug(reimburseFormName);
		
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
		//String superNull = userService.getUser(employee).getSupervisor();
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
				
			}
			/*if (loggedUser.getSupervisor().equals(loggedUser.getDephead())) {
				reimburseService.depheadIsSuper(reimburseApprove, employee, reimburseId);
				ctx.status(201);
				return;
			}*/
			else {
				ctx.status(403);
				if (superApproval.equals(false)) {
					ctx.status(403);
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
		
		
		if(loggedUser.getType().equals(UserType.BENCO)) {
			// calls both finalForm AND reimbursement
			// and eventually, add for exceedingFunds if necessary
			log.trace("Updating Benco approval");
			// reimbursement setup
			Reimbursement updateReimbursement = ctx.bodyAsClass(Reimbursement.class);
			reimburseService.updateBencoApproval(updateReimbursement, employee, reimburseId);
			
			// final form part, approved amount in body
			if(updateReimbursement.getBencoApproval().equals(true)) {
				Reimbursement reimbursement = reimburseService.viewOneReimbursement(reimburseId, employee);
				log.debug("Retrieved reimbursement: "+ reimbursement);
				FormType formType = FormType.valueOf(ctx.header("FormType"));
				FinalForm form = finalService.add(reimbursement, formType);
				
				User changeUser = userService.getUser(employee);
				
				Long pendingAmount = changeUser.getPendingFunds() - reimbursement.getRequestAmount();
				Long usedAmount = changeUser.getUsedFunds() + reimbursement.getApprovedAmount();
				userService.changePendingAmount(reimbursement.getEmployee(), pendingAmount);
				userService.changeUsedAmount(reimbursement.getEmployee(), usedAmount);
				
				User user = userService.getUser(reimbursement.getEmployee());
				
				if(user.getAvailableFunds() < 0) {
					log.trace("Approved amount exceeds funds");
					String reason = ctx.header("Reason");
					if(reason.equals(null)) {
						ctx.status(400);
						ctx.html("Need reason for exceeding funds");
					}
					exceedService.addExceedFunds(reimbursement, reason, loggedUser);
				}
				
				
				ctx.status(200);
				ctx.json(form);
			}
			
			
			ctx.status(201);
			
		} else {
			ctx.status(403);
		}
		
	}
	

}

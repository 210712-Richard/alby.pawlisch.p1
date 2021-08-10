package com.revature.controllers;

import java.util.List;
import java.util.UUID;

import com.revature.beans.FinalForm;
import com.revature.beans.User;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.FinalFormService;
import com.revature.services.FinalFormServiceImpl;

import io.javalin.http.Context;

@Log
public class FinalFormControllerImpl implements FinalFormController {	
	private FinalFormService finalService = (FinalFormService) BeanFactory.getFactory().get(FinalFormService.class, FinalFormServiceImpl.class);
	
	// security check: isAllowed(finalForm, loggedUser)
	
	// get all forms from an employee
	@Override
	public void employeeForms(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String employee = ctx.pathParam("employee");
		UUID formId = UUID.fromString(ctx.pathParam("formId"));
		FinalForm form = finalService.getById(employee, formId);
		
		if(employee.equals(loggedUser.getUsername())
				|| finalService.isAllowed(form, loggedUser).equals(true)) {
			// get all forms from employee
			List<FinalForm> list = finalService.getByEmployee(employee);
			ctx.json(list);
			ctx.status(200);
			return;
		} else {
			ctx.status(403);
			return;
		}
	}
	
	// get one final form
	@Override
	public void getForm(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String employee = ctx.pathParam("employee");
		UUID formId = UUID.fromString(ctx.pathParam("formId"));
		FinalForm form = finalService.getById(employee, formId);
		
		if(employee.equals(loggedUser.getUsername())
				|| finalService.isAllowed(form, loggedUser).equals(true)) {
			// get form
			FinalForm finalForm = finalService.getById(employee, formId);
			ctx.json(finalForm);
			ctx.status(200);
			
			return;
		} else {
			ctx.status(403);
			return;
		}
	}
	
	// submit a file to a final form
	@Override
	public void submitFile(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String employee = ctx.pathParam("employee");
		UUID formId = UUID.fromString(ctx.pathParam("formId"));
		
		if(employee.equals(loggedUser.getUsername())) {
			// do S3 stuff
		} else {
			ctx.status(403);
			return;
		}
	}
	
	// get/download a file
	@Override
	public void getFile(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String employee = ctx.pathParam("employee");
		UUID formId = UUID.fromString(ctx.pathParam("formId"));
		FinalForm form = finalService.getById(employee, formId);
		
		if(employee.equals(loggedUser.getUsername())
				|| finalService.isAllowed(form, loggedUser).equals(true)) {
			// get file, S3 stuff
			
			return;
		} else {
			ctx.status(403);
			return;
		}
	}
	
	// change approval status
	@Override
	public void approval(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String employee = ctx.pathParam("employee");
		UUID formId = UUID.fromString(ctx.pathParam("formId"));
		FinalForm form = finalService.getById(employee, formId);
		
		if (form == null) {
			ctx.status(404);
			ctx.html("Form does not exist");
			return;
		}
		
		if(finalService.isAllowed(form, loggedUser).equals(true)) {
			FinalForm updateForm = ctx.bodyAsClass(FinalForm.class);
			updateForm.setId(formId);
			updateForm.setEmployee(employee);
			
			finalService.updateApproval(updateForm);
			ctx.json(finalService.getById(employee, formId));
			ctx.status(200);
			return;
			
		} else {
			ctx.status(403);
			return;
		}
		
	}
	
}

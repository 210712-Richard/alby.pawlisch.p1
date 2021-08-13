package com.revature.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.ExceedFunds;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.ExceedFundsService;
import com.revature.services.ExceedFundsServiceImpl;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Context;

@Log
public class ExceedFundsControllerImpl implements ExceedFundsController {
	private ExceedFundsService exceedService = (ExceedFundsService) BeanFactory.getFactory().get(ExceedFundsService.class, ExceedFundsServiceImpl.class);
	
	private static Logger log = LogManager.getLogger(ReimbursementControllerImpl.class);
	
	// add method is in the reimbursement controller
	
	// view method
	@Override
	public void getExceedFunds(Context ctx) {
		log.trace("Called get ExceedFunds");
		log.debug("Viewing exceed funds for: " + ctx.pathParam("fundsId"));
		
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		if(loggedUser.getType().equals(UserType.BENCO)) {
			ExceedFunds exceed = exceedService.getExceed(UUID.fromString(ctx.pathParam("fundsId")));
			ctx.json(exceed);
		} else {
			ctx.status(403);
		}
		
		
	}
	
	@Override
	public void getAllExceedFunds(Context ctx) {
		log.trace("Called get ExceedFunds");
		log.debug("Viewing all exceed funds");
		
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		if(loggedUser.getType().equals(UserType.BENCO)) {
			List<ExceedFunds> exceeds = exceedService.getAllExceed();
			ctx.json(exceeds);
		} else {
			ctx.status(403);
		}
	}
	
	// delete method is in reimbursement controller
	

}

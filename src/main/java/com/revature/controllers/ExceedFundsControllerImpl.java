package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.factory.BeanFactory;
import com.revature.services.ExceedFundsService;
import com.revature.services.ExceedFundsServiceImpl;

import io.javalin.http.Context;

public class ExceedFundsControllerImpl implements ExceedFundsController {
	private ExceedFundsService exceedService = (ExceedFundsService) BeanFactory.getFactory().get(ExceedFundsService.class, ExceedFundsServiceImpl.class);
	private static Logger log = LogManager.getLogger(ReimbursementControllerImpl.class);
	
	// add method is in the reimbursement controller
	
	// view method
	
	// delete method

}

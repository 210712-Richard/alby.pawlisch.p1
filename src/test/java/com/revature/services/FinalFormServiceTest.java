package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.services.UserService;
import com.revature.data.FinalFormDao;
import com.revature.data.UserDao;

public class FinalFormServiceTest {
	// do the constructor dance in the actual service
	// need userService and finalService
	private static FinalFormServiceImpl service;
	private static UserService userService;
	private static FinalFormDao finalDao;
	
	@BeforeAll
	public static void setUpClass() {
		
		
	}

	@BeforeEach
	public void setUpTests() {
		finalDao = Mockito.mock(FinalFormDao.class);
		userService = Mockito.mock(UserService.class);
		service = new FinalFormServiceImpl(finalDao, userService);
	}
	
	
	// "add" a form
	
	// get the form
	
	// update approval
	
	// check isAllowed

}

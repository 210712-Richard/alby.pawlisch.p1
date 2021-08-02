package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.User;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Context;

@Log
public class UserControllerImpl implements UserController {

	private static Logger log = LogManager.getLogger(UserControllerImpl.class);
	private UserService userService = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
	
	@Override
	public void login(Context ctx) {
		log.trace("Login method called");
		log.debug(ctx.body());
		User user = ctx.bodyAsClass(User.class);
		log.debug(user);
		
		user = userService.login(user.getUsername());
		log.debug(user);
		
		if(user != null) {
			ctx.sessionAttribute("loggedUser", user);
			ctx.json(user);
			return;
		}
	}
	
	@Override
	public void logout(Context ctx) {
		ctx.req.getSession().invalidate();
		ctx.status(204);
	}
	
}

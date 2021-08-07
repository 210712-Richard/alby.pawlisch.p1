package com.revature.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Notification;
import com.revature.beans.User;
import com.revature.data.ReceivedNotifDao;
import com.revature.data.ReceivedNotifDaoImpl;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class UserServiceImpl implements UserService{
	
	public UserDao userDao = (UserDao) BeanFactory.getFactory().get(UserDao.class, UserDaoImpl.class);
	
	public ReceivedNotifDao receivedNotifDao = (ReceivedNotifDao) BeanFactory.getFactory().get(ReceivedNotifDao.class, ReceivedNotifDaoImpl.class);


	@Override
	public User getUser(String username) {
		User user = userDao.getUser(username);
		
		return user;
		
	}
	
	@Override
	public List<Notification> getInbox(String username){
		List<Notification> inbox = userDao.getUserInbox(username);
		return inbox;
		
	}
	
	@Override
	public Boolean allowedAccess(String username, String employee) {
		User checkEmployee = userDao.getUser(employee);
		
		Boolean isDephead;
		if(checkEmployee.getDephead() == username) {
			isDephead = true;
		} else {
			isDephead = false;
		}
		
		Boolean isSupervisor;
		if(checkEmployee.getSupervisor() == username) {
			isSupervisor = true;
		} else {
			isSupervisor = false;
		}
		
		Boolean allowed;
		if(isDephead.equals(true) || isSupervisor.equals(true)) {
			allowed = true;
			return allowed;
		} else {
			allowed = false;
		}
		
		return allowed;
		
	}

	
	
	

}

package com.revature.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Notification;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.NotificationDao;
import com.revature.data.NotificationDaoImpl;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class UserServiceImpl implements UserService{
	public UserDao userDao;
	public NotificationDao notifDao;
	public Logger log;
	
	public UserServiceImpl() {
		super();
		userDao = (UserDao) BeanFactory.getFactory().get(UserDao.class, UserDaoImpl.class);
		notifDao = (NotificationDao) BeanFactory.getFactory().get(NotificationDao.class, NotificationDaoImpl.class);
		log = LogManager.getLogger(ReimbursementServiceImpl.class);
	}
	
	public UserServiceImpl(UserDao userDao2, NotificationDao notifDao2) {
		this.userDao = userDao2;
		this.notifDao = notifDao2;
	}

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
	public Boolean isDephead(String username, String employee) {
		User checkEmployee = userDao.getUser(employee);
		
		Boolean isDephead;
		if(username.equals(checkEmployee.getDephead())) {
			isDephead = true;
		} else {
			isDephead = false;
		}
		
		return isDephead;
	}
	
	@Override
	public Boolean isSupervisor(String username, String employee) {
		User checkEmployee = userDao.getUser(employee);
		
		Boolean isSupervisor;
		if(username.equals(checkEmployee.getSupervisor())) {
			isSupervisor = true;
		} else {
			isSupervisor = false;
		}
		return isSupervisor;
	}
	
	@Override
	public Boolean isBenco(String username) {
		User user = userDao.getUser(username);
		
		Boolean isBenco;
		if(user.getType() != UserType.BENCO) {
			isBenco = true;
		} else {
			isBenco = false;
		}
		
		return isBenco;
	}
	
	@Override
	public Boolean allowedAccess(String username, String employee) {
		
		Boolean isDephead = isDephead(username, employee);
		
		Boolean isSupervisor = isSupervisor(username, employee);
		
		Boolean isBenco = isBenco(username);
		
		Boolean allowed;
		if(isDephead.equals(true) || isSupervisor.equals(true) || isBenco.equals(true)) {
			allowed = true;
			return allowed;
		} else {
			allowed = false;
		}
		
		return allowed;
		
	}
	
	@Override
	public void changeAvailableAmount(String employee) {
		log.trace("Called changeAvailableAmount");
		User user = getUser(employee);
		log.debug(user.getPendingFunds() + ", " + user.getUsedFunds());
		Long availableFunds = 1000 - user.getPendingFunds() - user.getUsedFunds();
		user.setAvailableFunds(availableFunds);
		userDao.changeAvailableAmount(user);
		
	}
	
	@Override
	public void changePendingAmount(String employee, Long amount) {
		//log.trace("Called changePendingAmount");
		User user = getUser(employee);
		//log.debug(user.getPendingFunds());
		user.setPendingFunds(amount);
		userDao.changePendingAmount(user);
		changeAvailableAmount(employee);
		
	}
	
	@Override
	public void changeUsedAmount(String employee, Long amount) {
		//log.trace("Called changeUsedAmount");
		User user = getUser(employee);
		user.setUsedFunds(amount);
		userDao.changeUsedAmount(user);
		changeAvailableAmount(employee);
	}

	
	
	

}

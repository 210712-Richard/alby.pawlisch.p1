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
	
	private Logger log = LogManager.getLogger(UserServiceImpl.class);
	public UserDao userDao = (UserDao) BeanFactory.getFactory().get(UserDao.class, UserDaoImpl.class);

	public ReceivedNotifDao receivedNotifDao = (ReceivedNotifDao) BeanFactory.getFactory().get(ReceivedNotifDao.class, ReceivedNotifDaoImpl.class);

	@Override
	public User login(String username) {
		User user = userDao.getUser(username);
		
		return user;
		
	}
	
	@Override
	public List<Notification> getInbox(String username){
		List<Notification> inbox = userDao.getUserInbox(username);
		return inbox;
		
	}
	
	
	
	

}

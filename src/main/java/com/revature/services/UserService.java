package com.revature.services;

import java.util.List;

import com.revature.beans.Notification;
import com.revature.beans.User;

public interface UserService {
	
	User login(String username);

	List<Notification> getInbox(String username);
	

}

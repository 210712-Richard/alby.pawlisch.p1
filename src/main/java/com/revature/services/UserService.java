package com.revature.services;

import java.util.List;

import com.revature.beans.Notification;
import com.revature.beans.User;

public interface UserService {

	User getUser(String username);

	List<Notification> getInbox(String username);

	Boolean allowedAccess(String username, String employee);

	

}

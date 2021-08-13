package com.revature.services;

import java.util.List;

import com.revature.beans.Notification;
import com.revature.beans.User;

public interface UserService {

	User getUser(String username);

	List<Notification> getInbox(String username);

	Boolean isDephead(String username, String employee);

	Boolean isSupervisor(String username, String employee);

	Boolean isBenco(String username);
	
	Boolean allowedAccess(String username, String employee);

	void changeAvailableAmount(String employee);

	void changePendingAmount(String employee, Long amount);

	void changeUsedAmount(String employee, Long amount);

	

}

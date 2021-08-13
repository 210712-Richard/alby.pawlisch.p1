package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Notification;
import com.revature.beans.User;

public interface UserDao {
	public void addUser(User u);
	
	public List<User> getUsers();
	
	public User getUser(String username);
	
	public void updateUser(User user);
	
	public List<Notification> getUserInbox(String username);

	void changeAvailableAmount(User user);

	void changeUsedAmount(User user);

	void changePendingAmount(User user);

}

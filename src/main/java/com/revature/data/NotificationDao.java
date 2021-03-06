package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Notification;

public interface NotificationDao {

	void addNotif(Notification notif);

	List<Notification> getNotifs();

	Notification getNotifById(UUID id);

}

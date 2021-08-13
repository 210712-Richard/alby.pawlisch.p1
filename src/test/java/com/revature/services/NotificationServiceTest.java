package com.revature.services;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.beans.Notification;
import com.revature.data.NotificationDao;

public class NotificationServiceTest {
	private static Notification notif;
	private static NotificationDao notifDao;
	private static NotificationServiceImpl service;
	
	@BeforeAll
	public static void setUpClass() {
		notif = new Notification();
		notif.setReciever("Test");
		notif.setMessage("test");
		notif.setSentDate(LocalDate.now());
		
		
		
	}

	@BeforeEach
	public void setUpTests() {
		notifDao = Mockito.mock(NotificationDao.class);
		service = new NotificationServiceImpl(notifDao);
	}
	
	
	@Test
	public void testCreate() {
		
	}
	
	
}

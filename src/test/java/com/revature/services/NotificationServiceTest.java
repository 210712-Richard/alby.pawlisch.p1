package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.revature.beans.Notification;
import com.revature.beans.Reimbursement;
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
		Reimbursement testReimburse = new Reimbursement();
		testReimburse.setId(UUID.fromString("6e2ab9c7-a2e1-4956-bca7-c439439d8dd6"));
		testReimburse.setEmployee("Test");
		testReimburse.setSubmissionDate(LocalDate.now());
		testReimburse.setRequestAmount(20l);
		testReimburse.setApprovedAmount(10l);

		// need reimbursement and form type
		service.sendChangeAmountNotif(testReimburse);
		
		ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
		
		Mockito.verify(service.notifDao).addNotif(captor.capture());
		
		Notification notif = captor.getValue();
		assertEquals("Test", notif.getReciever(), "Assert reciever is the same");
	}
	
	
}

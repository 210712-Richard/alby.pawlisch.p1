package com.revature.services;

import java.time.LocalDate;

import com.revature.beans.Notification;
import com.revature.beans.Reimbursement;
import com.revature.data.NotificationDao;
import com.revature.data.NotificationDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class NotificationServiceImpl implements NotificationService {
	public NotificationDao notifDao;
	
	public NotificationServiceImpl() {
		super();
		notifDao = (NotificationDao) BeanFactory.getFactory().get(NotificationDao.class, NotificationDaoImpl.class);
	}
	
	public NotificationServiceImpl(NotificationDao notifDao2) {
		this.notifDao = notifDao2;
	}
	
	@Override
	public void sendChangeAmountNotif(Reimbursement reimbursement) {
		Notification notif = new Notification();
		notif.setReciever(reimbursement.getEmployee());
		notif.setMessage("Reimbursement submitted on " + reimbursement.getSubmissionDate().toString() + " for "
		+ reimbursement.getRequestAmount() + " was approved for: " + reimbursement.getApprovedAmount());
		notif.setSentDate(LocalDate.now());
		notifDao.addNotif(notif);
	}
	
	@Override
	public void sendBencoEscalation(Reimbursement reimbursement) {
		Notification notif = new Notification();
		notif.setReciever("Dolores");
		notif.setMessage("Reimbursement " + reimbursement.getId() + " is awaiting approval");
		notif.setSentDate(LocalDate.now());
		notifDao.addNotif(notif);
	}

}

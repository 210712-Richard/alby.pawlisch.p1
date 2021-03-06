package com.revature.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Reimbursement;
import com.revature.data.NotificationDao;
import com.revature.data.NotificationDaoImpl;
import com.revature.data.ReimbursementDao;
import com.revature.data.ReimbursementDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class ReimbursementServiceImpl implements ReimbursementService {
	private Logger log;
	public ReimbursementDao reimburseDao;
	public NotificationService notifService;
	public UserService userService;
	
	public ReimbursementServiceImpl() {
		super();
		log = LogManager.getLogger(ReimbursementServiceImpl.class);
		reimburseDao = (ReimbursementDao) BeanFactory.getFactory().get(ReimbursementDao.class, ReimbursementDaoImpl.class);
		notifService = (NotificationService) BeanFactory.getFactory().get(NotificationService.class, NotificationServiceImpl.class);
		userService = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
		
	}
	
	public ReimbursementServiceImpl(ReimbursementDao reimburseDao2) {
		this.reimburseDao = reimburseDao2;
	}
	
	
	@Override
	public Reimbursement apply(String reimburseForm, String loggedUser, Long requestAmount, Boolean urgent, String classType) {
		// user only has to submit form, program figures out the rest
		
		// a lot of these are null because they change or are added to later
		
		//COURSE, SEMINAR, PREP, CERTIF, TECH, OTHER
		Double percentage = 0.0;
		if (classType.equals("COURSE")) {
			// 80%
			percentage = .8;
		}
		if (classType.equals("SEMINAR")) {
			// 60
			percentage = .6;
		}
		if (classType.equals("PREP")) {
			// 75
			percentage = .75;
		}
		if (classType.equals("CERTIF")) {
			// 100
			percentage = 1.0;
		}
		if (classType.equals("TECH")) {
			// 90
			percentage = .9;
		}
		if (classType.equals("OTHER")) {
			// 30
			percentage = .3;
		}
		
		Long predictedAmount = Math.round(requestAmount*percentage);
		
		Reimbursement reimburse = new Reimbursement();
		reimburse.setEmployee(loggedUser);
		reimburse.setReimburseForm(reimburseForm);
		reimburse.setApprovedEmail(null);
		reimburse.setSubmissionDate(LocalDate.now());
		reimburse.setLastApprovalDate(null);
		reimburse.setSuperApproval(null);
		reimburse.setHeadApproval(null);
		reimburse.setBencoApproval(null);
		reimburse.setUrgent(urgent);
		reimburse.setRequestAmount(predictedAmount);
		reimburse.setApprovedAmount(null);
		
		//log.trace("Called apply method in Service");
		//log.debug(reimburse);
		
		reimburseDao.addReimbursement(reimburse);
		//userService.changePendingAmount(loggedUser, requestAmount);
		
		return reimburse;
		
	}
	
	@Override
	public List<Reimbursement> viewReimbursementsFromEmployee(String employee) {
		List<Reimbursement> list = reimburseDao.getEmployeeReimbursements(employee);
		return list;
	}
	
	@Override
	public Reimbursement viewOneReimbursement(UUID id, String employee) {
		Reimbursement reimbursement = reimburseDao.getReimbursementById(id, employee);
		return reimbursement;
		
	}
	
	@Override
	public void deleteReimbursement(UUID id, String employee) {
		reimburseDao.deleteReimbursement(id, employee);
	}
	
	@Override
	public void emailApprove(Reimbursement reimbursement) {
		// users can do this after they submit a form
		// actual email is sent in controller
		reimburseDao.updateEmail(reimbursement);
		
		Reimbursement reimbursementGet = viewOneReimbursement(reimbursement.getId(), reimbursement.getEmployee());
		if (reimbursementGet.getUrgent().equals(true)) {
			isUrgent(reimbursement.getId(), reimbursement.getEmployee());
		}
		
	}
	
	@Override
	public void updateSuperApproval(Reimbursement reimbursement, String employee, UUID id) {
		// need reimbursement, employee, id
		// reimbursement already has approval status
		reimbursement.setLastApprovalDate(LocalDate.now());
		reimbursement.setEmployee(employee);
		reimbursement.setId(id);
		reimburseDao.updateSuperApproval(reimbursement);
	}
	
	@Override
	public void updateDepheadApproval(Reimbursement reimbursement, String employee, UUID id) {
		// need reimbursement, employee, id
		// reimbursement already has approval status
		reimbursement.setLastApprovalDate(LocalDate.now());
		reimbursement.setEmployee(employee);
		reimbursement.setId(id);
		reimburseDao.updateDepheadApproval(reimbursement);
		
		Reimbursement reimbursementGet = viewOneReimbursement(id, employee);
		if(reimbursementGet.getHeadApproval().equals(true)) {
			if (reimbursementGet.getUrgent().equals(true)) {
				isUrgent(id, employee);
			}
		}
		
	}
	
	@Override
	public void depheadIsSuper(Reimbursement reimbursement, String employee, UUID id) {
		// need reimbursement, employee, id
		// reimbursement already has approval status
		reimbursement.setSuperApproval(reimbursement.getHeadApproval());
		reimbursement.setLastApprovalDate(LocalDate.now());
		reimbursement.setEmployee(employee);
		reimbursement.setId(id);
		reimburseDao.updateSuperApproval(reimbursement);
		reimburseDao.updateDepheadApproval(reimbursement);
		
		Reimbursement reimbursementGet = viewOneReimbursement(id, employee);
		if(reimbursementGet.getHeadApproval().equals(true)) {
			if (reimbursementGet.getUrgent().equals(true)) {
				isUrgent(id, employee);
			}
		}
	}
	
	@Override
	public void updateBencoApproval(Reimbursement reimbursement, String employee, UUID id) {
		reimbursement.setLastApprovalDate(LocalDate.now());
		reimbursement.setEmployee(employee);
		reimbursement.setId(id);
		reimburseDao.updateBencoApproval(reimbursement);
		userService.changeUsedAmount(employee, reimbursement.getApprovedAmount());
		
		Reimbursement old = viewOneReimbursement(id, employee);
		reimbursement.setSubmissionDate(old.getSubmissionDate());
		reimbursement.setRequestAmount(old.getRequestAmount());
		if (reimbursement.getRequestAmount() != reimbursement.getApprovedAmount()) {
			notifService.sendChangeAmountNotif(reimbursement);
		}
	}
	
	@Override
	public void isUrgent(UUID id, String employee) {
		// 
		log.trace("Called isUrgent");
		Reimbursement reimbursement = viewOneReimbursement(id, employee);
		try {
			Thread.sleep(20000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		reimbursement.setSuperApproval(true);
		reimbursement.setLastApprovalDate(LocalDate.now());
		reimburseDao.updateSuperApproval(reimbursement);
		try {
			Thread.sleep(20000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		reimbursement.setHeadApproval(true);
		reimbursement.setLastApprovalDate(LocalDate.now());
		reimburseDao.updateDepheadApproval(reimbursement);
		
		
	}
	
	
	
	
}

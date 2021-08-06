package com.revature.services;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Reimbursement;
import com.revature.data.ReimbursementDao;
import com.revature.data.ReimbursementDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class ReimbursementServiceImpl implements ReimbursementService {
	private Logger log = LogManager.getLogger(ReimbursementServiceImpl.class);
	public ReimbursementDao reimburseDao = (ReimbursementDao) BeanFactory.getFactory().get(ReimbursementDao.class, ReimbursementDaoImpl.class);
	
	@Override
	public Reimbursement apply(String reimburseForm, String loggedUser, Long requestAmount) {
		// user only has to submit form, program figures out the rest
		
		// a lot of these are null because they change or are added to later
		
		Reimbursement reimburse = new Reimbursement();
		reimburse.setEmployee(loggedUser);
		reimburse.setReimburseForm(reimburseForm);
		reimburse.setApprovedEmail(null);
		reimburse.setSubmissionDate(LocalDate.now());
		reimburse.setLastApprovalDate(null);
		reimburse.setSuperApproval(null);
		reimburse.setHeadApproval(null);
		reimburse.setBencoApproval(null);
		// WORK OUT URGENT STUFF LATER
		reimburse.setUrgent(false);
		reimburse.setRequestAmount(requestAmount);
		reimburse.setApprovedAmount(null);
		
		return reimburse;
		
	}
	
	@Override
	public Reimbursement emailApprove() {
		// users can do this after they submit a form
		
		
		// GET RID OF THE RETURN NULL LATER
		return null;
	}
	
	@Override
	public Boolean filenameCheck(String key) {
		Boolean available = reimburseDao.checkFileNameAvailability(key);
		return available;
	}
	
	@Override
	public Integer reimbursementCount(String employee) {
		
		List<Reimbursement> list = reimburseDao.getEmployeeReimbursements(employee);
		
		Integer count = list.size();
		return count;
	}
	
}

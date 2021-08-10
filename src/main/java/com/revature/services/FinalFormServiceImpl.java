package com.revature.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.FinalForm;
import com.revature.beans.FormType;
import com.revature.beans.Reimbursement;
import com.revature.data.FinalFormDao;
import com.revature.data.FinalFormDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class FinalFormServiceImpl implements FinalFormService {
	private Logger log = LogManager.getLogger(ReimbursementServiceImpl.class);
	public FinalFormDao finalDao = (FinalFormDao) BeanFactory.getFactory().get(FinalFormDao.class, FinalFormDaoImpl.class);
	
	// create
	// gets reimbursement and pulls a lot of data from that
	@Override
	public FinalForm add(Reimbursement reimbursement, FormType formType) {
		FinalForm form = new FinalForm();
		form.setReimburseId(reimbursement.getId());
		form.setEmployee(reimbursement.getEmployee());
		form.setApproved(null);
		form.setSubmissionDate(reimbursement.getSubmissionDate());
		form.setFormType(formType);
		form.setUrgent(reimbursement.getUrgent());
		form.setFilename(null);
		
		log.trace("Called add method in Service");
		log.debug(form);
		
		finalDao.addFinalForm(form);
		
		return form;
	}
	
	// get all final forms from one employee
	@Override
	public List<FinalForm> getByEmployee(String employee) {
		List<FinalForm> list = finalDao.getFinalsByEmployee(employee);
		
		log.trace("Called all finals from: " + employee);
		
		return list;
	}
	
	// need to get a final form for viewing
	@Override
	public FinalForm getById(FinalForm finalForm) {
		FinalForm form = finalDao.getFinalById(finalForm);
		return form;
	}
	
	// need file update
	@Override
	public void updateFile(FinalForm finalForm) {
		finalDao.updateFile(finalForm);
	}
	
	// need approval update
	@Override
	public void updateApproval(FinalForm finalForm) {
		finalDao.updateApproval(finalForm);
	}
	
	// need delete
	@Override
	public void deleteFinalForm(FinalForm finalForm) {
		finalDao.deleteFinalForm(finalForm);
	}
	

}

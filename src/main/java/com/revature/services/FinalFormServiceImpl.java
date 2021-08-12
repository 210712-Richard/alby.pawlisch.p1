package com.revature.services;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.FinalForm;
import com.revature.beans.FormType;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.FinalFormDao;
import com.revature.data.FinalFormDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class FinalFormServiceImpl implements FinalFormService {
	private Logger log = LogManager.getLogger(ReimbursementServiceImpl.class);
	public FinalFormDao finalDao = (FinalFormDao) BeanFactory.getFactory().get(FinalFormDao.class, FinalFormDaoImpl.class);
	public UserService userService = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class); 
	
	// create
	// gets reimbursement and pulls a lot of data from that
	@Override
	public FinalForm add(Reimbursement reimbursement, FormType formType) {
		FinalForm form = new FinalForm();
		form.setId(reimbursement.getId());
		form.setEmployee(reimbursement.getEmployee());
		form.setApproved(null);
		form.setSubmissionDate(reimbursement.getSubmissionDate());
		form.setFormType(formType);
		form.setUrgent(reimbursement.getUrgent());
		form.setFilename(null);
		
		log.trace("Called add method in FinalFormService");
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
	public FinalForm getById(String employee, UUID id) {
		FinalForm form = finalDao.getFinalById(employee, id);
		
		log.trace("Called getById: " + id);
		
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
		log.trace("Changed approval for:" + finalForm.getId());
	}
	
	// need delete
	@Override
	public void deleteFinalForm(FinalForm finalForm) {
		finalDao.deleteFinalForm(finalForm);
	}
	
	// check if form should be viewed by user
	@Override
	public Boolean isAllowed(FinalForm finalForm, User loggedUser) {
		Boolean allowed = null;
		
		// get formType
		FormType formType = finalForm.getFormType();
		
		if (formType.equals(FormType.GRADE)) {
			// grade, only benco allowed
			if(loggedUser.getType().equals(UserType.BENCO)) {
				allowed = true;
			} else {
				allowed = false;
			}
			
		} else if (formType.equals(FormType.PRESENTATION)) {
			// presentation, only supervisor allowed
			String employeeName = finalForm.getEmployee();
			User employee = userService.getUser(employeeName);
			String supervisor = employee.getSupervisor();
			
			if(supervisor.equals(null)) {
				supervisor = employee.getDephead();
			}
			
			if(supervisor.equals(loggedUser.getUsername())){
				allowed = true;
			} else {
				allowed = false;
			}
			
		}
		
		log.trace("Called isAllowed");
		log.debug("Returned: " + allowed);
		
		return allowed;
		
	}
	

}

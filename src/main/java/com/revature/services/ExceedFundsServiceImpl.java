package com.revature.services;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.ExceedFunds;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.data.ExceedFundsDao;
import com.revature.data.ExceedFundsDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class ExceedFundsServiceImpl implements ExceedFundsService {
	private Logger log;
	public ExceedFundsDao exceedDao;
	
	public ExceedFundsServiceImpl() {
		super();
		log = LogManager.getLogger(ReimbursementServiceImpl.class);
		exceedDao = (ExceedFundsDao) BeanFactory.getFactory().get(ExceedFundsDao.class, ExceedFundsDaoImpl.class);

	}
	
	public ExceedFundsServiceImpl(ExceedFundsDao exceed2) {
		this.exceedDao = exceed2;
	}
	
	
	// add method
	// get extension reason from header, get all other data from reimbursement
	// also loggedUser
	@Override
	public void addExceedFunds(Reimbursement reimbursement, String reason, User loggedUser) {
		//log.trace("Called add in ExceedFundsService");
		Long amount = reimbursement.getApprovedAmount() - reimbursement.getRequestAmount();
		
		ExceedFunds exceed = new ExceedFunds();
		exceed.setId(reimbursement.getId());
		exceed.setAmount(amount);
		exceed.setReason(reason);
		exceed.setBencoName(loggedUser.getUsername());
		
		//log.trace("Created new exceed funds");
		//log.debug(exceed);
		
		exceedDao.addExceedFunds(exceed);
		
	}
	
	// view method
	@Override
	public ExceedFunds getExceed(UUID id) {
		log.trace("Called getExceed: " + id);
		ExceedFunds exceed = exceedDao.viewExceedFunds(id);
		
		log.debug(exceed);
		
		return exceed;
	}
	
	// delete method
	@Override
	public void delete(UUID id) {
		log.trace("Called delete");
		exceedDao.delete(id);
		
	}
}

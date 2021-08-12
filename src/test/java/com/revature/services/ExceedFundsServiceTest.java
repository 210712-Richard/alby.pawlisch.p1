package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.revature.beans.ExceedFunds;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.data.ExceedFundsDao;

public class ExceedFundsServiceTest {
	private static ExceedFundsServiceImpl service;
	private static ExceedFundsDao exceedDao;
	private static ExceedFunds exceed;
	
	@BeforeAll
	public static void setUpClass() {
		exceed = new ExceedFunds();
		exceed.setId(UUID.fromString("6e2ab9c7-a2e1-4956-bca7-c439439d8dd6"));
		exceed.setAmount(10l);
		exceed.setReason("reasons");
		exceed.setBencoName("Benco");
	}
	
	@BeforeEach
	public void setUpTests() {
		exceedDao = Mockito.mock(ExceedFundsDao.class);
		service = new ExceedFundsServiceImpl(exceedDao);
	}
	
	// add
	@Test
	public void testAdd() {
		Reimbursement testReimburse = new Reimbursement();
		testReimburse.setId(UUID.fromString("6e2ab9c7-a2e1-4956-bca7-c439439d8dd6"));
		testReimburse.setApprovedAmount(20l);
		testReimburse.setRequestAmount(10l);
		
		String reason = "big reasons";
		User user2 = new User();
		user2.setUsername("Benco");
		
		service.addExceedFunds(testReimburse, reason, user2);
		
		ArgumentCaptor<ExceedFunds> captor = ArgumentCaptor.forClass(ExceedFunds.class);
		
		Mockito.verify(service.exceedDao).addExceedFunds(captor.capture());
		
		ExceedFunds exceed2 = captor.getValue();
		assertEquals(testReimburse.getId(), exceed2.getId(), "Asserting Id is the same");
		assertEquals("Benco", exceed2.getBencoName(), "Assert Benco is the same");
		assertEquals("big reasons", exceed2.getReason(), "Assert reason is the same");
		
		
		
	}
	
	// get
	@Test
	public void testGetExceed() {
		assertEquals(UUID.fromString("6e2ab9c7-a2e1-4956-bca7-c439439d8dd6"), exceed.getId(), "Assert Id is the same");
		assertEquals(10l, exceed.getAmount(), "Assert amount is the same");
		assertEquals("reasons", exceed.getReason(), "Assert reason is the same");
		assertEquals("Benco", exceed.getBencoName(), "Assert Benco is the same");
	}

}

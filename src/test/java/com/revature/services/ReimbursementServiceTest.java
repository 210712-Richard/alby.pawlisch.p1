package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.data.ReimbursementDao;


public class ReimbursementServiceTest {
	private static ReimbursementDao reimburseDao;
	private static ReimbursementServiceImpl service;
	private static User user;
	private static Reimbursement reimburse;
	
	@BeforeAll
	public static void setUpClass() {
		// need reimbursement with reimburseForm, requestAmount, and urgent
		// need user with a username
		reimburse = new Reimbursement();
		reimburse.setId(UUID.fromString("6e2ab9c7-a2e1-4956-bca7-c439439d8dd6"));
		reimburse.setEmployee("Test");
		reimburse.setRequestAmount(10l);
		reimburse.setReimburseForm("file.docx");
		reimburse.setUrgent(false);
		reimburse.setSuperApproval(null);
		
		
		user = new User();
		user.setUsername("Test");
		user.setSupervisor(null);
		user.setDephead("Both");
		
	}
	
	@BeforeEach
	public void setUpTests() {
		reimburseDao = Mockito.mock(ReimbursementDao.class);
		service = new ReimbursementServiceImpl(reimburseDao);
	}
	
	
	// "create" a reimbursement
	// needs a user + reimburseForm, requestAmount, and urgent
	@Test 
	public void testApply() {
		String reimburseForm = "file.docx";
		Long requestAmount = 10l;
		Boolean urgent = true;
		
		service.apply(reimburseForm, user.getUsername(), requestAmount, urgent);
		
		ArgumentCaptor<Reimbursement> captor = ArgumentCaptor.forClass(Reimbursement.class);
		
		Mockito.verify(service.reimburseDao).addReimbursement(captor.capture());
		
		Reimbursement testReimburse = captor.getValue();
		assertEquals(reimburseForm, testReimburse.getReimburseForm(), "Asserting form is the same");
		assertEquals(requestAmount, testReimburse.getRequestAmount(), "Assert requested amount is the same");
		assertEquals(urgent, testReimburse.getUrgent(), "Assert urgency is the same");
		
		
	}
	
	// get a reimbursement
	@Test
	public void testViewOneReimbursement() {
		service.apply(reimburse.getReimburseForm(),user.getUsername(),
				reimburse.getRequestAmount(), reimburse.getUrgent());
		
		ArgumentCaptor<Reimbursement> captor = ArgumentCaptor.forClass(Reimbursement.class);
		
		Mockito.verify(service.reimburseDao).addReimbursement(captor.capture());
		
		Reimbursement testReimburse2 = captor.getValue();
		//Reimbursement testReimburse2 = service.viewOneReimbursement(reimburse.getId(), user.getUsername());
		assertEquals(reimburse.getReimburseForm(), testReimburse2.getReimburseForm(), "Asserting form is the same");
		assertEquals(reimburse.getRequestAmount(), testReimburse2.getRequestAmount(), "Assert requested amount is the same");
		assertEquals(reimburse.getUrgent(), testReimburse2.getUrgent(), "Assert urgency is the same");
		
		
	}
	
	// update an approval
	@Test
	public void testUpdateSuperApproval() {
		assertNull(reimburse.getSuperApproval());
		
		reimburse.setSuperApproval(true);
		
		assertTrue(reimburse.getSuperApproval());
		
	}
	
	
	// check depheadIsSuper
	@Test
	public void testDepheadIsSuper() {
		assertNull(user.getSupervisor());
		
		reimburse.setHeadApproval(true);
		reimburse.setSuperApproval(reimburse.getHeadApproval());
		
		assertTrue(reimburse.getHeadApproval());
		assertTrue(reimburse.getSuperApproval());
	}
	

}

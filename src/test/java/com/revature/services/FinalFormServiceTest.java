package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.revature.beans.FinalForm;
import com.revature.beans.FormType;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.FinalFormDao;

public class FinalFormServiceTest {
	// do the constructor dance in the actual service
	// need userService and finalService
	private static FinalFormServiceImpl service;
	private static UserService userService;
	private static FinalFormDao finalDao;
	private static FinalForm form;
	private static User user;
	private static Reimbursement reimburse;
	
	@BeforeAll
	public static void setUpClass() {
		form = new FinalForm();
		form.setId(UUID.fromString("6e2ab9c7-a2e1-4956-bca7-c439439d8dd6"));
		form.setEmployee("Test");
		form.setFilename("file.docx");
		form.setFormType(FormType.GRADE);
		
		user = new User();
		user.setType(UserType.BENCO);
		
		reimburse = new Reimbursement();
		reimburse.setId(form.getId());
		reimburse.setEmployee(form.getEmployee());
		
	}

	@BeforeEach
	public void setUpTests() {
		finalDao = Mockito.mock(FinalFormDao.class);
		userService = Mockito.mock(UserService.class);
		service = new FinalFormServiceImpl(finalDao, userService);
	}
	
	
	// "add" a form
	@Test
	public void testAdd() {
		Reimbursement testReimburse = new Reimbursement();
		testReimburse.setId(UUID.fromString("6e2ab9c7-a2e1-4956-bca7-c439439d8dd6"));
		

		// need reimbursement and form type
		service.add(testReimburse, FormType.GRADE);
		
		ArgumentCaptor<FinalForm> captor = ArgumentCaptor.forClass(FinalForm.class);
		
		Mockito.verify(service.finalDao).addFinalForm(captor.capture());
		
		FinalForm testForm = captor.getValue();
		assertEquals(testReimburse.getId(), testForm.getId(), "Asserting Id is the same");
		assertEquals(FormType.GRADE, testForm.getFormType(), "Assert Benco is the same");
		
	}
	
	
	// get the form
	@Test
	public void testGetById() {
		
		
		service.add(reimburse, form.getFormType());
		
		ArgumentCaptor<FinalForm> captor = ArgumentCaptor.forClass(FinalForm.class);
		
		Mockito.verify(service.finalDao).addFinalForm(captor.capture());
		
		FinalForm testForm2 = captor.getValue();
		
		assertEquals(form.getId(), testForm2.getId(), "Assert Id is the same");
		assertEquals("Test", testForm2.getEmployee(), "Assert employee is the same");
	}
	
	// update approval
	@Test
	public void updateApproval() {
		form.setApproved(true);
		
		service.updateApproval(form);
		assertTrue(form.getApproved());
	}
	
	// check isAllowed
	@Test public void testIsAllowed() {
		
		
		assertTrue(service.isAllowed(form, user));
	}

}

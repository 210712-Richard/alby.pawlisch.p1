package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.NotificationDao;
import com.revature.data.UserDao;

public class UserServiceTest {
	private static UserServiceImpl service;
	private static User user;
	private static User user2;
	private static UserDao userDao;
	private static NotificationDao notifDao;

	@BeforeAll
	public static void setUpClass() {
		user = new User();
		user.setUsername("Test");
		user.setSupervisor("Supervisor");
		user.setDephead("Dephead");
		user.setType(UserType.EMPLOYEE);
		user.setPendingFunds(500l);
		user.setUsedFunds(200l);
		user.setAvailableFunds(1000l);
		
		user2 = new User();
		user2.setUsername("Benco");
		user2.setType(UserType.BENCO);
		
		
	}

	@BeforeEach
	public void setUpTests() {
		userDao = Mockito.mock(UserDao.class);
		notifDao = Mockito.mock(NotificationDao.class);
		service = new UserServiceImpl(userDao, notifDao);
	}

	
	@Test 
	public void testLogin() { 
		String username = "Jacob"; User user = new
		User(); user.setUsername(username);
		Mockito.when(service.userDao.getUser(username)).thenReturn(user);
	  
		User recievedUser = service.getUser(username);
	  
		assertEquals(user, recievedUser, "We receive the correct user!");
	  
	}
	 
	
	  @Test 
	  public void testIsSupervisorTrue() { 
	  
		  String username = "Supervisor";
	  
		  assertEquals(username, user.getSupervisor(), "Asserting they are the supervisor");
	  
	  }
	  
	  @Test 
	  public void testIsSupervisorFalse() { 
		  String username = "Glen";
	  
		  assertNotEquals(username, user.getSupervisor(), "Asserting they are not the supervisor"); 
	  }
	  
	  @Test 
	  public void testIsDepheadTrue() { 
		  String username = "Dephead";
	  
		  assertEquals(username, user.getDephead(),
		  "Asserting they are the dephead"); 
	  }
	  
	  @Test 
	  public void testIsDepheadFalse() { 
		  String username = "Jen";
	  
		  assertNotEquals(username, user.getDephead(),"Asserting they are not the dephead"); 
	  }
	  
	  @Test
	  public void testIsBencoTrue() {
		  
		  assertEquals(UserType.BENCO, user2.getType(), "Asserting User2 is a Benco");
	  }
	  
	  @Test
	  public void testIsBencoFalse() {
		  assertNotEquals(UserType.BENCO, user.getType(), "Asserting User2 is a Benco");
	  }
	  
	  @Test
	  public void testPendingChange() {
		  /*
		  Long pendingAmount = 500l - 500l;
		  Long usedAmount = 0l + 500l;
		  service.changePendingAmount(user.getUsername(), pendingAmount);
		  service.changeUsedAmount(user.getUsername(), usedAmount);
		  
		  assertEquals(user.getPendingFunds(), 0l, "Assert changePending was successful");
		  assertEquals(user.getUsedFunds(), 700l, "Assert changePending was successful");
		  */
	  }
	  
	  
	  
	  
	  
	 
}

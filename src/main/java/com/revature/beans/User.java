package com.revature.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {
	private String username;
	private String email;
	private UserType type;
	private List<Notification> inbox;
	private String supervisor;
	private String dephead;
	public Long pendingFunds;
	public Long usedFunds;
	public Long availableFunds;
	
	public User() {
		super();
		this.type = UserType.EMPLOYEE;
		this.inbox = new ArrayList<Notification>();
	}
	
	
	
	public User(UUID id, String username, String email, UserType type, List<Notification> inbox) {
		this();
		this.username = username;
		this.email = email;
		this.type = type;
		this.inbox = inbox;
		this.pendingFunds = 0l;
		this.usedFunds = 0l;
		this.availableFunds = 1000l;
	}



	public User(String username, String email, String supervisor, String dephead) {
		// make an employee
		this();
		this.username = username;
		this.email = email;
		this.supervisor = supervisor;
		this.dephead = dephead;
		this.inbox = new ArrayList<Notification>();
		this.pendingFunds = 0l;
		this.usedFunds = 0l;
		this.availableFunds = 1000l;
	}
	
	public User(String username, String email, String dephead) {
		// make a supervisor
		this();
		this.username = username;
		this.email = email;
		this.supervisor = null;
		this.dephead = dephead;
		this.inbox = new ArrayList<Notification>();
		this.pendingFunds = 0l;
		this.usedFunds = 0l;
		this.availableFunds = 1000l;
	}



	public User(String username, String email) {
		// Make CEO
		this();
		this.username = username;
		this.email = email;
		this.supervisor = null;
		this.dephead = null;
		this.inbox = new ArrayList<Notification>();
		this.pendingFunds = 0l;
		this.usedFunds = 0l;
		this.availableFunds = 1000l;
	}



	
	public User(String username, String email, UserType type, List<Notification> inbox, String supervisor,
			String dephead, Long pendingFunds, Long usedFunds, Long availableFunds) {
		super();
		this.username = username;
		this.email = email;
		this.type = type;
		this.inbox = inbox;
		this.supervisor = supervisor;
		this.dephead = dephead;
		this.pendingFunds = pendingFunds;
		this.usedFunds = usedFunds;
		this.availableFunds = availableFunds;
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
	public List<Notification> getInbox() {
		return inbox;
	}
	public void setInbox(List<Notification> inbox) {
		this.inbox = inbox;
	}
	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	public String getDephead() {
		return dephead;
	}

	public void setDephead(String dephead) {
		this.dephead = dephead;
	}

	public Long getPendingFunds() {
		return pendingFunds;
	}

	public void setPendingFunds(Long pendingFunds) {
		this.pendingFunds = pendingFunds;
	}

	public Long getUsedFunds() {
		return usedFunds;
	}

	public void setUsedFunds(Long usedFunds) {
		this.usedFunds = usedFunds;
	}
	public Long getAvailableFunds() {
		return availableFunds;
	}

	public void setAvailableFunds(Long availableFunds) {
		this.availableFunds = availableFunds;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pendingFunds == null) ? 0 : pendingFunds.hashCode());
		result = prime * result + ((availableFunds == null) ? 0 : availableFunds.hashCode());
		result = prime * result + ((dephead == null) ? 0 : dephead.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((inbox == null) ? 0 : inbox.hashCode());
		result = prime * result + ((supervisor == null) ? 0 : supervisor.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((usedFunds == null) ? 0 : usedFunds.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		
		if (pendingFunds == null) {
			if (other.pendingFunds != null)
				return false;
		} else if (!pendingFunds.equals(other.pendingFunds))
			return false;
		
		if (availableFunds == null) {
			if (other.availableFunds != null)
				return false;
		} else if (!availableFunds.equals(other.availableFunds))
			return false;
		
		if (dephead == null) {
			if (other.dephead != null)
				return false;
		} else if (!dephead.equals(other.dephead))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (inbox == null) {
			if (other.inbox != null)
				return false;
		} else if (!inbox.equals(other.inbox))
			return false;
		if (supervisor == null) {
			if (other.supervisor != null)
				return false;
		} else if (!supervisor.equals(other.supervisor))
			return false;
		if (type != other.type)
			return false;
		if (usedFunds == null) {
			if (other.usedFunds != null)
				return false;
		} else if (!usedFunds.equals(other.usedFunds))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", type=" + type + ", inbox=" + inbox
				+ ", supervisor=" + supervisor + ", dephead=" + dephead + ", pendingFunds=" + pendingFunds
				+ ", usedFunds=" + usedFunds + ", availableFunds=" + availableFunds + "]";
	}






}
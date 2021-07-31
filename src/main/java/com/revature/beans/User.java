package com.revature.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {
	private UUID id;
	private String username;
	private String email;
	private UserType type;
	private List<Notification> inbox;
	private String supervisor;
	private String dephead;
	
	public User() {
		super();
		this.type = UserType.EMPLOYEE;
		this.inbox = new ArrayList<Notification>();
	}
	
	
	
	public User(UUID id, String username, String email, UserType type, List<Notification> inbox) {
		this();
		this.id = id;
		this.username = username;
		this.email = email;
		this.type = type;
		this.inbox = inbox;
	}



	public User(String username, String email, String supervisor, String dephead) {
		// make an employee
		this();
		this.username = username;
		this.email = email;
		this.supervisor = supervisor;
		this.dephead = dephead;
	}
	
	public User(String username, String email, String dephead) {
		// make a supervisor
		this();
		this.username = username;
		this.email = email;
		this.supervisor = null;
		this.dephead = dephead;
	}



	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dephead == null) ? 0 : dephead.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inbox == null) ? 0 : inbox.hashCode());
		result = prime * result + ((supervisor == null) ? 0 : supervisor.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", type=" + type + ", inbox=" + inbox
				+ ", supervisor=" + supervisor + ", dephead=" + dephead + "]";
	}
	
	
	
	
	
}
	
	
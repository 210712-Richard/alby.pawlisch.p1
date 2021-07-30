package com.revature.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Notification implements Serializable {
	private UUID id;
	private User sender;
	private User reciever;
	private String message;
	private LocalDate sentDate;
	
	public Notification() {
		super();
	}
	
	
	
	public Notification(UUID id, User sender, User reciever, String message, LocalDate sentDate) {
		this();
		this.id = id;
		this.sender = sender;
		this.reciever = reciever;
		this.message = message;
		this.sentDate = sentDate;
	}



	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDate getSentDate() {
		return sentDate;
	}
	public void setSentDate(LocalDate sentDate) {
		this.sentDate = sentDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((reciever == null) ? 0 : reciever.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((sentDate == null) ? 0 : sentDate.hashCode());
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
		Notification other = (Notification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (reciever == null) {
			if (other.reciever != null)
				return false;
		} else if (!reciever.equals(other.reciever))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (sentDate == null) {
			if (other.sentDate != null)
				return false;
		} else if (!sentDate.equals(other.sentDate))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "Notification [id=" + id + ", sender=" + sender + ", reciever=" + reciever + ", message=" + message
				+ ", sentDate=" + sentDate + "]";
	}
	
	
	
}

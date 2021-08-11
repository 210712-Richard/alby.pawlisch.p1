package com.revature.beans;

import java.io.Serializable;
import java.util.UUID;

public class ExceedFunds implements Serializable {
	private UUID id;
	private UUID reimburseId;
	private Long amount;
	private String reason;
	private String bencoName;
	
	public ExceedFunds() {
		
	}

	public ExceedFunds(UUID id, UUID reimburseId, Long amount, String reason, String bencoName) {
		super();
		this.id = id;
		this.reimburseId = reimburseId;
		this.amount = amount;
		this.reason = reason;
		this.bencoName = bencoName;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getReimburseId() {
		return reimburseId;
	}

	public void setReimburseId(UUID reimburseId) {
		this.reimburseId = reimburseId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getBencoName() {
		return bencoName;
	}

	public void setBencoName(String bencoName) {
		this.bencoName = bencoName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((bencoName == null) ? 0 : bencoName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((reimburseId == null) ? 0 : reimburseId.hashCode());
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
		ExceedFunds other = (ExceedFunds) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (bencoName == null) {
			if (other.bencoName != null)
				return false;
		} else if (!bencoName.equals(other.bencoName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (reimburseId == null) {
			if (other.reimburseId != null)
				return false;
		} else if (!reimburseId.equals(other.reimburseId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExceedFunds [id=" + id + ", reimburseId=" + reimburseId + ", amount=" + amount + ", reason=" + reason
				+ ", bencoName=" + bencoName + "]";
	}
	
	

}

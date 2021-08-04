package com.revature.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Reimbursement implements Serializable {
	private UUID id;
	private String employee;
	private String reimburseForm;
	private String approvedEmail;
	private LocalDate submissionDate;
	private LocalDate lastApprovalDate;
	private Boolean superApproval;
	private Boolean headApproval;
	private Boolean bencoApproval;
	private Boolean urgent;
	private Long requestAmount;
	private Long approvedAmount;
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getReimburseForm() {
		return reimburseForm;
	}
	public void setReimburseForm(String reimburseForm) {
		this.reimburseForm = reimburseForm;
	}
	public String getApprovedEmail() {
		return approvedEmail;
	}
	public void setApprovedEmail(String approvedEmail) {
		this.approvedEmail = approvedEmail;
	}
	public LocalDate getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(LocalDate submissionDate) {
		this.submissionDate = submissionDate;
	}
	public LocalDate getLastApprovalDate() {
		return lastApprovalDate;
	}
	public void setLastApprovalDate(LocalDate lastApprovalDate) {
		this.lastApprovalDate = lastApprovalDate;
	}
	public Boolean getSuperApproval() {
		return superApproval;
	}
	public void setSuperApproval(Boolean superApproval) {
		this.superApproval = superApproval;
	}
	public Boolean getHeadApproval() {
		return headApproval;
	}
	public void setHeadApproval(Boolean headApproval) {
		this.headApproval = headApproval;
	}
	public Boolean getBencoApproval() {
		return bencoApproval;
	}
	public void setBencoApproval(Boolean bencoApproval) {
		this.bencoApproval = bencoApproval;
	}
	public Boolean getUrgent() {
		return urgent;
	}
	public void setUrgent(Boolean urgent) {
		this.urgent = urgent;
	}
	public Long getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(Long requestAmount) {
		this.requestAmount = requestAmount;
	}
	public Long getApprovedAmount() {
		return approvedAmount;
	}
	public void setApprovedAmount(Long approvedAmount) {
		this.approvedAmount = approvedAmount;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvedAmount == null) ? 0 : approvedAmount.hashCode());
		result = prime * result + ((approvedEmail == null) ? 0 : approvedEmail.hashCode());
		result = prime * result + ((bencoApproval == null) ? 0 : bencoApproval.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((headApproval == null) ? 0 : headApproval.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastApprovalDate == null) ? 0 : lastApprovalDate.hashCode());
		result = prime * result + ((reimburseForm == null) ? 0 : reimburseForm.hashCode());
		result = prime * result + ((requestAmount == null) ? 0 : requestAmount.hashCode());
		result = prime * result + ((submissionDate == null) ? 0 : submissionDate.hashCode());
		result = prime * result + ((superApproval == null) ? 0 : superApproval.hashCode());
		result = prime * result + ((urgent == null) ? 0 : urgent.hashCode());
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
		Reimbursement other = (Reimbursement) obj;
		if (approvedAmount == null) {
			if (other.approvedAmount != null)
				return false;
		} else if (!approvedAmount.equals(other.approvedAmount))
			return false;
		if (approvedEmail == null) {
			if (other.approvedEmail != null)
				return false;
		} else if (!approvedEmail.equals(other.approvedEmail))
			return false;
		if (bencoApproval == null) {
			if (other.bencoApproval != null)
				return false;
		} else if (!bencoApproval.equals(other.bencoApproval))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (headApproval == null) {
			if (other.headApproval != null)
				return false;
		} else if (!headApproval.equals(other.headApproval))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastApprovalDate == null) {
			if (other.lastApprovalDate != null)
				return false;
		} else if (!lastApprovalDate.equals(other.lastApprovalDate))
			return false;
		if (reimburseForm == null) {
			if (other.reimburseForm != null)
				return false;
		} else if (!reimburseForm.equals(other.reimburseForm))
			return false;
		if (requestAmount == null) {
			if (other.requestAmount != null)
				return false;
		} else if (!requestAmount.equals(other.requestAmount))
			return false;
		if (submissionDate == null) {
			if (other.submissionDate != null)
				return false;
		} else if (!submissionDate.equals(other.submissionDate))
			return false;
		if (superApproval == null) {
			if (other.superApproval != null)
				return false;
		} else if (!superApproval.equals(other.superApproval))
			return false;
		if (urgent == null) {
			if (other.urgent != null)
				return false;
		} else if (!urgent.equals(other.urgent))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", employee=" + employee + ", reimburseForm=" + reimburseForm
				+ ", approvedEmail=" + approvedEmail + ", submissionDate=" + submissionDate + ", lastApprovalDate="
				+ lastApprovalDate + ", superApproval=" + superApproval + ", headApproval=" + headApproval
				+ ", bencoApproval=" + bencoApproval + ", urgent=" + urgent + ", requestAmount=" + requestAmount
				+ ", approvedAmount=" + approvedAmount + "]";
	}
	
	
	

}

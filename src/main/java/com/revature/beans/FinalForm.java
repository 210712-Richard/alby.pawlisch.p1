package com.revature.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class FinalForm implements Serializable {
	private UUID id;
	private String employee;
	private Boolean approved;
	private LocalDate submissionDate;
	private FormType formType;
	private Boolean urgent;
	private String filename;
	
	public FinalForm() {
		super();
	}

	public FinalForm(UUID id, String employee, Boolean approved, LocalDate submissionDate, FormType formType, Boolean urgent,
			String filename) {
		this();
		this.id = id;
		this.employee = employee;
		this.approved = approved;
		this.submissionDate = submissionDate;
		this.formType = formType;
		this.urgent = urgent;
		this.filename = filename;
	}

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

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public LocalDate getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(LocalDate submissionDate) {
		this.submissionDate = submissionDate;
	}

	public FormType getFormType() {
		return formType;
	}

	public void setFormType(FormType formType) {
		this.formType = formType;
	}

	public Boolean getUrgent() {
		return urgent;
	}

	public void setUrgent(Boolean urgent) {
		this.urgent = urgent;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approved == null) ? 0 : approved.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((formType == null) ? 0 : formType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((submissionDate == null) ? 0 : submissionDate.hashCode());
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
		FinalForm other = (FinalForm) obj;
		if (approved == null) {
			if (other.approved != null)
				return false;
		} else if (!approved.equals(other.approved))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (formType != other.formType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (submissionDate == null) {
			if (other.submissionDate != null)
				return false;
		} else if (!submissionDate.equals(other.submissionDate))
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
		return "FinalForm [id=" + id + ", employee=" + employee + ", approved=" + approved + ", submissionDate="
				+ submissionDate + ", formType=" + formType + ", urgent=" + urgent + ", filename=" + filename + "]";
	}


	
	
	
	
}
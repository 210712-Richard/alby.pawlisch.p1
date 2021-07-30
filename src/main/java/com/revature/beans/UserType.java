package com.revature.beans;

import java.io.Serializable;

public enum UserType implements Serializable {
	BENCO,
	HEAD,
	SUPERVISOR,
	EMPLOYEE
	// if someone is an employee's department head AND supervisor,
	// then leave supervisor spot null and set it so that if supervisor is null
	// the request goes right to department head

}

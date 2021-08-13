package com.revature.services;

import com.revature.beans.Reimbursement;

public interface NotificationService {

	void sendChangeAmountNotif(Reimbursement reimbursement);

	void sendBencoEscalation(Reimbursement reimbursement);

}

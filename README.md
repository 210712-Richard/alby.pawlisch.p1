# TUITION REIMBURSEMENT MANAGEMENT SYSTEM

## Project Description
The TRMS processes tuition reimbursements filed by employees for job relevant events. These include university courses, seminars, certifications, and more. After users file for a reimbursement the reimbursement works its way up the chain of command to the Benefits Coordinator, who gives the final approval. Anyone in the approval process may deny the reimbursement.

## Technologies Used
* Javalin - version 3.13.10
* JUnit - version 5.7.2
* Mockito - version 2.21.0
* Log42J - version 2.11.2 
* Amazon Keyspaces
* Amazon S3

## Features
* If the user filing for a reimbursement uploads an email from their supervisor approving the request the supervisor part of the approval process is skipped.
* The predicted reimbursement amount depends on what type of course the user put into the "ClassType" header
* Users receive a notification in their inbox if the amount their reimbursement is approved for is changed by the Benefits Coordinator

To-Do List:
* Change the approval email naming system so that it works when the reimbursement file isn't a .docx
* Keep track of how much an employee has been reimbursed for that year. If it passes $1000 alert the Benefits Coordinator and register why/how much the employee has gone over.

## Getting Started

## Usage

## License
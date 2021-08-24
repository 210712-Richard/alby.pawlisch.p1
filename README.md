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
* The predicted reimbursement amount depends on what type of course the user put into the ClassType header
* Users receive a notification in their inbox if the amount their reimbursement is approved for is changed by the Benefits Coordinator

To-Do List:
* Change the approval email naming system so that it works when the reimbursement file isn't a .docx
* Keep track of how much an employee has been reimbursed for that year. If it passes $1000 alert the Benefits Coordinator and register why/how much the employee has gone over.

## Getting Started
Use `cd` to go to the file you want the project in.

Clone the repository using:
`git clone https://github.com/210712-Richard/alby.pawlisch.p1.git`

In "Run Configurations", select or create the driver that runs this project. Go to it's environment variables tab. Add an AWS_USER variable with the value of your keyspace credentials username. Add an AWS_PASS variable with the value of your keyspace credentials password.
*If you're using your own keyspace account, make sure your keyspace is named project1.*

Create a Truststore for AWS Keyspaces

### BASH Terminal TrustStore (these commands will not work in powershell)
1. Open a BASH terminal inside of src/main/resources folder.
2. `curl https://certs.secureserver.net/repository/sf-class2-root.crt -O`
3. `openssl x509 -outform der -in sf-class2-root.crt -out temp_file.der`
4. `keytool -import -alias cassandra -keystore cassandra_truststore.jks -file temp_file.der`
5. Use the password: `p4ssw0rd`
6. Say yes when prompted

* If you're using your own keyspace, uncomment the command `instantiateDatabase();` in line 23 of the Driver the first time you run the application. 

## Usage

### Logging In and Out
1. POST to `http://localhost:8080/users`
2. Have `{"username" : "[username]"}` in the body of the request
3. You can log in as Damian (employee), Barbara (his supervisor), Bruce (the department head), Gordon (the Benefits Coordinator), or others.
    * The full list of users is in the DataBaseCreator file in com.revature package
4. Logging out is the same URL and request body, just a DELETE instead of a POST.

### Submitting a Reimbursement
1. PUT to `http://localhost:8080/reimbursements`
2. Attach a .docx file in the binary body of the request.
3. Create the headers `Extension`, `Urgent`, and `ClassType`
4. Fill the `Extension` header value with `docx` and the `Urgent` with either `true` or `false`.
5. Fill the `ClassType` header value with either `COURSE`(for a University Course), `SEMINAR`(for a Seminar), `PREP`(for a Certification Preparation Class), `CERTIF`(for a Certification), `TECH`(for Technical Training), or `OTHER`.

### Viewing Reimbursements
1. GET http://localhost:8080/reimbursements/{username}
2. For a specific reimbursement do GET http://localhost:8080/reimbursements/{username}/{reimbursementId}

### Supervisor or Department Head Approval
1. PUT to http://localhost:8080/reimbursements/{username}/{reimbursementId}/approval
2. In the body have either `{"superApproval":"[approval status]"}` or `{"headApproval":"[approval status]"}`

### Submit an Email to Skip Supervisor Approval
1. PUT to http://localhost:8080/reimbursements/{username}/{reimbursementId}/email
2. Attach to email document or image in the binary body.
3. Create the header `Extension` and fill the value with the extension of the email file.

### Benefits Coordinator Approval
1. PUT to http://localhost:8080/reimbursements/{username}/{reimbursementId}/approval/benefits
2. Body of request has `{"bencoApproval":"[approval status]", "approvedAmount":"[approved amount]"}`
3. Create header FormType
4. Input `GRADE` or `PRESENTATION` based on whether the class has a final grade or if the employee must make a presentation for their supervisor.

### Viewing Final Forms
*Who can view the final form depends on the type. Grade types can be viewed by employee and benefits coordinator, presentation types can by viewed by employee and their supervisor. Only the employee can view their list of final forms*
1. GET http://localhost:8080/finalforms/{username}
2. To get a specific final form, GET http://localhost:8080/finalforms/{username}/{finalFormId}

### Final Form Submission
1. PUT to http://localhost:8080/finalforms/{username}/{finalFormId}/files
2. Attach the document in the binary of the request body
3. Create the `Extension` header and make the value the file extension of the document.

### Final Form Approval
1. http://localhost:8080/finalforms/{username}/{finalFormId}/approval
2. Make the request body `{"approved":"[approval status}`

### Check Inbox
1. GET http://localhost:8080/notifications

### Download a Reimbursement or Final Form
*GET http://localhost:8080/reimbursements/{username}/{reimbursementId}/files for reimbursement
*GET http://localhost:8080/finalforms/{username}/{reimbursementId}/files for final form


## License
This project uses the following license: <license_name>.
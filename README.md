# Links for Practice

<a href="https://rahulshettyacademy.com/#/practice-project"> https://rahulshettyacademy.com/#/practice-project</a>
<br>
<a href="https://drive.google.com/file/d/1A3Q_HX8A_GtamXs5kpdZ_7jM8W-OajKS/view">https://drive.google.com/file/d/1A3Q_HX8A_GtamXs5kpdZ_7jM8W-OajKS/view </a>

------------------------------------------------------------------------------------
# Steps to setup jira

download from this link:
https://www.atlassian.com/software/jira/download-journey

For Installation refer : https://thoughtworks.udemy.com/course/rest-api-automation-testing-rest-assured/learn/lecture/6580656#questions/2349046

To Start JIRA in my local
Go to /Applications/atlassian-jira-software-8.22.0-standalone/bin
sh start-jira.sh

Now go to: http://localhost:8080/ 
click next next and give all details

Organization name: sriram
JIRA Server Key:
B9CC-7WB7-5EV9-3O5V

email: sriramkukkadapu@gmail.com
username: sriramkukkadapu
pwd: test123

project name created 
RSA

JIRA API docs for documentation:
https://docs.atlassian.com/software/jira/docs/api/REST/7.6.1/

Set JIRA to allow attachments  here it should be turned ON
http://localhost:8080/secure/admin/ViewAttachmentSettings.jspa

# Important Notes


To ignore HTTPS certificate validation by rest assured give this in given section <br>
```java
given().relaxedHTTPSValidation()
```

------------------------------------------------------------------------------------		
SessionFilter -> is used to filter session from auth api and pass it across the entire test script.

SessionFilter  session=new SessionFilter(); 
```java
 given() 
	.filter(session) 
	.post("<auth api url>") 
```    
	
subsequent API's this session can be used like below.
```java
	given() 
	.filter(session) 
	.when().post("<add user url>")
```	
------------------------------------------------------------------------------------		
Attach files in request

	given()
		.header("Content-Type","multipart/form-data")
		.pathParam("id", issueId )
		.filter(session)
		.multiPart("file", new File("attachment.png"))
		.when().post("rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);




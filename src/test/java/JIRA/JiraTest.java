package JIRA;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.List;

public class JiraTest {
public static void main(String args[]) {
	RestAssured.baseURI = "http://localhost:8080/";
	String issueId="10002";
	String response;
	JsonPath path;
	SessionFilter  session=new SessionFilter(); 
	
	// ========= Get Authentication =========
	response = given()
		.relaxedHTTPSValidation()
		.header("Content-Type","application/json")
		.body("{ \"username\": \"sriramkukkadapu\", \"password\": \"test123\" }")
		.log().all()
		.filter(session)
		.when()
		.post("rest/auth/1/session")
		.then().log().all().extract().response().asString();
	
	
	// ========= Add comment on a story=========
	response = given()
		.pathParam("id", issueId)
		.header("Content-Type","application/json")
		.body("{\n"
				+ "    \"body\": \"This comment is added from Postman using Rest API\",\n"
				+ "    \"visibility\": {\n"
				+ "        \"type\": \"role\",\n"
				+ "        \"value\": \"Administrators\"\n"
				+ "    }\n"
				+ "}")
		.log().all()
		.filter(session)
		.when()
		.post("rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		path = new JsonPath(response);
		String commentId = path.getString("id");
		System.out.println("========> comment id: "+commentId);
	
	
	// ========= Add attachment to a story =========
	given().header("X-Atlassian-Token","no-check")
		.header("Content-Type","multipart/form-data")
		.pathParam("id", issueId )
		.filter(session)
		.multiPart("file", new File("attachment.png"))
		.when().post("rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
	
	
	// ========= Get Issue details  =========
	response = given()
		.pathParam("id", issueId)
		.queryParam("fields", "comment")
		.filter(session)
		.when()
		.get("rest/api/2/issue/{id}")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		path = new JsonPath(response);
		List<String> commentIds = path.getList("fields.comment.comments.id");
		System.out.println(commentIds);
		System.out.println(commentIds.contains(commentId));
		int index = commentIds.indexOf(commentId);
		System.out.println(path.getString("fields.comment.comments["+index+"].body"));

	// ========= Delete comment  =========
	given()
		.filter(session)
		.pathParam("id", issueId)
		.pathParam("commentId", commentId)
		.when()
		.delete("rest/api/2/issue/{id}/comment/{commentId}")
		.then().assertThat().statusCode(204);
}
}

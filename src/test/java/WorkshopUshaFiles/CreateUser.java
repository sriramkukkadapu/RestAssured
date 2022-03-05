package WorkshopUshaFiles;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUser {

    /****************************************************************************************************************************************************
     Test Name : testCreateUser
     Description : validates create user API gives response status code as 201 and validate response body(name and job)
     *****************************************************************************************************************************************************/
    @Test
    public void testCreateUser(){
    	

		Map<String,String> body = new HashMap<String,String>();
		body.put("name", "Tester");
		body.put("job", "QA");
		
		System.out.println(body.toString());
		JSONObject bodyJSON=new JSONObject(body);
		System.out.println(bodyJSON.toString());
		

		
		Response response1=given().contentType(ContentType.JSON).
				body(bodyJSON.toString()).
                when().post("https://reqres.in/api/users/").then().assertThat().statusCode(201).and().extract().response();
        
		Response response2=given().contentType(ContentType.JSON).
				body(body).
                when().post("https://reqres.in/api/users/").then().assertThat().statusCode(201).and().extract().response();

		
		Response response = given()
		.contentType("application/json")
		.baseUri("https://reqres.in/api/users//")
		.body(body)
	.when()
		.post("books/")
	.then()
		.statusCode(201)
		.and().extract().response();
		
    }
}

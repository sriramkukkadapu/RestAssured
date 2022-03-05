package WorkshopUshaFiles;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class herokuauth {

    /****************************************************************************************************************************************************
     Test Name : testCreateUser
     Description : validates create user API gives response status code as 201 and validate response body(name and job)
     *****************************************************************************************************************************************************/
    @Test
    public void testCreateUser(){
    	Map<String,String> authPayload = new HashMap<String,String>();
		authPayload.put("username", "admin");
		authPayload.put("password", "password123");
		
		
		//GIVEN
		RestAssured
		   .given()
			  .baseUri("https://restful-booker.herokuapp.com/auth")
			  .contentType(ContentType.JSON)
			  .body(authPayload)
			  .log()
			  .all()
		// WHEN
		   .when()
			   .post()
		// THEN
		   .then()
			   .assertThat()
			   .statusCode(200)
			   .log()
			   .all();
		
    }
}

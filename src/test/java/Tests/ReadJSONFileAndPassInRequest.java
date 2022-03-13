package Tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import FetchPayload.Payload;
import Utils.ReusableMethods;

public class ReadJSONFileAndPassInRequest {

	public static void main(String args[]) throws IOException {
		//given
		//when
		//then
		
		JsonPath path;
		String response;
		
		String fileData = new String(Files.readAllBytes(Paths.get("datafiles/addPlace.json")));
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		/*=========== Post Basic ==========*/
		given().queryParam("key", "qaclick123")
		.log().all()
		.headers("Content-Type", "application/json")
		.body(fileData)
		
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);
		
		

		
		
		
	}
}

package Tests;

import org.testng.annotations.Test;

import FetchPayload.Payload;
import Utils.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class AddBookTest {

	
	@Test
	public void addBook() {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = 
			given()
				.log().all()
				.header("Content-Type","application/json")
				.body(Payload.addBook("jahsgdasd","761253"))
			.when()
				.post("/Library/Addbook.php")
			.then()
				.assertThat().statusCode(200).extract().response().asString();
		
		JsonPath  path = ReusableMethods.rawToJson(response);
		String id = path.getString("ID");
		System.out.println(id);		
	}
	
}

package Tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import FetchPayload.Payload;
import Utils.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DataProviderExample {

	
	@Test(dataProvider = "booksData")
	public void addBook(String aisle, String isbn) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = 
			given()
//				.log().all()
				.header("Content-Type","application/json")
				.body(Payload.addBook(aisle,isbn))
			.when()
				.post("/Library/Addbook.php")
			.then()
				.assertThat().statusCode(200).extract().response().asString();
		
		JsonPath  path = ReusableMethods.rawToJson(response);
		String id = path.getString("ID");
		System.out.println(id);		
	}
	
	@DataProvider(name="booksData")
	public Object[][] getData() {
		
		Object[][] data = new Object[][] {
			
			{"kjhassda","u712326"},
			{"jhagssds","761253783123"},
			{"jhssgsds","76125348123"}
			
		} ;
		
		return data;
	}
	
}

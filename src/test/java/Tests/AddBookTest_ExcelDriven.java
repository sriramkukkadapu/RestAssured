package Tests;

import org.testng.annotations.Test;

import FetchPayload.Payload;
import Utils.ReusableMethods;
import Utils.excelUtil;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AddBookTest_ExcelDriven {

	
	@Test
	public void addBook() throws IOException {
		RestAssured.baseURI = "http://216.10.245.166";
//		String data = "{\n"
//				+ "\n"
//				+ "\"name\":\"Learn Appium Automation with Java2\",\n"
//				+ "\"isbn\":\"asdasd\",\n"
//				+ "\"aisle\":\"229\",\n"
//				+ "\"author\":\"John foe\"\n"
//				+ "}\n";
		
		excelUtil eu = new excelUtil();
		ArrayList<String> dataExcel = eu.getData("Add Book", "bookSheet");
		
		HashMap<String, Object> data = new HashMap<String,Object>();
		data.put("name", dataExcel.get(0) );
		data.put("isbn", dataExcel.get(1)  );
		data.put("aisle", dataExcel.get(2)  );
		data.put("author", dataExcel.get(3) );
		
		String response =
			given()
				.log().all()
				.header("Content-Type","application/json")
				.body(data)
			.when()
				.post("/Library/Addbook.php")
			.then()
				.assertThat().statusCode(200).extract().response().asString();
		
		JsonPath  path = new JsonPath(response);
		String id = path.getString("ID");
		System.out.println(id);		
	}
	
}

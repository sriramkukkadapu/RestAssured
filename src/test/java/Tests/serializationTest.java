package Tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojoForMapsAPI.AddPlace;
import pojoForMapsAPI.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class serializationTest {

	public static void main(String args[]) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		
		p.setLocation(location);
		
		p.setName("Frontline house");
		List<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shop");
		
		p.setTypes(types);
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		
		Response response = given()
											.header("Content-Type","application/json")
											.queryParam("key", "qaclick123")
											.body(p)
											.log().all()
										.when()
											.post("maps/api/place/add/json")
										.then()
											.assertThat().statusCode(200)
											.extract().response();
		
		System.out.println("============");
		System.out.println(response.asString());
		
	}
}

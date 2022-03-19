package Tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoForMapsAPI.AddPlace;
import pojoForMapsAPI.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderExample {

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
		
		RequestSpecification reqSpec = new RequestSpecBuilder()
					.setBaseUri("https://rahulshettyacademy.com")
					.setContentType(ContentType.JSON)
					.addQueryParam("key", "qaclick123")
					.build();
		
		ResponseSpecification resSpec =  new ResponseSpecBuilder()
				.expectContentType(ContentType.JSON)
				.expectStatusCode(200).build();
		
		Response response = 
											given()
												.spec(reqSpec)
												.body(p)
												.log().all()
											.when()
												.post("maps/api/place/add/json")
											.then()
												.spec(resSpec)
												.extract().response();
			
		System.out.println("============");
		System.out.println(response.asString());
		
	}
}

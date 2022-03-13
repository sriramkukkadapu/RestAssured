package Tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import FetchPayload.Payload;
import Utils.ReusableMethods;

public class First {

	public static void main(String args[]) {
		//given
		//when
		//then
		
		JsonPath path;
		String response;
		
		String body="{\n"
				+ "  \"location\": {\n"
				+ "    \"lat\": -38.383494,\n"
				+ "    \"lng\": 33.427362\n"
				+ "  },\n"
				+ "  \"accuracy\": 50,\n"
				+ "  \"name\": \"Frontline house\",\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\n"
				+ "  \"types\": [\n"
				+ "    \"shoe park\",\n"
				+ "    \"shop\"\n"
				+ "  ],\n"
				+ "  \"website\": \"http://google.com\",\n"
				+ "  \"language\": \"French-IN\"\n"
				+ "}\n"
				;
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		/*=========== Post Basic ==========*/
		given().queryParam("key", "qaclick123")
		.log().all()
		.headers("Content-Type", "application/json")
		.body(body)
		
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);
		
		
		/*=========== Assert basic ==========*/
		given().queryParam("key", "qaclick123")
		.headers("Content-Type", "application/json")
		.body(Payload.addPlace())
		
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Content-Type", "application/json;charset=UTF-8");
		
		
		/*=========== Extract response ==========*/
		response = given().log().all().queryParam("key", "qaclick123")
		.headers("Content-Type", "application/json")
		.body(Payload.addPlace())
		
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Content-Type", "application/json;charset=UTF-8")
		.extract().response().asString();
		
		//System.out.println(response);
		path = new JsonPath(response);
		String place_id = path.getString("place_id");
		System.out.println("place_id: "+ place_id);
		
		
		/*=========== Basic Put ==========*/
		response = given().log().all().queryParam("place_id", place_id)
		.headers("Content-Type", "application/json")
		.body(Payload.putPlace(place_id))
		
		.when().put("maps/api/place/add/json")
		.then().log().all().statusCode(200)
		.extract().response().asString();
		
		
		/*=========== Get after above Put and verify update successful ==========*/
		response = given().log().all()
		.queryParam("place_id", place_id)
		.queryParam("key", "qaclick123")
		
		.when().get("maps/api/place/get/json")
		.then().log().all().statusCode(200)
		.extract().response().asString();
		
		path = ReusableMethods.rawToJson(response);
		System.out.println(path.getString("address"));
		
		Assert.assertEquals(path.getString("address"), "29, side layout, cohen 09" );
		
		
		
	}
}

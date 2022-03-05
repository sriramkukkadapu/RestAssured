package Utils;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {

	public static JsonPath rawToJson(String data) {
		JsonPath path = new JsonPath(data);
		return path;
	}
}

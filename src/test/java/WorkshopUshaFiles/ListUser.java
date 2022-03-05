package WorkshopUshaFiles;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.JSONValue;

import org.json.JSONObject;
import org.json.JSONArray;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ListUser {

    /****************************************************************************************************************************************************
     Test Name : testListUser
     Description : validates listuser API gives response status code as 200 and prints the response of the API
     *****************************************************************************************************************************************************/
    @Test
    public void testListUser(){
    	  Response response=given().contentType(ContentType.JSON).
                  when().get("https://reqres.in/api/users").then().assertThat().statusCode(200).and().extract().response();
          
                //System.out.println("response of the list user API is  "+ response.asString());
                response.prettyPrint();
                
//method 1- using JSON path provided default by rest assured
                
                JsonPath path=response.jsonPath() ;
                

                List emails =path.get("data.email");
                List data_list=path.getList("data");
                System.out.println(data_list);
                
                int actual_size=path.getList("data").size();
                System.out.println(emails);
                
                Assert.assertEquals(path.get("per_page"),actual_size );
                
                
//method 2 - using org.json.JSONObject
                
                JSONObject obj = new JSONObject(response.asString());
                JSONArray jsonArray = obj.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++) {
                	System.out.println(jsonArray.getJSONObject(i).getString("email"));
                }
                

//method 3 - using org.json.simple.JSONObject

//                Object responseObj = JSONValue.parse(response.asString());
//                JSONObject responseJSONObj = (JSONObject) responseObj;
//                JSONArray array =  (JSONArray) responseJSONObj.get("data");
//                for(int i=0;i<array.size();i++) {
//                	JSONObject arrayObj = (JSONObject) array.get(i);
//                	System.out.println(arrayObj.get("email"));
//                }
                

    }
}

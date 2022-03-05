package Tests;

import org.testng.Assert;

import FetchPayload.Payload;
import io.restassured.path.json.JsonPath;

public class ParseComplexJson {

	public static void main(String args[]) {
		
		System.out.println(Payload.CoursePrice());
		JsonPath path= new JsonPath(Payload.CoursePrice());
		System.out.println(path.getInt("courses.size()"));
		System.out.println(path.getInt("dashboard.purchaseAmount"));
		System.out.println(path.getString("courses[0].title"));
		
		//print all courses details
		int size=path.getInt("courses.size()");
		System.out.println("************************************");
		for(int i=0;i<size;i++) {
			System.out.println("Title: "+path.getString("courses["+i+"].title"));
			System.out.println("Price: "+path.getString("courses["+i+"].price"));
			System.out.println("Copies: "+path.getString("courses["+i+"].copies"));
			System.out.println("");
		}
		
		//Print copies sold for RPA
		System.out.println("************************************");
		for(int i=0;i<size;i++) {
			if(path.getString("courses["+i+"].title").equals("RPA")) {
				System.out.println("Copies of RPA: "+path.getString("courses["+i+"].copies"));
				break;
			}
		}
		
		//check all courses amount equals to total amount
		int total=path.getInt("dashboard.purchaseAmount");
		int actualTotal = 0;
		for(int i=0;i<size;i++) {
			int copies = path.getInt("courses["+i+"].copies");
			int price = path.getInt("courses["+i+"].price");
			int courseTotalPrice = copies*price;
			actualTotal = actualTotal + courseTotalPrice;
		}
		Assert.assertEquals(actualTotal, total);
		
		
		
		
	}
}



/*
 *
 
  {
  "dashboard": {
    "purchaseAmount": 1162,
    "website": "rahulshettyacademy.com"
  },
  "courses": [
    {
      "title": "Selenium Python",
      "price": 50,
      "copies": 6
    },
    {
      "title": "Cypress",
      "price": 40,
      "copies": 4
    },
    {
      "title": "RPA",
      "price": 45,
      "copies": 10
    },
    {
      "title": "Appium",
      "price": 36,
      "copies": 7
    }
  ]
}
  
 * */

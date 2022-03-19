package Tests;

import static io.restassured.RestAssured.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OauthExample {

	public static void main(String args[]) {
		
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver=new ChromeDriver();
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.findElement(By.xpath("//input[@id=\"identifierId\"]")).sendKeys("sriramku1989@gmail.com");
//		driver.findElement(By.xpath("//input[@id=\"identifierId\"]")).sendKeys(Keys.ENTER);
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("P@ssw0rd123!@@@");
//		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Keys.ENTER);
//		driver.getCurrentUrl();
		
		String url_to_sign_in_ui = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
//		String url_after_signin = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWiEKibXjvH9FGawbYVpbuDujVhYglJCzNId-WcaXRhzgwMEfZ3eMOJJj72fnWKtEw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
//		String temp = url_after_signin.split("code=")[1];
//		String code = temp.split("&scope=")[0];
		
		String code =  "4%2F0AX4XfWgY4-z81VuoCYP0xguXrhU6iXjNmHxE9AT6inVwlHVveVy1nwFDcm88AscQwuQ3cg";
		System.out.println(code);
		
//		System.exit(0);;
		
		String accessTokenResponse = given().urlEncodingEnabled(false)
			.queryParams("code",code)
			.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type", "authorization_code")
			.log().all()
		.when()
			.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		System.out.println(accessTokenResponse);
		JsonPath path = new JsonPath(accessTokenResponse);
		String access_token = path.getString("access_token");
		System.out.println(access_token);
		
		
		String response = given()
		.queryParam("access_token", access_token)
		.expect().defaultParser(Parser.JSON)
	.when()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println("===========");
		System.out.println(response);
		
		
		GetCourse gc = given()
			.queryParam("access_token", access_token)
			.expect().defaultParser(Parser.JSON)
		.when()
			.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		System.out.println("===========");
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		gc.printData();
//		System.out.println(response);
		
		System.out.println("===========");
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		System.out.println(gc.getCourses().getApi().get(1).getPrice());
		
		System.out.println("===========");
		List<API> apiList = gc.getCourses().getApi();
		for(int i=0;i<apiList.size();i++) {
			if(apiList.get(i).getCourseTitle().equals("SoapUI Webservices testing")) {
				System.out.println(apiList.get(i).getPrice());
			}
		}
		
		String courseTitles[] = {
				"Selenium Webdriver Java",
				"Cypress",
				"Protractor"
		};
		
		System.out.println("===========");
		ArrayList<String> coursesList = new  ArrayList<String>();
		List<WebAutomation> waList  = gc.getCourses().getWebAutomation();
		for(int i=0;i<waList.size();i++) {
				System.out.println(waList.get(i).getCourseTitle());
				coursesList.add(waList.get(i).getCourseTitle());
		}
		
		List<String> expectedCoursesList= Arrays.asList(courseTitles);
		Assert.assertEquals(true, coursesList.equals(expectedCoursesList));
		
		
	}
}

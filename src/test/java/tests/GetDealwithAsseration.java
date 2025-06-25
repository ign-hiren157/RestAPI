package tests;

import org.testng.annotations.Test;

import io.restassured.response.Response;

//import io.restassured.matcher.ResponseAwareMatcher;
//import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;


import static io.restassured.RestAssured.*;
import org.testng.Assert;

public class GetDealwithAsseration {
	
	@Test (priority = 1)
	public void testValidCredentialsAndValidPath() {
	    baseURI = "http://t2.vas.api.ignitiongroup.co.za/vas/";
	    String dealpath = "Safebase/GetAvailableDeals";

	    //Response response = 
	    given()
	        .auth()
	        .preemptive()
	        .basic("VasAPI", "Test@123")
	    .when()
	        .get(dealpath)
	    .then()
	        .statusCode(200)
	        .body("Message", equalTo("Deals returned successfully."))
	        .extract().response();
	    //System.out.println("Response:\n" + response.prettyPrint());
}

	@Test (priority = 2)
	public void testInvalidCredentials() {
	    baseURI = "http://t2.vas.api.ignitiongroup.co.za/vas/";
	    String dealpath = "Safebase/GetAvailableDeals";

	    given()
	        .auth()
	        .preemptive()
	        .basic("WrongUser", "WrongPass")
	    .when()
	        .get(dealpath)
	    .then()
	        .statusCode(200)
	        .body("Message", equalTo("The Api User could not be found or has not been Authenticated."));
	}

	@Test (priority = 3, enabled = false)
	public void testInvalidPath() {
	    baseURI = "http://t2.vas.api.ignitiongroup.co.za/vas/";
	    String invalidPath = "Safebase/GetAvailableDealsInvalid";

	    given()
	        .auth()
	        .preemptive()
	        .basic("VasAPI", "Test@123")
	    .when()
	        .get(invalidPath)
	    .then()
	        .statusCode(500)
	        .body("Message", equalTo("500 Internal Server Error"));
	}

	@Test (priority = 4, enabled = true)
	public void testResponseNotEmpty() {
	    baseURI = "http://t2.vas.api.ignitiongroup.co.za/vas/";
	    String dealpath = "Safebase/GetAvailableDeals";

	    String responseBody = given()
	        .auth()
	        .preemptive()
	        .basic("VasAPI", "Test@123")
	    .when()
	        .get(dealpath)
	        .then()
	        .statusCode(200)
	        .extract()
	        .asString();

	    Assert.assertFalse(responseBody.trim().isEmpty(), "Response body should not be empty");
	}

}

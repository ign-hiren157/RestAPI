package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

public class GetDealwithAsseration {

	private static final String BASE_URI 		= "http://t2.vas.api.ignitiongroup.co.za/vas/";
	private static final String DEAL_PATH 		= "Safebase/GetAvailableDeals";
	private static final String VALID_USERNAME 	= "VasAPI";
	private static final String VALID_PASSWORD 	= "Test@123";

	@BeforeClass
	public void setup() {
		
		RestAssured.baseURI = BASE_URI;
	}

	@Test(priority = 1)
	public void testValidCredentialsAndValidPath() {

		Response response = 
				            given().
							auth().
							preemptive().
							basic(VALID_USERNAME, VALID_PASSWORD).
							when().
							get(DEAL_PATH)
							.then().
							statusCode(200).
							body("Message", equalTo("Deals returned successfully.")).
							extract().
							response();
							//System.out.println(response.asString());
				            System.out.println("Response:\n" + response.prettyPrint());
	}

	@Test(priority = 2)
	public void testInvalidCredentials() {
		
							given().
							auth().
							preemptive().
							basic("WrongUser", "WrongPass").
							when().
							get(DEAL_PATH).
							then().
							statusCode(200).
							body("Message", equalTo("The Api User could not be found or has not been Authenticated."));
	}

	@Test(priority = 3, enabled = false)
	public void testInvalidPath() {

		String invalidPath = "Safebase/GetAvailableDealsInvalid";

							given().
							auth().
							preemptive().
							basic(VALID_USERNAME, VALID_PASSWORD).
							when().
							get(invalidPath).
							then().
							statusCode(500).
							body("Message", equalTo("500 Internal Server Error"));
	}

	@Test(priority = 4, enabled = true)
	public void testResponseNotEmpty() {

		String responseBody = given().
							  auth().
							  preemptive().
							  basic(VALID_USERNAME, VALID_PASSWORD).
							  when().
							  get(DEAL_PATH).
							  then().
							  statusCode(200).
							  extract().
							  asString();

		Assert.assertFalse(responseBody.trim().isEmpty(), "Response body should not be empty");
	}

}

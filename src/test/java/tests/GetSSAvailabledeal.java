package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.testng.Assert;

public class GetSSAvailabledeal {

	@Test
	public void testgetavailabledeal()
	{
		baseURI = "http://t2.vas.api.ignitiongroup.co.za/vas/";
		String dealpath = "Safebase/GetAvailableDeals";
		
		try {
		//For Authantication --> auth().preemptive().basic("VasAPI", "Admin@123");			
		int statusCode = given().auth().preemptive().
						basic("VasAPI", "Test@123").
						get(dealpath).
						then().extract().
						statusCode();
		  				Assert.assertEquals(statusCode, 200, "Expected status code 200, but got " + statusCode);
		  //statusCode(200).
		  //response();
		  //log().all();
		
	    // Additional check: Ensure the response body is not empty (to catch possible issues)
        String responseBody = given().auth().preemptive()
                              .basic("VasAPI", "Test@123")
                              .get(dealpath)
                              .then().extract().body()
                              .asString();
          
          					  Assert.assertFalse(responseBody.isEmpty(), "Response body is empty");
          
          					  // Log the response body for inspection
          					  System.out.println("Response Body: " + responseBody);
          					  
          					 //given().auth().preemptive()
          	                //.basic("VasAPI", "Test@123")
          	                //.get(dealpath)
          	                //.then()
          	                //.body("DealId", notNullValue )  // Check if "someKey" exists in the response
          	                //.log().all();
	  } catch (Exception e) {
          // Handle network failures or other issues
          System.out.println("Test failed due to: " + e.getMessage());
          Assert.fail("Test failed due to an exception: " + e.getMessage());
      }
	}
}

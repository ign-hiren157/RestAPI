package tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class GetSSAvailabledeal {

	@Test
	public void testgetavailabledeal()
	{
		baseURI = "http://t2.vas.api.ignitiongroup.co.za/vas/";
		String dealpath = "Safebase/GetAvailableDeals";
		
		//For Authantication --> auth().preemptive().basic("VasAPI", "Admin@123");			
		given().
		auth().
		preemptive().
		basic("VasAPI", "Test@123").
		  get(dealpath).
		then().		 
		  statusCode(200).
		  //extract().
		  //response();
		  log().all();
	}
}

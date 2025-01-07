package tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class GetExample {
	
	@Test
	public void testget()
	{
		baseURI = "https://reqres.in/";
		String path = "api/users?page=2";
		
		//baseURI = "https://ssza-vas-api-prd-stg.azurewebsites.net/vas/";
		//String path = "Safebase/GetAvailableDeals";
				
		
		given().
		  get(path).
		then().
		  statusCode(200).
		  log().all();
	}

}

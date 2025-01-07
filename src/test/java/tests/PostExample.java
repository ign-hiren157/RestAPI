package tests;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class PostExample {

	@Test
	public void testpost() 
	{
	 JSONObject request = new JSONObject();
	 
	// request.put("name", "Hiren");
	 
	 baseURI = "https://reqres.in/";
	 String path = "/users";
	 
	 given().
	     body(request.toJSONString()).
	 when().
	     post(path).
	 then().
	     statusCode(404).
	     log().all();
	 
	 
	}
	
}

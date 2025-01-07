package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import utils.ExcelReader;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


public class PostSSDebitOrder {
	
	  static {
	        RestAssured.baseURI = "http://dapr.webuildgreatsoftware.co.za/v1.0/invoke/ssapiintegration/method/OnlineSale/CreateOrder";
	    }
	  @DataProvider(name = "excelDataProvider")
	    public Object[][] getExcelData() throws Exception {
	        return ExcelReader.getExcelData("./Data/excelData.xlsx", "Sheet1");
	    }

	  @Test(dataProvider = "excelDataProvider")
	public void testpostssorder(String IdNumber, String dealId) 
	{
		System.out.println("ID: " + IdNumber);
		System.out.println("DealID: " + dealId);
 		 
         String reqestBody = 
        		 "{\n" +
        	     "  \"PersonalDetails\": {\n" +
        	                "        \"Title\": 1,\n" +
        	                "        \"FirstName\": \"Hiren\",\n" +
        	                "        \"LastName\": \"Praja\",\n" +
        	                "        \"MSISDN\": \"0828309318\",\n" +
        	                "        \"HomeNumber\": \"0828309315\",\n" +
        	                "        \"WorkNumber\": \"0814833316\",\n" +
        	                "        \"EmailAddress\": \"Hirenrest@yopmail.com\",\n" +
        	              //  "        \"IdNumber\":   \"5707215908083\",\n" +
        	                "        \"IdNumber\": \"" + IdNumber + "\",\n" + // Use dynamic IdNumber
        	                "        \"IdentificationType\": 1,\n" +
        	                "        \"ResidentialAddress\": {\n" +
        	                "            \"Building\": \"Titanium building\",\n" +
        	                "            \"StreetNum\": 690,\n" +
        	                "            \"StreetName\": \"690 Schoeman St\",\n" +
        	                "            \"City\": \"Pretoria\",\n" +
        	                "            \"Suburb\": \"Gauteng\",\n" +
        	                "            \"PostCode\": \"0078\",\n" +
        	                "            \"AddressCategoryType\": 13\n" +
        	                "        },\n" +
        	                "        \"DeliveryAddress\": {\n" +
        	                "            \"Building\": \"Twin tower\",\n" +
        	                "            \"StreetNum\": 31,\n" +
        	                "            \"StreetName\": \"31 C R Swart Rd\",\n" +
        	                "            \"City\": \"Roodepoort\",\n" +
        	                "            \"Suburb\": \"Ruimsig\",\n" +
        	                "            \"PostCode\": \"2724\",\n" +
        	                "            \"AddressCategoryType\": 1\n" +
        	                "        }\n" +
        	                "    },\n" +
        	                "    \"BankingDetails\": {\n" +
        	                "        \"PayMethodType\": 1,\n" +
        	                "        \"AccountNumber\": \"47559304019\",\n" +
        	                "        \"BankID\": 7,\n" +
        	                "        \"BranchCode\": \"470010\",\n" +
        	                "        \"AccountType\": 1,\n" +
        	                "        \"DebitDay\": 20\n" +
        	                "    },\n" +
        	                "    \"ProductDetails\": [\n" +
        	                "        {\n" +
        	             //   "            \"DealId\": 337,\n" +
        	                "            \"DealId\": " + dealId + ",\n" + // Use dynamic DealId
        	                "            \"providerSpecific\": {\n" +
        	                "                \"providerSpecificType\": \"3\",\n" +
        	                "                \"MSISDN\": \"0942864809\"\n" +
        	                "            }\n" +
        	                "        }\n" +
        	                "    ],\n" +
        	                "    \"CampaignId\": 177,\n" +
        	                "    \"IsMarketic\": false\n" +
        	                "}";
	 given().
	 auth().
	 preemptive().
	 //basic("VasAPI", "Test@123").
	 basic("Inapp1","P@ssword1").
	 contentType(ContentType.JSON).
	     body(reqestBody).
	 when().
	     post(baseURI).
	 then().
	     //statusCode(201).
	     log().all();
	}
}

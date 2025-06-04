package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import static io.restassured.RestAssured.*;

public class SSDebitorderAsseration {

    static {
        RestAssured.baseURI = "http://dapr.webuildgreatsoftware.co.za/v1.0/invoke/ssapiintegration/method/OnlineSale/CreateOrder";
    }

    @DataProvider(name = "excelDataProvider")
    public Object[][] getExcelData() throws Exception {
        return ExcelReader.getExcelData("./Data/excelData.xlsx", "Sheet1");
    }

    @Test(dataProvider = "excelDataProvider")
    public void testpostssorder(String IdNumber, String dealId) {
        System.out.println("ID: " + IdNumber);
        System.out.println("DealID: " + dealId);

        String requestBody = "{\n" +
            "  \"PersonalDetails\": {\n" +
            "        \"Title\": 1,\n" +
            "        \"FirstName\": \"Hiren\",\n" +
            "        \"LastName\": \"Praja\",\n" +
            "        \"MSISDN\": \"0828309319\",\n" +
            "        \"HomeNumber\": \"0828309315\",\n" +
            "        \"WorkNumber\": \"0814833316\",\n" +
            "        \"EmailAddress\": \"Hirenrest@yopmail.com\",\n" +
            "        \"IdNumber\": \"" + IdNumber + "\",\n" +
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
            "            \"DealId\": " + dealId + ",\n" +
            "            \"providerSpecific\": {\n" +
            "                \"providerSpecificType\": \"3\",\n" +
            "                \"MSISDN\": \"0942864809\"\n" +
            "            }\n" +
            "        }\n" +
            "    ],\n" +
            "    \"CampaignId\": 177,\n" +
            "    \"IsMarketic\": false\n" +
            "}";

        try {
            // Sending POST request
            Response response = given()
                .auth()
                .preemptive()
                .basic("Inapp123", "P@ssword1")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI)
                .then()
                .extract()
                .response();

            // Validate the status code (Expect 201 for created resources, adjust if needed)
            int statusCode = response.getStatusCode();
            Assert.assertEquals(statusCode, 201, "Expected status code 201, but got " + statusCode);

            // Log the response for inspection
            response.prettyPrint();

            // Check if response body contains expected fields
            Assert.assertTrue(response.jsonPath().getString("someKey") != null, "Expected key 'someKey' is missing in the response.");

            // Example of handling specific failure if the response contains a certain error message
            String errorMessage = response.jsonPath().getString("errorMessage");
            if (errorMessage != null) {
                Assert.fail("Test failed due to error in response: " + errorMessage);
            }

        } catch (Exception e) {
            // Catch any exceptions and fail the test
            System.out.println("Test failed due to: " + e.getMessage());
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }
}

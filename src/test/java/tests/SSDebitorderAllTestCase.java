package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
//import io.restassured.module.jsv.JsonSchemaValidator;
import utils.ExcelReader;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class SSDebitorderAllTestCase {

    private static final String USERNAME = "Inapp123";
    private static final String PASSWORD = "P@ssword1";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://dapr.webuildgreatsoftware.co.za/v1.0/invoke/ssapiintegration/method/OnlineSale/CreateOrder";
    }

    @DataProvider(name = "excelDataProvider")
    public Object[][] getExcelData() throws Exception {
        return ExcelReader.getExcelData("./Data/excelData.xlsx", "Sheet1");
    }

    private String buildRequestBody(String idNumber, String dealId) {
        return "{\n" +
            "  \"PersonalDetails\": {\n" +
            "        \"Title\": 1,\n" +
            "        \"FirstName\": \"Hiren\",\n" +
            "        \"LastName\": \"Praja\",\n" +
            "        \"MSISDN\": \"0828309319\",\n" +
            "        \"HomeNumber\": \"0828309315\",\n" +
            "        \"WorkNumber\": \"0814833316\",\n" +
            "        \"EmailAddress\": \"Hirenrest@yopmail.com\",\n" +
            "        \"IdNumber\": \"" + idNumber + "\",\n" +
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
    }

    @Test(dataProvider = "excelDataProvider", priority = 1)
    public void testValidOrderSubmission(String idNumber, String dealId) {
        String requestBody = buildRequestBody(idNumber, dealId);

        Response response = given()
            .auth().preemptive().basic(USERNAME, PASSWORD)
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post()
        .then()
            .log().all()
            .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, 
        	    "Expected 200, but got " + response.getStatusCode() + ". Body:\n" + response.asString());

    }

    @Test(priority = 2)
    public void testInvalidDealId() {
        String requestBody = buildRequestBody("9001015009087", "3371");

        Response response = given()
            .auth().preemptive().basic(USERNAME, PASSWORD)
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post()
        .then()
            .log().all()
            .extract().response();

        Assert.assertNotEquals(response.getStatusCode(), 200);
        String message = response.jsonPath().getString("Message");
        Assert.assertNotNull(message);
    }

    @Test(priority = 3)
    public void testMissingIdNumber() {
        String requestBody = buildRequestBody("", "1001");

        Response response = given()
            .auth().preemptive().basic(USERNAME, PASSWORD)
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post()
        .then()
            .log().all()
            .extract().response();

        Assert.assertTrue(response.getStatusCode() >= 400);
    }

    @Test(priority = 4)
    public void testResponseTimeUnder2Seconds() {
        String requestBody = buildRequestBody("9001015009087", "1001");

        long time = given()
            .auth().preemptive().basic(USERNAME, PASSWORD)
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post()
            .time();

        Assert.assertTrue(time < 2000);
    }

    // New: Invalid Authentication
    @Test(priority = 5, enabled = true)
    public void testInvalidAuthentication() {
        String requestBody = buildRequestBody("9001015009087", "1001");

        Response response = given()
            .auth().preemptive().basic("WrongUser", "WrongPass")
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post()
        .then()
            .extract().response();

        System.out.println("Invalid Auth Response Code: " + response.getStatusCode());

        // Flexible assertion for unexpected behavior
        int status = response.getStatusCode();
        Assert.assertTrue(status == 401 || status == 403 || status >= 400, 
            "Expected authentication error, got: " + status);
    }

} 

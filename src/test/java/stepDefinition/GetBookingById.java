package stepDefinition;

import Hooks.Hooks;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import utilities.ConfigReader;

import static org.junit.Assert.assertEquals;


public class GetBookingById extends Hooks {

    /**
     * This method is used to get booking information by id.
     * It is using GET method "GET - baseUrl/booking/{id}"
     * It is required to send the header which is in Content-Type:application/json
     * And Authorization:Bearer {token}
     *
     * @param expectedStatus   Expected status code is 200
     */
    @Given("Get Booking Information By Id which is crated, then verify response and status code is {int}")
    public void getBookingInformationByIdWhichIsCratedThenVerifyResponseAndStatusCodeIsInt(int expectedStatus) {

        /* Create a new booking for getting bookingId */
        CreateBooking createBooking = new CreateBooking();
        int bookingId = createBooking.createsANewBooking(200);


        baseUrl = ConfigReader.getProperty("booking.baseUrl");
        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .build();

        response = RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .get("/booking/" + bookingId);

        /* Create a JsonPath object to handle and navigate the JSON response. */
        JsonPath jsonPath = response.jsonPath();

        /* Verify the response body */
        assertEquals("Inar", jsonPath.getString("firstname"));
        assertEquals("Academy", jsonPath.getString("lastname"));
        assertEquals(1000, jsonPath.getInt("totalprice"));
        Assert.assertTrue(jsonPath.getBoolean("depositpaid"));
        assertEquals("2023-01-01", jsonPath.getString("bookingdates.checkin"));
        assertEquals("2023-01-02", jsonPath.getString("bookingdates.checkout"));
        assertEquals("Breakfast", jsonPath.getString("additionalneeds"));

        assertEquals(expectedStatus, response.getStatusCode());
    }
}
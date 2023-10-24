package stepDefinition;

import Hooks.BaseClass;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.junit.Assert;

import static Hooks.Hooks.info;

public class CreateBooking extends BaseClass {
    JSONObject bookingDates= new JSONObject();
    JSONObject body = new JSONObject();

    /**
     * This method is used to create a new booking.
     * It is using POST method "POST - baseUrl/booking"
     * It is required to send the body which is in JSON format.It contains the following fields:
     * {
     * firstname:
     * lastname:
     * totalprice:
     * depositpaid:
     * bookingdates:{
     * checkin:
     * checkout:
     * }
     * additionalneeds:
     * }
     * And it is required to send the header which is in Content-Type:application/json
     * and Accept:application/json
     *
     * @param expectedStatus Expected status code is 200
     * @return bookingId
     */
    @Given("Creates a new booking, then verify response and status code {int}")
    public int createsANewBooking(int expectedStatus) {

        info("Create Booking Test Started");

        body.put("firstname", "Inar");
        body.put("lastname", "Academy");
        body.put("totalprice", 1000);
        body.put("depositpaid", true);
        body.put("additionalneeds", "breakfast");
        body.put("bookingdates",
                bookingDates.put(
                        "checkin", "2023-01-01"));
        body.put("bookingdates",
                bookingDates.put(
                        "checkout", "2023-01-02"));

        info("Created Booking Body: " + body);

        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("booking")
                .build();

        response = RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .accept("application/json")
                .body(body)
                .when()
                .post();

        /* Create a JsonPath object to handle and navigate the JSON response. */

        JsonPath jsonPath = response.jsonPath();
        info("Response: " + jsonPath.prettify());
        info("Status Code: " + response.getStatusCode());

        /* Assert the response body and status code */

        Assert.assertEquals("Inar", jsonPath.getString("booking.firstname"));
        Assert.assertEquals("Academy", jsonPath.getString("booking.lastname"));
        Assert.assertEquals(1000, jsonPath.getInt("booking.totalprice"));
        Assert.assertTrue(jsonPath.getBoolean("booking.depositpaid"));
        Assert.assertEquals("2023-01-01", jsonPath.getString("booking.bookingdates.checkin"));
        Assert.assertEquals("2023-01-02", jsonPath.getString("booking.bookingdates.checkout"));
        Assert.assertEquals("Breakfast", jsonPath.getString("booking.additionalneeds"));

        Assert.assertEquals(expectedStatus, response.getStatusCode());

        int bookingId = jsonPath.getInt("bookingid");
        info("Booking ID: " + bookingId);

        return bookingId;
    }
}
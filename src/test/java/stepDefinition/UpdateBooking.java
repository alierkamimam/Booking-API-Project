package stepDefinition;

import Hooks.Hooks;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import utilities.ConfigReader;

import static org.junit.Assert.assertEquals;

public class UpdateBooking extends Hooks {

    /**
     * This method is used to update booking information by id
     * It is using PUT method "PUT - baseUrl/booking/{id}"
     * It is required to send the header which is in Content-Type:application/json, Cookie:token={token}
     * And Accept:application/json
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
     *
     * @param expectedStatus Expected status code is 200
     */
    @Given("Update Booking Information By Id which is crated, then verify response and status code is {int}")
    public void updateBookingInformationByIdWhichIsCratedThenVerifyResponseAndStatusCodeIsInt(int expectedStatus) {

        /* Firstname and lastname are updated */

        String body = "{\n" +
                "    \"firstname\":\"John\",\n" +
                "    \"lastname\":\"Smith\",\n" +
                "    \"totalprice\":111,\n" +
                "    \"depositpaid\":true,\n" +
                "    \"bookingdates\":{\n" +
                "        \"checkin\":\"2018-01-01\",\n" +
                "        \"checkout\":\"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\":\"Breakfast\"\n" +
                "\n" +
                "}";

        /* Create a new booking for getting bookingId */
        CreateBooking createBooking = new CreateBooking();
        int bookingId = createBooking.createsANewBooking(200);


        baseUrl = ConfigReader.getProperty("booking.baseUrl");
        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/booking/" + bookingId)
                .build();

        response = RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .accept("application/json")
                .header("Cookie", "token=" + getToken())
                .body(body)
                .when()
                .put();

        /* Create a JsonPath object to handle and navigate the JSON response. */
        JsonPath jsonPath = response.jsonPath();

        /* Verify the response body */
        assertEquals("John", jsonPath.getString("firstname"));
        assertEquals("Smith", jsonPath.getString("lastname"));

        assertEquals(expectedStatus, response.getStatusCode());
    }
}
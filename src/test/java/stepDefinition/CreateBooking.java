package stepDefinition;

import Hooks.Hooks;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import utilities.ConfigReader;

public class CreateBooking extends Hooks {

    /**
     * This method is used to create a new booking.
     * It is using POST method "POST - baseUrl/booking"
     * It is required to send the body which is in JSON format.It contains the following fields:
     *  {
     *      firstname:
     *      lastname:
     *      totalprice:
     *      depositpaid:
     *      bookingdates:{
     *           checkin:
     *           checkout:
     *      }
     *      additionalneeds:
     *  }
     * And it is required to send the header which is in Content-Type:application/json and Accept:application/json
     *
     * @param expectedStatus    Expected status code is 200
     */
    @Given("Creates a new booking, then verify response and status code {int}")
    public  int createsANewBooking(int expectedStatus) {

        String body = "{\n" +
                "    \"firstname\":\"Inar\",\n" +
                "    \"lastname\":\"Academy\",\n" +
                "    \"totalprice\":1000,\n" +
                "    \"depositpaid\":true,\n" +
                "    \"bookingdates\":{\n" +
                "        \"checkin\":\"2023-01-01\",\n" +
                "        \"checkout\":\"2023-01-02\"\n" +
                "    },\n" +
                "    \"additionalneeds\":\"Breakfast\"\n" +
                "\n" +
                "}";

        baseUrl = ConfigReader.getProperty("booking.baseUrl");

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

        /* Assert the response body and status code */

        Assert.assertEquals("Inar", jsonPath.getString("booking.firstname"));
        Assert.assertEquals("Academy", jsonPath.getString("booking.lastname"));
        Assert.assertEquals(1000, jsonPath.getInt("booking.totalprice"));
        Assert.assertTrue(jsonPath.getBoolean("booking.depositpaid"));
        Assert.assertEquals("2023-01-01", jsonPath.getString("booking.bookingdates.checkin"));
        Assert.assertEquals("2023-01-02", jsonPath.getString("booking.bookingdates.checkout"));
        Assert.assertEquals("Breakfast", jsonPath.getString("booking.additionalneeds"));

        Assert.assertEquals(expectedStatus, response.getStatusCode());

       return jsonPath.getInt("bookingid");
    }
}
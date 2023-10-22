package stepDefinition;

import Hooks.BaseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import utilities.ConfigReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import static Hooks.Hooks.info;
import static org.junit.Assert.assertEquals;

public class DeleteBooking extends BaseClass {

    int bookingId;

    /**
     * This method is used to delete the booking with ID
     * It is using DELETE method "DELETE - baseUrl/booking/{id}"
     * It is required to send the header which is in Content-Type:application/json, Cookie:token={token}
     *
     * @param expectedStatus Expected status code is 201
     */
    @Given("Delete the Booking by id which is created, then verify the status code is {int}")
    public void deleteTheBookingByIdWhichIsCreatedThenVerifyTheStatusCodeIs(int expectedStatus) {

        info("Delete Booking Test Started");

        /* Create a new booking for getting bookingId */
        CreateBooking createBooking = new CreateBooking();
        bookingId = createBooking.createsANewBooking(200);

        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/booking/" + bookingId)
                .build();

        response = RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + getToken())
                .when()
                .delete();

        Assert.assertEquals(response.statusCode(), expectedStatus);
    }

    /**
     * Check if the booking is deleted
     * It is using GET method "GET - baseUrl/booking/{id}" (GetBookingById -> stepDefinition)
     *
     * @param expectedStatus Expected status code is 404
     */
    @And("Get Booking Information By Id which is deleted, then verify response and status code is {int}")
    public void getBookingInformationByIdWhichIsDeletedThenVerifyResponseAndStatusCodeIs(int expectedStatus) throws IOException {

        info("Check if the booking is deleted");

        response = RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .get("/booking/" + bookingId);

        String notFound = "Not Found";
        int actualStatusCode = response.getStatusCode();
        String actualMessage = response.getBody().asString();
        info("Response Body: " + actualMessage);
        info("Status Code: " + actualStatusCode);

        Assert.assertEquals(notFound, actualMessage);
        Assert.assertEquals(actualStatusCode, expectedStatus);

    }
}
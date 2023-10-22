package Hooks;


import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyData;
import io.restassured.specification.RequestSpecification;
import utilities.Authentication;
import utilities.ConfigReader;

public class BaseClass {

    /**
     * This is the base class for BOOKING the test classes
     */
    public static final String baseUrl = ConfigReader.getProperty("booking.baseUrl");
    public static RequestSpecification spec;
    public static Response response;

    /**
     * This method is used to generate token
     *
     * @return token
     */
    public String getToken() {
        return Authentication.generateToken();
    }
}
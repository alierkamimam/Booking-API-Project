package Hooks;


import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.Authentication;

public class Hooks {

    /**
     * This is the base class for BOOKING the test classes
     */
    public static String baseUrl;
    public static RequestSpecification spec;
    public static Response response;

    /**
     * This method is used to generate token
     * @return token
     */
    public String getToken() {
       return Authentication.generateToken();

    }
}
package utilities;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class Authentication {
    public static RequestSpecification spec;

    /**
     * This method is used to generate token
     *
     * @return  token
     */
    public static String generateToken() {

        spec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("booking.baseUrl"))
                .build();

        JSONObject body = new JSONObject();
        body.put("username", ConfigReader.getProperty("booking.username"));
        body.put("password", ConfigReader.getProperty("booking.password"));

        Response response = RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post("/auth");

        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("token");
    }
}
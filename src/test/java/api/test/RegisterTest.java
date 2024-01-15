package api.test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class RegisterTest extends TestBase{


    @Test
    @DisplayName("Successfully registration new user")
    void successRegistrationTest(){

        File jsonFile = new File("src/test/resources/registrationData.json");

        given()
                .body(jsonFile)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Registration without password")
    void emptyPasswordRegistrationTest(){
        String jsonData = "{\"email\": \"sydney@fife\", \"password\": \"\"}";

        given()
                .body(jsonData)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().body()
                .log().status()
                .statusCode(400)
                .body("error", equalTo("Missing password"));

    }

    @Test
    @DisplayName("Registration without email address")
    void emptyEmailRegistrationTest(){
        String jsonData = "{\"email\": \"\", \"password\": \"pistol\"}";

        given()
                .body(jsonData)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", equalTo("Missing email or username"));

    }

}

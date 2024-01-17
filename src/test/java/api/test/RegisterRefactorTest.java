package api.test;


import io.restassured.http.ContentType;
import models.lombok.RegistrationBodyLombokModel;
import models.lombok.RegistrationResponseLombokModel;
import models.pojo.RegistrationBodyModel;
import models.pojo.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterRefactorTest extends TestBase{


    @Test
    @DisplayName("Successfully registration new user")
    void successRegistrationTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("pistol");


        RegistrationResponseModel response =  given()
                .body(registrationData)
                .contentType(ContentType.JSON)
        .when()
                .post("/register")
        .then()
                .log().body()
                .statusCode(200)
                .extract().as(RegistrationResponseModel.class);

        assertEquals(4, response.getId());
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

    }

    @Test
    @DisplayName("Registration without password")
    void emptyPasswordRegistrationTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("");


        RegistrationResponseModel response =  given()
                .body(registrationData)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().body()
                .log().status()
                .statusCode(400)
                .extract().as(RegistrationResponseModel.class);

        assertEquals("Missing password", response.getError());

    }

    @Test
    @DisplayName("Registration without email address")
    void emptyEmailRegistrationTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel();
        registrationData.setEmail("");
        registrationData.setPassword("pistol");

        RegistrationResponseModel response = given()
                .body(registrationData)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(400)
                .extract().as(RegistrationResponseModel.class);
        assertEquals("Missing email or username", response.getError());

    }

//    Test with Lombok models
    @Test
    @DisplayName("Successfully registration new user")
    void successRegistrationLombokTest(){

        RegistrationBodyLombokModel registrationData = new RegistrationBodyLombokModel();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("pistol");


        RegistrationResponseLombokModel response =  given()
                .filter(withCustomTemplates())
                .body(registrationData)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(200)
                .extract().as(RegistrationResponseLombokModel.class);

        assertEquals(4, response.getId());
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

    }

}

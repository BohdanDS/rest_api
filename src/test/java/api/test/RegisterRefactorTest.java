package api.test;


import io.restassured.http.ContentType;
import models.lombok.RegistrationBodyLombokModel;
import models.lombok.RegistrationResponseLombokModel;
import models.pojo.RegistrationBodyModel;
import models.pojo.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegistrationSpec.*;

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


        RegistrationResponseModel response =  step("Make Request", ()->
                given(registrationRequestSpec)
                        .body(registrationData)

                .when()
                    .post("/register")

                .then()
                        .spec(missingPasswordResponseSpec)
                    .extract().as(RegistrationResponseModel.class));

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

        RegistrationResponseLombokModel response = step("Make Request", ()->
                given(registrationRequestSpec)
                    .body(registrationData)

                .when()
                    .post("/register")

                .then()
                    .spec(registrationResponseSpec)
                    .extract().as(RegistrationResponseLombokModel.class));

        step("Check request", ()->{
            assertEquals(4, response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });


    }

}

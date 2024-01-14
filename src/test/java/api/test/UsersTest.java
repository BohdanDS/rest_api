package api.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class UsersTest {
    @Test
    @DisplayName("Getting user value by id")
    void getUserById(){
        given().log().uri().
        get("https://reqres.in/api/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.id", equalTo(2));
    }
}

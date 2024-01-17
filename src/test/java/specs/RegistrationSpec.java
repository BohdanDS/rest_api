package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;


public class RegistrationSpec {
     public static RequestSpecification registrationRequestSpec = with()
             .filter(withCustomTemplates())
             .contentType(ContentType.JSON)
             .log().uri()
             .log().body()
             .log().headers();

     public static ResponseSpecification registrationResponseSpec = new ResponseSpecBuilder()
             .expectStatusCode(200)
             .log(STATUS)
             .log(BODY)
             .build();
     public static ResponseSpecification missingPasswordResponseSpec = new ResponseSpecBuilder()
             .expectStatusCode(400)
             .log(STATUS)
             .log(BODY)
             .build();
}

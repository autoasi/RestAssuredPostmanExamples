package com.rest.requestParameters;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestParametersMultipartFormData {

    @Test
    public void multipart_form_data(){
        given().
                baseUri("https://postman-echo.com").
                multiPart("param1", "val1").
                multiPart("param2", "val2").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}

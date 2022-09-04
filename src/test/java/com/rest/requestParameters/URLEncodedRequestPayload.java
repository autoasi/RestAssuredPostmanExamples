package com.rest.requestParameters;

import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class URLEncodedRequestPayload {

    @Test
    public void form_urlencoded(){
        //When using formParam() method, we don't need to explicitly send the content type as
        // application/x-www-form-urlencoded as  REST Assured takes care of adding the content type automatically.
        // Actual contentType = Content-Type=application/x-www-form-urlencoded; charset=ISO-8859-1
        given().
                baseUri("https://postman-echo.com").
                //contentType(ContentType.URLENC).    // Option 1

                // Tell RestAssured not to use the default charset  // Option 2
                config(config().encoderConfig(EncoderConfig.encoderConfig()
                                   .appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                formParam("key1", "value1").
                formParam("key 2", "value 2").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}

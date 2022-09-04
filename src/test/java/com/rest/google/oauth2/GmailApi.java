package com.rest.google.oauth2;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GmailApi {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "ya29.a0AVA9y1sA5yscwZJ0ml1c03159iwlamz1OgU2MnQBDT-1n1otHF6EaPdpA5ls65kmtjiRgOjoY0Zi-_Xo86oYtGhVep6wznxpQU2anrt6QlncEO9y5Krw8XWMTY2beLlqsvedI9gcrXVmifp9gaCgYKATASAQASFQE65dr8bh4avnIQGR5ybIYGIa0G7Q0165";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://gmail.googleapis.com").
                addHeader("Authorization", "Bearer " + access_token).
                //        setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
                //                .appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                        setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getUserProfile(){
        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userid", "asi@gmail.com").
        when().
                get("/users/{userid}/profile").
        then().spec(responseSpecification);
    }

    @Test
    public void sendEmail(){
        // Build the message body
        String msg = "From: asi@gmail.com\n" +
                "To: test@gmail.com\n" +
                "Subject: Rest Assured Test Email\n" +
                "\n" +
                "Sending from Rest Assured";

        String base64UrlEncodedMsg = Base64.getUrlEncoder().encodeToString(msg.getBytes());

        HashMap<String, String> payload = new HashMap<>();
        payload.put("raw", base64UrlEncodedMsg);

        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userid", "asi@gmail.com").
                body(payload).
        when().
                post("/users/{userid}/messages/send").
        then().spec(responseSpecification);
    }

    @Test
    public void deleteGmailMessage(){
        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userId", "asi@gmail.com").
                pathParam("id", "182eaf0856ecb31").
        when().
                delete("users/{userId}/messages/{id}").
        then().spec(responseSpecification);

    }

    @Test
    public void getGmailMessage(){
        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userId", "asi@gmail.com").
                pathParam("id", "182eaf8c0912544c").
        when().
                get("/users/{userId}/messages/{id}").
        then().spec(responseSpecification);
    }
}

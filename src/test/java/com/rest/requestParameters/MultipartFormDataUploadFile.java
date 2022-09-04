package com.rest.requestParameters;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class MultipartFormDataUploadFile {

    @Test
    public void upload_file_multipart_form_data() {
        String attributes = "{\"name\":\"multipartFile.txt\",\"parent\":{\"id\":\"123456\"}}";
        given().
                baseUri("https://postman-echo.com").
                multiPart("file", new File("src/main/resources/multipartFile.txt")).
                multiPart("attributes", attributes, "application/json").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}

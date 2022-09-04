package com.rest.requestParameters;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class RequestParameters {

    @Test
    public void single_query_parameter(){
        given().
                baseUri("https://postman-echo.com").
                // param("param1", "val1").                // Option 1
                queryParam("param1", "val1").   // Option 2
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multiple_query_parameters(){
        given().
                baseUri("https://postman-echo.com").
                queryParam("param1", "val1").
                queryParam("param2", "val2").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multiple_query_parameters_using_map(){
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("param1", "val1");
        queryParams.put("param2", "val2");

        given().
                baseUri("https://postman-echo.com").
                queryParams(queryParams).
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multi_value_query_parameter(){
        given().
                baseUri("https://postman-echo.com").
                queryParam("param1", "val1;val2;val3").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void path_parameter(){
        given().
                baseUri("https://reqres.in").
                pathParam("userId", "2"). // note - for multiple path parameters use pathParams()
                log().all().
        when().
                get("/api/users/{userId}").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}

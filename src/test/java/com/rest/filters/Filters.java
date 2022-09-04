package com.rest.filters;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;

public class Filters {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        PrintStream fileOutPutStream = new PrintStream(new File("restAssured.log"));

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                addFilter(new RequestLoggingFilter(fileOutPutStream)).
                addFilter(new ResponseLoggingFilter(fileOutPutStream));
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void loggingFilter() {
        given().
                baseUri("https://postman-echo.com").
                // log().all().                       // Instead of using the log() method we can use filter()
                // filter(new RequestLoggingFilter()).      // print the full request
                // filter(new ResponseLoggingFilter()).     // print the full response
                filter(new RequestLoggingFilter(LogDetail.BODY)).      // print only request body
                filter(new ResponseLoggingFilter(LogDetail.STATUS)).     // print only response status
        when().
                get("/get").
        then().
                // log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void loggingFilterInToFile() throws FileNotFoundException {
        // Create file under the root folder
        PrintStream FileOutputStream = new PrintStream(new File("restAssured.log"));

        given().
                baseUri("https://postman-echo.com").
                // log().all().                       // Instead of using the log() method we can use filter()
                // filter(new RequestLoggingFilter(FileOutputStream)).    // print the full request into file
                // filter(new ResponseLoggingFilter(FileOutputStream)).   // print the full response into file
                filter(new RequestLoggingFilter(LogDetail.BODY,FileOutputStream)).   // print only request body into file
                filter(new ResponseLoggingFilter(LogDetail.STATUS,FileOutputStream)). // print only response status into file
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200);
    }
}



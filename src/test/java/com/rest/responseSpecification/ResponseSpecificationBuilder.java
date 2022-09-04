package com.rest.responseSpecification;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationBuilder {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        requestSpecification = with().   // With() is similar to given()
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161");

        // Ensure all response message will have status code 200 and content type Json
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_get_status_code_with_response_specification(){
        requestSpecification.get("/workspaces").
                then().spec(responseSpecification);
    }

    @Test
    public void validate_response_body_with_response_specification(){
        Response response = requestSpecification.get("/workspaces").
                then().spec(responseSpecification).
                extract().
                response();
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));
    }
}

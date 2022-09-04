package com.rest.requestSpecification;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RequestSpecificationExample {
    RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass(){
       requestSpecification = with().   // With() is similar to given()
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161");
    }

    @Test
    public void validate_get_status_code_with_request_specification_non_bdd(){
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
    }

    @Test
    public void validate_response_body_non_bdd(){
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));
    }

    @Test
    public void validate_get_status_code_with_request_specification_bdd(){
        /*RequestSpecification requestSpecification = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161");*/

        //given(requestSpecification).        // option 1
        given().spec(requestSpecification).   // option 2 - passing the req specification via spec
        when().
                get("/workspaces").
        then().
                log().all().     //prints the response
                assertThat().
                statusCode(200);
    }
}






package com.rest.postPutAndDelete;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePut {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        // Ensure all response message will have status code 200 and content type Json
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_put_request_bdd_style() {
        String workspaceId = "1071ed50-cec7-42e8-954b-c392e88f2580";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My Workspace - updated\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is created by Rest Assured.\"\n" +
                "    }\n" +
                "}";

        given().
                body(payload).
                pathParam("workspaceId",workspaceId).
        when().
                //put("/workspaces/" + workspaceId). // option 1: append the workspace id from string
                put("/workspaces/{workspaceId}"). // option 2: append the workspace id from pathParam
        then().
                assertThat().
                body("workspace.name", equalTo("My Workspace - updated"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
    }

    @Test
    public void validate_put_request_non_bdd_style() {

    }
}

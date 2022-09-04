package com.rest.hamcrestAssertions;

import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HamcrestAssertions {

    @Test
    public void validate_response_body_hamcrest_contains(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name",contains("My Workspace") // Hamcrest assertion contains
                );
    }

    @Test
    public void validate_response_body_hamcrest_empty(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name",is(not(empty())), // Hamcrest assertion not empty collection
                        "workspaces.name", not(emptyArray()), // Hamcrest assertion not empty array
                        "workspaces.name", hasSize(1),      // Hamcrest assertion hasSize
                        "workspaces.name", everyItem(startsWith("My")) // Hamcrest assertion everyItem
                );
    }

    @Test
    public void validate_response_body_hamcrest_map(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces[0]", hasKey("id"), // Hamcrest assertion hasKey
                        "workspaces[0]", hasValue("My Workspace"), // Hamcrest assertion hasValue
                        "workspaces[0]", hasEntry("name", "My Workspace"), // Hamcrest assertion hasEntry
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)) // Hamcrest assertion collection not empty
                        );
    }

    @Test
    public void validate_response_body_hamcrest_string(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces[0].name", allOf(startsWith("My"), containsString("Workspace")) // Hamcrest assertion allOf
                );
    }
}

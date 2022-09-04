package com.rest.logging;

import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class Logging {

    @Test
    public void request_response_logging(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                log().headers().   // print log for request headers only
        when().
                get("/workspaces").
        then().
                log().body().  // print log for response body only
                assertThat().
                statusCode(200);
    }

    @Test
    public void log_only_if_error(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                log().all().
        when().
                get("/workspaces").
        then().
                log().ifError().  // print log for response only if error in the request
                assertThat().
                statusCode(200);
    }

    @Test
    public void log_only_if_validation_fails(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
                //log().ifValidationFails(). // print log for request only if validation fails
        when().
                get("/workspaces").
        then().
                //log().ifValidationFails().  // print log for response only if validation fails
                assertThat().
                statusCode(201); // actual is 200
    }

    @Test
    public void logs_blacklist_header(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key"))).
                log().all().
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void logs_blacklist_headers(){
        Set<String> headers = new HashSet<String>();
        headers.add("X-Api-Key");
        headers.add("Accept");
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-629b85a17227927339d84ff3-fdbf03ad04bd47bb5ca4c35df136d84161").
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                log().all().
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }
}

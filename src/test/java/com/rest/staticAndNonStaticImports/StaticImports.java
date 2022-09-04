package com.rest.staticAndNonStaticImports;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class StaticImports {

    @Test
    public void simple_test_case(){
        given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-5ff2d720d2a39a004250e5da-c658c4a8a1cee3516762cb1a51cba6c5e2").
                when().
                get("/workspaces").
                then().
                statusCode(200).
                body("name", is(equalTo("Asi")),
                        "email", is(equalTo("asi@gmail.com")));
    }
}


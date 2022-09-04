package com.rest.staticAndNonStaticImports;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class NonStaticImports {

    @Test
    public void simple_test_case(){
        RestAssured.given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-5ff2d720d2a39a004250e5da-c658c4a8a1cee3516762cb1a51cba6c5e2").
                when().
                get("/workspaces").
                then().
                statusCode(200).
                body("name", Matchers.is(Matchers.equalTo("Asi")),
                        "email", Matchers.is(Matchers.equalTo("asi@gmail.com")));
    }
}

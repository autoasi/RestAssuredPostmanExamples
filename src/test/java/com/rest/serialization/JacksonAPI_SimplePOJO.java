package com.rest.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JacksonAPI_SimplePOJO {
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://6f6c9ee1-53e7-45b8-b653-2a36e24be505.mock.pstmn.io").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void using_string_instead_of_pojo_example1() {
        String payload = "{\n" +
                "    \"key1\":\"value1\",\n" +
                "    \"key2\":\"value2\"\n" +
                "}";

        given().
                body(payload).
        when().
                post("/postSimplePOJO").
        then().spec(responseSpecification).
                assertThat().
                body("key1", equalTo("value1"),
                        "key2",equalTo("value2"));
    }

    @Test
    public void using_simple_pojo_example2() {
        SimplePojo simplePojo = new SimplePojo("value1", "value2");
    /*  SimplePojo simplePojo = new SimplePojo();
        simplePojo.setKey1("value1");
        simplePojo.setKey2("value2");  */

        given().
                body(simplePojo).
        when().
                post("/postSimplePOJO").
        then().spec(responseSpecification).
                assertThat().
                body("key1", equalTo(simplePojo.getKey1()),
                        "key2",equalTo(simplePojo.getKey2()));

    }

    @Test
    public void deserialization_simple_pojo_with_example3() throws JsonProcessingException {
        SimplePojo simplePojo = new SimplePojo("value1", "value2");

        SimplePojo deserializedPojo = given().
                body(simplePojo).   // rest assured serializes the POJO object automatically
        when().
                post("/postSimplePOJO").
        then().spec(responseSpecification).
                extract().
                response().
                as(SimplePojo.class);

        // this step deserialize the response back to java object
        ObjectMapper objectMapper = new ObjectMapper();
        String deserializedPojoStr = objectMapper.writeValueAsString(deserializedPojo);
        String simplePojoStr = objectMapper.writeValueAsString(simplePojo);
        assertThat(objectMapper.readTree(deserializedPojoStr), equalTo(objectMapper.readTree(simplePojoStr)));
    }
}

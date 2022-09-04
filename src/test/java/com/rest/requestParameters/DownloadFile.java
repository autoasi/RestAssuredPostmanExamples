package com.rest.requestParameters;

import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class DownloadFile {

    @Test
    public void download_file_using_bytes_array() throws IOException {
        byte[] bytes = given().
                baseUri("https://raw.githubusercontent.com").
                log().all().
        when().
                get("/appium-boneyard/sample-code/master/sample-code/apps/ApiDemos/bin/ApiDemos-debug.apk").
        then().
                log().all().
                extract().
                response().asByteArray();

        OutputStream os = new FileOutputStream(new File("src/main/resources/ApiDemos-debug.apk"));
        os.write(bytes);
        os.close();
    }

    @Test
    public void download_file_using_input_stream() throws IOException {
        InputStream is = given().
                baseUri("https://raw.githubusercontent.com").
                log().all().
        when().
                get("/appium-boneyard/sample-code/master/sample-code/apps/ApiDemos/bin/ApiDemos-debug.apk").
        then().
                log().all().
                extract().
                response().asInputStream();

        OutputStream os = new FileOutputStream(new File("src/main/resources/ApiDemos-debug2.apk"));
        // convert InputStream into ByteArray
        byte[] bytes = new byte[is.available()]; // allocate enough bytes to bytes array
        is.read(bytes); // read the inputStream into bytes
        os.write(bytes);
        os.close();
    }
}

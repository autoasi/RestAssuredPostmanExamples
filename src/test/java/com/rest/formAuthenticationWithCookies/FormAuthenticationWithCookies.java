package com.rest.formAuthenticationWithCookies;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FormAuthenticationWithCookies {

    @BeforeClass
    public void beforeClass(){
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setRelaxedHTTPSValidation(). // tell Rest-Assured to not use ssl - handle SSLHandshakeException
                setBaseUri("https://localhost:8443").
                build();
    }

    @Test
    public void form_authentication_using_csrf_token_get_user_profile_using_cookie(){
        SessionFilter filter = new SessionFilter();
        given().
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword").
                        withAutoDetectionOfCsrf()). // Rest-Assured will automatically fetch the CSRF from the signin
                filter(filter).
                log().all().
        when().
                get("/login").
        then().
                log().all().
                assertThat().
                statusCode(200);

        System.out.println("Session id = " + filter.getSessionId());

        given().
                cookie("JSESSIONID", filter.getSessionId()). // Send the JSESSIONID cookie
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("html.body.div.p", equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void form_authentication_using_csrf_token_get_user_profile_using_cookie_builder(){
        SessionFilter filter = new SessionFilter();
        given().
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword").
                        withAutoDetectionOfCsrf()).
                filter(filter).
                log().all().
                when().
                get("/login").
                then().
                log().all().
                assertThat().
                statusCode(200);

        System.out.println("Session id = " + filter.getSessionId());

        // Cookie builder - Secured and HTTP only with comment
        Cookie cookie = new Cookie.Builder("JSESSIONID", filter.getSessionId()).setSecured(true)
                .setHttpOnly(true).setComment("my cookie").build();

        given().
                cookie(cookie). // Pass the cookie object
                log().all().
                when().
                get("/profile/index").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("html.body.div.p", equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void form_authentication_using_csrf_token_get_user_profile_using_multiple_cookies(){
        SessionFilter filter = new SessionFilter();
        given().
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword").
                        withAutoDetectionOfCsrf()).
                filter(filter).
                log().all().
        when().
                get("/login").
        then().
                log().all().
                assertThat().
                statusCode(200);

        System.out.println("Session id = " + filter.getSessionId());

        Cookie cookie = new Cookie.Builder("JSESSIONID", filter.getSessionId()).setSecured(true)
                .setHttpOnly(true).setComment("my cookie").build();
        Cookie cookie1 = new Cookie.Builder("dummy", "dummyValue").build();

        Cookies cookies = new Cookies(cookie, cookie1);

        given().
                cookies(cookies).
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("html.body.div.p", equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void fetch_single_cookie(){
        Response response = given().
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                response();

        System.out.println(response.getCookie("JSESSIONID"));
        System.out.println(response.getDetailedCookie("JSESSIONID"));
    }

    @Test
    public void fetch_multiple_cookies(){
        Response response = given().
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                response();

        Map<String, String> cookies = response.getCookies(); // collect all the cookies in hashmap

        for(Map.Entry<String, String> entry: cookies.entrySet()){
            System.out.println("cookie name = " + entry.getKey());
            System.out.println("cookie value = " + entry.getValue());
        }

        Cookies cookies1 = response.getDetailedCookies();
        List<Cookie> cookieList = cookies1.asList();

        for(Cookie cookie: cookieList){
            System.out.println("cookie = " + cookie.toString());
        }
    }
}


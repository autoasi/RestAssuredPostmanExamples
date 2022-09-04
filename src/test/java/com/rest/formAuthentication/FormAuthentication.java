package com.rest.formAuthentication;

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

public class FormAuthentication {

    @BeforeClass
    public void beforeClass(){
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setRelaxedHTTPSValidation(). // tell Rest-Assured to not use ssl - handle SSLHandshakeException
                setBaseUri("https://localhost:8443").
                build();
    }

    @Test
    public void form_authentication_using_csrf_token(){
        given().
                // FormAuthConfig needs the actual field names from the HTML response
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword")).
                log().all().
        when().
                get("/login").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void get_session_id_using_session_filter() {
        // By default, SessionFilter class in REST Assured can extract the session id only if the session id name is JSESSIONID
        SessionFilter filter = new SessionFilter();
        given().
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword")).
                filter(filter). // Session ID will be stored in the filter
                log().all().
        when().
                get("/login").
        then().
                log().all().
                assertThat().
                statusCode(200);

        System.out.println("Session id = " + filter.getSessionId());
    }

    @Test
    public void form_authentication_using_csrf_token_get_user_profile(){
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
                sessionId(filter.getSessionId()). // Pass the session id to the GET profile call
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
    public void form_authentication_using_csrf_token_cookie_example(){
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
                //            cookie("JSESSIONID", filter.getSessionId()).
                //            cookie(cookie).
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

        Map<String, String> cookies = response.getCookies();

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


package tests;

import dto.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static RequestSpecification getRequestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .addHeader("x-api-key", "pub_b7492691e5fddfe9ca6c4dc96c846448053f506cb021e456b3d93891ee8ed717")
                .setContentType(ContentType.JSON)
                .build();
    }

    protected static ResponseSpecification getResponseSpec() {

        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    static User user;

    @BeforeSuite
    public void setUp() {

        user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
    }

    protected Response register(User user) {

        return given(getRequestSpec())
                .filter(new io.qameta.allure.restassured.AllureRestAssured())
                .body(user)
                .post("register")
                .then()
                .spec(getResponseSpec())
                .extract().response();
    }
}
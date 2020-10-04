package es.codeurjc.posts;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createNewPostTest() {
        generatePost(1);
    }

    @Test
    public void getById() {

        given().request().
                when().
                       get("/posts/1").
                       then().
                       statusCode(200).
                       body("id", is(1));
    }

    @Test
    public void getByIdNofFound() {

        given().request().
                when().
                       get("/posts/1000").
                       then().
                       statusCode(404);
    }

    @Test
    public void deleteUser() {

        generatePost(1);

        given().request().
                when().
                       delete("/posts/1").
                       then().
                       statusCode(200).
                       body("id", is(1));

        given().request().
                when().
                       get("/posts/1").
                       then().
                       statusCode(404);
    }

    private void generatePost(int id) {
        given().request()
               .body("{\n" +
                       "   \"id\":" + id + ",\n" +
                       "   \"author\":\"Edu\",\n" +
                       "   \"title\":\"Project Reactor\",\n" +
                       "   \"text\":\"MongoDB\",\n" +
                       "   \"comments\":[\n" +
                       "      {\n" +
                       "         \"id\":14,\n" +
                       "         \"author\":\"Yo\",\n" +
                       "         \"content\":\"MomgoDB mucha guerra\",\n" +
                       "         \"date\":\"2020-10-03\"\n" +
                       "      },\n" +
                       "      {\n" +
                       "         \"id\":15,\n" +
                       "         \"author\":\"Sr. Flat\",\n" +
                       "         \"content\":\"Flat & Map\",\n" +
                       "         \"date\":\"2020-10-04\"\n" +
                       "      }\n" +
                       "   ]\n" +
                       "}")
               .contentType(ContentType.JSON).
                       when().
                       post("/posts/").
                       then().
                       statusCode(201).
                       body("id", notNullValue());
    }

}
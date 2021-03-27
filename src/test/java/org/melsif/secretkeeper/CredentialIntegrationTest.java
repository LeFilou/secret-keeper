package org.melsif.secretkeeper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class CredentialIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("The system should persist the credentials")
    public void should_persist_the_credentials() {
        final HashMap<String, String> secret = new HashMap<>();
        secret.put("url", "www.url.com");
        secret.put("username", "slimoux");
        secret.put("password", "password");
        given().contentType("application/json").body(secret)
            .when().post("/credentials")
            .then().statusCode(200)
        .body("url", equalTo("www.url.com"));
    }

    @Test
    @Sql("classpath:db/create-credentials.sql")
    @DisplayName("The system should retrieve all the credentials")
    public void retrieve_all_credentials() {
        when().get("/credentials")
            .then().statusCode(200)
            .body("$.size()", equalTo(5));
    }
}

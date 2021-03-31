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
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/remove-credentials.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/create-credentials.sql")
class CredentialIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("The system should retrieve all the credentials")
    public void should_retrieve_all_the_credentials() {
        when().get("/credentials")
            .then().statusCode(200)
            .body("$.size()", equalTo(5));
    }

    @Test
    @DisplayName("The system should retrieve all the credentials")
    public void credential_search_should_take_into_account_search_criteria() {
        given()
            .queryParam("url", "url1")
            .when().get("/credentials")
            .then().statusCode(200)
            .body("$.size()", equalTo(2))
            .body("url", hasItems("www.url1.com"));
    }

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
    @DisplayName("A url cannot be associated with two equal usernames")
    public void a_url_cannot_be_associated_with_two_equal_usernames() {
        final HashMap<String, String> secret = new HashMap<>();
        secret.put("url", "www.url1.com");
        secret.put("username", "username1");
        secret.put("password", "password");
        given().contentType("application/json").body(secret)
            .when().post("/credentials")
            .then().statusCode(409);
    }
}

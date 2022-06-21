package org.melsif.secretkeeper.credentials.acceptancetests;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.melsif.secretkeeper.credentials.domain.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.OK;

@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/remove-credentials.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/create-credentials.sql")
class CredentialsIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    CredentialService credentialService;

    @Nested
    class WhenFetchingForCredentialsTestCase {

        @Test
        void retrieves_all_credentials_if_no_search_criteria_is_provided() {
            when()
                    .get("api/credentials/")

                    .then()
                    .statusCode(OK.value())
                    .body("size()", is(5));
        }

        @Test
        void retrieves_only_the_credentials_who_meet_the_search_criteria() {
            final String urlPattern = "url1";
            final String usernamePattern = "name1";

            given()
                    .queryParam("url", urlPattern)
                    .queryParam("username", usernamePattern)

                    .when().get("api/credentials")

                    .then()
                    .statusCode(OK.value())
                    .body("$.size()", equalTo(1))
                    .body("[0].url", containsString(urlPattern))
                    .body("[0].username", containsString(usernamePattern));
        }
    }


    @Nested
    class WhenCreatingANewCredentialTestCase {

        @Test
        void creates_a_new_credential() {
            final HashMap<String, String> secret = new HashMap<>();
            secret.put("url", "http://url.com");
            secret.put("username", "slimoux");
            secret.put("password", "password");

            given().contentType("application/json").body(secret)
                    .when().post("/api/credentials")
                    .then().statusCode(200)
                    .body("url", equalTo("http://url.com"))
                    .body("username", equalTo("slimoux"));
        }

        @Test
        void creating_credentials_with_an_existing_url_and_username_is_forbidden() {
            final HashMap<String, String> secret = new HashMap<>();
            secret.put("url", "http://url1.com");
            secret.put("username", "username1");
            secret.put("password", "password");

            given().contentType("application/json").body(secret)
                    .when().post("/api/credentials")
                    .then().statusCode(409);
        }
    }
}

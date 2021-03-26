package org.melsif.secretkeeper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
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
        secret.put("creationDate", LocalDate.now().toString());
        secret.put("modificationDate", LocalDate.now().toString());

        given().contentType("application/json").body(secret)
            .when().post("/credentials")
            .then().statusCode(200)
        .body("url", equalTo("www.url.com"));
    }
}

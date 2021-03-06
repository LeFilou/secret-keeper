package org.melsif.secretkeeper.credentials.web;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.melsif.secretkeeper.credentials.domain.Credential;
import org.melsif.secretkeeper.credentials.domain.CredentialSearchCriteria;
import org.melsif.secretkeeper.credentials.domain.CredentialService;
import org.melsif.secretkeeper.util.AbstractControllerTest;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.HashMap;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.equalTo;
import static org.melsif.secretkeeper.commons.GlobalExceptionHandler.VALIDATION_ERROR;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CredentialControllerTest extends AbstractControllerTest {

    private CredentialService credentialService;

    @Override
    protected Object getTestedController() {
        credentialService = mock(CredentialService.class);
        var credentialMapper = new CredentialMapperImpl();
        return new CredentialController(credentialService, credentialMapper);
    }

    @Nested
    class WhenCreatingANewCredentialTestCase {

        @Test
        void returns_the_created_credential() {
            final String url = "http://url.com";
            final String username = "slimoux";
            final String password = "password";

            Mockito.when(credentialService.saveANewSecret(url, username, password))
                .thenReturn(Credential.newCredential(url, username, password));

            final HashMap<String, String> secret = new HashMap<>();
            secret.put("url", url);
            secret.put("username", username);
            secret.put("password", password);
            given().contentType("application/json")
                .body(secret)
                .when().post("/credentials")
                .then().statusCode(200)
                .body("url", equalTo(url));
        }

        @Test
        void returns_a_bad_request_if_arguments_are_invalid() {
            final HashMap<String, String> secret = new HashMap<>();
            secret.put("url", "http://url.com");
            secret.put("password", "password");
            given().contentType("application/json")
                .body(secret)
                .when().post("/credentials")
                .then().statusCode(BAD_REQUEST.value())
                .body("message", equalTo(VALIDATION_ERROR));
        }

        @Test
        void returns_a_conflict_error_if_the_credential_already_exists() {
            final HashMap<String, String> secret = new HashMap<>();
            final String url = "http://url1.com";
            final String username = "username1";
            final String password = "password";

            secret.put("url", url);
            secret.put("username", username);
            secret.put("password", password);

            Mockito.when(credentialService.saveANewSecret(url, username, password)).thenThrow(DataIntegrityViolationException.class);

            given().contentType("application/json").body(secret)
                .when().post("/credentials")
                .then().statusCode(CONFLICT.value());
        }


    }

    @Nested
    class WhenFetchingForCredentialsTestCase {

        @Test
        void returns_empty_collection_if_no_credential_matches_the_criteria() {
            final String url = "http://url.com";
            final String username = "slimoux";

            Mockito.when(credentialService.fetchCredentials(new CredentialSearchCriteria(url, username)))
                .thenReturn(Collections.emptyList());

            when().get("/credentials")
                .then().statusCode(200)
                .body("$.size()", equalTo(0));
        }
    }
}
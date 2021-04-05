package org.melsif.secretkeeper.credentials;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.melsif.secretkeeper.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/remove-credentials.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/create-credentials.sql")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CredentialServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    CredentialService credentialService;

    @Test
    public void retrieves_all_credentials() {
        final List<Credential> credentials = credentialService.fetchCredentials(null, null);
        assertThat(credentials).isNotEmpty();
        assertThat(credentials).hasSize(5);
    }

    @Test
    public void fetches_credentials_following_a_url_pattern() {
        final List<Credential> credentials = credentialService.fetchCredentials("url1", null);
        assertThat(credentials).isNotEmpty();
        assertThat(credentials).hasSize(2);
        final List<String> urls = credentials.stream().map(Credential::getUrl).collect(Collectors.toUnmodifiableList());
        assertThat(urls).allSatisfy(url -> assertThat(url).contains("url1"));
    }

    @Test
    public void creates_new_credential() {
        final Credential credential = credentialService.saveANewSecret("www.url.com", "slimoux", "password");
        assertThat(credential).isNotNull();
        assertThat(credential.getUrl()).isEqualTo("www.url.com");
        assertThat(credential.getUsername()).isEqualTo("slimoux");
    }

    @Test
    public void creating_credentials_with_the_same_url_and_username_is_forbidden() {
        assertThatExceptionOfType(DataIntegrityViolationException.class)
            .isThrownBy(() -> credentialService.saveANewSecret("www.url1.com", "username1", "password"));
    }
}

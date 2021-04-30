package org.melsif.secretkeeper.credentials.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.melsif.secretkeeper.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.melsif.secretkeeper.credentials.domain.Credential.CREDENTIAL_NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/remove-credentials.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/create-credentials.sql")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CredentialServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    CredentialService credentialService;

    @Test
    void retrieves_all_credentials() {
        final List<Credential> credentials = credentialService.fetchCredentials(CredentialSearchCriteria.NONE);
        assertThat(credentials).isNotEmpty().hasSize(5);
    }

    @Test
    void fetches_credentials_following_a_url_pattern() {
        final String urlPattern = "url1";
        final List<Credential> credentials = credentialService.fetchCredentials(new CredentialSearchCriteria(urlPattern, null));
        assertThat(credentials).isNotEmpty().hasSize(2);
        final List<String> urls = credentials.stream().map(Credential::getUrl).collect(Collectors.toUnmodifiableList());
        assertThat(urls).isNotEmpty().allSatisfy(url -> assertThat(url).contains(urlPattern));
    }

    @Test
    void fetches_credentials_following_a_url_and_a_username_pattern() {
        final String urlPattern = "url1";
        final String usernamePattern = "name1";

        final List<Credential> credentials = credentialService.fetchCredentials(new CredentialSearchCriteria(urlPattern, usernamePattern));

        assertThat(credentials).isNotEmpty().hasSize(1);
        final Credential credential = credentials.get(0);
        assertThat(credential.getUrl()).contains(urlPattern);
        assertThat(credential.getUsername()).contains(usernamePattern);
    }

    @Test
    void creates_a_new_credential() {
        final Credential credential = credentialService.saveANewSecret("http://url.com", "slimoux", "password");
        assertThat(credential).isNotNull();
        assertThat(credential.getUrl()).isEqualTo("http://url.com");
        assertThat(credential.getUsername()).isEqualTo("slimoux");
    }

    @Test
    void creating_credentials_with_the_same_url_and_username_is_forbidden() {
        assertThatExceptionOfType(DataIntegrityViolationException.class)
            .isThrownBy(() -> credentialService.saveANewSecret("http://url1.com", "username1", "password"));
    }

    @Test
    void changes_credential_password() {
        final long credentialId = 1L;
        final String newPassword = "new-password";
        final Credential credential = credentialService.changePassword(credentialId, newPassword);
        assertThat(credential.getPassword()).isEqualTo(newPassword);
        assertThat(credential.getModificationDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void find_credential_by_its_id() {
        final long credentialId = 1L;
        final Credential credential = credentialService.findCredential(credentialId);
        assertThat(credential).isNotNull();
        assertThat(credential.getUsername()).isEqualTo("username1");
        assertThat(credential.getUrl()).isEqualTo("http://url1.com");
    }

    @Test
    void delete_credential() {
        final long credentialId = 1L;
        credentialService.deleteCredential(credentialId);
        assertThatExceptionOfType(CredentialNotFoundException.class)
            .isThrownBy(() -> credentialService.findCredential(1L))
            .withMessage(CREDENTIAL_NOT_FOUND);
    }
}

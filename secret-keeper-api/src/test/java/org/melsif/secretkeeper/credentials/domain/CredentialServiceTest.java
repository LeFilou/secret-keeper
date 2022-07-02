package org.melsif.secretkeeper.credentials.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CredentialServiceTest {

    @Mock
    private CredentialRepository credentialRepository;

    @InjectMocks
    private RepositoryCredentialService credentialService;

    @Test
    void credential_dates_must_be_set_on_creation_date() {
        final String url = "http://url.com";
        final String username = "username";
        final String password = "password";
        when(credentialRepository.save(any(Credential.class)))
            .then(returnsFirstArg());
        final Credential save = credentialService.saveANewSecret(url, username, password);
        assertThat(save.getCreationDate()).isToday();
        assertThat(save.getModificationDate()).isToday();
    }
}
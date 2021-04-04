package org.melsif.secretkeeper.credentials;

import org.junit.jupiter.api.DisplayName;
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
class CredentialServiceTest {

    @Mock
    private CredentialRepository credentialRepository;

    @InjectMocks
    private RepositoryCredentialService credentialService;

    @Test
    @DisplayName("Credential dates must be set on creation date")
    public void credential_dates_must_be_sedt_on_creation_date() {
        final String url = "www.url.com";
        final String username = "username";
        final String password = "password";
        when(credentialRepository.save(any(Credential.class)))
            .then(returnsFirstArg());
        final Credential save = credentialService.save(url, username, password);
        assertThat(save.getCreationDate()).isToday();
        assertThat(save.getModificationDate()).isToday();
    }
}
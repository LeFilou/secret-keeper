package org.melsif.secretkeeper.credentials;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CredentialTest {

    @Test
    public void credential_url_must_be_valid() {
        final String url = "htt://url.com";
        final String username = "username";
        final String password = "password";
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Credential.newCredential(url, username, password));
    }

    @Test
    public void empty_username_or_url_are_not_accepted() {
        final String url = "http://url.com";
        final String username = "";
        final String password = "password";
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Credential.newCredential(url, username, password));
    }
}
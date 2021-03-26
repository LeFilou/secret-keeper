package org.melsif.secretkeeper.credentials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CredentialTest {

    @Test
    public void url_cannot_be_empty() {
        final Credential credential = Credential.newCredential("", "username", "password");
    }
}
package org.melsif.secretkeeper.credentials;

public interface CredentialService {

    Credential save(String url, String username, String password);

    Credential find(String url, String username);
}

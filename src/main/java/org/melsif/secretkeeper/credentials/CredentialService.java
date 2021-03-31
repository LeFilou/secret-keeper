package org.melsif.secretkeeper.credentials;

import java.util.List;

public interface CredentialService {

    Credential save(String url, String username, String password);

    List<Credential> loadCredentials(String url, String username);
}

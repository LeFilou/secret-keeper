package org.melsif.secretkeeper.credentials;

import java.util.List;

public interface CredentialService {

    Credential save(String url, String username, String password);

    Credential find(String url, String username);

    List<Credential> loadAllCredentials();
}

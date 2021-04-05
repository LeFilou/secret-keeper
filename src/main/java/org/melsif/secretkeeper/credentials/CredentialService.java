package org.melsif.secretkeeper.credentials;

import java.util.List;

public interface CredentialService {

    Credential saveANewSecret(String url, String username, String password);

    List<Credential> fetchCredentials(CredentialSearchCriteria credentialSearchCriteria);

    Credential changePassword(String url, String username, String newPassword);
}

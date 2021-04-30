package org.melsif.secretkeeper.credentials.domain;

import java.util.List;

public interface CredentialService {

    Credential saveANewSecret(String url, String username, String password);

    List<Credential> fetchCredentials(CredentialSearchCriteria credentialSearchCriteria);

    Credential findCredential(long credentialId);

    Credential changePassword(long credentialId, String newPassword);

    void deleteCredential(long credentialId);
}

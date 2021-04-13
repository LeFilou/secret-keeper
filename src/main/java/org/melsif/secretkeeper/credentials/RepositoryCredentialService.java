package org.melsif.secretkeeper.credentials;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.melsif.secretkeeper.credentials.Credential.CREDENTIAL_NOT_FOUND;
import static org.melsif.secretkeeper.credentials.CredentialSpecifications.urlContains;
import static org.melsif.secretkeeper.credentials.CredentialSpecifications.usernameContains;

@Service
@RequiredArgsConstructor
class RepositoryCredentialService implements CredentialService {

    private final CredentialRepository credentials;

    @Override
    public Credential saveANewSecret(String url, String username, String password) {
        final Credential credential = Credential.newCredential(url, username, password);
        return credentials.save(credential);
    }

    @Override
    public List<Credential> fetchCredentials(CredentialSearchCriteria credentialSearchCriteria) {
        final String urlPattern = credentialSearchCriteria.getUrlPattern();
        final String usernamePattern = credentialSearchCriteria.getUsernamePattern();
        return credentials.findAll(urlContains(urlPattern).and(usernameContains(usernamePattern)));
    }

    @Override
    public Credential changePassword(long credentialId, String newPassword) {
        final Credential credential = credentials
            .findById(credentialId)
            .orElseThrow(() -> new CredentialNotFound(CREDENTIAL_NOT_FOUND));
        credential.changePassword(newPassword);
        return credential;
    }
}

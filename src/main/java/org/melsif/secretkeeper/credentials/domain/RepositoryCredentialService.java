package org.melsif.secretkeeper.credentials.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.melsif.secretkeeper.credentials.domain.CredentialSpecifications.urlContains;
import static org.melsif.secretkeeper.credentials.domain.CredentialSpecifications.usernameContains;

@Service
@RequiredArgsConstructor
class RepositoryCredentialService implements CredentialService {

    private final CredentialRepository credentials;

    @Override
    public Credential saveANewSecret(String url, String username, String password) {
        var credential = Credential.newCredential(url, username, password);
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
        var credential = credentials
            .findOne(credentialId);
        credential.changePassword(newPassword);
        return credential;
    }

    @Override
    public Credential findCredential(long credentialId) {
        return credentials
            .findOne(credentialId);
    }

    @Override
    public void deleteCredential(long credentialId) {
        credentials.delete(credentialId);
    }
}

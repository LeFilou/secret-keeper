package org.melsif.secretkeeper.credentials;

import lombok.RequiredArgsConstructor;
import org.melsif.secretkeeper.credentials.Credential.CredentialIdentifiers;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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
        return credentials
            .findAll(urlContains(urlPattern).and(usernameContains(usernamePattern)));
    }

    @Override
    public Credential changePassword(String url, String username, String newPassword) {
        final Credential credential = credentials.findByCredentialIdentifiers(new CredentialIdentifiers(url, username))
            .orElseThrow(() -> new EntityNotFoundException("Credential not found"));
        credential.changePassword(newPassword);
        return credential;
    }
}

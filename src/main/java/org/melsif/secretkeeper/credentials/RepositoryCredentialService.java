package org.melsif.secretkeeper.credentials;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.melsif.secretkeeper.credentials.CredentialSpecifications.urlIsLike;
import static org.melsif.secretkeeper.credentials.CredentialSpecifications.usernameIsLike;

@Service
@RequiredArgsConstructor
class RepositoryCredentialService implements CredentialService {

    private final CredentialRepository credentials;

    @Override
    public Credential save(String url, String username, String password) {
        final Credential credential = Credential.newCredential(url, username, password);
        return credentials.save(credential);
    }

    @Override
    public List<Credential> loadCredentials(String url, String username) {
        return credentials
            .findAll(urlIsLike(url).and(usernameIsLike(username)));
    }
}

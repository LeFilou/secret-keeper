package org.melsif.secretkeeper.credentials;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
class CredentialServiceImpl implements CredentialService {

    private final CredentialRepository credentials;

    @Override
    @Validated
    public Credential save(String url, String username, String password) {
        final Credential credential = Credential.newCredential(url, username, password);
        return credentials.save(credential);
    }

    @Override
    public Credential find(String url, String username) {
        return credentials.find(url, username)
            .orElseThrow(() -> new EntityNotFoundException("No credential for url : " + url + "and username : " + username));
    }

    @Override
    public List<Credential> loadAllCredentials() {
        return credentials.findAll();
    }
}

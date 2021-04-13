package org.melsif.secretkeeper.web.credentials;

import lombok.RequiredArgsConstructor;
import org.melsif.secretkeeper.credentials.Credential;
import org.melsif.secretkeeper.credentials.CredentialSearchCriteria;
import org.melsif.secretkeeper.credentials.CredentialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class CredentialController implements CredentialsApi {

    private final CredentialService credentialService;
    private final CredentialMapper credentialMapper;

    @Override
    public ResponseEntity<CredentialDetails> newCredential(CredentialRequest credentialRequest) {
        var credential = credentialService.saveANewSecret(credentialRequest.getUrl(), credentialRequest.getUsername(), credentialRequest.getPassword());
        return ResponseEntity.ok(credentialMapper.toCredentialDetails(credential));
    }

    @Override
    public ResponseEntity<List<CredentialDetails>> getCredentials(@Valid String urlPattern, @Valid String usernamePattern) {
        var credentials = credentialService
            .fetchCredentials(new CredentialSearchCriteria(urlPattern, usernamePattern))
            .stream()
            .map(credentialMapper::toCredentialDetails)
            .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(credentials);
    }

    @Override
    public ResponseEntity<CredentialDetails> updateCredential(Long credentialId, @Valid NewCredentialData newCredentialData) {
        final Credential updatedCredential = credentialService.changePassword(credentialId, newCredentialData.getNewPassword());
        return ResponseEntity.ok(credentialMapper.toCredentialDetails(updatedCredential));
    }
}

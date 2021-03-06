package org.melsif.secretkeeper.credentials.web;

import lombok.RequiredArgsConstructor;
import org.melsif.secretkeeper.credentials.domain.CredentialSearchCriteria;
import org.melsif.secretkeeper.credentials.domain.CredentialService;
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
}

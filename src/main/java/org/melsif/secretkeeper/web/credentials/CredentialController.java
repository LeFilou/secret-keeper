package org.melsif.secretkeeper.web.credentials;

import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(credentialMapper.toCredential(credential));
    }

    @Override
    public ResponseEntity<List<CredentialDetails>> getCredentials(@Valid String url, @Valid String username) {
        var credentials = credentialService.fetchCredentials(url, username)
            .stream()
            .map(credentialMapper::toCredential)
            .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(credentials);
    }
}

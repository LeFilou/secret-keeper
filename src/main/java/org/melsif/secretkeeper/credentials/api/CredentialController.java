package org.melsif.secretkeeper.credentials.api;

import org.melsif.secretkeeper.api.CredentialsApi;
import org.melsif.secretkeeper.credentials.domain.CredentialService;
import org.melsif.secretkeeper.model.CredentialDetails;
import org.melsif.secretkeeper.model.CredentialRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CredentialController implements CredentialsApi {

    private final CredentialService credentialService;
    private final CredentialMapper credentialMapper;

    @Autowired
    public CredentialController(CredentialService credentialService, CredentialMapper credentialMapper) {
        this.credentialService = credentialService;
        this.credentialMapper = credentialMapper;
    }

    @Override
    public ResponseEntity<CredentialDetails> newCredential(CredentialRequest credentialRequest) {
        var credential = credentialService.save(credentialRequest.getUrl(), credentialRequest.getUsername(), credentialRequest.getPassword());
        return ResponseEntity.ok(credentialMapper.toCredential(credential));
    }

    @Override
    public ResponseEntity<List<CredentialDetails>> getCredentials(@Valid String url, @Valid String username) {
        var credentials = credentialService.loadCredentials(url, username)
            .stream()
            .map(credentialMapper::toCredential)
            .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(credentials);
    }
}

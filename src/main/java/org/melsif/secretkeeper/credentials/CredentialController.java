package org.melsif.secretkeeper.credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("credentials")
public class CredentialController {

    private final CredentialService credentialService;

    @Autowired
    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping
    Credential newCredential(@RequestBody CredentialDto credentialDto) {
        return credentialService.save(credentialDto.getUrl(), credentialDto.getUsername(), credentialDto.getPassword());
    }

    @GetMapping
    List<Credential> loadAllCredentials() {
        return credentialService.loadAllCredentials();
    }
}

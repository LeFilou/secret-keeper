package org.melsif.secretkeeper.credentials.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import static org.melsif.secretkeeper.credentials.domain.Credential.CREDENTIAL_NOT_FOUND;

interface CredentialRepository extends JpaRepository<Credential, Long>, JpaSpecificationExecutor<Credential> {

    Credential deleteById(long credentialId);

    default Credential findOne(long credentialId) {
        return findById(credentialId)
            .orElseThrow(() -> new CredentialNotFoundException(CREDENTIAL_NOT_FOUND));
    }

    default Credential delete(long credentialId) {
        return deleteById(credentialId);
    }
}

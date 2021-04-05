package org.melsif.secretkeeper.credentials;

import org.melsif.secretkeeper.credentials.Credential.CredentialIdentifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

interface CredentialRepository extends JpaRepository<Credential, Long>, JpaSpecificationExecutor<Credential> {

    Optional<Credential> findByCredentialIdentifiers(CredentialIdentifiers credentialIdentifiers);
}

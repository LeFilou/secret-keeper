package org.melsif.secretkeeper.credentials;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findCredentialByUrlAndUsername(String url, String username);

    default Optional<Credential> find(String url, String username) {
        return findCredentialByUrlAndUsername(url, username);
    }
}

package org.melsif.secretkeeper.credentials.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface CredentialRepository extends JpaRepository<Credential, Long>, JpaSpecificationExecutor<Credential> {

}

package org.melsif.secretkeeper.credentials.api;

import org.mapstruct.Mapper;
import org.melsif.secretkeeper.credentials.domain.Credential;
import org.melsif.secretkeeper.model.CredentialDetails;

@Mapper(componentModel = "spring")
public interface CredentialMapper {
    CredentialDetails toCredential(Credential credential);
}

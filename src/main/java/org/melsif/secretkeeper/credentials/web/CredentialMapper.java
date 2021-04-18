package org.melsif.secretkeeper.credentials.web;

import org.mapstruct.Mapper;
import org.melsif.secretkeeper.credentials.domain.Credential;

@Mapper(componentModel = "spring")
public interface CredentialMapper {
    CredentialDetails toCredentialDetails(Credential credential);
}

package org.melsif.secretkeeper.web.credentials;

import org.mapstruct.Mapper;
import org.melsif.secretkeeper.credentials.Credential;

@Mapper(componentModel = "spring")
public interface CredentialMapper {
    CredentialDetails toCredentialDetails(Credential credential);
}

package org.melsif.secretkeeper.credentials;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter @AllArgsConstructor
public class CredentialDto {
    private String url;
    private String username;
    private String password;
}

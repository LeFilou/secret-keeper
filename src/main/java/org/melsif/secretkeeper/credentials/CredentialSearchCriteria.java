package org.melsif.secretkeeper.credentials;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class CredentialSearchCriteria {

    public static final CredentialSearchCriteria NONE = new CredentialSearchCriteria(null, null);

    private final String urlPattern;
    private final String usernamePattern;
}

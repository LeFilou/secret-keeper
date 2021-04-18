package org.melsif.secretkeeper.credentials.domain;


import org.melsif.secretkeeper.commons.EntityNotFoundException;

public class CredentialNotFoundException extends EntityNotFoundException {

    public CredentialNotFoundException(String message) {
        super(message);
    }
}

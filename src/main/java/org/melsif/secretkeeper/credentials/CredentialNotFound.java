package org.melsif.secretkeeper.credentials;


import org.melsif.secretkeeper.commons.EntityNotFoundException;

public class CredentialNotFound extends EntityNotFoundException {

    public CredentialNotFound(String message) {
        super(message);
    }
}

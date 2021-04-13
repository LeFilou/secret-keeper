package org.melsif.secretkeeper.credentials;


import javax.persistence.EntityNotFoundException;

public class CredentialNotFound extends EntityNotFoundException {

    public CredentialNotFound(String message) {
        super(message);
    }
}

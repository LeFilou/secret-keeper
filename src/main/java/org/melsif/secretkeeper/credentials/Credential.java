package org.melsif.secretkeeper.credentials;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "CREDENTIALS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Credential implements Serializable {

    @Id
    @SequenceGenerator(name = "credentials_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credentials_id_seq")
    private Long id ;

    @Embedded
    private CredentialIdentifiers credentialIdentifiers;

    private String password;
    private LocalDate creationDate;
    private LocalDate modificationDate;

    private Credential(String url, String username, String password) {
        credentialIdentifiers = new CredentialIdentifiers(url, username);
        this.password = Objects.requireNonNull(password);
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
    }

    public static Credential newCredential(String url, String username, String password) {
        return new Credential(url, username, password);
    }

    public String getUrl() {
        return credentialIdentifiers.getUrl();
    }

    public String getUsername() {
        return credentialIdentifiers.getUsername();
    }

    public void changePassword(String newPassword) {
        modificationDate = LocalDate.now();
        password = newPassword;
    }

    @Embeddable
    @NoArgsConstructor
    @Getter
    static class CredentialIdentifiers {
        public static final String URL_OR_USERNAME_EMPTY = "credentialIdentifiers.empty.fields.error";
        public static final String URL_INVALID = "credentialIdentifiers.invalid.url.error";
        private String url;
        private String username;

        CredentialIdentifiers(String url, String username) {
            if (Strings.isBlank(url) || Strings.isBlank(username)) {
                throw new IllegalArgumentException(URL_OR_USERNAME_EMPTY);
            }
            if (!new UrlValidator().isValid(url)) {
                throw new IllegalArgumentException(URL_INVALID);
            }
            this.url = url;
            this.username = username;
        }
    }
}

package org.melsif.secretkeeper.credentials;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @SequenceGenerator(name = "credentials_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credentials_seq")
    private Long id;
    private String url;
    private String username;
    private String password;
    private LocalDate creationDate;
    private LocalDate modificationDate;

    private Credential(String url, String username, String password) {
        this.url = Objects.requireNonNull(url);
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
    }

    static Credential newCredential(String url, String username, String password) {
        return new Credential(url, username, password);
    }
}

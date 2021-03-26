CREATE TABLE CREDENTIALS
(
    id                INTEGER constraint credentials_pk primary key,
    url               VARCHAR(255) NOT NULL,
    username          VARCHAR(255) NOT NULL,
    creation_date     TIMESTAMP,
    modification_date TIMESTAMP,
    password          VARCHAR(255),
    constraint credentials_uq
        unique (url, username)
);

CREATE SEQUENCE credentials_seq;
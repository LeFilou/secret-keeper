package org.melsif.secretkeeper.credentials;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import static lombok.AccessLevel.NONE;

@NoArgsConstructor(access = NONE)
class CredentialSpecifications {

    public static Specification<Credential> urlContains(String url) {
        return (root, query, cb) ->  {
            if (StringUtils.isBlank(url)) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.like(cb.lower(root.get(Credential_.credentialIdentifiers).get("url")), getLikePattern(url));
        };
    }

    public static Specification<Credential> usernameContains(String username) {
        return (root, query, cb) ->  {
            if (StringUtils.isBlank(username)) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.like(cb.lower(root.get(Credential_.credentialIdentifiers).get("username")), getLikePattern(username));
        };
    }

    private static String getLikePattern(final String searchTerm) {
        return "%" + searchTerm.toLowerCase() + "%";
    }
}

package org.melsif.secretkeeper.credentials.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CredentialSpecifications {

    public static Specification<Credential> urlIsLike(String url) {
        return (root, query, cb) ->  {
            if (StringUtils.isBlank(url)) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.like(cb.lower(root.get("url")), getLikePattern(url));
        };
    }

    public static Specification<Credential> usernameIsLike(String username) {
        return (root, query, cb) ->  {
            if (StringUtils.isBlank(username)) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.like(cb.lower(root.get("username")), getLikePattern(username));
        };
    }

    private static String getLikePattern(final String searchTerm) {
        return "%" + searchTerm.toLowerCase() + "%";
    }
}

package org.melsif.secretkeeper.credentials;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

class CredentialSpecifications {

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

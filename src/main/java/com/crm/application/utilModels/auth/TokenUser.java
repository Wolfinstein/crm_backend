package com.crm.application.utilModels.auth;

import com.crm.application.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;

public class TokenUser extends org.springframework.security.core.userdetails.User {
    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);
    private User user;

    public TokenUser(User user) {
        //super(String.valueOf(user.getId()), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        super(user.getLogin(), user.getPasswordHash(), true, true, true, true, AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getRole() {
        return user.getRole().toString();
    }
}

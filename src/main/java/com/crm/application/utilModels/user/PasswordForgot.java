package com.crm.application.utilModels.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class PasswordForgot {

    @Email
    @NotEmpty
    private String email;

    public PasswordForgot() {
    }

    public PasswordForgot(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
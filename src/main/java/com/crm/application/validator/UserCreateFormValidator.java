package com.crm.application.validator;

import com.crm.application.utilModels.user.UserCreateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.crm.application.service.UserService;


@Component
public class UserCreateFormValidator implements Validator {

    private final UserService userService;

    public UserCreateFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateForm form = (UserCreateForm) target;
        validateEmail(errors, form);
        validateLogin(errors, form);
    }

    private void validateEmail(Errors errors, UserCreateForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            errors.reject("email.exists");
        }
    }

    private void validateLogin(Errors errors, UserCreateForm form) {
        if (userService.getUserByEmail(form.getLogin()).isPresent())
            errors.reject("login.exists");
        if (form.getLogin().length() < 6) {
            errors.reject("login.too.short");
        }
    }

}
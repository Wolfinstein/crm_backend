package com.crm.application.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.crm.application.model.User;
import com.crm.application.service.UserService;

@Component
public class UserUpdateValidator implements Validator {

    private final UserService userService;

    public UserUpdateValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        validateEmail(errors, user);
    }

    private void validateEmail(Errors errors, User user) {
        if (userService.getUserByEmail(user.getEmail()).isPresent() && !userService.getUserByEmail(user.getEmail()).get().getId().equals(user.getId())) {
            errors.reject("email.exists");
        }
    }
}

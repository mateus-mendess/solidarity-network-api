package com.solidarity.api.validation.validator;

import com.solidarity.api.dto.request.UserRequest;
import com.solidarity.api.validation.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRequest> {
    @Override
    public boolean isValid(UserRequest userRequest, ConstraintValidatorContext constraintValidatorContext) {
        return userRequest.getPassword().equals(userRequest.getConfirmPassword());
    }
}

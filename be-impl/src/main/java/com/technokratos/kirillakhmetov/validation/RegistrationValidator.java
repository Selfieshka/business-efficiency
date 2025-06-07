package com.technokratos.kirillakhmetov.validation;

import com.technokratos.kirillakhmetov.form.RegistrationForm;
import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RegistrationValidator implements Validator {

    private final OwnerService ownerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationForm form = (RegistrationForm) target;

        if (ownerService.existsByEmail(form.getEmail())) {
            errors.rejectValue("email", "email.exists", "Пользователь с таким email уже существует");
        }
    }
} 

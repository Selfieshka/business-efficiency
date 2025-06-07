package com.technokratos.kirillakhmetov.validator;

import com.technokratos.kirillakhmetov.form.RevenueForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class RevenueValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RevenueForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RevenueForm revenueForm = (RevenueForm) target;

        if (revenueForm.getDate().isAfter(LocalDate.now())) {
            errors.rejectValue("date", "date.after", "Дата не может быть в будущем");
        }
    }
}
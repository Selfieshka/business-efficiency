package com.technokratos.kirillakhmetov.form;

public record ProfileForm(
        String firstName,
        String lastName,
        String patronymic,
        Integer age,
        String phoneNumber) {
}

package com.technokratos.kirillakhmetov.dto.response;

public record OwnerResponse(
        String firstName,
        String lastName,
        String patronymic,
        Integer age,
        String email,
        String phoneNumber,
        String profilePhotoUrl) {
}

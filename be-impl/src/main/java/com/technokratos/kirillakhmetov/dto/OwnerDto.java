package com.technokratos.kirillakhmetov.dto;

import lombok.Builder;

@Builder
public record OwnerDto(
        String firstName,
        String lastName,
        String patronymic,
        Integer age,
        String email,
        String phoneNumber,
        String profilePhotoUrl) {
}

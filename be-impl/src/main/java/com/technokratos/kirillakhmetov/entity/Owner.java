package com.technokratos.kirillakhmetov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Owner {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private int age;
    private String email;
    private String password;
    private String phoneNumber;
    private String businessName;
    private String profilePhotoUrl;
}

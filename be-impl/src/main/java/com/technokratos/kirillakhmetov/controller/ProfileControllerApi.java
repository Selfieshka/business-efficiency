package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.api.ProfileApi;
import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProfileControllerApi implements ProfileApi {
    private final OwnerService ownerServiceImpl;

    @Override
    public void uploadAvatar(MultipartFile avatarRequest) {
        ownerServiceImpl.uploadProfilePhoto(100000L, avatarRequest);
    }
}

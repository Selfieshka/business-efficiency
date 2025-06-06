package com.technokratos.kirillakhmetov.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/profile")
public interface ProfileApi {
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    void uploadAvatar(@RequestPart("profilePhoto") MultipartFile avatarRequest);
}

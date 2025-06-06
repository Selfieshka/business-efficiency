package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.form.ProfileForm;
import org.springframework.web.multipart.MultipartFile;

public interface OwnerService {
    Long getOwnerIdByEmail(String email);

    OwnerResponse getProfileInfo(String email);

    OwnerResponse changePersonalData(long ownerId, ProfileForm profileForm);

    void deleteOwner(String email);

    void uploadProfilePhoto(long ownerId, MultipartFile avatarRequest);
}

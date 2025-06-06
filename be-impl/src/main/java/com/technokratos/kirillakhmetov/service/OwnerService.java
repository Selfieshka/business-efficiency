package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.form.ProfileForm;
import com.technokratos.kirillakhmetov.form.RegistrationForm;
import org.springframework.web.multipart.MultipartFile;

public interface OwnerService {
    Long getOwnerIdByEmail(String email);

    OwnerResponse getProfileInfo(Long ownerId);

    OwnerResponse changePersonalData(Long ownerId, ProfileForm profileForm);

    void deleteOwner(Long ownerId);

    void uploadProfilePhoto(long ownerId, MultipartFile avatarRequest);

    void save(RegistrationForm registrationForm);
}

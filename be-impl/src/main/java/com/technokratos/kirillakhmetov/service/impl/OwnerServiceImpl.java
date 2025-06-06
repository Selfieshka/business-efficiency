package com.technokratos.kirillakhmetov.service.impl;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.form.ProfileForm;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.service.ImageService;
import com.technokratos.kirillakhmetov.service.OwnerService;
import com.technokratos.kirillakhmetov.util.mapper.OwnerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final ImageService imageService;
    private final OwnerMapper ownerMapper;

    @Override
    public Long getOwnerIdByEmail(String email) {
        return ownerRepository.findByEmail(email)
                .map(Owner::getId)
                .orElseThrow(() -> new RuntimeException("Пользователь с почтой %s - не найден".formatted(email)));
    }

    @Override
    public OwnerResponse getProfileInfo(String email) {
        return ownerMapper.toResponse(
                ownerRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Пользователь с почтой %s - не найден".formatted(email)))
        );
    }

    @Override
    @Transactional
    public OwnerResponse changePersonalData(long ownerId, ProfileForm profileForm) {
        ownerRepository.updateOwnerProfile(
                ownerId,
                profileForm.firstName(),
                profileForm.lastName(),
                profileForm.patronymic(),
                profileForm.age(),
                profileForm.phoneNumber()
        );

        return ownerMapper.toResponse(
                ownerRepository
                        .findById(ownerId)
                        .orElseThrow(() -> new RuntimeException("Пользователь с id %s - не найден ".formatted(ownerId)))
        );
    }

    @Override
    public void deleteOwner(String email) {
        ownerRepository.deleteByEmail(email);
    }

    @Override
    @Transactional
    public void uploadProfilePhoto(long ownerId, MultipartFile avatarRequest) {
        try (InputStream inputStream = avatarRequest.getInputStream()) {
            String url = imageService.uploadProfilePhoto(inputStream, avatarRequest.getOriginalFilename());
            System.out.println(url);
            ownerRepository.updateProfilePhotoUrlByOwnerId(ownerId, url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

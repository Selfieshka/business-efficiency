package com.technokratos.kirillakhmetov.service;

import com.cloudinary.Cloudinary;
import com.technokratos.kirillakhmetov.dto.OwnerDto;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.util.mapper.OwnerMapper;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Paths;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private static final String UPLOAD_DIRECTORY = "uploads";
    private static final int DIRECTORIES_COUNT = 10;

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final Cloudinary cloudinary;

    public Long getOwnerIdByEmail(String email) {
        return ownerRepository.findByEmail(email)
                .map(Owner::getId)
                .orElseThrow(() -> new RuntimeException("Пользователь с почтой %s - не найден".formatted(email)));
    }

    public OwnerDto getProfileInfo(String email) {
        return ownerMapper.toOwnerDto(
                ownerRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Пользователь с почтой %s - не найден".formatted(email)))
        );
    }

    @Transactional
    public OwnerDto changePersonalData(OwnerDto ownerDto) {
        ownerRepository.updateOwnerProfile(
                ownerDto.firstName(),
                ownerDto.lastName(),
                ownerDto.patronymic(),
                ownerDto.age(),
                ownerDto.phoneNumber(),
                ownerDto.email()
        );

        return ownerMapper.toOwnerDto(
                ownerRepository
                        .findByEmail(ownerDto.email())
                        .orElseThrow(() -> new RuntimeException("Пользователь с почтой %s - не найден".formatted(ownerDto.email())))
        );
    }

    public void deleteOwner(String email) {
        ownerRepository.deleteByEmail(email);
    }

    @Transactional
    public String uploadProfilePhoto(Part photo, String email) {
        try (InputStream inputStream = photo.getInputStream()) {
            String url = uploadImage(inputStream, photo.getSubmittedFileName());
            ownerRepository.updateProfilePhotoUrlByEmail(url, email);
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String uploadImage(InputStream image, String submittedFileName) throws IOException {
        String filename = Paths.get(submittedFileName).getFileName().toString();
        File tempFile = createTemporaryFile(filename, image);
        Map<String, String> uploadInfo = cloudinary.uploader().upload(tempFile, Map.of());
        if (tempFile.exists()) {
            tempFile.delete();
        }
        return uploadInfo.get("url");
    }

    private File createTemporaryFile(String filename, InputStream image) throws IOException {
        File uploadDir = new File(UPLOAD_DIRECTORY + File.separator + Math.abs(filename.hashCode() % DIRECTORIES_COUNT));
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File tempFile = new File(uploadDir, filename);
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(image);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(bytesRead);
            }
        }
        return tempFile;
    }
}

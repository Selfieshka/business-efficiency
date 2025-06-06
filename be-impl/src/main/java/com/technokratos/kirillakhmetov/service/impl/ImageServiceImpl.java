package com.technokratos.kirillakhmetov.service.impl;

import com.cloudinary.Cloudinary;
import com.technokratos.kirillakhmetov.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private static final String UPLOAD_DIRECTORY = "uploads";
    private static final int DIRECTORIES_COUNT = 10;
    private final Cloudinary cloudinary;

    @Override
    public String uploadProfilePhoto(InputStream image, String originalFilename) {
        try {
            String uniqueFilename = "%s_%s".formatted(UUID.randomUUID(), originalFilename);
            File tempFile = createTemporaryFile(uniqueFilename, image);

            Map<String, Object> result = cloudinary.uploader().upload(tempFile, Map.of(
                    "public_id", "avatars/%s".formatted(UUID.randomUUID()),
                    "resource_type", "image"
            ));

            return (String) result.get("url");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка обработки фотографии", e);
        }
    }

    private File createTemporaryFile(String filename, InputStream image) throws IOException {
        File uploadDir = new File(UPLOAD_DIRECTORY + File.separator + Math.abs(filename.hashCode() % DIRECTORIES_COUNT));

        if (!uploadDir.exists()) uploadDir.mkdirs();

        File tempFile = new File(uploadDir, filename);

        try (FileOutputStream fos = new FileOutputStream(tempFile);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = image.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }
}

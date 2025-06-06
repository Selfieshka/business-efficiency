package com.technokratos.kirillakhmetov.service;

import java.io.InputStream;

public interface ImageService {
    String uploadProfilePhoto(InputStream inputStream, String originalFilename);
}

package com.iteducator.courses.service;

import com.iteducator.courses.exception.ImageException;
import com.iteducator.courses.model.Photo;
import com.iteducator.courses.repository.PhotoRepository;
import lombok.SneakyThrows;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @SneakyThrows
    public Photo convertToPhoto(MultipartFile file) {
        return new Photo(file.getOriginalFilename(),
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
    }

    public Photo overrideImage(Photo image, Photo existingImage) {
        try {
            validateImage(image);
            image.setId(existingImage.getId());
        } catch (ImageException e) {
            return photoRepository.save(existingImage);
        }
        return photoRepository.save(image);
    }

    public void validateImage(Photo image) {
        if (image != null && image.getImage().length() == 0) {
            throw new ImageException("Image is required");
        }
    }
}

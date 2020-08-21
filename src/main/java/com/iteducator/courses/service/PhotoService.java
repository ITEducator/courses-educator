package com.iteducator.courses.service;

import com.iteducator.courses.model.Photo;
import com.iteducator.courses.repository.PhotoRepository;
import lombok.SneakyThrows;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @SneakyThrows
    public Photo createPhoto(MultipartFile file) {
        if (file == null || file.getSize() == 0) {
            throw new RuntimeException("Image is empty");
        }
        Photo photo = new Photo();

        photo.setTitle(file.getOriginalFilename());
        photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

        return photo;
    }

    public Photo overrideImage(Photo image, Photo existingImage) {
        image.setId(existingImage != null ?
                existingImage.getId() : UUID.randomUUID().toString());
        return photoRepository.save(image);
    }

    public Photo getPhoto(String id) {
        return photoRepository.findById(id).get();
    }
}

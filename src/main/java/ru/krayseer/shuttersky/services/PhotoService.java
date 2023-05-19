package ru.krayseer.shuttersky.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.krayseer.shuttersky.domain.entities.Photo;
import ru.krayseer.shuttersky.domain.repositories.PhotoRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PhotoService {

    PhotoRepository photoRepository;

    RabbitService rabbitService;

    public ResponseEntity<byte[]> getPhoto(String uuid) {
        Photo photo = photoRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Такого фото не существует"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getFileType()));
        return new ResponseEntity<>(photo.getData(), headers, HttpStatus.OK);
    }

    public String uploadPhoto(MultipartFile multipartFile) {
        System.out.println("Изменения");
        if(multipartFile.isEmpty()) {
            throw new RuntimeException("Выберите файл");
        }
        var uuid = UUID.randomUUID().toString();
        try {
            photoRepository.save(Photo
                    .builder()
                    .fileType(multipartFile.getContentType())
                    .uuid(uuid)
                    .data(multipartFile.getBytes())
                    .build());
            rabbitService.send("Фотография успешно загружена");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла");
        }
        return uuid;
    }

}

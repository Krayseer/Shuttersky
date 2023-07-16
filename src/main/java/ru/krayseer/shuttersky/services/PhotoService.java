package ru.krayseer.shuttersky.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    /**
     * Получить фотографию
     * @param uuid идентификатор фотографии
     */
    public ResponseEntity<byte[]> getPhoto(String uuid) {
        Photo photo = photoRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Такого фото не существует"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getFileType()));
        log.info("Load photo by uuid: {}", uuid);
        return new ResponseEntity<>(photo.getData(), headers, HttpStatus.OK);
    }

    /**
     * Загрузить фотографию
     * @param multipartFile фотография, которую нужно загрузить
     * @return идентификатор загруженной фотографии
     */
    public String uploadPhoto(MultipartFile multipartFile) {
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
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла");
        }
        log.info("Upload photo with uuid: {}", uuid);
        return uuid;
    }

    /**
     * Удалить фотографию
     * @param uuid идентификатор фотографии
     */
    public void deletePhoto(String uuid) {
        log.info("Delete photo by uuid: {}", uuid);
        photoRepository.deleteByUuid(uuid);
    }

}

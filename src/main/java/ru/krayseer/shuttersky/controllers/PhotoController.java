package ru.krayseer.shuttersky.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.krayseer.shuttersky.services.PhotoService;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String uuid) {
        return photoService.getPhoto(uuid);
    }

    @PostMapping
    public String upload(@RequestParam("photo") MultipartFile file) {
        return photoService.uploadPhoto(file);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePhoto(@PathVariable String uuid) {
        photoService.deletePhoto(uuid);
    }

}

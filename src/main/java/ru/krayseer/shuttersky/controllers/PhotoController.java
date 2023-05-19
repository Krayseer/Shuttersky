package ru.krayseer.shuttersky.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.krayseer.shuttersky.services.PhotoService;

import java.io.IOException;

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
    public ResponseEntity<String> upload(@RequestParam("photo") MultipartFile file) throws IOException {
        return ResponseEntity.ok(photoService.uploadPhoto(file));
    }

}

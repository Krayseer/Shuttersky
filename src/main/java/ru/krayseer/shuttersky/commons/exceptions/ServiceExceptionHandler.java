package ru.krayseer.shuttersky.commons.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handlerException(RuntimeException ex) {
        log.error("Ошибка: " + ex.getMessage());
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .message(ex.getMessage())
                .build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> multipartExceptionHandler(MultipartException ex) {
        log.error("Ошибка: " + ex.getMessage());
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .message("Ошибка загрузки файла")
                .build(), HttpStatus.BAD_GATEWAY);
    }
}

package ru.krayseer.shuttersky.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class RabbitService {

    RabbitTemplate rabbitTemplate;

    @SneakyThrows
    public void send(String photoUrl) {
        rabbitTemplate.convertAndSend("photoQueue", photoUrl);
        log.info("Отправлен url загруженной фотографии: " + photoUrl);
    }
}

package ru.krayseer.shuttersky.commons.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private final String message;

}

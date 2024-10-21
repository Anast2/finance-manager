package com.itclopedia.cources.exceptions.handler;

import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.exceptions.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

import org.springframework.web.context.request.WebRequest;

@ControllerAdvice           // обработка исключений всего приложения
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object> handleInternalDtoException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ErrorDto.builder()
                .result(HttpStatus.NOT_FOUND.value())
                .description(ex.getMessage())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@Nonnull Exception ex,
                                                             @Nullable Object body,
                                                             @Nonnull HttpHeaders headers,
                                                             @Nonnull HttpStatusCode statusCode,
                                                             @Nonnull WebRequest request) {
        return new ResponseEntity<>(ErrorDto.builder()
                .result(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .description(ex.getMessage())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

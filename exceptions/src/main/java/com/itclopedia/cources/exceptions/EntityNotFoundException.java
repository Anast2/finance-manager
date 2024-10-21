package com.itclopedia.cources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, Number id) {
        super(String.format(ExceptionMessages.ENTITY_IS_NOT_FOUND_BY_ID, entity, id));
    }

    public EntityNotFoundException(String entity, String name) {
        super(String.format(ExceptionMessages.ENTITY_IS_NOT_FOUND_BY_NAME, entity, name));
    }

}

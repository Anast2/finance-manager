package com.itclopedia.cources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String entity, Number id) {
        super(String.format(ExceptionMessages.ENTITY_ALREADY_EXIST_BY_ID, entity, id));
    }

    public EntityAlreadyExistException(String entity, String name) {
        super(String.format(ExceptionMessages.ENTITY_ALREADY_EXIST_BY_NAME, entity, name));
    }

}
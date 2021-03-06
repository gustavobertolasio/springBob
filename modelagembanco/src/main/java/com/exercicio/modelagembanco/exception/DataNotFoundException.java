package com.exercicio.modelagembanco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -6754767023122935455L;

    public DataNotFoundException(String message) {
        super(message);
    }


} 
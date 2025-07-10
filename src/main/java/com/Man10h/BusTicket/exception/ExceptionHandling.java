package com.Man10h.BusTicket.exception;

import com.Man10h.BusTicket.exception.ex.BusErrorException;
import com.Man10h.BusTicket.exception.ex.RoleErrorException;
import com.Man10h.BusTicket.exception.ex.UserErrorException;
import com.Man10h.BusTicket.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(UserErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleUserErrorException(UserErrorException ex) {
        return ErrorDTO.builder()
                .errorCode(400L)
                .errorMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(RoleErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleRoleErrorException(RoleErrorException ex) {
        return ErrorDTO.builder()
                .errorCode(400L)
                .errorMessage(ex.getMessage())
                .build();
    }


    @ExceptionHandler(BusErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBusErrorException(BusErrorException ex) {
        return ErrorDTO.builder()
                .errorCode(400L)
                .errorMessage(ex.getMessage())
                .build();
    }
}

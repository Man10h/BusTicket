package com.Man10h.BusTicket.exception;

import com.Man10h.BusTicket.exception.ex.*;
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


    @ExceptionHandler(TripErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleTripErrorException(TripErrorException ex) {
        return ErrorDTO.builder()
                .errorCode(400L)
                .errorMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(TicketErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleTicketErrorException(TicketErrorException ex) {
        return ErrorDTO.builder()
                .errorCode(400L)
                .errorMessage(ex.getMessage())
                .build();
    }
}

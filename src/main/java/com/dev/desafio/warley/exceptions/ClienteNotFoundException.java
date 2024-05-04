package com.dev.desafio.warley.exceptions;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException(){}

    public ClienteNotFoundException(String message){
        super(message);
    }
}

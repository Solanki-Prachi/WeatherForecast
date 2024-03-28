package com.forecast.weather.exception;

public class InvalidZipcodeException extends RuntimeException{

    public InvalidZipcodeException(){
        super("ZipCode not found");
    }

    public InvalidZipcodeException(String message){
        super(message);
    }
}

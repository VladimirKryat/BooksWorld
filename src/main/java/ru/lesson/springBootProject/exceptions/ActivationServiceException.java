package ru.lesson.springBootProject.exceptions;

public class ActivationServiceException extends RuntimeException{
    public ActivationServiceException(){
        super();
    }
    public ActivationServiceException(String message){
        super(message);
    }
}

package ru.lesson.springBootProject.exceptions;

public class UserServiceException extends RuntimeException{
    public UserServiceException(){
        super();
    }
    public UserServiceException(String message){
        super(message);
    }
    public UserServiceException(RuntimeException exception){
        super(exception);
    }
}

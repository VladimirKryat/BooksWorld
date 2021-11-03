package ru.lesson.springBootProject.exceptions;

public class AuthorServiceException extends RuntimeException{
    public AuthorServiceException(){
        super();
    }
    public AuthorServiceException(String message){
        super(message);
    }
}

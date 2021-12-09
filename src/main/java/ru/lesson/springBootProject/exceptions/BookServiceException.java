package ru.lesson.springBootProject.exceptions;

public class BookServiceException extends RuntimeException{
    public BookServiceException(){
        super();
    }
    public BookServiceException(String message){
        super(message);
    }
}

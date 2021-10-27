package ru.lesson.springBootProject.exceptions;

public class UsernameNotUniqueException extends UserServiceException{
    public UsernameNotUniqueException(){
        super();
    }
    public UsernameNotUniqueException(String message){
        super(message);
    }
}

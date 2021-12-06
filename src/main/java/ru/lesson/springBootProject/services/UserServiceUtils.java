package ru.lesson.springBootProject.services;

import ru.lesson.springBootProject.models.User;

import java.util.regex.Pattern;

public class UserServiceUtils {
    public static boolean isValidUsername(User user){
        return user.getUsername()!=null && user.getUsername().strip().length()>0;
    }

    //    The following restrictions are imposed in the email addresses local-part by using this regex:
    //
    //    It allows numeric values from 0 to 9
    //    Both uppercase and lowercase letters from a to z are allowed
    //    Allowed are underscore “_”, hyphen “-” and dot “.”
    //    Dot isn't allowed at the start and end of the local-part
    //    Consecutive dots aren't allowed
    //    For the local part, a maximum of 64 characters are allowed
    //
    //Restrictions for the domain-part in this regular expression include:
    //
    //    It allows numeric values from 0 to 9
    //    We allow both uppercase and lowercase letters from a to z
    //    Hyphen “-” and dot “.” isn't allowed at the start and end of the domain-part
    //    No consecutive dots
    public static boolean isValidEmail(User user){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return user.getEmail()!=null && Pattern.compile(regexPattern).matcher(user.getEmail()).matches();
    }

    public static boolean isValidPassword(User user){
        return user.getPassword()!=null && user.getPassword().length()>=4;
    }

    public static boolean isValidUserdata(User user){
        return isValidUsername(user)&&isValidEmail(user)&&isValidPassword(user);
    }
    public static String messageForInvalidUserdata(User user){
        StringBuilder result=new StringBuilder("");
        if (!isValidUsername(user)) result.append("Invalid username.");
        if (!isValidPassword(user)) result.append("Invalid password.");
        if (!isValidEmail(user)) result.append("Invalid email.");
        return result.toString();
    }
}

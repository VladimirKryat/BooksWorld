package ru.lesson.springBootProject.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    private Long userId;
    private String username;
    public static UserForm from(User user){
        return new UserForm(user.getUserId(),user.getUsername());
    }
    public static UserForm from(UserDetailsImpl userDetails){
        return new UserForm(userDetails.getUser().getUserId(),userDetails.getUsername());
    }
}

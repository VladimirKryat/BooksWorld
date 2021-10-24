package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.repositories.UserRepository;
import ru.lesson.springBootProject.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

//RequestMapping на уровне класса позволяет не прописывать петь для каждого метода
//вешаем аннотацию для входа только админов, в любой из мапингов ниже
@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());
        return "userList";
    }
    //спринг при получении userID в пути вытаскивает с репозитория сущность User
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("states",State.values());
        return "userEdit";
    }

    //запрос на изменение данных пользователя
    @PostMapping
    public String userSave(
            @RequestParam("userID") User user,
            //так как unchecked поля не отправляются, то запрос будет содержать разное количество полей
            //следовательно нужно получать Map
            @RequestParam Map<String,String> mapUser
    ){
        if(mapUser.containsKey("username")){
            user.setUsername(mapUser.get("username"));
        }
        if(mapUser.containsKey("state"))
        {
            user.setState(State.valueOf(mapUser.get("state")));
        }
        if(mapUser.containsKey("email")){
            user.setEmail(mapUser.get("email"));
        }
        //чтобы отобрать из полученных параметров роли, нужно получить список возможных значений Role в строковом представлении
        //иначе Role.valueOf будет выбрасывать исключения
        List<String> listRoles = Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toList());
        user.getRoles().clear();
        for (String key : mapUser.keySet()) {
            if (listRoles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userService.save(user);
        return "redirect:/user";
    }
}

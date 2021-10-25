package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//RequestMapping на уровне класса позволяет не прописывать петь для каждого метода
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //вешаем аннотацию для входа только админов, в любой из мапингов ниже

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());
        return "userList";
    }
    //спринг при получении userID в пути вытаскивает с репозитория сущность User
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("states",State.values());
        return "userEdit";
    }

    //запрос на изменение данных пользователя
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam("userID") User user,
            //так как unchecked поля не отправляются, то запрос будет содержать разное количество полей
            //следовательно нужно получать Map
            @RequestParam Map<String,String> mapUser
    ){
        if(mapUser.containsKey("username")&&!mapUser.get("username").strip().isEmpty()){
            user.setUsername(mapUser.get("username").strip());
        }
        if(mapUser.containsKey("state")&&!mapUser.get("state").strip().isEmpty())
        {
            user.setState(State.valueOf(mapUser.get("state").strip().toUpperCase()));
        }
        if(mapUser.containsKey("email")&&!mapUser.get("email").strip().isEmpty()){
            user.setEmail(mapUser.get("email").strip());
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

    @GetMapping("/profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                             @RequestParam(value = "message", required = false) String message
                             ){
        model.addAttribute("user",userDetails.getUser());
        model.addAttribute("message",message);
        return "profile";
    }
    @PostMapping("/profile")
    public ModelAndView saveProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    User newUser,
                                    @RequestParam String oldPassword,
                                    HttpServletRequest req,
                                    Map<String,Object> model
                              ){
        User oldUser = userDetails.getUser();
        if (userService.checkPassword(oldUser, oldPassword)){
            if (newUser.getEmail()!=null&&!newUser.getEmail().equals(oldUser.getEmail())){
                model.put("message", "Confirm email before login, please");
            }
            User user = userService.change(oldUser, newUser);
//            выполняем logout
            req.getSession(false).invalidate();
            System.out.println(req.getHeader("Referer"));
            SecurityContextHolder.clearContext();
            return new ModelAndView("redirect:/login",model);
        }
        model.put("message","Incorrect password");
        return new ModelAndView("redirect:/user/profile",model);
    }

}

package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;
import ru.lesson.springBootProject.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
                                    @Valid User newUser,
                                    BindingResult bindingResult,
                                    @RequestParam String newPassword,
                                    @RequestParam String passwordConfirm,
                                    HttpServletRequest req,
                                    Model model
    ){
        User oldUser = userDetails.getUser();
        //валидируем новый пароль и его подтверждение если он введён
        boolean isValidPasswordConfirm=true;
        boolean isValidNewPassword = true;
        if (newPassword!=null&&!newPassword.strip().isEmpty()) {
            isValidNewPassword = newPassword.strip().length() >= 4 &&
                    newPassword.strip().length() <= 20;
            isValidPasswordConfirm = userService.checkPasswordConfirm(newPassword,passwordConfirm);
        }
        boolean isValidPassword = userService.checkPassword(oldUser,newUser.getPassword());
        //возвращаемся в редактирование профиля если:
        //ошибки валидации согласно аннотациям user
        //если старый пароль не верен
        //если новый пароль введён и не совпадает с confirm или не соответствует требованию длинны
        if (bindingResult.hasErrors()||!isValidPasswordConfirm||!isValidPassword||!isValidNewPassword){
            //если пользователь не ввёл новый пароль, а остальные
            if (!isValidPasswordConfirm)
                model.addAttribute("passwordConfirmError", "Password confirmation failed");
            if (!isValidPassword)
                model.addAttribute("passwordError","Password not correct");
            if (!isValidNewPassword)
                model.addAttribute("newPasswordError", "Password length must be from 4 to 100");
            model.mergeAttributes(ControllerUtils.getErrorMap(bindingResult));
            //если необходимые(not empty) значения затёрты восстанавливаем их
            if (newUser.getEmail()==null||newUser.getEmail().strip().isEmpty()){
                newUser.setEmail(oldUser.getEmail());
            }
            if (newUser.getUsername()==null||newUser.getUsername().strip().isEmpty()){
                newUser.setUsername(oldUser.getUsername());
            }
            //передаём значения для автозаполнения
            model.addAttribute("user",newUser);
            return new ModelAndView("profile",model.asMap());
        }
        //если изменён email выводим сообщение, что нужно его подтвердить
        if (newUser.getEmail()!=null&&!newUser.getEmail().equals(oldUser.getEmail())){
            model.addAttribute("message", "Confirm email before login, please");
        }
        //нельзя брать isValidNewPassword как условие сохранения нового пароля,
        //так как пользователь может не вводить новый пароль
        if (newPassword!=null&&!newPassword.strip().isEmpty()){
            newUser.setPassword(newPassword.strip());
        }
        User user = userService.change(oldUser, newUser);
//            выполняем logout
        System.out.println("Referer link: "+req.getHeader("Referer"));
        req.getSession(false).invalidate();
        SecurityContextHolder.clearContext();
        return new ModelAndView("redirect:/",model.asMap());

    }

}

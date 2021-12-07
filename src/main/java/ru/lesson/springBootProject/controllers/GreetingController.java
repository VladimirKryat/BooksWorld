package ru.lesson.springBootProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.util.Map;

@Controller
public class GreetingController {
    @GetMapping("/")
    public String root( Map<String, Object> map,
                        @RequestParam(value = "message",required = false)String message){
        map.put("some","Hello, Lets code!");
        map.put("message",message);
        return "main";
    }
}

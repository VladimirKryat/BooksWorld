package ru.lesson.springBootProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue = "World") String name,
            Map<String,Object> map
    ){
        map.put("name",name);
        return "greeting";
    }

    @GetMapping("/")
    public String root( Map<String, Object> map){
        map.put("some","Hello, Lets code!");
        return "main";
    }
}

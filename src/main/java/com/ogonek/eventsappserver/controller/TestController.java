package com.ogonek.eventsappserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Тестовый контроллер
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * Тестовый метод, возвращает строку "Hello!"
     */
    @RequestMapping("/hello")
    public @ResponseBody String hello(){
        return "Hello!";
    }
}

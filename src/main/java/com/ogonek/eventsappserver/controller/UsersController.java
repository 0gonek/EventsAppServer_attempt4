package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> getAllEvents(){
        Iterable<User> list = usersService.getAll();
        return list;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public @ResponseBody Iterable<User> newEvent(){
        usersService.addEvent();
        Iterable<User> list = usersService.getAll();
        return list;
    }
}

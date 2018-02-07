package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> getAllUsers(){
        Iterable<User> list = usersService.getAll();
        return list;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> addUser(){
        usersService.addUser();
        Iterable<User> list = usersService.getAll();
        return list;
    }

//
//    @RequestMapping(value = "/new", method = RequestMethod.GET)
//    public @ResponseBody Iterable<User> newEvent(){
//        usersService.addEvent();
//        Iterable<User> list = usersService.getAll();
//        return list;
//    }

    @RequestMapping(value = "/id={id}&name={name}", method = RequestMethod.GET)
    public Iterable<User> renameUser(@PathVariable Long id, @PathVariable String name){
        usersService.renameUser(id, name);
        return usersService.getAll();
    }
}

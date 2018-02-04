package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.repository.UsersRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    UsersRep usersRep;

    public Iterable<User> getAll() {
        Iterable<User> iterable = usersRep.findAll();
        return iterable;
    }

    public void addEvent(){
        usersRep.save(new User("testLogin", "testPass"));
    }
}

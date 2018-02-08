package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @RequestMapping(value = "/new/name={name}&code={code}&token={token}&integrationType={integrationType}&integrationId={integrationId}", method = RequestMethod.POST)
    public @ResponseBody
    void addUser1(@PathVariable String name, @PathVariable String code, @PathVariable String token, @PathVariable String integrationType, @PathVariable String integrationId ){
        usersService.addUser1(name, code, token, integrationType, integrationId);
    }

    @RequestMapping(value = "/new/name={name}&token={token}&integrationType={integrationType}&integrationId={integrationId}", method = RequestMethod.POST)
    public @ResponseBody
    void addUser2(@PathVariable String name, @PathVariable String token, @PathVariable String integrationType, @PathVariable String integrationId ){
        usersService.addUser2(name, token, integrationType, integrationId);
    }

    @RequestMapping(value = "/name/id={id}&token={token}", method = RequestMethod.GET)
    public String getUserName(@PathVariable Long id, @PathVariable String token){
        if(usersService.verifyToken(id, token))
            return usersService.getUserName(id);
        return "Not verified";
    }

    @Modifying
    @RequestMapping(value = "/verify/id={id}&token={token}", method = RequestMethod.GET)
    public boolean verifyUserCode(@PathVariable Long id, @PathVariable String token){
        return usersService.verifyToken(id, token);
    }

    @Modifying
    @RequestMapping(value = "/change/id={id}&token={token}&name={name}", method = RequestMethod.POST)
    public boolean changeUserName(@PathVariable Long id, @PathVariable String token, @PathVariable String name){
        if(usersService.verifyToken(id, token))
            return usersService.changeUserName(id, name);
        return false;
    }

    @Modifying
    @RequestMapping(value = "/change/id={id}&newtoken={newtoken}", method = RequestMethod.POST)
    public boolean changeUserCode(@PathVariable Long id, @PathVariable String newtoken){
        return usersService.changeUserToken(id, newtoken);
    }

//
//    @Modifying
//    @RequestMapping(value = "/test/integrationdd={integrationid}&integrationtype={integrationtype}", method = RequestMethod.GET)
//    public @ResponseBody User findByIntegration(@PathVariable String integrationid, @PathVariable String integrationtype){
//        return usersService.findByIntegration(integrationid, integrationtype);
//    }

    @Modifying
    @RequestMapping(value = "/testvk", method = RequestMethod.GET)
    public String testVK() throws Exception{
        String url = "http://localhost:8080/test/hello";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }
}

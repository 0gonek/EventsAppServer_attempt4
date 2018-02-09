package com.ogonek.eventsappserver.controller;

import com.google.gson.Gson;
import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.service.UsersService;
import com.ogonek.eventsappserver.social.ResponseVK;
import com.ogonek.eventsappserver.social.UserVK;
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

    private long addUser1(String name,  String code,  String token,  String integrationType,  String integrationId ){
        return usersService.addUser1(name, code, token, integrationType, integrationId);
    }

    private long addUser2(String name, String token, String integrationType, String integrationId ){
        return usersService.addUser2(name, token, integrationType, integrationId);
    }

    @RequestMapping(value = "/name/id={id}&token={token}", method = RequestMethod.GET)
    public String getUserName(@PathVariable Long id, @PathVariable String token){
        if(usersService.verifyToken(id, token))
            return usersService.getUserName(id);
        return "Not verified";
    }

    @RequestMapping(value = "/id/integrationid={integrationid}&integrationtype={integrationtype}", method = RequestMethod.GET)
    public @ResponseBody long findByIntegration(@PathVariable String integrationid, @PathVariable String integrationtype){
        try {
            return usersService.findByIntegration(integrationid, integrationtype);
        }
        catch (NullPointerException ex){
            return -1;
        }
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
    public boolean changeUserToken(@PathVariable Long id, @PathVariable String newtoken){
        return usersService.changeUserToken(id, newtoken);
    }

    @Modifying
    @RequestMapping(value = "/delete/id={id}&token={token}", method = RequestMethod.POST)
    public boolean deleteUser(@PathVariable Long id, @PathVariable String token){
        if(usersService.verifyToken(id, token))
            return usersService.deleteUser(id);
        return false;
    }

    @Modifying
    @RequestMapping(value = "/loginvk/integrationid={integrationid}&integrationtype={integrationtype}&token={token}", method = RequestMethod.POST)
    public long loginVK(@PathVariable String integrationid, @PathVariable String integrationtype, @PathVariable String token) throws Exception{
        String url = "https://api.vk.com/method/users.get?access_token=" + token;

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

        long userId = -1;
        Gson gson = new Gson();
        try {
            UserVK userVK = gson.fromJson(response.toString(), UserVK.class);
            if (userVK.getResponse()[0].getUid().equals(integrationid)) {
                userId = findByIntegration(integrationid, integrationtype);
                if (userId == -1) {
                    return addUser2(userVK.getResponse()[0].getFirst_name(), token, integrationtype, integrationid);
                }
            }
        }
        catch (NullPointerException ex){
            return -1;
        }
        return userId;
    }

    @Modifying
    @RequestMapping(value = "/testvk", method = RequestMethod.GET)
    public String testVK() throws Exception{
        String url = "https://api.vk.com/method/users.get?access_token=cc4e1e666851246f6a95ba9239445336b9159a1cb62830dc1e4af4c3cf7885c80f0ce135e29d85a0a562b";

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

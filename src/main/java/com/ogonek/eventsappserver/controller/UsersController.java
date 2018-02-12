package com.ogonek.eventsappserver.controller;

import com.google.gson.Gson;
import com.ogonek.eventsappserver.Pojo.UserName;
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

    @RequestMapping(value = "/get_name", method = RequestMethod.GET)
    public String getUserName(@RequestParam("id") Long id, @RequestParam("token") String token){
        if(usersService.verifyToken(id, token))
            return usersService.getUserName(id);
        return "Not verified";
    }

    @RequestMapping(value = "/get_id", method = RequestMethod.GET)
    public @ResponseBody long findByIntegration(@RequestParam("integration_id") String integrationid, @RequestParam("integration_type") String integrationtype){
        try {
            return usersService.findByIntegration(integrationid, integrationtype);
        }
        catch (NullPointerException ex){
            return -1;
        }
    }

    @Modifying
    @RequestMapping(value = "/change_token?id={id}&new_token={newtoken}", method = RequestMethod.GET)
    public boolean changeUserToken(@PathVariable Long id, @PathVariable String newtoken){
        return usersService.changeUserToken(id, newtoken);
    }

    @Modifying
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean deleteUser(@RequestParam("id") Long id, @RequestParam("token") String token){
        if(usersService.verifyToken(id, token))
            return usersService.deleteUser(id);
        return false;
    }

    @Modifying
    @RequestMapping(value = "/loginvk", method = RequestMethod.GET)
    public long loginVK(@RequestParam("integration_id") String integrationid, @RequestParam("integration_type") String integrationtype, @RequestParam("token") String token) throws Exception{
        return usersService.loginVK(integrationid, integrationtype, token);
    }

    @Modifying
    @RequestMapping(value = "/change_name", method = RequestMethod.POST)
    public boolean changeUserName(@RequestBody UserName userName){
        if(usersService.verifyToken(userName.getId(), userName.getToken()))
            return usersService.changeUserName(userName.getId(), userName.getName());
        return false;
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

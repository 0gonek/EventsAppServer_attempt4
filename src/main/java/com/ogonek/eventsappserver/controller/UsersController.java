package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.Pojo.PojoNameAndAvatar;
import com.ogonek.eventsappserver.Pojo.PojoUserName;
import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Контроллер для запросов, касающихся пользователей. Все запросы по пути /users
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    /**
     * Сервис с пользователями
     */
    @Autowired
    UsersService usersService;

    /**
     * Возвращает информацию обо всех пользователях
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> getAllUsers(){
        return usersService.getAll();
    }

    private long addUser1(String name,  String code,  String token,  String integrationType,  String integrationId ){
        return usersService.addUser1(name, code, token, integrationType, integrationId);
    }

    private long addUser2(String name, String token, String integrationType, String integrationId ){
        return usersService.addUser2(name, token, integrationType, integrationId);
    }

    /**
     * Возвращает имя пользователя
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     */
    @RequestMapping(value = "/get_name", method = RequestMethod.GET)
    public String getUserName(@RequestParam("id") Long id, @RequestParam("token") String token){
        if(usersService.verifyToken(id, token))
            return usersService.getUserName(id);
        return "Not verified";
    }

    /**
     * Возвращает айди пользователя по известным integration id и integration type
     * @param integrationid айди пользователя в одной из соцсетей
     * @param integrationtype тип соцсети
     */
    @RequestMapping(value = "/get_id", method = RequestMethod.GET)
    public @ResponseBody long findByIntegration(@RequestParam("integration_id") String integrationid, @RequestParam("integration_type") String integrationtype){
        try {
            return usersService.findByIntegration(integrationid, integrationtype);
        }
        catch (NullPointerException ex){
            return -1;
        }
    }

    /**
     * Возвращает путь в аватарке пользователя
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     */
    @RequestMapping(value = "/get_avatar", method = RequestMethod.GET)
    public String getAvatar(@RequestParam("id") Long id, @RequestParam("token") String token){
        if(usersService.verifyToken(id, token))
            return usersService.getBigAvatarVK(id);
        return null;
    }

    /**
     * Позволяет изменить уникальный ключ пользователя. Возвращает boolean - успешно ли прошло добавление
     * @param id айди пользователя
     * @param newtoken новый уникальный ключ пользователя
     */
    @Modifying
    @RequestMapping(value = "/change_token?id={id}&new_token={newtoken}", method = RequestMethod.GET)
    public boolean changeUserToken(@PathVariable Long id, @PathVariable String newtoken){
        return usersService.changeUserToken(id, newtoken);
    }

    /**
     * Позволяет удалить пользователя. Возвращает boolean - успешно ли прошло удаление
     * @param id айди владельца группы
     * @param token уникальный ключ владельца группы
     */
    @Modifying
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean deleteUser(@RequestParam("id") Long id, @RequestParam("token") String token){
        if(usersService.verifyToken(id, token))
            return usersService.deleteUser(id);
        return false;
    }

    /**
     * Возвращает информацию о пользователе, если логин чреез VK прошёл успешно, иначе null
     * @param integrationid integration id VK
     * @param token token VK
     */
    @Modifying
    @RequestMapping(value = "/loginvk", method = RequestMethod.GET)
    public PojoNameAndAvatar loginVK(@RequestParam("integration_id") String integrationid, @RequestParam("token") String token) throws Exception{
        return usersService.loginVK(integrationid, token);
    }

    /**
     * Позволяет изменить имя ключ пользователя. Возвращает boolean - успешно ли прошло добавление
     * @param pojoUserName новое имя, айди и токен пользователя
     */
    @Modifying
    @RequestMapping(value = "/change_name", method = RequestMethod.POST)
    public boolean changeUserName(@RequestBody PojoUserName pojoUserName){
        if(usersService.verifyToken(pojoUserName.getId(), pojoUserName.getToken()))
            return usersService.changeUserName(pojoUserName.getId(), pojoUserName.getName());
        return false;
    }

    /**
     * Тестовый метод для логина VK
     */
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

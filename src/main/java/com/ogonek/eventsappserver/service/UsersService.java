package com.ogonek.eventsappserver.service;

import com.google.gson.Gson;
import com.ogonek.eventsappserver.Pojo.PojoNameAndAvatar;
import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.repository.EventsRep;
import com.ogonek.eventsappserver.repository.IdPairsRep;
import com.ogonek.eventsappserver.repository.OwnerIdPairsRep;
import com.ogonek.eventsappserver.repository.UsersRep;
import com.ogonek.eventsappserver.social.ResponseVKSmallAvatar;
import com.ogonek.eventsappserver.social.UserVKBigAvatar;
import com.ogonek.eventsappserver.social.UserVKLogin;
import com.ogonek.eventsappserver.social.UserVkSmallAvatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для пользователей и всего, что с ними связано
 */
@Service
public class UsersService {

    /**
     * База пользователей
     */
    @Autowired
    UsersRep usersRep;
    /**
     * База мероприятий
     */
    @Autowired
    EventsRep eventsRep;
    /**
     * База пар мероприятие-пользователь
     */
    @Autowired
    IdPairsRep idPairsRep;
    /**
     * База пар мероприятие-владелец
     */
    @Autowired
    OwnerIdPairsRep ownerIdPairRep;

    /**
     * Возвращает лист всех пар мероприятие-посетитель
     */
    public Iterable<User> getAll() {
        return usersRep.findAll();
    }

    /**
     * Добавляет пользователя с паролем (устаревшее)
     * @param name имя
     * @param code пароль
     * @param token уникальный идентификатор
     * @param integrationType тип интеграции
     * @param integrationId айди интеграции
     */
    public long addUser1(String name, String code, String token, String integrationType, String integrationId){
        User user = new User(name, code, token, integrationType, integrationId);
        usersRep.save(user);
        return user.getId();
    }

    /**
     * Добавляет пользователя в базу
     * @param name имя
     * @param token уникальный идентификатор
     * @param integrationType тип интеграции
     * @param integrationId айди интеграции
     */
    public long addUser2(String name, String token, String integrationType, String integrationId){
        User user = new User(name, token, integrationType, integrationId);
        usersRep.save(user);
        return user.getId();
    }

    /**
     * Удаляет пользователя из базы
     * @param userId айди пользователя
     */
    public boolean deleteUser(Long userId){
        usersRep.deleteById(userId);
        return true;
    }

    /**
     * Возвращает boolean, верный ли у пользователя токен
     * @param userId айди пользователя
     * @param token уникальный идентификатор
     */
    public boolean verifyToken(Long userId, String token){
        return usersRep.findById(userId).verifyToken(token);
    }

    /**
     * Изменяет имя пользователя, возвращает boolean - успешно ли прошло изменение
     * @param id айди пользователя
     * @param newName новое имя
     */
    public boolean changeUserName(long id, String newName){
        return usersRep.changeUserName(id, newName) == 1;
    }

    public boolean changeUserCode(long id, String newCode){
        return  usersRep.changeUserCode(id, newCode) == 1;
    }

    /**
     * Изменяет уникальный идентификатор пользователя, возвращает boolean - успешно ли прошло изменение
     * @param id айди пользователя
     * @param newToken новый уникальный идентификатор
     */
    public boolean changeUserToken(long id, String newToken){
        return  usersRep.changeUserToken(id, newToken) == 1;
    }

    public boolean changeUserIntegrationType(long id, String newIntegrationType){
        return usersRep.changeUserIntegrationType(id, newIntegrationType) == 1;
    }

    public boolean changeUserIntegrationId(long id, String newIntegrationId){
        return usersRep.changeUserIntegrationId(id, newIntegrationId) == 1;
    }

    /**
     * Возвращает имя пользователя
     * @param id айди пользователя
     */
    public String getUserName(long id){
        return usersRep.findById(id).getName();
    }

    /**
     * Возвращает айди пользователя по его айди интеграции и её типу
     * @param integrationId айди интеграции
     * @param integrationType тип интеграции
     */
    public long findByIntegration(String integrationId, String integrationType){
        return usersRep.findByIntegrationIdAndIntegrationType(integrationId, integrationType).getId();
    }

    /**
     * Возвращает путь к картинке с большим аватаром пользователя из VK
     * @param id айди пользователя
     */
    public String getBigAvatarVK(long id){
        String url = "https://api.vk.com/method/users.get?user_ids=" + usersRep.findById(id).getIntegrationId()
                + "&fields=photo_200" + "&access_token=e5377f96e5377f96e5377f96b0e5699969ee537e5377f96bf4d32aecf699309c93418f5" + "&v=5.8";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            try {
                UserVKBigAvatar userVKBigAvatar = gson.fromJson(response.toString(), UserVKBigAvatar.class);
                return userVKBigAvatar.getResponse()[0].getPhoto_200();
            } catch (NullPointerException ex) {
                return null;
            }
        }
        catch (Exception ex){
            return null;
        }
    }

    /**
     * Возвращает лист путей к картинкам пользователей с их маленькими аватарками из VK
     * @param id массив айди пользователей
     */
    List<String> getSmallAvatarsVK(Long[] id){
        StringBuilder url = new StringBuilder("https://api.vk.com/method/users.get?user_ids=");
        int n = id.length;
        url.append(usersRep.findById(id[0]).getIntegrationId());
        for (int i = 1; i < n; i++) {
            url.append(",").append(usersRep.findById(id[i]).getIntegrationId());
        }
        url.append("&fields=photo_50");
        url.append("&access_token=e5377f96e5377f96e5377f96b0e5699969ee537e5377f96bf4d32aecf699309c93418f5");
        url.append("&v=5.8");
        try {
            URL obj = new URL(url.toString());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            try {
                UserVkSmallAvatar userVkSmallAvatar = gson.fromJson(response.toString(), UserVkSmallAvatar.class);
                List<String> avatars = new ArrayList<>();
                n = userVkSmallAvatar.getResponse().length;
                ResponseVKSmallAvatar responseVKSmallAvatar[] = userVkSmallAvatar.getResponse();
                for (int i = 0; i < n; i++) {
                    avatars.add(responseVKSmallAvatar[i].getPhoto_50());
                }
                return avatars;
            } catch (NullPointerException ex) {
                return null;
            }
        }
        catch (Exception ex){
            return null;
        }
    }

    /**
     * Возвращает имя и ауть к картинке с аватаром из VK пользователя, если айди интеграции и токен были валидными.
     * Иначе возвращает null.
     * @param integrationid айди интеграции
     * @param token цникальный идентификатор
     */
    public PojoNameAndAvatar loginVK(String integrationid, String token) throws Exception{
        String url = "https://api.vk.com/method/users.get?access_token=" + token + "&v=5.8";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        long userId;
        Gson gson = new Gson();
        try {
            UserVKLogin userVKLogin = gson.fromJson(response.toString(), UserVKLogin.class);
            if (userVKLogin.getResponse()[0].getId().equals(integrationid)) {
                try {
                    userId = findByIntegration(integrationid, "VK");
                }
                catch (NullPointerException ex)
                {
                    userId = -1;
                }
                if (userId == -1) {
                    userId = addUser2(userVKLogin.getResponse()[0].getFirst_name(), token, "VK", integrationid);
                }
                else{
                    changeUserToken(userId, token);
                }
                User user = usersRep.findById(userId);
                return new PojoNameAndAvatar(userId, user.getName(), getBigAvatarVK(userId));
            }
            else
                return null;
        }
        catch (NullPointerException ex){
            return null;
        }
    }
}

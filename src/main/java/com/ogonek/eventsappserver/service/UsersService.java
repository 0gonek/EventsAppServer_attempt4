package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.repository.EventsRep;
import com.ogonek.eventsappserver.repository.IdPairsRep;
import com.ogonek.eventsappserver.repository.OwnerIdPairsRep;
import com.ogonek.eventsappserver.repository.UsersRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    UsersRep usersRep;
    @Autowired
    EventsRep eventsRep;
    @Autowired
    IdPairsRep idPairsRep;
    @Autowired
    OwnerIdPairsRep ownerIdPairRep;

    public Iterable<User> getAll() {
        Iterable<User> iterable = usersRep.findAll();
        return iterable;
    }

    public long addUser1(String name, String code, String token, String integrationType, String integrationId){
        User user = new User(name, code, token, integrationType, integrationId);
        usersRep.save(user);
        return user.getId();
    }

    public long addUser2(String name, String token, String integrationType, String integrationId){
        User user = new User(name, token, integrationType, integrationId);
        usersRep.save(user);
        return user.getId();
    }

    public boolean deleteUser(Long userId){
        usersRep.deleteById(userId);
        return true;
    }

    public boolean verifyToken(Long userId, String token){
        return usersRep.findById(userId).verifyToken(token);
    }

    public boolean changeUserName(long id, String newName){
        return usersRep.changeUserName(id, newName) == 1;
    }

    public boolean changeUserCode(long id, String newCode){
        return  usersRep.changeUserCode(id, newCode) == 1;
    }

    public boolean changeUserToken(long id, String newToken){
        return  usersRep.changeUserToken(id, newToken) == 1;
    }

    public boolean changeUserIntegrationType(long id, String newIntegrationType){
        return usersRep.changeUserIntegrationType(id, newIntegrationType) == 1;
    }

    public boolean changeUserIntegrationId(long id, String newIntegrationId){
        return usersRep.changeUserIntegrationId(id, newIntegrationId) == 1;
    }

    public String getUserName(long id){
        return usersRep.findById(id).getName();
    }

    public long findByIntegration(String integrationId, String integrationType){
        return usersRep.findByIntegrationIdAndIntegrationType(integrationId, integrationType).getId();
    }
}

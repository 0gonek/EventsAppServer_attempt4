package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRep extends JpaRepository<User, Long> {
    User findById(long id);
}

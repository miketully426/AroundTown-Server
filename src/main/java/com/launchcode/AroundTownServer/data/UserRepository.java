package com.launchcode.AroundTownServer.data;

import com.launchcode.AroundTownServer.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> deleteByUsername(String username);
}

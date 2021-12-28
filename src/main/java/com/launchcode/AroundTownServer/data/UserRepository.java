package com.launchcode.AroundTownServer.data;

import com.launchcode.AroundTownServer.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}

package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findByName(String name);
    Optional<User> findById(int id);
    User save(User user) ;
    void removeUser(User user);
    User createUser(User user) ;
    boolean resultAlreadyCreated(String userLoggedIn, Result result);

    void addResult(String userLoggedIn, Result result);
}

package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findByName(String name);
    Optional<User> findById(int id);

    void modifyResult(String userLoggedIn, Result result, Result resultOld);

    User save(User user) ;
    void removeUser(User user);
    User createUser(User user) ;
    boolean resultAlreadyCreated(String userLoggedIn, Result result);

    void addResult(String userLoggedIn, Result result);

    void deleteAll();


}

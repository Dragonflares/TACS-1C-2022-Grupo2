package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findByName(String name);
    Optional<User> findById(int id);
    User save(User user) throws UserAlreadyExistsException;
    void removeUser(User user);
    User createUser(User user) throws UserAlreadyExistsException;
}

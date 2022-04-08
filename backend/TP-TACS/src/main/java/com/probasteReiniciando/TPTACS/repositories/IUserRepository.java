package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.UserDao;

import java.util.Optional;

public interface IUserRepository {

    Optional<UserDao> findByName(String name);
    Optional<UserDao> findById(Long id);
    UserDao save(UserDao user);
    void delete(UserDao user);
}

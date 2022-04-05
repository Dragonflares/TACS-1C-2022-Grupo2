package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findByName(String name);
    Optional<User> findById(Long id);
    User save(User user);
    void delete(User user);
}

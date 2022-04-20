package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryInMemory implements IUserRepository {

    private List<User> repositoryInMemory = new ArrayList<>();
    private UserRepositoryInMemory userRepositoryInMemory;


    @Override
    public Optional<User> findByName(String name) {
        return repositoryInMemory.stream().filter(x -> x.getName().equals(name)).findFirst();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        if (repositoryInMemory.contains(user))
        {
            throw new IllegalStateException("User already exists");
        }
        repositoryInMemory.add(user);
        return user;
    }

    @Override
    public void delete(UserDao user) {
        repositoryInMemory.removeIf(x -> x.getName().equals(user.getName()));
    }
}

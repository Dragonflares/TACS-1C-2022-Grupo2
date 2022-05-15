package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryInMemory implements IUserRepository {

    private List<User> repositoryInMemory = new ArrayList<>();
    private UserRepositoryInMemory userRepositoryInMemory;
    private Integer currentId = 0;


    @Override
    public Optional<User> findByName(String name) {
        return repositoryInMemory.stream().filter(x -> x.getUsername().equals(name)).findFirst();
    }

    @Override
    public Optional<User> findById(int id) {
        return repositoryInMemory.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public User createUser(User user)  {
        modifyId();
        user.setId(this.currentId);
        this.save(user);
        return user;
    }

    @Override
    public boolean resultAlreadyCreated(String userLoggedIn, Result result) {
        return findByName(userLoggedIn).get().getResults().
                stream().anyMatch(x ->
                        (
                                x.getDate().equals(result.getDate()) &&
                                        x.getLanguage().equals(result.getLanguage())
                        )
                );
    }

    @Override
    public void addResult(String userLoggedIn, Result result) {
        findByName(userLoggedIn).get().getResults().add(result);
    }

    @Override
    public User save(User user) {

        repositoryInMemory.add(user);
        return user;
    }

    @Override
    public void removeUser(User user) {
        repositoryInMemory.removeIf(x -> x.getId().equals(user.getId()));
    }

    private void modifyId(){
        this.currentId++;
    }

}

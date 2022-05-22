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
    private Integer currentIdUser = 0;
    private Integer currentIdResult = 0;


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
        modifyIdUser();
        user.setId(this.currentIdUser);
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
        User actualUser = findByName(userLoggedIn).get();
        result.setUsername(actualUser.getUsername());
        modifyIdResult();
        result.setId(currentIdResult);
        actualUser.getResults().add(result);
        this.repositoryInMemory.removeIf(x -> x.getUsername().equals(actualUser.getUsername()));
        this.save(actualUser);
    }

    @Override
    public void deleteAll() {
        this.repositoryInMemory.clear();
    }


    @Override
    public void modifyResult(String userLoggedIn, Result result, Result resultOld) {

        User actualUser = findByName(userLoggedIn).get();
        result.setUsername(actualUser.getUsername());
        result.setId(resultOld.getId());
        actualUser.getResults().add(result);
        this.repositoryInMemory.removeIf(x -> x.getUsername().equals(actualUser.getUsername()));
        this.save(actualUser);

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

    private void modifyIdUser(){
        this.currentIdUser++;
    }

    private void modifyIdResult(){
        this.currentIdResult++;
    }
}

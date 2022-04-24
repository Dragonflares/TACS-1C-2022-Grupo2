package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
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
        return repositoryInMemory.stream().filter(x -> x.getName().equals(name)).findFirst();
    }

    @Override
    public Optional<User> findById(int id) {
        return repositoryInMemory.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public User createUser(UserDto dto){
        modifyId();
        User newUser = User.builder()
                .name(dto.getUsername())
                .password(dto.getPassword())
                .id(this.currentId)
                .build();
        this.save(newUser);
        return newUser;
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
    public void removeUser(User user) {
        repositoryInMemory.removeIf(x -> x.getId().equals(user.getId()));
    }

    private void modifyId(){
        this.currentId++;
    }

}

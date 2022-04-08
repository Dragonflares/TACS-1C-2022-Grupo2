package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.domain.UserDao;
import com.probasteReiniciando.TPTACS.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public Optional<UserDao> findByUsername(String username) {
        return Optional.of(userRepository.findByName(username).get());
    }

    public UserDao save(UserDao newUser) {
        return userRepository.save(newUser);
    }
}

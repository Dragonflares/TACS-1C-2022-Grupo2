package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return Optional.of(userRepository.findByName(username).get());
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}

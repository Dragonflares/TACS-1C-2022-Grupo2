package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import com.probasteReiniciando.TPTACS.exceptions.ResultAlreadyExistsException;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;
import com.probasteReiniciando.TPTACS.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    private ModelMapperTacs modelMapper = new ModelMapperTacs();

    public Optional<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }

    public User save(UserLoginDto userParam)  {

        if (findByUsername(userParam.getUsername()).isPresent())
        {
            throw new UserAlreadyExistsException(userParam.getUsername());
        }

        User newUser = User.builder()
                .username(userParam.getUsername())
                .password(userParam.getPassword())
                .build();

        return userRepository.createUser(newUser);
    }

    public Result createResult(String userLoggedIn, ResultDto resultDto) {

        Result result = modelMapper.map(resultDto,Result.class);

        if(userRepository.resultAlreadyCreated(userLoggedIn, result)){
            throw new ResultAlreadyExistsException(userLoggedIn,result.getDate(),result.getLanguage());
        }

        userRepository.addResult(userLoggedIn,result);

        return result;

    }

    public List<Result> getResultsByUser(String userLoggedIn) {
        return userRepository.findByName(userLoggedIn).get().getResults();
    }
}

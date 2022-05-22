package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import com.probasteReiniciando.TPTACS.exceptions.ResultAlreadyExistsException;
import com.probasteReiniciando.TPTACS.exceptions.ResultCanNotBeModified;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;
import com.probasteReiniciando.TPTACS.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Result createResult(String userLoggedIn, Result result) {

        if(userRepository.resultAlreadyCreated(userLoggedIn, result)){
            throw new ResultAlreadyExistsException(userLoggedIn,result.getDate(),result.getLanguage());
        }

        userRepository.addResult(userLoggedIn,result);

        return result;

    }

    public List<Result> getResultsByUser(String userLoggedIn) {
        return userRepository.findByName(userLoggedIn).get().getResults();
    }

    public List<Result> getTodayResultsByUser(String userLoggedIn) {
        return userRepository.findByName(userLoggedIn).get().getResults().stream().filter(result -> result.getDate().equals(LocalDate.now())).toList();
    }

    public Result modifyResult(String userLoggedIn, Result result, int resultId) {

        List<Result> resultsUser = getResultsByUserAndDateAndId(userLoggedIn, LocalDate.now(),resultId);
        if(resultsUser.isEmpty() || !result.getDate().equals(LocalDate.now())){
            throw new ResultCanNotBeModified();
        }

        userRepository.modifyResult(userLoggedIn,result,resultsUser.get(0));

        return result;

    }

    private List<Result> getResultsByUserAndDateAndId(String userLoggedIn, LocalDate now, int resultId) {
        return getResultsByUser(userLoggedIn).stream().filter(x -> x.getDate().equals(now) && x.getId().equals(resultId)).toList();
    }

    private List<Result> getResultsByUserAndDate(String userLoggedIn, LocalDate now) {
        return getResultsByUser(userLoggedIn).stream().filter(x -> x.getDate().equals(now)).toList();
    }
}

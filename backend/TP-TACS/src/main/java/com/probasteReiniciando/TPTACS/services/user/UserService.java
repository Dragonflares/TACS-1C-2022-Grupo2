package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dao.ResultDAO;
import com.probasteReiniciando.TPTACS.dao.UserDAO;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import com.probasteReiniciando.TPTACS.exceptions.ResultAlreadyExistsException;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;
import com.probasteReiniciando.TPTACS.repositories.IUserRepositoryMongoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private IUserRepositoryMongoDB userRepository;
    

    private ModelMapperTacs modelMapper = new ModelMapperTacs();

    public Optional<User> findByUsername(String username) {

        Optional<UserDAO> userDAO = userRepository.findByName(username);

        Optional<User> user = Optional.empty();

        if(userDAO.isPresent()) {
            user =  Optional.of(modelMapper.map(userDAO.get(),User.class));
        }

        return user;

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

        return modelMapper.map(userRepository.save(modelMapper.map(newUser,UserDAO.class)),User.class);

    }

    public Result createResult(String userLoggedIn, Result result) {


        UserDAO userDAO = userRepository.findByName(userLoggedIn).get();
        List<ResultDAO> resultsDAO = userDAO.getResultDAOS();

        boolean resultAlreadyCreated = false;

        if(resultsDAO != null && !resultsDAO.isEmpty()) {
            Result finalResult = result;
            resultAlreadyCreated =
                    resultsDAO.stream().filter(r -> r.getDate().equals(finalResult.getDate()) && r.getLanguage().equals(finalResult.getLanguage())).collect(Collectors.toList()).size() > 0;

        }

        if(resultAlreadyCreated){
            throw new ResultAlreadyExistsException(userLoggedIn,result.getDate(),result.getLanguage());
        }

        ResultDAO resultDAO = modelMapper.map(result,ResultDAO.class);
        userDAO.getResultDAOS().add(resultDAO);
        userRepository.save(userDAO);
        result = modelMapper.map(resultDAO,Result.class);

        return result;

    }

    public List<Result> getResultsByUser(String userLoggedIn) {
        return modelMapper.map(userRepository.findByName(userLoggedIn).get(),User.class).getResults();
    }

    public List<Result> getTodayResultsByUser(String userLoggedIn) {
        return modelMapper.map(userRepository.findByName(userLoggedIn).get(),User.class).getResults().stream().filter(result -> result.getDate().equals(LocalDate.now())).toList();
    }



    private List<Result> getResultsByUserAndDateAndId(String userLoggedIn, LocalDate now, int resultId) {
        return getResultsByUser(userLoggedIn).stream().filter(x -> x.getDate().equals(now) && x.getId().equals(resultId)).toList();
    }

    private List<Result> getResultsByUserAndDate(String userLoggedIn, LocalDate now) {
        return getResultsByUser(userLoggedIn).stream().filter(x -> x.getDate().equals(now)).toList();
    }
}

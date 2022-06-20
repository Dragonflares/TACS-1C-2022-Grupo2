package com.probasteReiniciando.TPTACS.services.user;


import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dao.UserDAO;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import com.probasteReiniciando.TPTACS.repositories.IUserRepositoryMongoDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private IUserRepositoryMongoDB userRepository;

    final private ModelMapperTacs modelMapper = new ModelMapperTacs();

    @Test
    public void findByUsername () {

        User expectedUser = User.builder().username("pepe").build();

        UserDAO expecUserDAO = modelMapper.map(expectedUser,UserDAO.class);

        when(userRepository.findByName(expectedUser.getUsername())).thenReturn(Optional.of(expecUserDAO));

        UserService userService = new UserService(userRepository);

        Optional<User> actualUser = userService.findByUsername(expectedUser.getUsername());

        if(actualUser.isPresent()){
            Assertions.assertEquals(expectedUser, actualUser.get());
        } else {
            throw new RuntimeException();
        }

    }

    @Test
    public void save () {

        UserLoginDto userInput = UserLoginDto.builder().username("pepe").password("pass").build();

        User expectedUser = User.builder().username(userInput.getUsername()).password(userInput.getPassword()).build();

        UserDAO expectUserDAO = modelMapper.map(expectedUser,UserDAO.class);

        when(userRepository.findByName(expectedUser.getUsername())).thenReturn(Optional.empty());

        when(userRepository.save(expectUserDAO)).thenReturn(expectUserDAO);

        UserService userService = new UserService(userRepository);

        User actualUser = userService.save(userInput);

        Assertions.assertEquals(expectedUser, actualUser);

    }

    @Test
    public void createResult() {

        User user = User.builder().username("pepe").build();

        UserDAO userDAO = modelMapper.map(user,UserDAO.class);

        Result expectedResult = Result.builder().points(2).language(Language.SPANISH).date(LocalDate.now()).build();

        String dateString = Instant.now().atZone(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);

        when(userRepository.existResultToday(user.getUsername(), expectedResult.getLanguage().name(), dateString)).thenReturn(false);
        when(userRepository.findByName(user.getUsername())).thenReturn(Optional.of(userDAO));
        when(userRepository.save(userDAO)).thenReturn(userDAO);

        UserService userService = new UserService(userRepository);

        Result actualResult = userService.createResult(user.getUsername(),expectedResult);

        Assertions.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void getTodayResultsByUser() {

        Result expectedResult = Result.builder().points(2).language(Language.SPANISH).date(LocalDate.now()).build();

        List<Result> expectedResults = List.of(expectedResult);

        User user = User.builder().username("pepe").results(expectedResults).build();

        UserDAO userDAO = modelMapper.map(user,UserDAO.class);

        when(userRepository.findByName(user.getUsername())).thenReturn(Optional.of(userDAO));

        UserService userService = new UserService(userRepository);

        List<Result> actualResults = userService.getTodayResultsByUser(user.getUsername());

        Assertions.assertEquals(expectedResults, actualResults);

    }

}

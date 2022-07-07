package com.probasteReiniciando.TPTACS.services.session;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dao.SessionDAO;
import com.probasteReiniciando.TPTACS.dao.UserDAO;
import com.probasteReiniciando.TPTACS.domain.Session;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;
import com.probasteReiniciando.TPTACS.exceptions.UserNotFoundException;
import com.probasteReiniciando.TPTACS.repositories.ISessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private ISessionRepository sessionRepository;

    @Autowired
    private ModelMapperTacs modelMapper;


    public Optional<Session> findByUsername(String username) {

        Optional<SessionDAO> sessionDAO = sessionRepository.findByName(username);

        Optional<Session> session = Optional.empty();

        if(sessionDAO.isPresent()) {
            session =  Optional.of(modelMapper.map(sessionDAO.get(),Session.class));
        }

        return session;

    }

    public Session save(String  username, String token)  {


        deleteSession(username);

        Session session = Session.builder()
                .username(username)
                .token(token)
                .build();

        return modelMapper.map(sessionRepository.save(modelMapper.map(session,SessionDAO.class)),Session.class);

    }

    public void deleteSession(String userLoggedIn) {
        Optional<SessionDAO> sessionOld = sessionRepository.findByName(userLoggedIn);
        sessionOld.ifPresent(sessionDAO -> sessionRepository.delete(sessionDAO));
    }
}

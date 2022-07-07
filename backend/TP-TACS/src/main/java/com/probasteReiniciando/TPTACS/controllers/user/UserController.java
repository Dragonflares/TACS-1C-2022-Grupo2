package com.probasteReiniciando.TPTACS.controllers.user;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dto.LogOutDto;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.TournamentsMetadataDto;
import com.probasteReiniciando.TPTACS.services.session.SessionService;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@Slf4j
public class UserController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ModelMapperTacs modelMapper;

    @DeleteMapping(path = "/accesstoken" ,produces = "application/json")
    public LogOutDto obtainTournamentsMetadata (@RequestAttribute(name="userAttributeName") String userLoggedIn) {

        sessionService.deleteSession(userLoggedIn);

        return LogOutDto.builder().message("GoodBye").build();

    }

}
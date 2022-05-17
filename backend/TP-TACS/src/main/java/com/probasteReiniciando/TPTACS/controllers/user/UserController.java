package com.probasteReiniciando.TPTACS.controllers.user;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@Slf4j
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private ModelMapperTacs modelMapper;

    @GetMapping(path = "/tournaments", produces = "aplication/json")
    public List<TournamentDto> obtainTorunamentsByPlayer(@RequestAttribute(name = "userAttributeName") String userLoggedIn, @RequestParam int offset, @RequestParam int limit) {

        modelMapper.mapList(tournamentService.obtainTorunamentsByPlayer(userLoggedIn, offset, limit), TournamentDto.class);
        return null;
    }

}
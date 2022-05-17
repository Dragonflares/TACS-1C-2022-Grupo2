package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.exceptions.ErrorParameterException;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@Slf4j
@RequestMapping({ "/tournaments" })
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private ModelMapperTacs modelMapper;

    @PostMapping(produces = "application/json")
    public TournamentDto createTournament(@RequestBody TournamentDto tournament,@RequestAttribute(name="userAttributeName") String userLoggedIn) {
        return modelMapper.map(tournamentService.createTournament(tournament, userLoggedIn),TournamentDto.class);
    }

    @GetMapping(produces = "application/json")
    public List<TournamentDto> publicTournaments(@RequestParam(defaultValue = "1")  int page, @RequestParam(defaultValue = "10") int limit) {
        validateParamsPagination(page,limit);
        return  modelMapper.mapList(tournamentService.obtainPublicTournaments(page, limit),TournamentDto.class);
    }



    @GetMapping(path="/{id}", produces = "application/json")
    public TournamentDto singleTournaments(@PathVariable int id)  {
        return  modelMapper.map(tournamentService.getTournamentById(id),TournamentDto.class);
    }


    @PostMapping(path="/{tournamentId}/participants", produces = "application/json")
    public List<String> addParticipants(@PathVariable int tournamentId, @RequestBody UserDto user, @RequestAttribute(name="userAttributeName") String userLoggedIn)  {
        return  tournamentService.addUser(tournamentId, user.getUsername(), userLoggedIn);
    }

    //Si no ponen el orderby ni el order, la query sirve para ver los participantes
    @GetMapping(path="/{tournamentId}/participants", produces = "application/json")
    public List<String> obtainParticipants(@PathVariable int tournamentId, @RequestParam Optional<String> orderBy, @RequestParam Optional<String> order) {

        return tournamentService.obtainParticipants(tournamentId, orderBy, order);

    }

    @PatchMapping(path="/{tournamentId}", produces = "application/json")
    public TournamentDto updateTournament
            (@PathVariable int tournamentId, @RequestBody TournamentDto tournament, @RequestAttribute(name="userAttributeName") String userLoggedIn) {

        return modelMapper.map(tournamentService.updateTournament(tournamentId, tournament, userLoggedIn),TournamentDto.class);

    }

    @PostMapping(path="/{tournamentId}/participants", produces = "aplication/json")
    public List<String> addAllParticipants(@PathVariable int tournamentId, @RequestBody List<UserDto> users, @RequestAttribute(name="userAttributeName") String userLoggedIn)  {

        List<String> participants = null;

        for (UserDto user : users){

            participants = modelMapper.mapList(tournamentService.addUser(tournamentId, user.getUsername(), userLoggedIn),String.class);

        }

        return participants;
    }


    private void validateParamsPagination(int page, int limit) {
        if(page < 1 || limit <0)
            throw new ErrorParameterException("The page or limit are wrong");
    }

}

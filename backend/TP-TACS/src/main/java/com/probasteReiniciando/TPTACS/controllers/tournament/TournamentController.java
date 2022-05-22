package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.TournamentsMetadata;
import com.probasteReiniciando.TPTACS.dto.PositionDto;
import com.probasteReiniciando.TPTACS.dto.QuantityTournamentDto;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.TournamentsMetadataDto;
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
    public TournamentDto createTournament(@RequestBody TournamentDto tournamentDto,@RequestAttribute(name="userAttributeName") String userLoggedIn) {
        Tournament tournament = modelMapper.map(tournamentDto, Tournament.class);
        return modelMapper.map(tournamentService.createTournament(tournament, userLoggedIn),TournamentDto.class);
    }

    @GetMapping(produces = "application/json")
    public List<TournamentDto> obtainTournaments (@RequestParam(defaultValue = "1")  int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "PUBLIC") Privacy privacy, @RequestAttribute(name="userAttributeName") String userLoggedIn) {
        validateParamsPagination(page,limit);
        return  modelMapper.mapList(tournamentService.obtainTournaments(page, limit, privacy, userLoggedIn),TournamentDto.class);
    }

    @GetMapping(path = "/quantity" ,produces = "application/json")
    public QuantityTournamentDto quantityTournaments (@RequestParam(defaultValue = "PUBLIC") Privacy privacy, @RequestAttribute(name="userAttributeName") String userLoggedIn) {
        return  modelMapper.map(tournamentService.getQuantityOfTournaments(privacy, userLoggedIn),QuantityTournamentDto.class);
    }

    @GetMapping(path = "/metadata" ,produces = "application/json")
    public TournamentsMetadataDto obtainTournamentsMetadata (@RequestAttribute(name="userAttributeName") String userLoggedIn) {

        return modelMapper.map(tournamentService.obtainTournamentMetadata(userLoggedIn),TournamentsMetadataDto.class);

    }


    @GetMapping(path="/{id}", produces = "application/json")
    public TournamentDto singleTournaments(@PathVariable int id)  {
        return  modelMapper.map(tournamentService.getTournamentById(id),TournamentDto.class);
    }

    @PostMapping(path="/{tournamentId}/participants", produces = "application/json")
    public List<UserDto> addParticipants(@PathVariable int tournamentId, @RequestBody UserDto user, @RequestAttribute(name="userAttributeName") String userLoggedIn)  {
        return  modelMapper.mapList(tournamentService.addUser(tournamentId, user.getUsername(), userLoggedIn),UserDto.class);
    }

    //Si no ponen el orderby ni el order, la query sirve para ver los participantes
    @GetMapping(path="/{tournamentId}/participants", produces = "application/json")
    public List<UserDto> obtainParticipants(@PathVariable int tournamentId, @RequestParam Optional<String> orderBy, @RequestParam Optional<String> order) {

        return modelMapper.mapList(tournamentService.obtainParticipants(tournamentId, orderBy, order),UserDto.class);

    }

    @GetMapping(path="/{tournamentId}/positions", produces = "application/json")
    public List<PositionDto> obtainPositions(@PathVariable int tournamentId, @RequestParam Optional<String> order) {

        return modelMapper.mapList(tournamentService.obtainPositions(tournamentId, order),PositionDto.class);

    }

    @PatchMapping(path="/{tournamentId}", produces = "application/json")
    public TournamentDto updateTournament
            (@PathVariable int tournamentId, @RequestBody TournamentDto tournamentDto, @RequestAttribute(name="userAttributeName") String userLoggedIn) {

        Tournament tournament = modelMapper.map(tournamentDto, Tournament.class);
        return modelMapper.map(tournamentService.updateTournament(tournamentId, tournament, userLoggedIn),TournamentDto.class);

     }

    private void validateParamsPagination(int page, int limit) {
        if(page < 1 || limit <0)
            throw new ErrorParameterException("The page or limit are wrong");
    }

}

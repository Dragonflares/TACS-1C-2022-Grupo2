package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.*;
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
    public PagedListDto<TournamentTableDto> obtainTournaments (@RequestParam(defaultValue = "1")  int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "PUBLIC") Privacy privacy, @RequestAttribute(name="userAttributeName") String userLoggedIn) {
        validateParamsPagination(page,limit);

        var tournaments = tournamentService.obtainTournaments(page, limit, privacy, userLoggedIn);

        return  modelMapper.map(
                // si saben usar el mapper para que me deje meter la condicion
                new PagedListDto<TournamentTableDto>(
                        tournaments.getContent().stream().map(p ->
                        {
                            return new TournamentTableDto(
                                    p.getId(),
                                    p.getName(),
                                    p.getLanguage(),
                                    p.getStartDate(),
                                    p.getEndDate(),
                                    p.getPrivacy(),
                                    p.getOwner().getUsername().equals(userLoggedIn)
                            );
                        }).toList(),
                        tournaments.getTotalElements()
                ),
                PagedListDto.class);
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
    public TournamentDto singleTournaments(@PathVariable String id)  {
        return  modelMapper.map(tournamentService.getTournamentById(id),TournamentDto.class);
    }

    @PostMapping(path="/{tournamentId}/participants", produces = "application/json")
    public List<UserDto> addParticipants(@PathVariable String tournamentId, @RequestBody UserDto user, @RequestAttribute(name="userAttributeName") String userLoggedIn)  {
        return  modelMapper.mapList(tournamentService.addUser(tournamentId, user.getUsername(), userLoggedIn),UserDto.class);
    }

    @PostMapping(path="/{tournamentId}/participants/self", produces = "application/json")
    public List<UserDto> addSelf(@PathVariable String tournamentId, @RequestAttribute(name="userAttributeName") String userLoggedIn)  {
        return  modelMapper.mapList(tournamentService.addUser(tournamentId, userLoggedIn, userLoggedIn),UserDto.class);
    }

    //Si no ponen el orderby ni el order, la query sirve para ver los participantes
    @GetMapping(path="/{tournamentId}/participants", produces = "application/json")
    public List<UserDto> obtainParticipants(@PathVariable String tournamentId, @RequestParam Optional<String> orderBy, @RequestParam Optional<String> order) {

        return modelMapper.mapList(tournamentService.obtainParticipants(tournamentId, orderBy, order),UserDto.class);

    }

    @GetMapping(path="/{tournamentId}/positions", produces = "application/json")
    public List<PositionDto> obtainPositions(@PathVariable String tournamentId, @RequestParam Optional<String> order) {

        return modelMapper.mapList(tournamentService.obtainPositions(tournamentId, order),PositionDto.class);

    }

    @PatchMapping(path="/{tournamentId}", produces = "application/json")
    public TournamentDto updateTournament
            (@PathVariable String tournamentId, @RequestBody TournamentDto tournamentDto, @RequestAttribute(name="userAttributeName") String userLoggedIn) {

        Tournament tournament = modelMapper.map(tournamentDto, Tournament.class);
        return modelMapper.map(tournamentService.updateTournament(tournamentId, tournament, userLoggedIn),TournamentDto.class);

     }

    private void validateParamsPagination(int page, int limit) {
        if(page < 1 || limit <0)
            throw new ErrorParameterException("The page or limit are wrong");
    }

}

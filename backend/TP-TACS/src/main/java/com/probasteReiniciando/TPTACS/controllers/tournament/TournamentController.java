package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public TournamentDto createTournament(@RequestBody TournamentDto tournament,@RequestAttribute(name="userAttributeName") String username) {
        log.info(username);
        return modelMapper.map(tournamentService.createTournament(tournament),TournamentDto.class);
    }

    @GetMapping(produces = "application/json")
    public List<TournamentDto> publicTournaments(@RequestParam int offset, @RequestParam int limit) {
        return  modelMapper.mapList(tournamentService.obtainPublicTournaments(offset, limit),TournamentDto.class);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public TournamentDto singleTournaments(@PathVariable int id)  {
        return  modelMapper.map(tournamentService.getTournamentById(id),TournamentDto.class);
    }


    @PatchMapping(path="/{tournamentId}/participants", produces = "application/json")
    public List<String> addParticipants(@PathVariable int tournamentId, @RequestBody UserDto user)  {
        return  modelMapper.mapList(tournamentService.addUser(tournamentId, user.getUsername()),String.class);
    }

    //Si no ponen el orderby ni el order, la query sirve para ver los participantes
    @GetMapping(path="/{id}/participants", produces = "application/json")
    public List<String> obtainParticipants(@PathVariable int id, @RequestParam String orderBy, @RequestParam String order) {
        return List.of(Integer.toString(id), Integer.toString(id + 324));
    }


}

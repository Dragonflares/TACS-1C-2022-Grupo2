package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin()
@RequestMapping({ "/tournaments" })
public class TournamentController {


    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private ModelMapperTacs modelMapper;


    @GetMapping(produces = "application/json")
    public List<TournamentDto> publicTournaments() {

        return  modelMapper.mapList(tournamentService.getPublicTournaments(),TournamentDto.class);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public TournamentDto singleTournaments(@PathVariable int id) {

        return  modelMapper.map(tournamentService.getTournamentById(id),TournamentDto.class);

    }

    @PostMapping(produces = "application/json")
    public TournamentDto createTournament(@RequestBody TournamentDto tournament) {
        return modelMapper.map(tournamentService.postTournament(tournament),TournamentDto.class);
    }
    @PutMapping(path="/tournaments/{id}/participants", produces = "application/json")
    public List<String> addUser(@PathVariable int id) {
        return List.of(Integer.toString(id), Integer.toString(id+323));
    }

    //Si no ponen el orderby ni el order, la query sirve para ver los participantes
    @GetMapping(path="/tournaments/{id}/participants", produces = "application/json")
    public List<String> addUser(@PathVariable int id, @RequestParam String orderBy, @RequestParam String order) {
        return List.of(Integer.toString(id), Integer.toString(id + 324));
    }


}

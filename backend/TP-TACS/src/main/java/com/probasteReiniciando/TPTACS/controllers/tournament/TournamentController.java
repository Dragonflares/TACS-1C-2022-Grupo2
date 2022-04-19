package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import com.probasteReiniciando.TPTACS.functions.TournamentConverter;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;


import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping({ "/tournaments" })
public class TournamentController {


    @Autowired
    TournamentService tournamentService;

    @Autowired
    TournamentConverter tournamentConverter;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping(produces = "application/json")
    public JSONWrapper publicTournaments() {

        return new JSONWrapper<>(List.of((modelMapper.map(tournamentService.getPublicTournaments(),TournamentDto.class))));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public JSONWrapper<TournamentDto> singleTournaments(@PathVariable int id) {

        return new JSONWrapper<>(List.of(modelMapper.map(tournamentService.getTournamentById(id),TournamentDto.class)));

    }

    @PostMapping(produces = "application/json")
    public JSONWrapper<TournamentDto> createTournament(@RequestBody TournamentDto tournament) {
        return new JSONWrapper<>(List.of(modelMapper.map(tournamentService.postTournament(tournament),TournamentDto.class)));
    }
    @PutMapping(path="/tournaments/{id}/participants", produces = "application/json")
    public ResponseEntity<JSONWrapper<String>> addUser(@PathVariable int id) {
        return ResponseEntity.ok(new JSONWrapper<>(List.of(Integer.toString(id), Integer.toString(id+323))));
    }

    //Si no ponen el orderby ni el order, la query sirve para ver los participantes
    @GetMapping(path="/tournaments/{id}/participants", produces = "application/json")
    public JSONWrapper<String> addUser(@PathVariable int id, @RequestParam String orderBy, @RequestParam String order) {
        return new JSONWrapper<>(List.of(Integer.toString(id), Integer.toString(id+324)));
    }



}

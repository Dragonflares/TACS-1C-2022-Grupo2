package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import com.probasteReiniciando.TPTACS.functions.TournamentConverter;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping({ "/tournaments" })
public class TournamentController {


    @Autowired
    TournamentService tournamentService;

    @Autowired
    TournamentConverter tournamentConverter;

    @GetMapping(produces = "application/json")
    public ResponseEntity<JSONWrapper> publicTournaments() {
        return ResponseEntity.ok(new JSONWrapper<>(tournamentConverter.convertListTournamentToDto(tournamentService.getPublicTournaments())));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<JSONWrapper<TournamentDto>> singleTournaments(@PathVariable int id) {
        return ResponseEntity.ok(new JSONWrapper<>(List.of(tournamentConverter.convertTournamentToDto(tournamentService.getTournamentById(id)))));

    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<JSONWrapper<TournamentDto>> createTournament(@RequestBody TournamentDto tournament) {
        // DEBERIA DEVOLVER SOLO EL ID
        return ResponseEntity.ok(new JSONWrapper<>(List.of(tournamentConverter.convertTournamentToDto(tournamentService.postTournament(tournament)))));
    }
    @PostMapping(path="/{id}/participants", produces = "application/json")
    public ResponseEntity<JSONWrapper<String>> addUser(@PathVariable int id) {
        return ResponseEntity.ok(new JSONWrapper<>(List.of(Integer.toString(id), Integer.toString(id+323))));
    }

    //Si no ponen el orderby ni el order, la query sirve para ver los participantes
    @GetMapping(path="/{id}/participants", produces = "application/json")
    public ResponseEntity<JSONWrapper<String>> addUser(@PathVariable int id, @RequestParam String orderBy, @RequestParam String order) {
        return ResponseEntity.ok(new JSONWrapper<>(List.of(Integer.toString(id), Integer.toString(id+324))));
    }



}

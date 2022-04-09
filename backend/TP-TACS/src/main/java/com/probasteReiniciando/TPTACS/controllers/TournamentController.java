package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping({ "/tournaments" })
public class TournamentController {


    @Autowired
    TournamentService tournamentService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<JSONWrapper> publicTournaments() {
        return ResponseEntity.ok(new JSONWrapper<>(tournamentService.getPublicTournaments()));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public  ResponseEntity<JSONWrapper> singleTournaments(@PathVariable int id) {
        return ResponseEntity.ok(new JSONWrapper<>(tournamentService.getPublicTournaments()));
        //FALTA HACER EL WRAPER
        //return ResponseEntity.ok(new JSONWrapper<>(tournamentService.getTournamentById(id)));
    }
}

package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping({ "/results" })
public class ResultController {

    @Autowired
    TournamentService tournamentService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<JSONWrapper> results() {
        return ResponseEntity.ok(new JSONWrapper<>(tournamentService.getResults()));
    }

}

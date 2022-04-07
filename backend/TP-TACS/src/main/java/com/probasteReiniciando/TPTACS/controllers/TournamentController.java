package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TournamentController {


    @Autowired
    TournamentService tournamentService;

    @RequestMapping({ "/tournaments" })
    public ResponseEntity<JSONWrapper> publicTournaments() {
        return ResponseEntity.ok(new JSONWrapper<>(tournamentService.getPublicTournaments()));
    }
}

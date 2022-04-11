package com.probasteReiniciando.TPTACS.controllers.result;

import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping({ "users/{id}/results" })
public class ResultController {

    @Autowired
    TournamentService tournamentService;

    //obtiene los resultados
    @GetMapping(produces = "application/json")
    public ResponseEntity<JSONWrapper> results(@PathVariable int id) {
        return ResponseEntity.ok(new JSONWrapper<>(tournamentService.getResults()));
    }

    //modifica resultado
    @PutMapping(path="/{resultId}", produces = "application/json")
    public ResponseEntity<JSONWrapper> results(@PathVariable int id, @PathVariable int resultId, @RequestBody ResultDto result) {
        return ResponseEntity.ok(new JSONWrapper<>(tournamentService.getResults()));
    }

    //crea resultado
    @PostMapping(produces = "application/json")
    public ResponseEntity<JSONWrapper> results(@PathVariable int id, @RequestBody ResultDto result) {
        return ResponseEntity.ok(new JSONWrapper<>(tournamentService.getResults()));
    }
}

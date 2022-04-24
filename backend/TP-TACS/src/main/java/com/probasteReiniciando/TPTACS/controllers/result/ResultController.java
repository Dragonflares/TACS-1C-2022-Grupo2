package com.probasteReiniciando.TPTACS.controllers.result;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping({ "users/{id}/results" })
public class ResultController {

    @Autowired
    private TournamentService tournamentService;

    //obtiene los resultados
    @GetMapping(produces = "application/json")
    public List<Result> results(@PathVariable int id) {
        return tournamentService.getResults(id);
    }

    //modifica resultado
    @PutMapping(path="/{resultId}", produces = "application/json")
    public List<Result> results(@PathVariable int id, @PathVariable int resultId, @RequestBody ResultDto result) {
        return tournamentService.getResults(id);
    }

    //crea resultado
    @PostMapping(produces = "application/json")
    public List<Result> results(@PathVariable int id, @RequestBody ResultDto result) {
        return tournamentService.getResults(id);
    }
}

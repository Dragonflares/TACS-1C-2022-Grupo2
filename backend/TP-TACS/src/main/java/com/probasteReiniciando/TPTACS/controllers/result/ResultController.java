package com.probasteReiniciando.TPTACS.controllers.result;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import com.probasteReiniciando.TPTACS.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping({ "users/results" })
public class ResultController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapperTacs modelMapper;

    //TODO obtiene los resultados
    @GetMapping(produces = "application/json")
    public List<Result> results(@PathVariable int id) {
        return null;
    }

    //TODO modifica resultado
    @PutMapping(path="/{resultId}", produces = "application/json")
    public List<Result> results(@PathVariable int id, @PathVariable int resultId, @RequestBody ResultDto result) {
        return null;
    }

    //crea resultado
    @PostMapping(produces = "application/json")
    public ResultDto results(@RequestAttribute(name="userAttributeName") String userLoggedIn, @RequestBody ResultDto result) {
        return modelMapper.map(userService.createResult(userLoggedIn,result),ResultDto.class);
    }
}

package com.probasteReiniciando.TPTACS.controllers.result;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.dto.PositionDto;
import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.exceptions.ResultBadRequestException;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import com.probasteReiniciando.TPTACS.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping({ "/results" })
public class ResultController {

    @Autowired
    private UserService userService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private ModelMapperTacs modelMapper;

    @GetMapping(produces = "application/json")
    public List<ResultDto> getResults(@RequestAttribute(name="userAttributeName") String userLoggedIn) {
        return modelMapper.mapList(userService.getTodayResultsByUser(userLoggedIn),ResultDto.class);
    }

    @GetMapping(path = "/top10" ,produces = "application/json")
    public List<PositionDto> getTop10Participants(@RequestParam(defaultValue = "desc") Optional<String> order) {
        return modelMapper.mapList(tournamentService.obtainAllParticipantsInPublicTournaments(order), PositionDto.class);
    }

    @PostMapping(produces = "application/json")
    public ResultDto createResult(@RequestAttribute(name="userAttributeName") String userLoggedIn, @RequestBody ResultDto resultDto) {
        validateResultDto(resultDto);
        Result result = modelMapper.map(resultDto,Result.class);
        return modelMapper.map(userService.createResult(userLoggedIn,result),ResultDto.class);
    }


    private void validateResultDto(ResultDto resultDto) {
        if(resultDto.getPoints() < 0 || resultDto.getPoints() > 7)
            throw new ResultBadRequestException();
    }
}

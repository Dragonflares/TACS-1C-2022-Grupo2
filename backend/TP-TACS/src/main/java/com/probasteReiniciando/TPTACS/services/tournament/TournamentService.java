package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.exceptions.TournamentNotFoundException;
import com.probasteReiniciando.TPTACS.exceptions.UserNotFoundException;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepository;
import com.probasteReiniciando.TPTACS.repositories.IUserRepository;
import com.probasteReiniciando.TPTACS.validators.TournamentValidator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class TournamentService {

    @Autowired
    private ITournamentRepository tournamentRepository;

    @Autowired
    private IUserRepository userRepository;

    public TournamentDto createTournament(TournamentDto tournamentDto) throws TournamentBadRequestException {

        TournamentValidator.validateRangeDate(tournamentDto.getStartDate(), tournamentDto.getEndDate());

        Tournament tournament = Tournament.builder()
                .name(tournamentDto.getName())
                .language(tournamentDto.getLanguage())
                .startDate(tournamentDto.getStartDate())
                .endDate(tournamentDto.getEndDate())
                .privacy(tournamentDto.getPrivacy())
                //TODO owner: obtener de los datos de la sesion?
                .build();
        tournament = tournamentRepository.createTournament(tournament);

        tournamentDto.setId(tournament.getId());

        return tournamentDto;
    }

    public List<Tournament> obtainPublicTournaments(int offset, int limit) {
        return tournamentRepository.obtainPublicTournaments(offset, limit);
    }

    public Tournament getTournamentById(int id)  {
        return tournamentRepository.obtainTournament(id).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(id)));
    }

    public List<String> addUser(int tournamentId, String userName)  {

        Tournament tournament = tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId)));

        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException(userName));

        if (Privacy.PUBLIC.equals(tournament.getPrivacy())) {

            return tournamentRepository.addUser(tournament, user);


        } else {
            //punto 3: aca tendriamos que fijarnos si el user logueado es el owner del torneo, sino tirar excepcion (forbiden?)
        }

        return null;
    }

    public List<Result> getResults(int id) {
        return tournamentRepository.obtainResults(id);
    } //TODO revisar

}

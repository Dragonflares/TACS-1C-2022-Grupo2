package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.exceptions.TournamentNotFoundException;
import com.probasteReiniciando.TPTACS.exceptions.UnAuthorizedException;
import com.probasteReiniciando.TPTACS.exceptions.UserNotFoundException;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepository;
import com.probasteReiniciando.TPTACS.repositories.IUserRepository;
import com.probasteReiniciando.TPTACS.validators.TournamentValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {

    final private ITournamentRepository tournamentRepository;

    final private IUserRepository userRepository;

    private ModelMapperTacs modelMapper = new ModelMapperTacs();

    public TournamentService(ITournamentRepository tournamentRepository, IUserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    public TournamentDto createTournament(TournamentDto tournamentDto, String userLoggedIn) {

        TournamentValidator.validateRangeDate(tournamentDto.getStartDate(), tournamentDto.getEndDate());

        Tournament tournament = modelMapper.map(tournamentDto, Tournament.class);

        User owner = userRepository.findByName(userLoggedIn).orElseThrow(() -> new UserNotFoundException(userLoggedIn));

        tournament.setOwner(owner);

        tournament.setParticipants(new ArrayList<>());

        tournament = tournamentRepository.createTournament(tournament);

        tournamentDto.setOwner(modelMapper.map(owner, UserDto.class));

        tournamentDto.setId(tournament.getId());

        return tournamentDto;
    }

    public List<Tournament> obtainTournaments(int page, int limit, Privacy privacy, String username) {

        switch(privacy) {

            case PUBLIC : return tournamentRepository.obtainPublicTournaments(page, limit);

            case PRIVATE : return tournamentRepository.obtainPrivateTournaments(page, limit, username);

            default: return List.of();

        }

    }

    public Tournament getTournamentById(int id) {
        return tournamentRepository.obtainTournament(id).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(id)));
    }

    public List<String> addUser(int tournamentId, String userName, String userLoggedIn) {

        Tournament tournament = tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId)));

        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException(userName));

        if (Privacy.PUBLIC.equals(tournament.getPrivacy())) {

            TournamentValidator.validateStartDate(tournament.getStartDate());

            return tournamentRepository.addUser(tournament, user);

        } else {

            if (tournament.getOwner().getUsername().equals(userLoggedIn)) {

            } else {

                throw new UnAuthorizedException(userLoggedIn);

            }

        }

        return null;
    }

    public List<Result> getResults(int id) {
        return tournamentRepository.obtainResults(id);
    } //TODO revisar


    public List<String> obtainParticipants(int tournamentId, Optional<String> orderBy, Optional<String> order) {
        return tournamentRepository.obtainParticipants(tournamentId, orderBy, order);


    }

    public Tournament updateTournament(int tournamentId, TournamentDto tournamentDto, String userLoggedIn) {

        Tournament tournament = tournamentRepository.obtainTournament(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId)));

        if (!tournament.getOwner().getUsername().equals(userLoggedIn)) {
            throw new UnAuthorizedException(userLoggedIn);
        }

        tournamentRepository.updateTournament(tournamentId, modelMapper.map(tournamentDto, Tournament.class));

        return tournamentRepository.obtainTournament(tournamentId).get();

    }

    public List<Tournament> obtainTorunamentsByPlayer(String userLoggedIn, int page, int limit){

        return tournamentRepository.findByOwner(userLoggedIn, page, limit);


    }

    public Integer getQuantityOfTournaments(Privacy privacy, String userLoggedIn) {
        Integer value = 0;
        switch(privacy) {

            case PUBLIC : value = tournamentRepository.quantityOfPublicTournaments();
            break;

            case PRIVATE : value =  tournamentRepository.quantityOfPrivateTournaments(userLoggedIn);
            break;

        }
        return value;
    }

}

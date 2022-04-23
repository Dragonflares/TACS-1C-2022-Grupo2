package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TournamentRepository implements ITournamentRepository {

    private List<Tournament> tournaments = new ArrayList<>();

    private Integer currentId = 0;

    @Override
    public List<Tournament> obtainPublicTournaments(int offset, int limit) {
        return tournaments.stream().filter(x -> x.getPrivacy().equals(Privacy.PUBLIC) && x.getId() >= offset && x.getId() < offset + limit).collect(Collectors.toList());
    }

    @Override
    public Optional<Tournament> obtainTournament(int id) {
        return tournaments.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public List<Result> obtainResults(int id) {
        return obtainTournament(id).get().getResults();
    }

    @Override
    public Tournament createTournament(Tournament tournament) {

        incrementId();

        tournaments.add(tournament);
        tournament.setId(currentId);

        return tournament;

    }

    private void incrementId() {
        currentId++;
    }


}

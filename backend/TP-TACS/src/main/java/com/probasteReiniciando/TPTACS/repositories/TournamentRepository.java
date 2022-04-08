package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Tournament;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TournamentRepository implements  ITournamentRepository {
    @Override
    public List<Tournament> getPublicTournaments() {
        return List.of(Tournament.builder().name("TournamentExample").build());
    }
    @Override
    public Tournament getTournament(int id){
        return Tournament.builder()
                .name("Prueba")
                .build();
    }

}

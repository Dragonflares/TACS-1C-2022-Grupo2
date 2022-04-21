package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TournamentRepository implements  ITournamentRepository {
    private List<Tournament> tournaments = new ArrayList<>();
    @Override
    public List<Tournament> obtainPublicTournaments() {
        return List.of(Tournament.builder().name("TournamentExampleRepository").language(Language.ENGLISH).build());
    }
    @Override
    public Optional<Tournament> obtainTournament(int id){

        return Integer.valueOf(id).equals(1) ? Optional.ofNullable(Tournament.builder()
                .name("Prueba")
                .language(Language.SPANISH)
                .build()) : Optional.empty();
    }
    @Override
    public List<Result> obtainResults(){
        return List.of(Result.builder().language(Language.SPANISH).date(LocalDate.now()).build());
    }
    @Override
    public Tournament createTournament(TournamentDto dto){
        return  Tournament.builder().name("Prueba").language(Language.SPANISH).build();
    }

}

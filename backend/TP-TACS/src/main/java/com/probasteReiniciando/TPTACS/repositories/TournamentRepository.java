package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.UserDao;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TournamentRepository implements  ITournamentRepository {
    @Override
    public List<Tournament> getPublicTournaments() {
        return List.of(Tournament.builder().name("TournamentExampleRepository").language(Language.ENGLISH).build());
    }

    @Override
    public Tournament getTournament(int id){
        return Tournament.builder()
                .name("Prueba")
                .language(Language.SPANISH)
                .build();
    }
    @Override
    public List<Result> getResults(){
        return List.of(Result.builder()
                .language(Language.SPANISH)
                .date(LocalDate.now())
                .build());
    }
    @Override
    public Tournament postTournament(TournamentDto dto){
        return  Tournament.builder()
                .name("Prueba")
                .language(Language.SPANISH)
                .build();
    }

}

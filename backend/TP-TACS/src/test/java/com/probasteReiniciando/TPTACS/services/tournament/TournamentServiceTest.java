package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.repositories.TournamentRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TournamentServiceTest {

    private TournamentRepository tournamentRepository = new TournamentRepository();
    private TournamentService tournamentService = new TournamentService();

    @Test
    public void createTournament() throws TournamentBadRequestException {

        tournamentService.setTournamentRepository(tournamentRepository);

        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 10);
        Date endDate = calendar.getTime();

        TournamentDto dto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENG)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        Assert.assertNotNull(tournamentService.createTournament(dto));

    }

    @Test
    public void TournamentBadRequestExceptionTest() {

        TournamentService tournamentService = new TournamentService();
        tournamentService.setTournamentRepository(tournamentRepository);

        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 10);
        Date endDate = calendar.getTime();

        TournamentDto dto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENG)
                .startDate(endDate).endDate(startDate)
                .privacy(Privacy.PUBLIC).build();

        TournamentBadRequestException thrown = assertThrows ( TournamentBadRequestException.class,
                () -> tournamentService.createTournament(dto));

        assertTrue(thrown.getMessage().contains("range date is invalid"));
    }



}

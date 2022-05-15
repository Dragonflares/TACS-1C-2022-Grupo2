package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.repositories.TournamentRepositoryInMemory;
import com.probasteReiniciando.TPTACS.repositories.UserRepositoryInMemory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TournamentServiceTest {

    @MockBean
    private UserRepositoryInMemory userRepository;
    private TournamentRepositoryInMemory tournamentRepository = new TournamentRepositoryInMemory();


    @Test
    public void createTournament() throws TournamentBadRequestException {

        TournamentService tournamentService = new TournamentService(tournamentRepository,userRepository);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto dto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        Assert.assertNotNull(tournamentService.createTournament(dto));

    }

    @Test
    public void createTournamentBadRequestException() {

        TournamentService tournamentService = new TournamentService(tournamentRepository,userRepository);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto dto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(endDate).endDate(startDate)
                .privacy(Privacy.PUBLIC).build();

        TournamentBadRequestException thrown = assertThrows ( TournamentBadRequestException.class,
                () -> tournamentService.createTournament(dto));

        assertTrue(thrown.getMessage().contains("range date is invalid"));
    }

    @Test
    public void addUserToPublicTournament() throws Exception {

        User userPepe = User.builder().username("pepe").build();

        when(userRepository.findByName("pepe")).thenReturn(Optional.of(userPepe));

        TournamentService tournamentService = new TournamentService(tournamentRepository,userRepository);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto dto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();
        dto = tournamentService.createTournament(dto);

        List<String> users = tournamentService.addUser(dto.getId(), "pepe");

        Assert.assertEquals(users, List.of(userPepe.getUsername()));

    }

}

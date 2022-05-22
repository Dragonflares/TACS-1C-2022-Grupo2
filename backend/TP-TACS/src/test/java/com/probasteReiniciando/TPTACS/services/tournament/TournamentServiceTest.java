package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.repositories.TournamentRepositoryInMemory;
import com.probasteReiniciando.TPTACS.repositories.UserRepositoryInMemory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TournamentServiceTest {

    @MockBean
    private UserRepositoryInMemory userRepository;
    private TournamentRepositoryInMemory tournamentRepository = new TournamentRepositoryInMemory();


    //TODO
    @Test
    public void createTournament() throws TournamentBadRequestException {

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto dto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        //Assert.assertNotNull(tournamentService.createTournament(dto,"pepe"));

    }

    //TODO
    @Test
    public void createTournamentBadRequestException() {

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto dto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(endDate).endDate(startDate)
                .privacy(Privacy.PUBLIC).build();

        // TournamentBadRequestException thrown = assertThrows ( TournamentBadRequestException.class,
        //       () -> tournamentService.createTournament(dto,"pepe"));

        //assertTrue(thrown.getMessage().contains("range date is invalid"));
    }


    //TODO corregir y tratar de contemplar todos los casos
    @Test
    public void addUserToPublicTournament() throws Exception {

        User userPepe = User.builder().username("pepe").build();

        when(userRepository.findByName("pepe")).thenReturn(Optional.of(userPepe));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto dto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();
        //dto = tournamentService.createTournament(dto,"duenio");

        //List<String> users = tournamentService.addUser(dto.getId(), "pepe","duenio");

        //Assert.assertEquals(users, List.of(userPepe.getUsername()));

    }

    //TODO
    @Test
    public void obtainPositions() {
    }

    //TODO
    @Test
    public void updateTournament() {
    }

    //TODO
    @Test
    public void obtainTournaments() {
    }

}

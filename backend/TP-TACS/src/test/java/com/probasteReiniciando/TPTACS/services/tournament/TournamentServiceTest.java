package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.domain.*;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepository;
import com.probasteReiniciando.TPTACS.repositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TournamentServiceTest {

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private ITournamentRepository tournamentRepository;

    @Test
    public void createTournament() throws TournamentBadRequestException {

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);
        Tournament expectedTournament = Tournament.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        when(tournamentRepository.createTournament(expectedTournament)).thenReturn(expectedTournament);

        User userLoggedIn = User.builder().username("pepe").build();

        when(userRepository.findByName(userLoggedIn.getUsername())).thenReturn(Optional.of(userLoggedIn));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        Tournament actualTournament = tournamentService.createTournament(expectedTournament, userLoggedIn.getUsername());

        Assertions.assertEquals(expectedTournament, actualTournament);

    }

    @Test
    public void createTournamentBadRequestException() {

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        Tournament tournament = Tournament.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(endDate).endDate(startDate)
                .privacy(Privacy.PUBLIC).build();

        Assertions.assertThrowsExactly(TournamentBadRequestException.class, () -> tournamentService.createTournament(tournament, "Pepe"));

    }


    @Test
    public void addUserToPublicTournament() {

        User participant = User.builder().username("pepe").build();

        List<User> expectedParticipants = List.of(participant);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        Tournament tournament = Tournament.builder()
                .id(1)
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        when(userRepository.findByName(participant.getUsername())).thenReturn(Optional.of(participant));

        when(tournamentRepository.obtainParticipants(tournament.getId(), Optional.empty(), Optional.empty())).thenReturn(expectedParticipants);

        when(tournamentRepository.obtainTournament(tournament.getId())).thenReturn(Optional.of(tournament));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        List<User> actualParticipants = tournamentService.addUser(tournament.getId(), participant.getUsername(), "pepe");

        Assertions.assertEquals(expectedParticipants, actualParticipants);

    }

    @Test
    public void addUserToPrivateTournament() {

        User participant = User.builder().username("pepe").build();

        User owner = User.builder().username("the owner").build();

        List<User> expectedParticipants = List.of(participant);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        Tournament tournament = Tournament.builder()
                .id(1)
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .owner(owner)
                .privacy(Privacy.PRIVATE).build();

        when(userRepository.findByName(participant.getUsername())).thenReturn(Optional.of(participant));

        when(tournamentRepository.obtainParticipants(tournament.getId(), Optional.empty(), Optional.empty())).thenReturn(expectedParticipants);

        when(tournamentRepository.obtainTournament(tournament.getId())).thenReturn(Optional.of(tournament));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        List<User> actualParticipants = tournamentService.addUser(tournament.getId(), participant.getUsername(), owner.getUsername());

        Assertions.assertEquals(expectedParticipants, actualParticipants);

    }

    @Test
    public void obtainPositions() {

        LocalDate startDate = LocalDate.now().minusDays(1);

        Result result1 = Result.builder().points(2).date(startDate).build();
        Result result2 = Result.builder().points(3).date(startDate.plusDays(1)).build();
        Result result3 = Result.builder().points(6).date(startDate).build();

        User user1 = User.builder().username("pepe").build();
        user1.getResults().add(result1);
        user1.getResults().add(result2);

        User user2 = User.builder().username("juan").build();
        user2.getResults().add(result3);

        Position position1Expected = Position.builder().user(user1).points(result1.getPoints() + result2.getPoints()).build();
        Position position2Expected = Position.builder().user(user2).points(result3.getPoints() + 7).build();

        List<Position> expectedPositions = List.of(position1Expected, position2Expected);

        Tournament tournament = Tournament.builder()
                .id(1)
                .startDate(startDate).endDate(startDate.plusDays(10))
                .participants(List.of(user1, user2))
                .build();

        when(tournamentRepository.obtainTournament(tournament.getId())).thenReturn(Optional.of(tournament));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        List<Position> actualPositions = tournamentService.obtainPositions(tournament.getId(), Optional.empty());

        Assertions.assertEquals(expectedPositions, actualPositions);

    }

    @Test
    public void updateTournament() {

        User owner = User.builder().username("pepe").build();

        Tournament expectedTournament = Tournament.builder()
                .id(1)
                .owner(owner)
                .build();

        when(tournamentRepository.obtainTournament(expectedTournament.getId())).thenReturn(Optional.of(expectedTournament));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        Tournament actualTournament = tournamentService.updateTournament(expectedTournament.getId(), expectedTournament, owner.getUsername());

        Assertions.assertEquals(expectedTournament,actualTournament);

    }

    @Test
    public void obtainTournaments() {

        Tournament expectedPublicTournament1 = Tournament.builder().name("Expected Public Tournament 1").privacy(Privacy.PUBLIC).build();
        Tournament expectedPublicTournament2 = Tournament.builder().name("Expected Public Tournament 2").privacy(Privacy.PUBLIC).build();
        Tournament expectedPrivateTournament1 = Tournament.builder().name("Expected Private Tournament 1").privacy(Privacy.PRIVATE).build();
        Tournament expectedPrivateTournament2 = Tournament.builder().name("Expected Private Tournament 2").privacy(Privacy.PRIVATE).build();

        List<Tournament> expectedPublicTournaments = List.of(expectedPublicTournament1,expectedPublicTournament2);
        List<Tournament> expectedPrivateTournaments = List.of(expectedPrivateTournament1,expectedPrivateTournament2);

        when(tournamentRepository.obtainPublicTournaments(1,1)).thenReturn(expectedPublicTournaments);
        when(tournamentRepository.obtainPrivateTournaments(1,1,"pepe")).thenReturn(expectedPrivateTournaments);

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        List<Tournament> actualPublicTournaments = tournamentService.obtainTournaments(1,1,Privacy.PUBLIC,"pepe");
        List<Tournament> actualPrivateTournaments = tournamentService.obtainTournaments(1,1,Privacy.PRIVATE,"pepe");

        Assertions.assertEquals(expectedPublicTournaments,actualPublicTournaments);
        Assertions.assertEquals(expectedPrivateTournaments,actualPrivateTournaments);

    }

}

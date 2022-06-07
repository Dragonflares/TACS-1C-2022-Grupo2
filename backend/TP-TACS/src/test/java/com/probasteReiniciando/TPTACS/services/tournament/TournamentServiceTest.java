package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dao.TournamentDAO;
import com.probasteReiniciando.TPTACS.dao.UserDAO;
import com.probasteReiniciando.TPTACS.domain.*;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepository;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepositoryMongoDB;
import com.probasteReiniciando.TPTACS.repositories.IUserRepositoryMongoDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TournamentServiceTest {

    @MockBean
    private IUserRepositoryMongoDB userRepository;

    @MockBean
    private ITournamentRepositoryMongoDB tournamentRepository;

    final private ModelMapperTacs modelMapper = new ModelMapperTacs();

    @Test
    public void createTournament() throws TournamentBadRequestException {

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);
        Tournament expectedTournament = Tournament.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        TournamentDAO expectedTournamentDAO = modelMapper.map(expectedTournament, TournamentDAO.class);

        when(tournamentRepository.save(expectedTournamentDAO)).thenReturn(expectedTournamentDAO);

        UserDAO userLoggedIn = UserDAO.builder().username("pepe").build();

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
        UserDAO participantDAO = modelMapper.map(participant,UserDAO.class);

        List<User> expectedParticipants = List.of(participant);
        List<UserDAO> expectedParticipantsDAO = modelMapper.mapList(expectedParticipants,UserDAO.class);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDAO tournament = TournamentDAO.builder()
                .id("1")
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        when(userRepository.findByName(participant.getUsername())).thenReturn(Optional.of(participantDAO));

        when(tournamentRepository.obtainParticipants(tournament.getId())).thenReturn(expectedParticipantsDAO);

        when(tournamentRepository.obtainTournament(tournament.getId())).thenReturn(Optional.of(tournament));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        List<User> actualParticipants = tournamentService.addUser(tournament.getId(), participant.getUsername(), "pepe");

        Assertions.assertEquals(expectedParticipants, actualParticipants);

    }

    @Test
    public void addUserToPrivateTournament() {

        User participant = User.builder().username("pepe").build();
        UserDAO participantDAO = modelMapper.map(participant,UserDAO.class);

        UserDAO owner = UserDAO.builder().username("the owner").build();

        List<User> expectedParticipants = List.of(participant);
        List<UserDAO> expectedParticipantsDAO = modelMapper.mapList(expectedParticipants,UserDAO.class);

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDAO tournament = TournamentDAO.builder()
                .id("1")
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .owner(owner)
                .privacy(Privacy.PRIVATE).build();

        when(userRepository.findByName(participant.getUsername())).thenReturn(Optional.of(participantDAO));

        when(tournamentRepository.obtainParticipants(tournament.getId())).thenReturn(expectedParticipantsDAO);

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
                .id("1")
                .startDate(startDate).endDate(startDate.plusDays(10))
                .participants(List.of(user1, user2))
                .build();

        TournamentDAO tournamentDAO = modelMapper.map(tournament,TournamentDAO.class);

        when(tournamentRepository.obtainTournament(tournament.getId())).thenReturn(Optional.of(tournamentDAO));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        List<Position> actualPositions = tournamentService.obtainPositions(tournament.getId(), Optional.empty());

        Assertions.assertEquals(expectedPositions, actualPositions);

    }

    @Test
    public void updateTournament() {

        User owner = User.builder().username("pepe").build();

        Tournament expectedTournament = Tournament.builder()
                .id("1")
                .owner(owner)
                .build();

        TournamentDAO expectedTournamentDAO = modelMapper.map(expectedTournament,TournamentDAO.class);

        when(tournamentRepository.obtainTournament(expectedTournament.getId())).thenReturn(Optional.of(expectedTournamentDAO));

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

        List<TournamentDAO> expectedPublicTournamentsDAO = modelMapper.mapList(expectedPublicTournaments,TournamentDAO.class);
        List<TournamentDAO> expectedPrivateTournamentsDAO = modelMapper.mapList(expectedPrivateTournaments,TournamentDAO.class);

        when(tournamentRepository.obtainPublicTournaments("pepe", PageRequest.of(1, 1))).thenReturn(new PageImpl<>(expectedPublicTournamentsDAO));
        when(tournamentRepository.obtainPrivateTournaments("pepe", PageRequest.of(1, 1))).thenReturn(new PageImpl<>(expectedPrivateTournamentsDAO));

        TournamentService tournamentService = new TournamentService(tournamentRepository, userRepository);

        List<TournamentDAO> actualPublicTournaments = tournamentService.obtainTournaments(1,1,Privacy.PUBLIC,"pepe").stream().toList();
        List<TournamentDAO> actualPrivateTournaments = tournamentService.obtainTournaments(1,1,Privacy.PRIVATE,"pepe").stream().toList();

        Assertions.assertEquals(expectedPublicTournaments,modelMapper.mapList(actualPublicTournaments,Tournament.class));
        Assertions.assertEquals(expectedPrivateTournaments,modelMapper.mapList(actualPrivateTournaments,Tournament.class));

    }

}

package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dao.TournamentDAO;
import com.probasteReiniciando.TPTACS.dao.UserDAO;
import com.probasteReiniciando.TPTACS.domain.*;
import com.probasteReiniciando.TPTACS.exceptions.TournamentNotFoundException;
import com.probasteReiniciando.TPTACS.exceptions.UnAuthorizedException;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;
import com.probasteReiniciando.TPTACS.exceptions.UserNotFoundException;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepositoryMongoDB;
import com.probasteReiniciando.TPTACS.repositories.IUserRepositoryMongoDB;
import com.probasteReiniciando.TPTACS.validators.TournamentValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    final private ITournamentRepositoryMongoDB tournamentRepository;

    final private IUserRepositoryMongoDB userRepository;

    final private ModelMapperTacs modelMapper = new ModelMapperTacs();

    public TournamentService(ITournamentRepositoryMongoDB tournamentRepository, IUserRepositoryMongoDB userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    public Tournament createTournament(Tournament tournament, String userLoggedIn) {

        TournamentValidator.validateRangeDate(tournament.getStartDate(), tournament.getEndDate());

        UserDAO ownerDAO = userRepository.findByName(userLoggedIn).orElseThrow(() -> new UserNotFoundException(userLoggedIn));

        User owner = modelMapper.map(ownerDAO,User.class);

        tournament.setOwner(owner);

        List<User> participants = new ArrayList<>();

        participants.add(owner);

        tournament.setParticipants(participants);

        TournamentDAO tournamentDAO = modelMapper.map(tournament, TournamentDAO.class);

        return modelMapper.map(tournamentRepository.save(tournamentDAO),Tournament.class);

    }

    public Page<TournamentDAO> obtainTournaments(int page, int limit, Privacy privacy, String username) {

        return switch (privacy) {

            case PUBLIC -> tournamentRepository.obtainPublicTournaments(username, PageRequest.of(page - 1, limit));

            case PRIVATE -> tournamentRepository.obtainPrivateTournaments(username, PageRequest.of(page - 1, limit));

        };

    }

    public Tournament getTournamentById(String id) {
        return modelMapper.map(tournamentRepository.obtainTournament(id).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(id))),Tournament.class);
    }

    public List<User> addUser(String tournamentId, String userName, String userLoggedIn) {

        Tournament tournament = modelMapper.map(tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId))),Tournament.class);

        TournamentValidator.validateStartDate(tournament.getStartDate());

        UserDAO userDAO = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException(userName));

        User user = modelMapper.map(userDAO,User.class);

        if(tournament.getParticipants().stream().anyMatch(user1 -> user1.getUsername().equals(user.getUsername()))) {
            throw new UserAlreadyExistsException(user.getUsername() + " is already in tournament");
        }

        if (Privacy.PUBLIC.equals(tournament.getPrivacy())) {

            tournament.getParticipants().add(user);
            tournamentRepository.save(modelMapper.map(tournament,TournamentDAO.class));

            return modelMapper.mapList(tournamentRepository.findById(tournamentId).get().getParticipants(),User.class);

        } else {

            if (tournament.getOwner().getUsername().equals(userLoggedIn)) {

                tournamentRepository.addUser(tournament.getId(), user);

                return modelMapper.mapList(tournamentRepository.findById(tournamentId).get().getParticipants(), User.class);

            } else {

                throw new UnAuthorizedException(userLoggedIn);

            }

        }

    }

    public List<Result> getResults(String id) {
        return tournamentRepository.obtainResults(id);
    } //TODO revisar


    public List<User> obtainParticipants(String tournamentId, Optional<String> orderBy, Optional<String> order) {
        return modelMapper.mapList(tournamentRepository.findById(tournamentId).get().getParticipants(), User.class);


    }

    public Tournament updateTournament(String tournamentId, Tournament updatedTournament, String userLoggedIn) {

        Tournament tournament = modelMapper.map(tournamentRepository.obtainTournament(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId))),Tournament.class);

        if (!tournament.getOwner().getUsername().equals(userLoggedIn)) {
            throw new UnAuthorizedException(userLoggedIn);
        }

        tournamentRepository.save(modelMapper.map(updatedTournament,TournamentDAO.class));

        return modelMapper.map(tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId))),Tournament.class);

    }

    public List<Tournament> obtainTorunamentsByPlayer(String userLoggedIn, int page, int limit) {

        return modelMapper.mapList(tournamentRepository.findByOwner(userLoggedIn, page, limit),Tournament.class);

    }

    public QuantityTournament getQuantityOfTournaments(Privacy privacy, String userLoggedIn) {

        Integer quantity = switch (privacy) {

            case PUBLIC -> tournamentRepository.quantityOfPublicTournaments();

            case PRIVATE -> tournamentRepository.quantityOfPrivateTournaments(userLoggedIn);

        };

        return QuantityTournament.builder().quantity(quantity).build();

    }

    public List<Position> obtainPositions(String tournamentId, Optional<String> order) {

        List<Position> positions = new ArrayList<>();

        Tournament tournament = modelMapper.map(tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId))),Tournament.class);

        LocalDate tournamentStartDate = tournament.getStartDate();

        LocalDate tournamentEndDate =tournament.getEndDate();

        LocalDate currentDate = LocalDate.now();

        List<LocalDate> tournamentDates;

        if(currentDate.isBefore(tournamentStartDate))
            tournamentDates = new ArrayList<>();
        else {
            if (currentDate.isBefore(tournamentEndDate))
                tournamentDates = tournamentStartDate.datesUntil(currentDate.plusDays(1)).toList();
            else
                tournamentDates = tournamentStartDate.datesUntil(tournamentEndDate.plusDays(1)).toList();
        }

        for (User participant : tournament.getParticipants()) {

            int points = 0;

            for(LocalDate localDateIndex : tournamentDates) {

                Optional<Result> result = participant.getResults().stream().filter(r -> r.getDate().equals(localDateIndex)).findFirst();

                if (result.isPresent())
                    points += result.get().getPoints();
                else
                    points += 7;

            }

            positions.add(Position.builder().points(points).user(participant).build());

        }

        positions = positions.stream().sorted(PositionComparator.getInstance()).collect(Collectors.toList());

        if(order.isPresent() && "desc".equals(order.get())){
            Collections.reverse(positions);
        }

        return positions.subList(0,Math.min(positions.size(),10));

    }

    public TournamentsMetadata obtainTournamentMetadata(String userLoggedIn) {

        return TournamentsMetadata.builder()
                .publicTournamentsQuantity(tournamentRepository.quantityOfPublicTournaments())
                .privateTournamentsQuantity(tournamentRepository.quantityOfPrivateTournaments(userLoggedIn))
                .privacys(Arrays.stream(Privacy.values()).map(p -> p.toString()).toList())
                .build();

    }

}

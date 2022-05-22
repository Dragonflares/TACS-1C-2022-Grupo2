package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.*;
import com.probasteReiniciando.TPTACS.exceptions.TournamentNotFoundException;
import com.probasteReiniciando.TPTACS.exceptions.UnAuthorizedException;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;
import com.probasteReiniciando.TPTACS.exceptions.UserNotFoundException;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepository;
import com.probasteReiniciando.TPTACS.repositories.IUserRepository;
import com.probasteReiniciando.TPTACS.validators.TournamentValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    final private ITournamentRepository tournamentRepository;

    final private IUserRepository userRepository;

    final private ModelMapperTacs modelMapper = new ModelMapperTacs();

    public TournamentService(ITournamentRepository tournamentRepository, IUserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    public Tournament createTournament(Tournament tournament, String userLoggedIn) {

        TournamentValidator.validateRangeDate(tournament.getStartDate(), tournament.getEndDate());

        User owner = userRepository.findByName(userLoggedIn).orElseThrow(() -> new UserNotFoundException(userLoggedIn));

        tournament.setOwner(owner);

        tournament.setParticipants(new ArrayList<>());

        return tournamentRepository.createTournament(tournament);

    }

    public List<Tournament> obtainTournaments(int page, int limit, Privacy privacy, String username) {

        return switch (privacy) {

            case PUBLIC -> tournamentRepository.obtainPublicTournaments(page, limit);

            case PRIVATE -> tournamentRepository.obtainPrivateTournaments(page, limit, username);

        };

    }

    public Tournament getTournamentById(int id) {
        return tournamentRepository.obtainTournament(id).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(id)));
    }

    public List<User> addUser(int tournamentId, String userName, String userLoggedIn) {

        Tournament tournament = tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId)));

        TournamentValidator.validateStartDate(tournament.getStartDate());

        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException(userName));

        if(tournament.getParticipants().stream().anyMatch(user1 -> user1.getUsername().equals(user.getUsername()))) {
            throw new UserAlreadyExistsException(user.getUsername() + " is already in tournament");
        }

        if (Privacy.PUBLIC.equals(tournament.getPrivacy())) {

            tournamentRepository.addUser(tournament, user);

            return tournamentRepository.obtainParticipants(tournamentId,Optional.empty(),Optional.empty());

        } else {

            if (tournament.getOwner().getUsername().equals(userLoggedIn)) {

                tournamentRepository.addUser(tournament, user);

                return tournamentRepository.obtainParticipants(tournamentId,Optional.empty(),Optional.empty());

            } else {

                throw new UnAuthorizedException(userLoggedIn);

            }

        }

    }

    public List<Result> getResults(int id) {
        return tournamentRepository.obtainResults(id);
    } //TODO revisar


    public List<User> obtainParticipants(int tournamentId, Optional<String> orderBy, Optional<String> order) {
        return tournamentRepository.obtainParticipants(tournamentId, orderBy, order);


    }

    public Tournament updateTournament(int tournamentId, Tournament updatedTournament, String userLoggedIn) {

        Tournament tournament = tournamentRepository.obtainTournament(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId)));

        if (!tournament.getOwner().getUsername().equals(userLoggedIn)) {
            throw new UnAuthorizedException(userLoggedIn);
        }

        tournamentRepository.updateTournament(tournamentId, updatedTournament);

        return tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId)));

    }

    public List<Tournament> obtainTorunamentsByPlayer(String userLoggedIn, int page, int limit) {

        return tournamentRepository.findByOwner(userLoggedIn, page, limit);

    }

    public QuantityTournament getQuantityOfTournaments(Privacy privacy, String userLoggedIn) {

        Integer quantity = switch (privacy) {

            case PUBLIC -> tournamentRepository.quantityOfPublicTournaments();

            case PRIVATE -> tournamentRepository.quantityOfPrivateTournaments(userLoggedIn);

        };

        return QuantityTournament.builder().quantity(quantity).build();

    }

    public List<Position> obtainPositions(int tournamentId, Optional<String> order) {

        List<Position> positions = new ArrayList<>();

        Tournament tournament = tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId)));

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

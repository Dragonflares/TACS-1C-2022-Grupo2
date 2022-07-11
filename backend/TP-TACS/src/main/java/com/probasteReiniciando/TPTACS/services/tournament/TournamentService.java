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

    public List<Position> obtainAllParticipantsInPublicTournaments(Optional<String> order){

        int page = 1;

        Boolean control = true;
        List<Position> tournamentPositions = new ArrayList<>();

        while(control) {
            Page<TournamentDAO> tournaments = tournamentRepository.obtainAllPublicTournaments(PageRequest.of(page - 1, 5));
            page++;
            control = tournaments.hasNext();
            for(TournamentDAO tournament : tournaments) {
                List<Position> positions = obtainPositions(tournament.getId(),order);
                for(Position position : positions){
                    Optional<Position> positionFound = tournamentPositions.stream().filter(x -> x.getUser().getUsername().equals(position.getUser().getUsername())).findFirst();
                    if(positionFound.isEmpty()){
                        tournamentPositions.add(position);
                    } else {
                        positionFound.get().setPoints(positionFound.get().getPoints().intValue() + position.getPoints().intValue());
                    }
                }
            }
        }

        tournamentPositions = tournamentPositions.stream().sorted(PositionComparator.getInstance()).collect(Collectors.toList());

        if(order.isPresent() && "desc".equals(order.get())){
            Collections.reverse(tournamentPositions);
        }

        return tournamentPositions.stream().limit(10).collect(Collectors.toList());
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

                tournament.getParticipants().add(user);
                tournamentRepository.save(modelMapper.map(tournament,TournamentDAO.class));

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

        tournament = mergedTournament(tournament,updatedTournament);
        tournamentRepository.save(modelMapper.map(tournament,TournamentDAO.class));

        return modelMapper.map(tournamentRepository.obtainTournament(tournamentId).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(tournamentId))),Tournament.class);

    }

    private Tournament mergedTournament(Tournament tournament, Tournament updatedTournament) {

        if(updatedTournament.getName() != null)
            tournament.setName(updatedTournament.getName());

        if(updatedTournament.getPrivacy() != null)
            tournament.setPrivacy(updatedTournament.getPrivacy());

        if(updatedTournament.getStartDate() != null)
            tournament.setStartDate(updatedTournament.getStartDate());

        if(updatedTournament.getLanguage() != null)
            tournament.setLanguage(updatedTournament.getLanguage());

        if(updatedTournament.getEndDate() != null)
            tournament.setEndDate(updatedTournament.getEndDate());

        if(updatedTournament.getParticipants() != null && updatedTournament.getParticipants().size() > 0 )
            tournament.getParticipants().addAll(updatedTournament.getParticipants());

        return tournament;
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

        List<String> idsParticipants = tournament.getParticipants().stream().map(User::getId).collect(Collectors.toList());

        List<User> participantsDb = modelMapper.mapList(userRepository.findByIds(idsParticipants),User.class);

        //TODO REFACTOR THIS SO MODEL MAPPER REALLY MAP ALSO THE RESULTS
        for (User participant : participantsDb) {

            int points = 0;

            for(LocalDate localDateIndex : tournamentDates) {

                Optional<Result> result = participant.getResults().stream()
                        .filter(r -> r.getDate().equals(localDateIndex) && r.getLanguage().equals(tournament.getLanguage()))
                        .findFirst();

                if (result.isPresent())
                    points += result.get().getPoints();
                else
                    points += 7;

            }

            positions.add(Position.builder().points(points).user(modelMapper.map(participant,User.class)).build());

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

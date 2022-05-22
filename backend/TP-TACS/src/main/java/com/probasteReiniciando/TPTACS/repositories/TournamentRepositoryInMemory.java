package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.*;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TournamentRepositoryInMemory implements ITournamentRepository {

    private List<Tournament> tournaments = new ArrayList<>();

    private Integer currentId = 0;


    @Override
    public Integer quantityOfPublicTournaments() {
        return tournaments.stream().filter(x -> x.getPrivacy().equals(Privacy.PUBLIC))
                .toList().size();
    }

    @Override
    public Integer quantityOfPrivateTournaments(String username) {
        return tournaments.stream().filter(x -> x.getOwner().getUsername().equals(username) || x.getParticipants().stream().anyMatch( p -> p.getUsername().equals(username)))
                .toList().size();
    }

    @Override
    public List<Tournament> obtainPublicTournaments(int page, int limit) {
        return tournaments.stream().filter(x -> x.getPrivacy().equals(Privacy.PUBLIC))
                .skip((long) (page - 1) * limit).limit(limit).toList();
    }

    @Override
    public List<Tournament> obtainPrivateTournaments(int page, int limit, String username) {
        return tournaments.stream().filter(x -> x.getOwner().getUsername().equals(username) || x.getParticipants().stream().anyMatch( p -> p.getUsername().equals(username)))
                .skip((long) (page - 1) * limit).limit(limit).toList();
    }

    @Override
    public Optional<Tournament> obtainTournament(int id) {
        return tournaments.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public List<Result> obtainResults(int id) {
        return obtainTournament(id).get().getResults();
    }

    @Override
    public Tournament createTournament(Tournament tournament) {

        incrementId();

        tournaments.add(tournament);
        tournament.setId(currentId);

        return tournament;

    }

    public void addUser(Tournament tournament, User user) {

        if(tournament.getParticipants() == null) {
            tournament.setParticipants(new ArrayList<>());
        }

        tournament.getParticipants().add(user);

    }

    @Override
    public List<User> obtainParticipants(int tournamentId, Optional<String> orderBy, Optional<String> order) {

        List<User> participants = new ArrayList<>();

        Optional<Tournament> tournament = obtainTournament(tournamentId);

        if (tournament.isPresent()) {

            //TODO en mongo utilizar strategy o algun patron
            //TODO falta contemplar los dias que un usuario no jugo
            if("result".equals(orderBy.orElseGet(() -> "none"))) {

                UserResultComparator userResultComparator = new UserResultComparator();

                participants = tournament.get().getParticipants().stream().sorted(userResultComparator).collect(Collectors.toList());

                if ("desc".equals(order.orElseGet(() -> "none"))) {

                    Collections.reverse(participants);

                }

            } else {

                participants = tournament.get().getParticipants().stream().collect(Collectors.toList());

            }

        }

        return participants;
    }

    public void updateTournament(int tournamentId, Tournament tournament) {

        Tournament tournament2 = tournaments.stream().filter(x -> x.getId().equals(tournamentId)).findFirst().get();

        if (tournament.getName() != null) {
            tournament2.setName(tournament.getName());
        }
        if (tournament.getStartDate() != null) {
            tournament2.setStartDate(tournament.getStartDate());
        }
        if (tournament.getEndDate() != null) {
            tournament2.setEndDate(tournament.getEndDate());
        }
        if (tournament.getLanguage() != null) {
            tournament2.setLanguage(tournament.getLanguage());
        }

    }

    @Override
    public List<Tournament> findByOwner(String owner, int page, int limit) {

       return this.tournaments.stream().filter(x -> x.getOwner().getUsername().equals(owner))
               .skip((long) (page - 1) * limit).limit(limit).toList();
    }

    private void incrementId() {
        currentId++;
    }

}
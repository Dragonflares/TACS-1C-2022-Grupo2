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
    public List<Tournament> obtainPublicTournaments(int offset, int limit) {
        return tournaments.stream().filter(x -> x.getPrivacy().equals(Privacy.PUBLIC) && x.getId() >= offset && x.getId() < offset + limit).collect(Collectors.toList());
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

    public List<String> addUser(Tournament tournament, User user) {

        if(tournament.getParticipants() == null) {
            tournament.setParticipants(new ArrayList<>());
        }

        tournament.getParticipants().add(user);
        return tournament.getParticipants().stream().map(userStream -> userStream.getUsername()).collect(Collectors.toList());


    }

    @Override
    public List<String> obtainParticipants(int tournamentId, Optional<String> orderBy, Optional<String> order) {

        List<String> participants = new ArrayList<>();

        Optional<Tournament> tournament = obtainTournament(tournamentId);

        if (tournament.isPresent()) {

            //TODO en mongo utilizar strategy o algun patron
            if("result".equals(orderBy.get())) {

                UserResultComparator userResultComparator = new UserResultComparator();

                participants = tournament.get().getParticipants().stream().sorted(userResultComparator).map(userStream -> userStream.getUsername()).collect(Collectors.toList());

                if ("desc".equals(order.get())) {

                    Collections.reverse(participants);

                }

            } else {

                participants = tournament.get().getParticipants().stream().map(userStream -> userStream.getUsername()).collect(Collectors.toList());

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

    private void incrementId() {
        currentId++;
    }


}

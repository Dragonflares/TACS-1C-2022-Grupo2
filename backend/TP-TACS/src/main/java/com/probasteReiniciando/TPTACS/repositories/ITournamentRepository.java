package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Tournament;

import java.util.List;

public interface ITournamentRepository {
    List<Tournament> getPublicTournaments();
}

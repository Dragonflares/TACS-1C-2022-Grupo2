package com.probasteReiniciando.TPTACS.services.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface WordFinder {

    Optional<String> findWord (String param, String language) throws JsonProcessingException;

}

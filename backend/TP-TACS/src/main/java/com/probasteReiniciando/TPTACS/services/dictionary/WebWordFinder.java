package com.probasteReiniciando.TPTACS.services.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WebWordFinder implements WordFinder {

    private String urlApi = "https://lexicala1.p.rapidapi.com/search?text={text}&language={language}";

    @Override
    public Optional<String> findWord(String word, String language) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Host", "lexicala1.p.rapidapi.com");
        headers.add("X-RapidAPI-Key", "1c4317a744mshd65440aeaef3d67p1f8fb0jsn36ff2d050c0e");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        Map<String, String> vars = new HashMap<>();
        vars.put("text", word);
        vars.put("language", language);


        ResponseEntity<String> response = restTemplate.exchange(
                urlApi, HttpMethod.GET, requestEntity, String.class, vars);
        System.out.println(response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        return Optional.ofNullable(jsonNode.get("results").get(0).get("senses").get(0).get("definition").asText());
    }
}

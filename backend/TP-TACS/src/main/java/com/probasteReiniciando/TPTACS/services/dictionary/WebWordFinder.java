package com.probasteReiniciando.TPTACS.services.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("webWordFinder")
public class WebWordFinder implements WordFinder {
    @Value("${app.urlApi}")
    private String urlApi;

    @Value("${app.host}")
    private String host;

    @Value("${app.key}")
    private String key;

    @Override
    public Optional<String> findWord(String word, String language) throws JsonProcessingException {

        WebClient client = WebClient.create();
        ResponseEntity<String> response = client.get()
                .uri(urlApi, uri -> uri
                        .queryParam("text", word)
                        .queryParam("language", language)
                        .build())
                .header("X-RapidAPI-Host", host)
                .header("X-RapidAPI-Key", key)
                .retrieve() /* .exchange() is deprecated! */
                .toEntity(String.class)
                .block();

        System.out.println(response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode.get("n_results").asInt()> 0 ?
                Optional.ofNullable(jsonNode.get("results").get(0).get("senses").get(0).get("definition").asText()) :
                Optional.empty();

    }
}

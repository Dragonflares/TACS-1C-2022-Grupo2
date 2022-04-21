package com.probasteReiniciando.TPTACS.services.dictionary;

import com.probasteReiniciando.TPTACS.domain.Language;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WebWordFinder implements WordFinder {

    private String urlApi = "https://lexicala1.p.rapidapi.com/search";

    @Override
    public String findWord(String param) {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlApi, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Host", "lexicala1.p.rapidapi.com");
        headers.add("X-RapidAPI-Key", "1c4317a744mshd65440aeaef3d67p1f8fb0jsn36ff2d050c0e");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                urlApi, HttpMethod.GET, requestEntity, String.class, param);
        System.out.println(response.getBody());
        return null;
    }
}

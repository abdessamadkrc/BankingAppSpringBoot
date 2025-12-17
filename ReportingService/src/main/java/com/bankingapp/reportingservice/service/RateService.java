package com.bankingapp.reportingservice.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class RateService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${myapp.api.key}")
    private  String accessKey;
    private final String apiSource = "GBP"; // source fixe pour l'API

    public Double getRate(String from, String to) {
        // On récupère les taux par rapport à la source fixe (GBP)
        String url = "https://api.exchangerate.host/live?access_key=" + accessKey
                + "&source=" + apiSource
                + "&currencies=" + from + "," + to;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !(Boolean) response.get("success")) {
            return 1.0; // fallback en cas d'erreur
        }

        Map<String, Object> quotes = (Map<String, Object>) response.get("quotes");

        // clé API: source + devise
        double sourceToFrom = ((Number) quotes.get(apiSource + from)).doubleValue();
        double sourceToTo = ((Number) quotes.get(apiSource + to)).doubleValue();

        // formule: from → to
        return sourceToTo / sourceToFrom;
    }
}
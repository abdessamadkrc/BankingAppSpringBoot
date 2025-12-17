package com.bankingapp.reportingservice.controller;


import com.bankingapp.reportingservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RateController {

    @Autowired
    private RateService rateService;

    // Exemple: /api/rate?from=USD&to=EUR
    @GetMapping("/rate")
    public Map<String, Object> getRate(@RequestParam String from, @RequestParam String to) {
        Double rate = rateService.getRate(from.toUpperCase(), to.toUpperCase());

        Map<String, Object> result = new HashMap<>();
        result.put("from", from.toUpperCase());
        result.put("to", to.toUpperCase());
        result.put("rate", rate);

        return result;
    }
}


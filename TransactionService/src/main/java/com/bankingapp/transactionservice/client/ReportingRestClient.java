package com.bankingapp.transactionservice.client;

import com.bankingapp.transactionservice.model.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "REPORTING-SERVICE")
public interface ReportingRestClient {
    
    @GetMapping("/api/rate")
    ExchangeRate getExchangeRate(@RequestParam("from") String from, @RequestParam("to") String to);
}

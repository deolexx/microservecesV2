package com.deo.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
    CurrencyExchangeRepo currencyExchangeRepo;
    Environment environment;

    @Autowired
    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepo currencyExchangeRepo) {
        this.environment = environment;
        this.currencyExchangeRepo = currencyExchangeRepo;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

        CurrencyExchange currencyExchange = currencyExchangeRepo.findByFromAndTo(from, to);
        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + from + " and " + to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);


        return currencyExchange;

    }
}

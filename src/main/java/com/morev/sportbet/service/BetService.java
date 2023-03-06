package com.morev.sportbet.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class BetService {
    private final Map<String, Integer> bets = new HashMap<>();

    {
        bets.put("ferrari", 0);
        bets.put("bmw", 0);
        bets.put("audi", 0);
        bets.put("mercedes", 0);
    }

    public String placeBet(String car, int amount) {
        if (!isValidCar(car)) {
            return "Invalid car: " + car;
        }
        if (!isValidAmount(amount)) {
            return "Invalid amount: " + amount;
        }
        bets.merge(car, amount, Integer::sum);
        return "Bet placed successfully";
    }

    public Map<String, Integer> getBets(String car) {
        Map<String, Integer> result = new HashMap<>(bets);
        if (!isValidCar(car)) {
            return result;
        } else {
            return new HashMap<>() {{
                put(car, bets.get(car));
            }};
        }
    }

    private boolean isValidCar(String car) {
        Set<String> validCars = Set.of("ferrari", "bmw", "audi", "mercedes");
        return validCars.contains(car);
    }

    private boolean isValidAmount(int amount) {
        return amount >= 0;
    }
}

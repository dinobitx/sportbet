package com.morev.betapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class BetController {
    private final Map<String, Integer> bets = new HashMap<>();

    @PostMapping("/bet")
    public ResponseEntity<String> placeBet(@RequestParam String car, @RequestParam int amount) {
        if (!isValidCar(car)) {
            return ResponseEntity.badRequest().body("Invalid car: " + car);
        }
        if (!isValidAmount(amount)) {
            return ResponseEntity.badRequest().body("Invalid amount: " + amount);
        }
        bets.merge(car, amount, Integer::sum);
        return ResponseEntity.ok("Bet placed successfully");
    }

    @GetMapping("/bets")
    public ResponseEntity<Map<String, Integer>> getBets(@RequestParam(required = false) String car) {
        Map<String, Integer> result = new HashMap<>();
        if (car == null) {
            result.putAll(bets);
        } else {
            if (!isValidCar(car)) {
                return ResponseEntity.badRequest().body(new HashMap<String, Integer>() {{
                    put("error", -1);
                }});
            }
            result.put(car, bets.getOrDefault(car, 0));
        }
        return ResponseEntity.ok(result);
    }


    private boolean isValidCar(String car) {
        Set<String> validCars = Set.of("ferrari", "bmw", "audi", "mercedes");
        return validCars.contains(car);
    }

    public boolean isValidAmount(int amount) {
        return amount >= 0;
    }
}

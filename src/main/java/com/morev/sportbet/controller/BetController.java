package com.morev.sportbet.controller;

import com.morev.sportbet.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/bets")
@RequiredArgsConstructor
public class BetController {
    private final BetService service;

    @PostMapping
    public ResponseEntity<String> placeBet(@RequestParam final String car,
                                           @RequestParam final int amount) {
        final String result = service.placeBet(car, amount);
        return result.contains("Invalid") ? ResponseEntity.badRequest().body(result)
                : ResponseEntity.ok(result);
    }


    @GetMapping("/{car}")
    public ResponseEntity<Map<String, Integer>> getBets(@PathVariable(required = false) final String car) {
        final Map<String, Integer> result = service.getBets(car);
        return ResponseEntity.ok(result);
    }
}

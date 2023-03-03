package com.morev.betapp.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Bet {
    private final Long id;
    private final String car;
    private final int amount;
}

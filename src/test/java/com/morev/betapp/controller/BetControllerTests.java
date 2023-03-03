package com.morev.betapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.hamcrest.Matchers.is;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WebMvcTest(BetController.class)
public class BetControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    void givenInvalidCar_whenPlacingBet_shouldReturnBadRequest() throws Exception {
        String invalidCar = "invalid";
        int amount = 100;
        mockMvc.perform(post("/bet")
                .param("car", invalidCar)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid car: " + invalidCar));
    }

    @Test
    public void givenValidCar_whenPlacingBet_shouldUpdateBets() throws Exception {
        String validCar = "ferrari";
        int amount = 100;
        mockMvc.perform(post("/bet")
                .param("car", validCar)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(content().string("Bet placed successfully"));

        mockMvc.perform(get("/bets")
                .param("car", validCar))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ferrari", is(amount)));
    }

    @Test
    public void givenNoCar_whenGettingBets_shouldReturnAllBets() throws Exception {
        String car1 = "ferrari";
        String car2 = "bmw";
        int amount1 = 100;
        int amount2 = 200;
        mockMvc.perform(post("/bet")
                .param("car", car1)
                .param("amount", String.valueOf(amount1)))
                .andExpect(status().isOk())
                .andExpect(content().string("Bet placed successfully"));
        mockMvc.perform(post("/bet")
                .param("car", car2)
                .param("amount", String.valueOf(amount2)))
                .andExpect(status().isOk())
                .andExpect(content().string("Bet placed successfully"));

        mockMvc.perform(get("/bets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ferrari", is(amount1)))
                .andExpect(jsonPath("$.bmw", is(amount2)))
                .andExpect(jsonPath("$.audi", is(0)))
                .andExpect(jsonPath("$.mercedes", is(0)));
    }
}

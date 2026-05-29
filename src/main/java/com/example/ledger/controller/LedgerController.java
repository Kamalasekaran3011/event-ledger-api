package com.example.ledger.controller;

import com.example.ledger.dto.EventRequest;
import com.example.ledger.entity.LedgerEvent;
import com.example.ledger.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class LedgerController {

    private final LedgerService service;

    public LedgerController(LedgerService service) {
        this.service = service;
    }

    @PostMapping("/events")
    public ResponseEntity<LedgerEvent> submit(
            @Valid @RequestBody EventRequest request) {

        LedgerEvent event = service.submit(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @GetMapping("/events/{eventId}")
    public LedgerEvent getById(@PathVariable String eventId) {
        return service.getEvents(eventId).stream().findFirst().orElseThrow();
    }

    @GetMapping("/events")
    public List<LedgerEvent> getEvents(@RequestParam String account) {
        return service.getEvents(account);
    }

    @GetMapping("/accounts/{accountId}")
    public List<LedgerEvent> getAccountEvents(
            @PathVariable String accountId) {

        return service.getEvents(accountId);
    }

    @GetMapping("/account/{accountId}/balance")
    public Map<String, BigDecimal> balance(
            @PathVariable String accountId) {

        return Map.of(
                "balance",
                service.computeBalance(accountId)
        );
    }
}
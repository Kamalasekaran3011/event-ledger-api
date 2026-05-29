
package com.example.eventledger;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/events")
    public ResponseEntity<Event> create(@Valid @RequestBody EventRequest request) {

        boolean exists = false;

        try {
            service.getByEventId(request.eventId);
            exists = true;
        } catch (Exception ignored) {}

        Event event = service.create(request);

        return new ResponseEntity<>(event,
                exists ? HttpStatus.OK : HttpStatus.CREATED);
    }

    @GetMapping("/events/{id}")
    public Event get(@PathVariable String id) {
        return service.getByEventId(id);
    }

    @GetMapping("/events")
    public List<Event> getByAccount(@RequestParam String accountId) {
        return service.getByAccount(accountId);
    }

    @GetMapping("/accounts/{accountId}")
    public List<Event> accountEvents(@PathVariable String accountId) {
        return service.getByAccount(accountId);
    }

    @GetMapping("/accounts/{accountId}/balance")
    public BigDecimal balance(@PathVariable String accountId) {
        return service.getBalance(accountId);
    }
}

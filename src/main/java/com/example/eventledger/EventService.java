
package com.example.eventledger;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event create(EventRequest request) {

        return repository.findByEventId(request.eventId)
                .orElseGet(() -> {
                    Event e = new Event();
                    e.setEventId(request.eventId);
                    e.setAccountId(request.accountId);
                    e.setType(request.type);
                    e.setAmount(request.amount);
                    e.setCurrency(request.currency);
                    e.setEventTimestamp(request.eventTimestamp);
                    e.setMetadata(request.metadata == null ? null : request.metadata.toString());
                    return repository.save(e);
                });
    }

    public Event getByEventId(String id) {
        return repository.findByEventId(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public List<Event> getByAccount(String accountId) {
        return repository.findByAccountIdOrderByEventTimestampAsc(accountId);
    }

    public BigDecimal getBalance(String accountId) {

        return getByAccount(accountId).stream()
                .map(e -> e.getType() == EventType.CREDIT
                        ? e.getAmount()
                        : e.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

package com.example.ledger.service;

import com.example.ledger.dto.EventRequest;
import com.example.ledger.entity.EventType;
import com.example.ledger.entity.LedgerEvent;
import com.example.ledger.repository.LedgerEventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LedgerService {

    private final LedgerEventRepository repository;

    public LedgerService(LedgerEventRepository repository) {
        this.repository = repository;
    }

    public LedgerEvent submit(EventRequest request) {

        return repository.findByEventId(request.getEventId())
                .orElseGet(() -> {

                    LedgerEvent event = new LedgerEvent();

                    event.setEventId(request.getEventId());
                    event.setAccountId(request.getAccountId());
                    event.setType(request.getType());
                    event.setAmount(request.getAmount());
                    event.setCurrency(request.getCurrency());
                    event.setEventTimestamp(request.getEventTimestamp());

                    return repository.save(event);
                });
    }

    public List<LedgerEvent> getEvents(String accountId) {
        return repository.findByAccountIdOrderByEventTimestampAsc(accountId);
    }

    public BigDecimal computeBalance(String accountId) {

        return getEvents(accountId)
                .stream()
                .map(event -> event.getType() == EventType.CREDIT
                        ? event.getAmount()
                        : event.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
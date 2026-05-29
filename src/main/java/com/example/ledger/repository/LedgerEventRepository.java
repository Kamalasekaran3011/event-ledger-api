package com.example.ledger.repository;

import com.example.ledger.entity.LedgerEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LedgerEventRepository extends JpaRepository<LedgerEvent, Long> {

    Optional<LedgerEvent> findByEventId(String eventId);

    List<LedgerEvent> findByAccountIdOrderByEventTimestampAsc(String accountId);
}
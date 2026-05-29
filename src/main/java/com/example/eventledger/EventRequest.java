
package com.example.eventledger;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public class EventRequest {

    @NotBlank
    public String eventId;

    @NotBlank
    public String accountId;

    @NotNull
    public EventType type;

    @NotNull
    @DecimalMin(value = "0.01")
    public BigDecimal amount;

    @NotBlank
    public String currency;

    @NotNull
    public Instant eventTimestamp;

    public Object metadata;
}

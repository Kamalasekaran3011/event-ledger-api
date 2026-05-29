
package com.example.eventledger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void duplicateEventShouldBeIdempotent() throws Exception {

        String payload = '''
            {
              "eventId":"evt-001",
              "accountId":"acct-123",
              "type":"CREDIT",
              "amount":100,
              "currency":"USD",
              "eventTimestamp":"2026-05-15T14:02:11Z"
            }
            ''';

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk());
    }

    @Test
    void validationShouldFail() throws Exception {

        String payload = '''
            {
              "eventId":"evt-002",
              "accountId":"acct-123",
              "type":"INVALID",
              "amount":-1,
              "currency":"USD",
              "eventTimestamp":"2026-05-15T14:02:11Z"
            }
            ''';

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }
}

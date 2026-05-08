/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.origination.web.controllers;

import com.firefly.core.lending.origination.core.services.SimulationService;
import com.firefly.core.lending.origination.interfaces.dtos.SimulationDTO;
import com.firefly.core.lending.origination.interfaces.dtos.UpdateSimulationCalculationsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimulationControllerTest {

    private SimulationService service;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        service = mock(SimulationService.class);
        webTestClient = WebTestClient
                .bindToController(new SimulationController(service))
                .build();
    }

    @Test
    void postSimulation_returns201WithBody() {
        UUID id = UUID.randomUUID();
        SimulationDTO request = SimulationDTO.builder()
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("10000.00"))
                .term(36)
                .currency("EUR")
                .build();
        SimulationDTO saved = SimulationDTO.builder()
                .simulationId(id)
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("10000.00"))
                .term(36)
                .currency("EUR")
                .build();

        when(service.create(any(SimulationDTO.class))).thenReturn(Mono.just(saved));

        webTestClient.post()
                .uri("/api/v1/simulations")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.simulationId").isEqualTo(id.toString())
                .jsonPath("$.productType").isEqualTo("PERSONAL_LOAN")
                .jsonPath("$.requestedAmount").isEqualTo(10000.00)
                .jsonPath("$.term").isEqualTo(36);
    }

    @Test
    void getSimulation_returns200WhenFound() {
        UUID id = UUID.randomUUID();
        SimulationDTO body = SimulationDTO.builder()
                .simulationId(id)
                .productType("LEASING")
                .requestedAmount(new BigDecimal("25000.00"))
                .term(48)
                .currency("EUR")
                .build();

        when(service.getById(eq(id))).thenReturn(Mono.just(body));

        webTestClient.get()
                .uri("/api/v1/simulations/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.simulationId").isEqualTo(id.toString())
                .jsonPath("$.productType").isEqualTo("LEASING");
    }

    @Test
    void getSimulation_returns404WhenMissing() {
        UUID id = UUID.randomUUID();
        when(service.getById(eq(id))).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/api/v1/simulations/{id}", id)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void patchSimulation_returns200WithUpdatedBody() {
        UUID id = UUID.randomUUID();
        UpdateSimulationCalculationsRequest patch = UpdateSimulationCalculationsRequest.builder()
                .monthlyPayment(new BigDecimal("320.55"))
                .tin(new BigDecimal("7.9900"))
                .tae(new BigDecimal("8.2900"))
                .totalAmount(new BigDecimal("11539.80"))
                .build();

        SimulationDTO updated = SimulationDTO.builder()
                .simulationId(id)
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("10000.00"))
                .term(36)
                .currency("EUR")
                .monthlyPayment(patch.getMonthlyPayment())
                .tin(patch.getTin())
                .tae(patch.getTae())
                .totalAmount(patch.getTotalAmount())
                .build();

        when(service.updateCalculations(eq(id),
                eq(patch.getMonthlyPayment()),
                eq(patch.getTin()),
                eq(patch.getTae()),
                eq(patch.getTotalAmount()))).thenReturn(Mono.just(updated));

        webTestClient.patch()
                .uri("/api/v1/simulations/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patch)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.monthlyPayment").isEqualTo(320.55)
                .jsonPath("$.tin").isEqualTo(7.9900)
                .jsonPath("$.tae").isEqualTo(8.2900)
                .jsonPath("$.totalAmount").isEqualTo(11539.80);
    }
}

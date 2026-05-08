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

import com.firefly.core.lending.origination.core.services.ApplicationPartyService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationPartyDTO;
import com.firefly.core.lending.origination.interfaces.dtos.EmploymentDataPatchDTO;
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

class ApplicationPartyEmploymentControllerTest {

    private ApplicationPartyService service;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        service = mock(ApplicationPartyService.class);
        webTestClient = WebTestClient
                .bindToController(new ApplicationPartyEmploymentController(service))
                .build();
    }

    @Test
    void patchEmploymentData_returns200WithUpdatedBody() {
        UUID id = UUID.randomUUID();
        EmploymentDataPatchDTO patch = EmploymentDataPatchDTO.builder()
                .employer("Acme")
                .position("Engineer")
                .monthlySalary(new BigDecimal("3500.0000"))
                .build();

        ApplicationPartyDTO updated = ApplicationPartyDTO.builder()
                .applicationPartyId(id)
                .loanApplicationId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .roleCodeId(UUID.randomUUID())
                .employmentTypeId(UUID.randomUUID())
                .employer("Acme")
                .position("Engineer")
                .monthlySalary(new BigDecimal("3500.0000"))
                .build();

        when(service.updateEmploymentData(eq(id), any(EmploymentDataPatchDTO.class)))
                .thenReturn(Mono.just(updated));

        webTestClient.patch()
                .uri("/api/v1/application-parties/{id}/employment-data", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patch)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.applicationPartyId").isEqualTo(id.toString())
                .jsonPath("$.employer").isEqualTo("Acme")
                .jsonPath("$.position").isEqualTo("Engineer")
                .jsonPath("$.monthlySalary").isEqualTo(3500.0000);
    }

    @Test
    void getPrimary_returns200WhenFound() {
        UUID applicationId = UUID.randomUUID();
        UUID partyId = UUID.randomUUID();
        ApplicationPartyDTO primary = ApplicationPartyDTO.builder()
                .applicationPartyId(partyId)
                .loanApplicationId(applicationId)
                .partyId(UUID.randomUUID())
                .roleCodeId(UUID.randomUUID())
                .employmentTypeId(UUID.randomUUID())
                .isPrimary(Boolean.TRUE)
                .build();

        when(service.findPrimaryByApplicationId(eq(applicationId))).thenReturn(Mono.just(primary));

        webTestClient.get()
                .uri("/api/v1/application-parties/by-application/{applicationId}/primary", applicationId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.applicationPartyId").isEqualTo(partyId.toString())
                .jsonPath("$.isPrimary").isEqualTo(true);
    }

    @Test
    void getPrimary_returns404WhenMissing() {
        UUID applicationId = UUID.randomUUID();
        when(service.findPrimaryByApplicationId(eq(applicationId))).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/api/v1/application-parties/by-application/{applicationId}/primary", applicationId)
                .exchange()
                .expectStatus().isNotFound();
    }
}

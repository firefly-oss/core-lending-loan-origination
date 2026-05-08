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


package com.firefly.core.lending.origination.core.services;

import com.firefly.core.lending.origination.core.mappers.SimulationMapper;
import com.firefly.core.lending.origination.core.services.impl.SimulationServiceImpl;
import com.firefly.core.lending.origination.interfaces.dtos.SimulationDTO;
import com.firefly.core.lending.origination.models.entities.Simulation;
import com.firefly.core.lending.origination.models.repositories.SimulationRepository;
import org.fireflyframework.web.error.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationServiceImplTest {

    @Mock
    private SimulationRepository repository;

    @Mock
    private SimulationMapper mapper;

    @InjectMocks
    private SimulationServiceImpl service;

    private SimulationDTO inputDto;
    private Simulation entity;
    private SimulationDTO outputDto;

    @BeforeEach
    void setUp() {
        inputDto = SimulationDTO.builder()
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("10000.00"))
                .term(36)
                .currency("EUR")
                .build();

        entity = Simulation.builder()
                .simulationId(UUID.randomUUID())
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("10000.00"))
                .term(36)
                .currency("EUR")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        outputDto = SimulationDTO.builder()
                .simulationId(entity.getSimulationId())
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("10000.00"))
                .term(36)
                .currency("EUR")
                .build();
    }

    @Test
    void create_persistsAndReturnsDto_clearingClientSuppliedId() {
        SimulationDTO clientWithId = SimulationDTO.builder()
                .simulationId(UUID.randomUUID()) // should be cleared by the service
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("10000.00"))
                .term(36)
                .currency("EUR")
                .build();

        Simulation entityFromMapper = Simulation.builder()
                .simulationId(clientWithId.getSimulationId())
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("10000.00"))
                .term(36)
                .currency("EUR")
                .build();

        when(mapper.toEntity(any(SimulationDTO.class))).thenReturn(entityFromMapper);
        when(repository.save(any(Simulation.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(outputDto);

        StepVerifier.create(service.create(clientWithId))
                .expectNext(outputDto)
                .verifyComplete();

        ArgumentCaptor<Simulation> captor = ArgumentCaptor.forClass(Simulation.class);
        org.mockito.Mockito.verify(repository).save(captor.capture());
        assertThat(captor.getValue().getSimulationId()).isNull();
    }

    @Test
    void create_defaultsCurrencyToEur_whenBlank() {
        SimulationDTO noCurrency = SimulationDTO.builder()
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("1000"))
                .term(12)
                .build();

        Simulation entityFromMapper = Simulation.builder()
                .productType("PERSONAL_LOAN")
                .requestedAmount(new BigDecimal("1000"))
                .term(12)
                .currency(null)
                .build();

        when(mapper.toEntity(any(SimulationDTO.class))).thenReturn(entityFromMapper);
        when(repository.save(any(Simulation.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(outputDto);

        StepVerifier.create(service.create(noCurrency))
                .expectNextCount(1)
                .verifyComplete();

        ArgumentCaptor<Simulation> captor = ArgumentCaptor.forClass(Simulation.class);
        org.mockito.Mockito.verify(repository).save(captor.capture());
        assertThat(captor.getValue().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void getById_returnsMappedDto() {
        UUID id = entity.getSimulationId();
        when(repository.findById(id)).thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(outputDto);

        StepVerifier.create(service.getById(id))
                .expectNext(outputDto)
                .verifyComplete();
    }

    @Test
    void getById_returnsEmpty_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(service.getById(id))
                .verifyComplete();
    }

    @Test
    void updateCalculations_writesAllFourValuesAndPersists() {
        UUID id = entity.getSimulationId();
        when(repository.findById(id)).thenReturn(Mono.just(entity));
        when(repository.save(any(Simulation.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(mapper.toDTO(any(Simulation.class))).thenReturn(outputDto);

        BigDecimal mp = new BigDecimal("320.55");
        BigDecimal tin = new BigDecimal("7.9900");
        BigDecimal tae = new BigDecimal("8.2900");
        BigDecimal total = new BigDecimal("11539.80");

        StepVerifier.create(service.updateCalculations(id, mp, tin, tae, total))
                .expectNext(outputDto)
                .verifyComplete();

        ArgumentCaptor<Simulation> captor = ArgumentCaptor.forClass(Simulation.class);
        org.mockito.Mockito.verify(repository).save(captor.capture());
        Simulation saved = captor.getValue();
        assertThat(saved.getMonthlyPayment()).isEqualByComparingTo(mp);
        assertThat(saved.getTin()).isEqualByComparingTo(tin);
        assertThat(saved.getTae()).isEqualByComparingTo(tae);
        assertThat(saved.getTotalAmount()).isEqualByComparingTo(total);
        assertThat(saved.getUpdatedAt()).isNotNull();
    }

    @Test
    void updateCalculations_errorsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(service.updateCalculations(id,
                        BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO))
                .expectErrorSatisfies(err -> {
                    assertThat(err).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) err;
                    assertThat(be.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(be.getCode()).isEqualTo("SIMULATION_NOT_FOUND");
                })
                .verify();
    }
}

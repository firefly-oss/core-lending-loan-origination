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


package com.firefly.core.lending.origination.core.services.impl;

import com.firefly.core.lending.origination.core.mappers.SimulationMapper;
import com.firefly.core.lending.origination.core.services.SimulationService;
import com.firefly.core.lending.origination.interfaces.dtos.SimulationDTO;
import com.firefly.core.lending.origination.models.entities.Simulation;
import com.firefly.core.lending.origination.models.repositories.SimulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireflyframework.web.error.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SimulationServiceImpl implements SimulationService {

    private final SimulationRepository repository;
    private final SimulationMapper mapper;

    @Override
    public Mono<SimulationDTO> create(SimulationDTO dto) {
        log.debug("Creating simulation for productType={}, productId={}, requestedAmount={}, term={}",
                dto.getProductType(), dto.getProductId(), dto.getRequestedAmount(), dto.getTerm());
        Simulation entity = mapper.toEntity(dto);
        // Ensure the persistence layer assigns the id; never trust client-supplied ids on create.
        entity.setSimulationId(null);
        if (entity.getCurrency() == null || entity.getCurrency().isBlank()) {
            entity.setCurrency("EUR");
        }
        return repository.save(entity)
                .doOnNext(saved -> log.info("Simulation persisted with id={}", saved.getSimulationId()))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<SimulationDTO> getById(UUID id) {
        log.debug("Loading simulation id={}", id);
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<SimulationDTO> updateCalculations(UUID id,
                                                  BigDecimal monthlyPayment,
                                                  BigDecimal tin,
                                                  BigDecimal tae,
                                                  BigDecimal totalAmount) {
        log.debug("Updating calculations for simulation id={}, monthlyPayment={}, tin={}, tae={}, totalAmount={}",
                id, monthlyPayment, tin, tae, totalAmount);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(
                        HttpStatus.NOT_FOUND,
                        "SIMULATION_NOT_FOUND",
                        "Simulation not found: " + id)))
                .flatMap(existing -> {
                    existing.setMonthlyPayment(monthlyPayment);
                    existing.setTin(tin);
                    existing.setTae(tae);
                    existing.setTotalAmount(totalAmount);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existing);
                })
                .doOnNext(saved -> log.info("Simulation calculations updated id={}", saved.getSimulationId()))
                .map(mapper::toDTO);
    }
}

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

import com.firefly.core.lending.origination.interfaces.dtos.SimulationDTO;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service contract for persisted lending simulations.
 */
public interface SimulationService {

    /**
     * Persists a new simulation row. Calculation values may be {@code null}
     * at this point — they are typically filled in later via
     * {@link #updateCalculations(UUID, BigDecimal, BigDecimal, BigDecimal, BigDecimal)}.
     *
     * @param dto the simulation data
     * @return the persisted simulation including its generated id and timestamps
     */
    Mono<SimulationDTO> create(SimulationDTO dto);

    /**
     * Retrieves a simulation by its identifier.
     *
     * @param id the simulation id
     * @return the simulation or an empty {@code Mono} if it does not exist
     */
    Mono<SimulationDTO> getById(UUID id);

    /**
     * Persists the calculated values onto an existing simulation row.
     *
     * @param id             the simulation id
     * @param monthlyPayment the computed monthly payment
     * @param tin            the nominal interest rate (TIN)
     * @param tae            the annual equivalent rate (TAE)
     * @param totalAmount    the total amount to repay
     * @return the updated simulation
     */
    Mono<SimulationDTO> updateCalculations(UUID id,
                                           BigDecimal monthlyPayment,
                                           BigDecimal tin,
                                           BigDecimal tae,
                                           BigDecimal totalAmount);
}

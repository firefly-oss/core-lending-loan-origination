/*
 * Copyright 2025 Firefly Software Foundation
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


package com.firefly.core.lending.origination.models.repositories;

import com.firefly.core.lending.origination.models.entities.ApplicationParty;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationPartyRepository extends BaseRepository<ApplicationParty, UUID> {

    /**
     * Returns the primary party for the given loan application
     * (the row whose {@code is_primary} flag is {@code TRUE}).
     *
     * <p>The partial unique index {@code ux_application_party_primary} guarantees
     * at most one such row per application.</p>
     *
     * @param loanApplicationId the loan application identifier
     * @return the primary party, or empty if none has been marked as primary yet
     */
    Mono<ApplicationParty> findFirstByLoanApplicationIdAndIsPrimaryTrue(UUID loanApplicationId);
}

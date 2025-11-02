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

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationPartyDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationPartyService {

    /**
     * Retrieves a paginated list of application party details associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the party details are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationPartyDTO objects
     *         representing the details of the application parties associated with the provided application ID
     */
    Mono<PaginationResponse<ApplicationPartyDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new party associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the party is to be added
     * @param dto the data transfer object containing details of the party to be created
     * @return a reactive Mono containing the created ApplicationPartyDTO representing the new party
     */
    Mono<ApplicationPartyDTO> createParty(UUID applicationId, ApplicationPartyDTO dto);

    /**
     * Retrieves details of a specific party associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the party is linked.
     * @param partyId the unique identifier of the party to be retrieved.
     * @return a reactive Mono containing the ApplicationPartyDTO object with details of the specified party.
     */
    Mono<ApplicationPartyDTO> getParty(UUID applicationId, UUID partyId);

    /**
     * Updates an existing application party with the provided details.
     *
     * @param applicationId the unique identifier of the loan application that the party is associated with
     * @param partyId the unique identifier of the party to be updated
     * @param dto the data transfer object containing updated details for the application party
     * @return a Mono emitting the updated ApplicationPartyDTO upon successful update
     */
    Mono<ApplicationPartyDTO> updateParty(UUID applicationId, UUID partyId, ApplicationPartyDTO dto);

    /**
     * Deletes a specific party associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the party belongs
     * @param partyId the unique identifier of the party to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteParty(UUID applicationId, UUID partyId);
}
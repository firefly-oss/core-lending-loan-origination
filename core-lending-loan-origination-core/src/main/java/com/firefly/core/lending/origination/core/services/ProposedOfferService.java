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
import com.firefly.core.lending.origination.interfaces.dtos.ProposedOfferDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProposedOfferService {

    /**
     * Retrieves a paginated list of proposed offers associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the proposed offers are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ProposedOfferDTO objects
     *         representing the proposed offers associated with the provided application ID
     */
    Mono<PaginationResponse<ProposedOfferDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a proposed offer for the specified loan application.
     *
     * @param applicationId the unique identifier of the loan application for which the proposed offer is to be created
     * @param dto the data transfer object containing the details of the proposed offer to create
     * @return a reactive Mono containing the created ProposedOfferDTO object representing the proposed offer
     */
    Mono<ProposedOfferDTO> createOffer(UUID applicationId, ProposedOfferDTO dto);

    /**
     * Retrieves the details of a proposed loan offer associated with a specific application and offer ID.
     *
     * @param applicationId the unique identifier of the loan application to which the proposed offer is linked
     * @param offerId the unique identifier of the proposed offer to be retrieved
     * @return a reactive Mono containing the ProposedOfferDTO object with details of the specified proposed offer
     */
    Mono<ProposedOfferDTO> getOffer(UUID applicationId, UUID offerId);

    /**
     * Updates an existing proposed offer associated with the specified application ID and offer ID.
     *
     * @param applicationId the unique identifier of the loan application to which the offer belongs
     * @param offerId the unique identifier of the offer to be updated
     * @param dto the data transfer object containing the updated details of the proposed offer
     * @return a Mono emitting the updated ProposedOfferDTO upon successful update
     */
    Mono<ProposedOfferDTO> updateOffer(UUID applicationId, UUID offerId, ProposedOfferDTO dto);

    /**
     * Deletes a specific offer associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the offer belongs
     * @param offerId the unique identifier of the offer to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteOffer(UUID applicationId, UUID offerId);
}
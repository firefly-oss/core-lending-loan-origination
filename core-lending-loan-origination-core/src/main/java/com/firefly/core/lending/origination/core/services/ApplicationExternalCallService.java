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

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationExternalCallDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationExternalCallService {

    /**
     * Retrieves a paginated list of external calls associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the external calls are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationExternalCallDTO objects
     */
    Mono<PaginationResponse<ApplicationExternalCallDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new external call associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the external call is to be added
     * @param dto the data transfer object containing details of the external call to be created
     * @return a reactive Mono containing the created ApplicationExternalCallDTO
     */
    Mono<ApplicationExternalCallDTO> createExternalCall(UUID applicationId, ApplicationExternalCallDTO dto);

    /**
     * Retrieves a specific external call associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the external call belongs
     * @param callId the unique identifier of the external call to be retrieved
     * @return a reactive Mono containing the ApplicationExternalCallDTO object
     */
    Mono<ApplicationExternalCallDTO> getExternalCall(UUID applicationId, UUID callId);

    /**
     * Updates an existing external call associated with a specific application and call ID.
     *
     * @param applicationId the unique identifier of the application to which the external call belongs
     * @param callId the unique identifier of the external call to be updated
     * @param dto the data transfer object containing the updated details for the external call
     * @return a Mono emitting the updated ApplicationExternalCallDTO
     */
    Mono<ApplicationExternalCallDTO> updateExternalCall(UUID applicationId, UUID callId, ApplicationExternalCallDTO dto);

    /**
     * Deletes a specific external call associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the external call belongs
     * @param callId the unique identifier of the external call to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteExternalCall(UUID applicationId, UUID callId);
}


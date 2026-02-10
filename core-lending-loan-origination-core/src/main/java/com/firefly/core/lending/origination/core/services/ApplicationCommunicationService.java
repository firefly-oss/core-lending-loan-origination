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
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationCommunicationDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationCommunicationService {

    /**
     * Retrieves a paginated list of communications associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the communications are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationCommunicationDTO objects
     */
    Mono<PaginationResponse<ApplicationCommunicationDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new communication associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the communication is to be added
     * @param dto the data transfer object containing details of the communication to be created
     * @return a reactive Mono containing the created ApplicationCommunicationDTO
     */
    Mono<ApplicationCommunicationDTO> createCommunication(UUID applicationId, ApplicationCommunicationDTO dto);

    /**
     * Retrieves a specific communication associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the communication belongs
     * @param communicationId the unique identifier of the communication to be retrieved
     * @return a reactive Mono containing the ApplicationCommunicationDTO object
     */
    Mono<ApplicationCommunicationDTO> getCommunication(UUID applicationId, UUID communicationId);

    /**
     * Updates an existing communication associated with a specific application and communication ID.
     *
     * @param applicationId the unique identifier of the application to which the communication belongs
     * @param communicationId the unique identifier of the communication to be updated
     * @param dto the data transfer object containing the updated details for the communication
     * @return a Mono emitting the updated ApplicationCommunicationDTO
     */
    Mono<ApplicationCommunicationDTO> updateCommunication(UUID applicationId, UUID communicationId, ApplicationCommunicationDTO dto);

    /**
     * Deletes a specific communication associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the communication belongs
     * @param communicationId the unique identifier of the communication to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteCommunication(UUID applicationId, UUID communicationId);
}


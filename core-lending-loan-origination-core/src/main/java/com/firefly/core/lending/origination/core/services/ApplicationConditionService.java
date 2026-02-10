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
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationConditionDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationConditionService {

    /**
     * Retrieves a paginated list of conditions associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the conditions are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationConditionDTO objects
     */
    Mono<PaginationResponse<ApplicationConditionDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new condition associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the condition is to be added
     * @param dto the data transfer object containing details of the condition to be created
     * @return a reactive Mono containing the created ApplicationConditionDTO
     */
    Mono<ApplicationConditionDTO> createCondition(UUID applicationId, ApplicationConditionDTO dto);

    /**
     * Retrieves a specific condition associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the condition belongs
     * @param conditionId the unique identifier of the condition to be retrieved
     * @return a reactive Mono containing the ApplicationConditionDTO object
     */
    Mono<ApplicationConditionDTO> getCondition(UUID applicationId, UUID conditionId);

    /**
     * Updates an existing condition associated with a specific application and condition ID.
     *
     * @param applicationId the unique identifier of the application to which the condition belongs
     * @param conditionId the unique identifier of the condition to be updated
     * @param dto the data transfer object containing the updated details for the condition
     * @return a Mono emitting the updated ApplicationConditionDTO
     */
    Mono<ApplicationConditionDTO> updateCondition(UUID applicationId, UUID conditionId, ApplicationConditionDTO dto);

    /**
     * Deletes a specific condition associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the condition belongs
     * @param conditionId the unique identifier of the condition to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteCondition(UUID applicationId, UUID conditionId);
}


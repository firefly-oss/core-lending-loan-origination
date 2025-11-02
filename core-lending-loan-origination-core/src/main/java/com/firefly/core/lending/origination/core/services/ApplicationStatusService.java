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

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationStatusDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationStatusService {
    /**
     * Filters application statuses based on the provided filter criteria.
     *
     * @param filterRequest the filtering criteria and parameters to apply when retrieving application statuses.
     * @return a reactive Mono containing a paginated response with a list of application statuses
     *         that match the filter criteria.
     */
    Mono<PaginationResponse<ApplicationStatusDTO>> filterApplicationStatuses(FilterRequest<ApplicationStatusDTO> filterRequest);

    /**
     * Retrieves a paginated list of application statuses.
     *
     * @param paginationRequest the request object containing pagination details, such as page number and size
     * @return a reactive Mono containing a paginated response of application statuses encapsulated in ApplicationStatusDTO objects
     */
    Mono<PaginationResponse<ApplicationStatusDTO>> listApplicationStatuses(PaginationRequest paginationRequest);

    /**
     * Creates a new application status based on the given application status details.
     *
     * @param dto The ApplicationStatusDTO containing the details of the application status to be created.
     * @return A Mono containing the ApplicationStatusDTO of the created application status.
     */
    Mono<ApplicationStatusDTO> createApplicationStatus(ApplicationStatusDTO dto);

    /**
     * Retrieves the details of an application status based on the given status ID.
     *
     * @param applicationStatusId the unique identifier of the application status to be retrieved
     * @return a reactive Mono containing the ApplicationStatusDTO object with details of the requested application status
     */
    Mono<ApplicationStatusDTO> getApplicationStatus(UUID applicationStatusId);

    /**
     * Updates an existing application status with the provided data.
     *
     * @param applicationStatusId the unique identifier of the application status to be updated
     * @param dto the data transfer object containing updated application status details
     * @return a {@link Mono} emitting the updated {@link ApplicationStatusDTO} upon successful update
     */
    Mono<ApplicationStatusDTO> updateApplicationStatus(UUID applicationStatusId, ApplicationStatusDTO dto);

    /**
     * Deletes an application status by its unique identifier.
     *
     * @param applicationStatusId the unique identifier of the application status to be deleted
     * @return a Mono signaling when the deletion is completed
     */
    Mono<Void> deleteApplicationStatus(UUID applicationStatusId);

    /**
     * Finds an application status by its code.
     *
     * @param code the code of the application status to be retrieved
     * @return a reactive Mono containing the ApplicationStatusDTO object with details of the requested application status
     */
    Mono<ApplicationStatusDTO> findByCode(String code);
}
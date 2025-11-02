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
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationMetricDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationMetricService {

    /**
     * Retrieves a paginated list of metrics associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the metrics are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationMetricDTO objects
     */
    Mono<PaginationResponse<ApplicationMetricDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new metric associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the metric is to be added
     * @param dto the data transfer object containing details of the metric to be created
     * @return a reactive Mono containing the created ApplicationMetricDTO
     */
    Mono<ApplicationMetricDTO> createMetric(UUID applicationId, ApplicationMetricDTO dto);

    /**
     * Retrieves a specific metric associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the metric belongs
     * @param metricId the unique identifier of the metric to be retrieved
     * @return a reactive Mono containing the ApplicationMetricDTO object
     */
    Mono<ApplicationMetricDTO> getMetric(UUID applicationId, UUID metricId);

    /**
     * Updates an existing metric associated with a specific application and metric ID.
     *
     * @param applicationId the unique identifier of the application to which the metric belongs
     * @param metricId the unique identifier of the metric to be updated
     * @param dto the data transfer object containing the updated details for the metric
     * @return a Mono emitting the updated ApplicationMetricDTO
     */
    Mono<ApplicationMetricDTO> updateMetric(UUID applicationId, UUID metricId, ApplicationMetricDTO dto);

    /**
     * Deletes a specific metric associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the metric belongs
     * @param metricId the unique identifier of the metric to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteMetric(UUID applicationId, UUID metricId);
}


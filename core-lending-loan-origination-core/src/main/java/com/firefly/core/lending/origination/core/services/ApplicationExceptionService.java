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


package com.firefly.core.lending.origination.core.services.exception.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.exception.v1.ApplicationExceptionDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationExceptionService {

    /**
     * Retrieves a paginated list of exceptions associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the exceptions are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationExceptionDTO objects
     */
    Mono<PaginationResponse<ApplicationExceptionDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new exception associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the exception is to be added
     * @param dto the data transfer object containing details of the exception to be created
     * @return a reactive Mono containing the created ApplicationExceptionDTO
     */
    Mono<ApplicationExceptionDTO> createException(UUID applicationId, ApplicationExceptionDTO dto);

    /**
     * Retrieves a specific exception associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the exception belongs
     * @param exceptionId the unique identifier of the exception to be retrieved
     * @return a reactive Mono containing the ApplicationExceptionDTO object
     */
    Mono<ApplicationExceptionDTO> getException(UUID applicationId, UUID exceptionId);

    /**
     * Updates an existing exception associated with a specific application and exception ID.
     *
     * @param applicationId the unique identifier of the application to which the exception belongs
     * @param exceptionId the unique identifier of the exception to be updated
     * @param dto the data transfer object containing the updated details for the exception
     * @return a Mono emitting the updated ApplicationExceptionDTO
     */
    Mono<ApplicationExceptionDTO> updateException(UUID applicationId, UUID exceptionId, ApplicationExceptionDTO dto);

    /**
     * Deletes a specific exception associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the exception belongs
     * @param exceptionId the unique identifier of the exception to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteException(UUID applicationId, UUID exceptionId);
}


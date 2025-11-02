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
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationVerificationDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationVerificationService {

    /**
     * Retrieves a paginated list of verifications associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the verifications are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationVerificationDTO objects
     */
    Mono<PaginationResponse<ApplicationVerificationDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new verification associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the verification is to be added
     * @param dto the data transfer object containing details of the verification to be created
     * @return a reactive Mono containing the created ApplicationVerificationDTO
     */
    Mono<ApplicationVerificationDTO> createVerification(UUID applicationId, ApplicationVerificationDTO dto);

    /**
     * Retrieves a specific verification associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the verification belongs
     * @param verificationId the unique identifier of the verification to be retrieved
     * @return a reactive Mono containing the ApplicationVerificationDTO object
     */
    Mono<ApplicationVerificationDTO> getVerification(UUID applicationId, UUID verificationId);

    /**
     * Updates an existing verification associated with a specific application and verification ID.
     *
     * @param applicationId the unique identifier of the application to which the verification belongs
     * @param verificationId the unique identifier of the verification to be updated
     * @param dto the data transfer object containing the updated details for the verification
     * @return a Mono emitting the updated ApplicationVerificationDTO
     */
    Mono<ApplicationVerificationDTO> updateVerification(UUID applicationId, UUID verificationId, ApplicationVerificationDTO dto);

    /**
     * Deletes a specific verification associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the verification belongs
     * @param verificationId the unique identifier of the verification to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteVerification(UUID applicationId, UUID verificationId);
}


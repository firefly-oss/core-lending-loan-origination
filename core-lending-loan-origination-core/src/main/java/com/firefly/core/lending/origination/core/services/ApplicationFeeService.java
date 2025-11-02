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


package com.firefly.core.lending.origination.core.services.fee.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.fee.v1.ApplicationFeeDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationFeeService {

    /**
     * Retrieves a paginated list of fees associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the fees are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationFeeDTO objects
     */
    Mono<PaginationResponse<ApplicationFeeDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new fee associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the fee is to be added
     * @param dto the data transfer object containing details of the fee to be created
     * @return a reactive Mono containing the created ApplicationFeeDTO
     */
    Mono<ApplicationFeeDTO> createFee(UUID applicationId, ApplicationFeeDTO dto);

    /**
     * Retrieves a specific fee associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the fee belongs
     * @param feeId the unique identifier of the fee to be retrieved
     * @return a reactive Mono containing the ApplicationFeeDTO object
     */
    Mono<ApplicationFeeDTO> getFee(UUID applicationId, UUID feeId);

    /**
     * Updates an existing fee associated with a specific application and fee ID.
     *
     * @param applicationId the unique identifier of the application to which the fee belongs
     * @param feeId the unique identifier of the fee to be updated
     * @param dto the data transfer object containing the updated details for the fee
     * @return a Mono emitting the updated ApplicationFeeDTO
     */
    Mono<ApplicationFeeDTO> updateFee(UUID applicationId, UUID feeId, ApplicationFeeDTO dto);

    /**
     * Deletes a specific fee associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the fee belongs
     * @param feeId the unique identifier of the fee to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteFee(UUID applicationId, UUID feeId);
}


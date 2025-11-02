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
import com.firefly.core.lending.origination.interfaces.dtos.LoanApplicationStatusHistoryDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanApplicationStatusHistoryService {
    /**
     * Retrieves a paginated list of loan application status history records for a specified loan application.
     *
     * @param applicationId the unique identifier of the loan application for which status history is to be retrieved
     * @param paginationRequest the request object containing pagination details such as page number and size
     * @return a reactive Mono containing a paginated response of loan application status history
     *         encapsulated in LoanApplicationStatusHistoryDTO objects
     */
    Mono<PaginationResponse<LoanApplicationStatusHistoryDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new status history entry for the specified loan application.
     *
     * @param applicationId the unique identifier of the loan application for which the status history is to be created
     * @param dto the details of the status history to be created, encapsulated in a LoanApplicationStatusHistoryDTO object
     * @return a Mono emitting the created LoanApplicationStatusHistoryDTO once the operation is successfully completed
     */
    Mono<LoanApplicationStatusHistoryDTO> createStatusHistory(UUID applicationId, LoanApplicationStatusHistoryDTO dto);

    /**
     * Retrieves the status history of a loan application by its application ID and status history ID.
     *
     * @param applicationId the unique identifier of the loan application associated with the status history
     * @param statusHistoryId the unique identifier of the specific status history to retrieve
     * @return a reactive Mono containing the LoanApplicationStatusHistoryDTO object with details of the requested status history
     */
    Mono<LoanApplicationStatusHistoryDTO> getStatusHistory(UUID applicationId, UUID statusHistoryId);

    /**
     * Updates an existing status history entry for a given loan application.
     *
     * @param applicationId the unique identifier of the loan application whose status history is to be updated
     * @param statusHistoryId the unique identifier of the status history entry to be updated
     * @param dto the data transfer object containing the updated details of the status history
     * @return a Mono emitting the updated LoanApplicationStatusHistoryDTO reflecting the updated status history details
     */
    Mono<LoanApplicationStatusHistoryDTO> updateStatusHistory(UUID applicationId, UUID statusHistoryId, LoanApplicationStatusHistoryDTO dto);

    /**
     * Deletes a specific status history entry for a loan application.
     *
     * @param applicationId the unique identifier of the loan application whose status history is to be deleted
     * @param statusHistoryId the unique identifier of the status history entry to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteStatusHistory(UUID applicationId, UUID statusHistoryId);
}
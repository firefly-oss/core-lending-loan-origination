package com.catalis.core.lending.origination.core.services.status.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.origination.interfaces.dtos.status.v1.LoanApplicationStatusHistoryDTO;
import reactor.core.publisher.Mono;

public interface LoanApplicationStatusHistoryService {
    /**
     * Retrieves a paginated list of loan application status history records for a specified loan application.
     *
     * @param applicationId the unique identifier of the loan application for which status history is to be retrieved
     * @param paginationRequest the request object containing pagination details such as page number and size
     * @return a reactive Mono containing a paginated response of loan application status history
     *         encapsulated in LoanApplicationStatusHistoryDTO objects
     */
    Mono<PaginationResponse<LoanApplicationStatusHistoryDTO>> findAll(Long applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new status history entry for the specified loan application.
     *
     * @param applicationId the unique identifier of the loan application for which the status history is to be created
     * @param dto the details of the status history to be created, encapsulated in a LoanApplicationStatusHistoryDTO object
     * @return a Mono emitting the created LoanApplicationStatusHistoryDTO once the operation is successfully completed
     */
    Mono<LoanApplicationStatusHistoryDTO> createStatusHistory(Long applicationId, LoanApplicationStatusHistoryDTO dto);

    /**
     * Retrieves the status history of a loan application by its application ID and status history ID.
     *
     * @param applicationId the unique identifier of the loan application associated with the status history
     * @param statusHistoryId the unique identifier of the specific status history to retrieve
     * @return a reactive Mono containing the LoanApplicationStatusHistoryDTO object with details of the requested status history
     */
    Mono<LoanApplicationStatusHistoryDTO> getStatusHistory(Long applicationId, Long statusHistoryId);

    /**
     * Updates an existing status history entry for a given loan application.
     *
     * @param applicationId the unique identifier of the loan application whose status history is to be updated
     * @param statusHistoryId the unique identifier of the status history entry to be updated
     * @param dto the data transfer object containing the updated details of the status history
     * @return a Mono emitting the updated LoanApplicationStatusHistoryDTO reflecting the updated status history details
     */
    Mono<LoanApplicationStatusHistoryDTO> updateStatusHistory(Long applicationId, Long statusHistoryId, LoanApplicationStatusHistoryDTO dto);

    /**
     * Deletes a specific status history entry for a loan application.
     *
     * @param applicationId the unique identifier of the loan application whose status history is to be deleted
     * @param statusHistoryId the unique identifier of the status history entry to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteStatusHistory(Long applicationId, Long statusHistoryId);
}
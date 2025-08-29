package com.firefly.core.lending.origination.core.services.application.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.application.v1.LoanApplicationDTO;
import reactor.core.publisher.Mono;

public interface LoanApplicationsService {
    /**
     * Filters loan applications based on the provided filter criteria.
     *
     * @param filterRequest the filtering criteria and parameters to apply when retrieving loan applications.
     * @return a reactive Mono containing a paginated response with a list of loan applications
     *         that match the filter criteria.
     */
    Mono<PaginationResponse<LoanApplicationDTO>> filterLoanApplications(FilterRequest<LoanApplicationDTO> filterRequest);

    /**
     * Retrieves a paginated list of loan applications.
     *
     * @param paginationRequest the request object containing pagination details, such as page number and size
     * @return a reactive Mono containing a paginated response of loan applications encapsulated in LoanApplicationDTO objects
     */
    Mono<PaginationResponse<LoanApplicationDTO>> listLoanApplications(PaginationRequest paginationRequest);

    /**
     * Creates a new loan application based on the given loan application details.
     *
     * @param dto The LoanApplicationDTO containing the details of the loan application to be created.
     * @return A Mono containing the LoanApplicationDTO of the created loan application.
     */
    Mono<LoanApplicationDTO> createLoanApplication(LoanApplicationDTO dto);

    /**
     * Retrieves the details of a loan application based on the given application ID.
     *
     * @param applicationId the unique identifier of the loan application to be retrieved
     * @return a reactive Mono containing the LoanApplicationDTO object with details of the requested loan application
     */
    Mono<LoanApplicationDTO> getLoanApplication(Long applicationId);

    /**
     * Updates an existing loan application with the provided data.
     *
     * @param applicationId the unique identifier of the loan application to be updated
     * @param dto the data transfer object containing updated loan application details
     * @return a {@link Mono} emitting the updated {@link LoanApplicationDTO} upon successful update
     */
    Mono<LoanApplicationDTO> updateLoanApplication(Long applicationId, LoanApplicationDTO dto);

    /**
     * Deletes a loan application by its unique identifier.
     *
     * @param applicationId the unique identifier of the loan application to be deleted
     * @return a Mono signaling when the deletion is completed
     */
    Mono<Void> deleteLoanApplication(Long applicationId);
}
package com.firefly.core.lending.origination.core.services.decision.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.decision.v1.UnderwritingDecisionDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UnderwritingDecisionService {

    /**
     * Retrieves a paginated list of underwriting decisions associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application for which the underwriting decisions are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of UnderwritingDecisionDTO objects
     *         representing the underwriting decisions associated with the provided loan application ID
     */
    Mono<PaginationResponse<UnderwritingDecisionDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates an underwriting decision for the specified loan application.
     *
     * @param applicationId the unique identifier of the loan application for which the underwriting decision is to be created
     * @param dto the data transfer object containing the details of the underwriting decision to create
     * @return a reactive Mono containing the created UnderwritingDecisionDTO object representing the underwriting decision
     */
    Mono<UnderwritingDecisionDTO> createDecision(UUID applicationId, UnderwritingDecisionDTO dto);

    /**
     * Retrieves the underwriting decision for a specific application and decision ID.
     *
     * @param applicationId the unique identifier of the loan application to which the underwriting decision is related
     * @param decisionId the unique identifier of the underwriting decision to be retrieved
     * @return a reactive Mono containing the UnderwritingDecisionDTO object with details of the specified underwriting decision
     */
    Mono<UnderwritingDecisionDTO> getDecision(UUID applicationId, UUID decisionId);

    /**
     * Updates an existing underwriting decision associated with the specified application ID and decision ID.
     *
     * @param applicationId the unique identifier of the loan application to which the underwriting decision belongs
     * @param decisionId the unique identifier of the underwriting decision to be updated
     * @param dto the data transfer object containing updated details of the underwriting decision
     * @return a reactive Mono containing the updated UnderwritingDecisionDTO upon successful update
     */
    Mono<UnderwritingDecisionDTO> updateDecision(UUID applicationId, UUID decisionId, UnderwritingDecisionDTO dto);

    /**
     * Deletes a specific decision associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the decision belongs
     * @param decisionId the unique identifier of the decision to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteDecision(UUID applicationId, UUID decisionId);
}

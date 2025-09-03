package com.firefly.core.lending.origination.core.services.score.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.score.v1.UnderwritingScoreDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UnderwritingScoreService {

    /**
     * Retrieves a paginated list of underwriting scores associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the underwriting scores are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a pagitated response with a list of UnderwritingScoreDTO objects
     *         representing the underwriting scores associated with the provided application ID
     */
    Mono<PaginationResponse<UnderwritingScoreDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new underwriting score for the specified loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the underwriting score is associated
     * @param dto the data transfer object containing the details of the underwriting score to be created
     * @return a reactive Mono containing the created UnderwritingScoreDTO object representing the underwriting score
     */
    Mono<UnderwritingScoreDTO> createScore(UUID applicationId, UnderwritingScoreDTO dto);

    /**
     * Retrieves the underwriting score associated with a specific application ID and score ID.
     *
     * @param applicationId the unique identifier of the loan application to which the underwriting score is associated
     * @param scoreId the unique identifier of the underwriting score to retrieve
     * @return a reactive Mono containing the UnderwritingScoreDTO object with details of the requested underwriting score
     */
    Mono<UnderwritingScoreDTO> getScore(UUID applicationId, UUID scoreId);

    /**
     * Updates an existing underwriting score associated with the specified application ID and score ID.
     *
     * @param applicationId the unique identifier of the loan application associated with the underwriting score to be updated
     * @param scoreId the unique identifier of the underwriting score to be updated
     * @param dto the data transfer object containing updated details for the underwriting score
     * @return a Mono emitting the updated UnderwritingScoreDTO object upon successful update
     */
    Mono<UnderwritingScoreDTO> updateScore(UUID applicationId, UUID scoreId, UnderwritingScoreDTO dto);

    /**
     * Deletes a specific score associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the score belongs
     * @param scoreId the unique identifier of the score to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteScore(UUID applicationId, UUID scoreId);
}
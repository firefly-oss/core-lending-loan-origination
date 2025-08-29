package com.firefly.core.lending.origination.core.services.offer.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.offer.v1.ProposedOfferDTO;
import reactor.core.publisher.Mono;

public interface ProposedOfferService {

    /**
     * Retrieves a paginated list of proposed offers associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the proposed offers are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ProposedOfferDTO objects
     *         representing the proposed offers associated with the provided application ID
     */
    Mono<PaginationResponse<ProposedOfferDTO>> findAll(Long applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a proposed offer for the specified loan application.
     *
     * @param applicationId the unique identifier of the loan application for which the proposed offer is to be created
     * @param dto the data transfer object containing the details of the proposed offer to create
     * @return a reactive Mono containing the created ProposedOfferDTO object representing the proposed offer
     */
    Mono<ProposedOfferDTO> createOffer(Long applicationId, ProposedOfferDTO dto);

    /**
     * Retrieves the details of a proposed loan offer associated with a specific application and offer ID.
     *
     * @param applicationId the unique identifier of the loan application to which the proposed offer is linked
     * @param offerId the unique identifier of the proposed offer to be retrieved
     * @return a reactive Mono containing the ProposedOfferDTO object with details of the specified proposed offer
     */
    Mono<ProposedOfferDTO> getOffer(Long applicationId, Long offerId);

    /**
     * Updates an existing proposed offer associated with the specified application ID and offer ID.
     *
     * @param applicationId the unique identifier of the loan application to which the offer belongs
     * @param offerId the unique identifier of the offer to be updated
     * @param dto the data transfer object containing the updated details of the proposed offer
     * @return a Mono emitting the updated ProposedOfferDTO upon successful update
     */
    Mono<ProposedOfferDTO> updateOffer(Long applicationId, Long offerId, ProposedOfferDTO dto);

    /**
     * Deletes a specific offer associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the offer belongs
     * @param offerId the unique identifier of the offer to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteOffer(Long applicationId, Long offerId);
}
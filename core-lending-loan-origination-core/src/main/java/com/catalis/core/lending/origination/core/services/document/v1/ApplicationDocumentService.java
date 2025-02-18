package com.catalis.core.lending.origination.core.services.document.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.origination.interfaces.dtos.document.v1.ApplicationDocumentDTO;
import reactor.core.publisher.Mono;

public interface ApplicationDocumentService {

    /**
     * Retrieves a paginated list of application documents associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the documents are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationDocumentDTO objects
     *         representing the details of the application documents associated with the provided application ID
     */
    Mono<PaginationResponse<ApplicationDocumentDTO>> findAll(Long applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new document associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the document is to be added
     * @param dto the data transfer object containing details of the document to be created
     * @return a reactive Mono containing the created ApplicationDocumentDTO representing the new document
     */
    Mono<ApplicationDocumentDTO> createDocument(Long applicationId, ApplicationDocumentDTO dto);

    /**
     * Retrieves a specific document associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the document belongs
     * @param documentId the unique identifier of the document to be retrieved
     * @return a reactive Mono containing the ApplicationDocumentDTO object with details of the requested document
     */
    Mono<ApplicationDocumentDTO> getDocument(Long applicationId, Long documentId);

    /**
     * Updates an existing document associated with a specific application and document ID.
     *
     * @param applicationId the unique identifier of the application to which the document belongs
     * @param documentId the unique identifier of the document to be updated
     * @param dto the data transfer object containing the updated details for the application document
     * @return a Mono emitting the updated ApplicationDocumentDTO upon a successful update operation
     */
    Mono<ApplicationDocumentDTO> updateDocument(Long applicationId, Long documentId, ApplicationDocumentDTO dto);

    /**
     * Deletes a specific document associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the document belongs
     * @param documentId the unique identifier of the document to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteDocument(Long applicationId, Long documentId);
}

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


package com.firefly.core.lending.origination.core.services.comment.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.comment.v1.ApplicationCommentDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationCommentService {

    /**
     * Retrieves a paginated list of comments associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the comments are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationCommentDTO objects
     */
    Mono<PaginationResponse<ApplicationCommentDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new comment associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the comment is to be added
     * @param dto the data transfer object containing details of the comment to be created
     * @return a reactive Mono containing the created ApplicationCommentDTO
     */
    Mono<ApplicationCommentDTO> createComment(UUID applicationId, ApplicationCommentDTO dto);

    /**
     * Retrieves a specific comment associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the comment belongs
     * @param commentId the unique identifier of the comment to be retrieved
     * @return a reactive Mono containing the ApplicationCommentDTO object
     */
    Mono<ApplicationCommentDTO> getComment(UUID applicationId, UUID commentId);

    /**
     * Updates an existing comment associated with a specific application and comment ID.
     *
     * @param applicationId the unique identifier of the application to which the comment belongs
     * @param commentId the unique identifier of the comment to be updated
     * @param dto the data transfer object containing the updated details for the comment
     * @return a Mono emitting the updated ApplicationCommentDTO
     */
    Mono<ApplicationCommentDTO> updateComment(UUID applicationId, UUID commentId, ApplicationCommentDTO dto);

    /**
     * Deletes a specific comment associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the comment belongs
     * @param commentId the unique identifier of the comment to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteComment(UUID applicationId, UUID commentId);
}


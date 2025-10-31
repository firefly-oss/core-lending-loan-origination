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


package com.firefly.core.lending.origination.core.services.task.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.task.v1.ApplicationTaskDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationTaskService {

    /**
     * Retrieves a paginated list of tasks associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application for which the tasks are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationTaskDTO objects
     */
    Mono<PaginationResponse<ApplicationTaskDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new task associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the task is to be added
     * @param dto the data transfer object containing details of the task to be created
     * @return a reactive Mono containing the created ApplicationTaskDTO
     */
    Mono<ApplicationTaskDTO> createTask(UUID applicationId, ApplicationTaskDTO dto);

    /**
     * Retrieves a specific task associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the task belongs
     * @param taskId the unique identifier of the task to be retrieved
     * @return a reactive Mono containing the ApplicationTaskDTO object
     */
    Mono<ApplicationTaskDTO> getTask(UUID applicationId, UUID taskId);

    /**
     * Updates an existing task associated with a specific application and task ID.
     *
     * @param applicationId the unique identifier of the application to which the task belongs
     * @param taskId the unique identifier of the task to be updated
     * @param dto the data transfer object containing the updated details for the task
     * @return a Mono emitting the updated ApplicationTaskDTO
     */
    Mono<ApplicationTaskDTO> updateTask(UUID applicationId, UUID taskId, ApplicationTaskDTO dto);

    /**
     * Deletes a specific task associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the task belongs
     * @param taskId the unique identifier of the task to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteTask(UUID applicationId, UUID taskId);
}


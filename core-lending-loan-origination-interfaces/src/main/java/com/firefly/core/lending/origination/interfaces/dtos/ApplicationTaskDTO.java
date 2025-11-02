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


package com.firefly.core.lending.origination.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationTaskDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID taskId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @NotBlank(message = "Task type is required")
    @Size(max = 50, message = "Task type cannot exceed 50 characters")
    private String taskType;

    @Size(max = 50, message = "Task status cannot exceed 50 characters")
    private String taskStatus;

    @Size(max = 20, message = "Priority cannot exceed 20 characters")
    private String priority;

    private String assignedTo;

    private LocalDateTime assignedAt;

    private LocalDateTime dueDate;

    private LocalDateTime completedAt;

    private String completedBy;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


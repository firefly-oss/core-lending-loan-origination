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


package com.firefly.core.lending.origination.interfaces.dtos.exception.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.utils.annotations.FilterableId;
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
public class ApplicationExceptionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID exceptionId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @Size(max = 50, message = "Exception type cannot exceed 50 characters")
    private String exceptionType;

    @Size(max = 1000, message = "Exception reason cannot exceed 1000 characters")
    private String exceptionReason;

    private String requestedBy;

    private LocalDateTime requestedAt;

    @Size(max = 50, message = "Approval status cannot exceed 50 characters")
    private String approvalStatus;

    private String approvedBy;

    private LocalDateTime approvedAt;

    @Size(max = 1000, message = "Rejection reason cannot exceed 1000 characters")
    private String rejectionReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


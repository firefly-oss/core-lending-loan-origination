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
import com.firefly.core.lending.origination.interfaces.enums.application.v1.ApplicationStatusEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class LoanApplicationStatusHistoryDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID statusHistoryId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @NotNull(message = "Old status is required")
    private ApplicationStatusEnum oldStatus;

    @NotNull(message = "New status is required")
    private ApplicationStatusEnum newStatus;

    @Size(max = 500, message = "Change reason cannot exceed 500 characters")
    private String changeReason;

    @NotNull(message = "Changed at timestamp is required")
    @PastOrPresent(message = "Changed at timestamp cannot be in the future")
    private LocalDateTime changedAt;

    @NotBlank(message = "Changed by is required")
    @Size(max = 100, message = "Changed by cannot exceed 100 characters")
    private String changedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

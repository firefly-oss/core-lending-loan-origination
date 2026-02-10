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
import org.fireflyframework.utils.annotations.FilterableId;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFeeDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID feeId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @NotBlank(message = "Fee type is required")
    @Size(max = 50, message = "Fee type cannot exceed 50 characters")
    private String feeType;

    @Size(max = 200, message = "Fee name cannot exceed 200 characters")
    private String feeName;

    @DecimalMin(value = "0.0", message = "Fee amount must be greater than or equal to 0")
    private BigDecimal feeAmount;

    @DecimalMin(value = "0.0", message = "Fee percentage must be greater than or equal to 0")
    private BigDecimal feePercentage;

    private Boolean isRefundable;

    private Boolean isFinanced;

    @Size(max = 50, message = "Payment status cannot exceed 50 characters")
    private String paymentStatus;

    private LocalDateTime paidAt;

    @Size(max = 1000, message = "Waived reason cannot exceed 1000 characters")
    private String waivedReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


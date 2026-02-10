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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDocumentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID applicationDocumentId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @FilterableId
    @NotNull(message = "Document ID is required")
    private UUID documentId;

    @FilterableId
    @NotNull(message = "Document type ID is required")
    private UUID documentTypeId;

    @NotNull(message = "Mandatory flag is required")
    private Boolean isMandatory;

    @NotNull(message = "Received flag is required")
    private Boolean isReceived;
    private LocalDateTime receivedAt;

    @Size(max = 200, message = "Document name cannot exceed 200 characters")
    private String documentName;

    private Long fileSizeBytes;

    @Size(max = 100, message = "MIME type cannot exceed 100 characters")
    private String mimeType;

    @Size(max = 50, message = "Verification status cannot exceed 50 characters")
    private String verificationStatus;

    @FilterableId
    private UUID verifiedBy;

    private LocalDateTime verifiedAt;

    @Size(max = 500, message = "Rejection reason cannot exceed 500 characters")
    private String rejectionReason;

    private LocalDate expiryDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
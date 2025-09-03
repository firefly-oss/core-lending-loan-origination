package com.firefly.core.lending.origination.interfaces.dtos.document.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotNull;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
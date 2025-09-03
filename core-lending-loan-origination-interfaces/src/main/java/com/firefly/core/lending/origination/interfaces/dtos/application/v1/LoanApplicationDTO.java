package com.firefly.core.lending.origination.interfaces.dtos.application.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.lending.origination.interfaces.enums.application.v1.ApplicationStatusEnum;
import com.firefly.core.lending.origination.interfaces.enums.application.v1.SubmissionChannelEnum;
import com.firefly.core.lending.origination.interfaces.enums.status.v1.ApplicationSubStatusEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class LoanApplicationDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanApplicationId;

    private UUID applicationNumber;

    @NotNull(message = "Application status is required")
    private ApplicationStatusEnum applicationStatus;

    @NotNull(message = "Application sub-status is required")
    private ApplicationSubStatusEnum applicationSubStatus;

    @NotNull(message = "Application date is required")
    @PastOrPresent(message = "Application date cannot be in the future")
    private LocalDate applicationDate;

    @NotNull(message = "Submission channel is required")
    private SubmissionChannelEnum submissionChannel;
    
    /**
     * Identifier for a known customer who launched the application.
     * This field is null for unknown non-customers.
     */
    @FilterableId
    private UUID partyId;
    
    /**
     * Identifier for a known distributor who launched the application.
     * This field is null if not submitted via a distributor.
     */
    @FilterableId
    private UUID distributorId;

    @Size(max = 1000, message = "Note cannot exceed 1000 characters")
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
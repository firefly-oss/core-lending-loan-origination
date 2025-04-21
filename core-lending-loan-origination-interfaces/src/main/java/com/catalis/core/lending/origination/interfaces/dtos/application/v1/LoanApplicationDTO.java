package com.catalis.core.lending.origination.interfaces.dtos.application.v1;

import com.catalis.core.lending.origination.interfaces.enums.application.v1.ApplicationStatusEnum;
import com.catalis.core.lending.origination.interfaces.enums.status.v1.ApplicationSubStatusEnum;
import com.catalis.core.lending.origination.interfaces.enums.application.v1.SubmissionChannelEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long loanApplicationId;

    private String applicationNumber;
    private ApplicationStatusEnum applicationStatus;
    private ApplicationSubStatusEnum applicationSubStatus;
    private LocalDate applicationDate;
    private SubmissionChannelEnum submissionChannel;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
package com.firefly.core.lending.origination.interfaces.dtos.status.v1;

import com.firefly.core.lending.origination.interfaces.enums.application.v1.ApplicationStatusEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationStatusHistoryDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long statusHistoryId;

    @FilterableId
    private Long loanApplicationId;

    private ApplicationStatusEnum oldStatus;
    private ApplicationStatusEnum newStatus;
    private String changeReason;
    private LocalDateTime changedAt;
    private String changedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

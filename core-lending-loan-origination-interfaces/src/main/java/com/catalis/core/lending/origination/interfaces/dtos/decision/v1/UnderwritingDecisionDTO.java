package com.catalis.core.lending.origination.interfaces.dtos.decision.v1;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingDecisionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long underwritingDecisionId;

    @FilterableId
    private Long loanApplicationId;

    private LocalDateTime decisionDate;

    @FilterableId
    private Long decisionCodeId;
    private BigDecimal approvedAmount;
    private BigDecimal approvedInterestRate;
    private Integer tenorMonths;

    @FilterableId
    private Long riskGradeId;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
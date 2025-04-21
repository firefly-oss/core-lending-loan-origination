package com.catalis.core.lending.origination.interfaces.dtos.decision.v1;

import com.catalis.core.lending.origination.interfaces.enums.decision.v1.DecisionCodeEnum;
import com.catalis.core.lending.origination.interfaces.enums.score.v1.RiskGradeEnum;
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
    private DecisionCodeEnum decisionCode;
    private BigDecimal approvedAmount;
    private BigDecimal approvedInterestRate;
    private Integer tenorMonths;
    private RiskGradeEnum riskGrade;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
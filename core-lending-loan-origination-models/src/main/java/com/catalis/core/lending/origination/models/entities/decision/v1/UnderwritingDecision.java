package com.catalis.core.lending.origination.models.entities.decision.v1;

import com.catalis.core.lending.origination.interfaces.enums.decision.v1.DecisionCodeEnum;
import com.catalis.core.lending.origination.interfaces.enums.score.v1.RiskGradeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("underwriting_decision")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingDecision {
    @Id
    @Column("underwriting_decision_id")
    private Long underwritingDecisionId;

    @Column("loan_application_id")
    private Long loanApplicationId;

    @Column("decision_date")
    private LocalDateTime decisionDate;

    @Column("decision_code")
    private DecisionCodeEnum decisionCode;

    @Column("approved_amount")
    private BigDecimal approvedAmount;

    @Column("approved_interest_rate")
    private BigDecimal approvedInterestRate;

    @Column("tenor_months")
    private Integer tenorMonths;

    @Column("risk_grade")
    private RiskGradeEnum riskGrade;

    @Column("remarks")
    private String remarks;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
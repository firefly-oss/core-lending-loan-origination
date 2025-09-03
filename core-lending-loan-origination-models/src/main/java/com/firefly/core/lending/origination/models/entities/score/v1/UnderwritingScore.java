package com.firefly.core.lending.origination.models.entities.score.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("underwriting_score")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingScore {
    @Id
    @Column("underwriting_score_id")
    private UUID underwritingScoreId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("score_value")
    private BigDecimal scoreValue;

    @Column("scorecard_name")
    private String scorecardName;

    @Column("reason_codes")
    private String reasonCodes; // JSON or text

    @Column("scoring_timestamp")
    private LocalDateTime scoringTimestamp;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
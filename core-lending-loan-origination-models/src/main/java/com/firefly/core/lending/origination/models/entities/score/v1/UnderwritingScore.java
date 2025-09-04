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
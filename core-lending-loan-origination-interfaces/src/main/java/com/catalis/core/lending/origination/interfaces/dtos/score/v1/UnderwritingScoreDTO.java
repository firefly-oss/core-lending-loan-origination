package com.catalis.core.lending.origination.interfaces.dtos.score.v1;

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
public class UnderwritingScoreDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long underwritingScoreId;

    @FilterableId
    private Long loanApplicationId;

    private BigDecimal scoreValue;
    private String scorecardName;
    private String reasonCodes; // JSON or textual reasons
    private LocalDateTime scoringTimestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
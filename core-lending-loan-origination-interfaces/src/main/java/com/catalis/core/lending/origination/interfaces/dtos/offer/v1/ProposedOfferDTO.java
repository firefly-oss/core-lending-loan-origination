package com.catalis.core.lending.origination.interfaces.dtos.offer.v1;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposedOfferDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long proposedOfferId;

    @FilterableId
    private Long loanApplicationId;

    @FilterableId
    private Long productId;

    private BigDecimal requestedAmount;
    private Integer requestedTenorMonths;
    private BigDecimal requestedInterestRate;
    private LocalDate validUntil;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
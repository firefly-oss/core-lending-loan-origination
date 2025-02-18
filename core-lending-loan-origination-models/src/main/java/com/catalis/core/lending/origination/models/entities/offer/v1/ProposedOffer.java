package com.catalis.core.lending.origination.models.entities.offer.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("proposed_offer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposedOffer {
    @Id
    @Column("proposed_offer_id")
    private Long proposedOfferId;

    @Column("loan_application_id")
    private Long loanApplicationId;

    @Column("product_id")
    private Long productId;

    @Column("requested_amount")
    private BigDecimal requestedAmount;

    @Column("requested_tenor_months")
    private Integer requestedTenorMonths;

    @Column("requested_interest_rate")
    private BigDecimal requestedInterestRate;

    @Column("valid_until")
    private LocalDate validUntil;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}


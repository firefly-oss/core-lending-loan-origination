package com.catalis.core.lending.origination.interfaces.dtos.party.v1;

import com.catalis.core.lending.origination.interfaces.enums.party.v1.EmploymentTypeEnum;
import com.catalis.core.lending.origination.interfaces.enums.score.v1.RoleCodeEnum;
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
public class ApplicationPartyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long applicationPartyId;

    @FilterableId
    private Long loanApplicationId;

    @FilterableId
    private Long partyId;

    private RoleCodeEnum roleCode;
    private BigDecimal sharePercentage;
    private BigDecimal annualIncome;
    private BigDecimal monthlyExpenses;
    private EmploymentTypeEnum employmentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
package com.firefly.core.lending.origination.interfaces.dtos.collateral.v1;

import com.firefly.core.lending.origination.interfaces.enums.collateral.v1.CollateralTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
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
public class ApplicationCollateralDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long applicationCollateralId;

    @FilterableId
    private Long loanApplicationId;

    private CollateralTypeEnum collateralType;
    private BigDecimal estimatedValue;
    private String ownershipDetails;
    private Boolean isPrimaryCollateral;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
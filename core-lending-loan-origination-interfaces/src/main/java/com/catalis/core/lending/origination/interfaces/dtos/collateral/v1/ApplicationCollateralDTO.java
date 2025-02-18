package com.catalis.core.lending.origination.interfaces.dtos.collateral.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.lending.origination.interfaces.enums.collateral.v1.CollateralTypeEnum;
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
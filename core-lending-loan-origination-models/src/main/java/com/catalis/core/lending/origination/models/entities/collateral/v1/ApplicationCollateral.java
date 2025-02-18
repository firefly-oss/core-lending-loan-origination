package com.catalis.core.lending.origination.models.entities.collateral.v1;

import com.catalis.core.lending.origination.interfaces.enums.collateral.v1.CollateralTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("application_collateral")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCollateral {
    @Id
    @Column("application_collateral_id")
    private Long applicationCollateralId;

    @Column("loan_application_id")
    private Long loanApplicationId;

    @Column("collateral_type")
    private CollateralTypeEnum collateralType;

    @Column("estimated_value")
    private BigDecimal estimatedValue;

    @Column("ownership_details")
    private String ownershipDetails;

    @Column("is_primary_collateral")
    private Boolean isPrimaryCollateral;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
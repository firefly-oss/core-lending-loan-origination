package com.firefly.core.lending.origination.core.mappers.collateral.v1;

import com.firefly.core.lending.origination.interfaces.dtos.collateral.v1.ApplicationCollateralDTO;
import com.firefly.core.lending.origination.models.entities.collateral.v1.ApplicationCollateral;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationCollateralMapper {
    ApplicationCollateralDTO toDTO(ApplicationCollateral entity);
    ApplicationCollateral toEntity(ApplicationCollateralDTO dto);
}

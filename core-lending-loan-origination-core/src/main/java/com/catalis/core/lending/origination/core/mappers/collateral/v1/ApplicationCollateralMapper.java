package com.catalis.core.lending.origination.core.mappers.collateral.v1;

import com.catalis.core.lending.origination.interfaces.dtos.collateral.v1.ApplicationCollateralDTO;
import com.catalis.core.lending.origination.models.entities.collateral.v1.ApplicationCollateral;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationCollateralMapper {
    ApplicationCollateralDTO toDTO(ApplicationCollateral entity);
    ApplicationCollateral toEntity(ApplicationCollateralDTO dto);
}

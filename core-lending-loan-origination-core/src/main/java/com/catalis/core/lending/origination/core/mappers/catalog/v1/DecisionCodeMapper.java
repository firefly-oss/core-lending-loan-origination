package com.catalis.core.lending.origination.core.mappers.catalog.v1;

import com.catalis.core.lending.origination.interfaces.dtos.catalog.v1.DecisionCodeDTO;
import com.catalis.core.lending.origination.models.entities.catalog.v1.DecisionCode;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DecisionCodeMapper {

    DecisionCodeDTO toDto(DecisionCode entity);

    DecisionCode toEntity(DecisionCodeDTO dto);
}
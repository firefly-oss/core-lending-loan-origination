package com.catalis.core.lending.origination.core.mappers.catalog.v1;

import com.catalis.core.lending.origination.interfaces.dtos.catalog.v1.DocumentTypeDTO;
import com.catalis.core.lending.origination.models.entities.catalog.v1.DocumentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DocumentTypeMapper {

    DocumentTypeDTO toDto(DocumentType entity);

    DocumentType toEntity(DocumentTypeDTO dto);
}
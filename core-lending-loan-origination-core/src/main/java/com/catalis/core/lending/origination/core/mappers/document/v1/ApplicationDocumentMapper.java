package com.catalis.core.lending.origination.core.mappers.document.v1;

import com.catalis.core.lending.origination.interfaces.dtos.document.v1.ApplicationDocumentDTO;
import com.catalis.core.lending.origination.models.entities.document.v1.ApplicationDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ApplicationDocumentMapper {
    
    @Mapping(target = "documentTypeId", expression = "java(entity.getDocumentType() != null ? Long.valueOf(entity.getDocumentType().ordinal()) : null)")
    ApplicationDocumentDTO toDTO(ApplicationDocument entity);
    
    @Mapping(target = "documentType", expression = "java(dto.getDocumentTypeId() != null ? com.catalis.core.lending.origination.interfaces.enums.document.v1.DocumentTypeEnum.values()[dto.getDocumentTypeId().intValue()] : null)")
    ApplicationDocument toEntity(ApplicationDocumentDTO dto);
}

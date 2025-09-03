package com.firefly.core.lending.origination.core.mappers.document.v1;

import com.firefly.core.lending.origination.interfaces.dtos.document.v1.ApplicationDocumentDTO;
import com.firefly.core.lending.origination.models.entities.document.v1.ApplicationDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ApplicationDocumentMapper {
    
    ApplicationDocumentDTO toDTO(ApplicationDocument entity);
    
    ApplicationDocument toEntity(ApplicationDocumentDTO dto);
}

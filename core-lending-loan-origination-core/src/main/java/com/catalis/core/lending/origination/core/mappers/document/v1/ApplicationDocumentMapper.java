package com.catalis.core.lending.origination.core.mappers.document.v1;

import com.catalis.core.lending.origination.interfaces.dtos.document.v1.ApplicationDocumentDTO;
import com.catalis.core.lending.origination.models.entities.document.v1.ApplicationDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationDocumentMapper {
    ApplicationDocumentDTO toDTO(ApplicationDocument entity);
    ApplicationDocument toEntity(ApplicationDocumentDTO dto);
}

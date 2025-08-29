package com.firefly.core.lending.origination.core.mappers.application.v1;

import com.firefly.core.lending.origination.interfaces.dtos.application.v1.LoanApplicationDTO;
import com.firefly.core.lending.origination.models.entities.application.v1.LoanApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {
    LoanApplicationDTO toDTO(LoanApplication entity);
    LoanApplication toEntity(LoanApplicationDTO dto);
}

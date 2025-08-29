package com.firefly.core.lending.origination.core.mappers.status.v1;

import com.firefly.core.lending.origination.interfaces.dtos.status.v1.LoanApplicationStatusHistoryDTO;
import com.firefly.core.lending.origination.models.entities.status.v1.LoanApplicationStatusHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationStatusHistoryMapper {
    LoanApplicationStatusHistoryDTO toDTO(LoanApplicationStatusHistory entity);
    LoanApplicationStatusHistory toEntity(LoanApplicationStatusHistoryDTO dto);
}

package com.catalis.core.lending.origination.core.mappers.status.v1;

import com.catalis.core.lending.origination.interfaces.dtos.status.v1.LoanApplicationStatusHistoryDTO;
import com.catalis.core.lending.origination.models.entities.status.v1.LoanApplicationStatusHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationStatusHistoryMapper {
    LoanApplicationStatusHistoryDTO toDTO(LoanApplicationStatusHistory entity);
    LoanApplicationStatusHistory toEntity(LoanApplicationStatusHistoryDTO dto);
}

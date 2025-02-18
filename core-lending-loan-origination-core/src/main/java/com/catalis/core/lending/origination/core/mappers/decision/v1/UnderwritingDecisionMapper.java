package com.catalis.core.lending.origination.core.mappers.decision.v1;

import com.catalis.core.lending.origination.interfaces.dtos.decision.v1.UnderwritingDecisionDTO;
import com.catalis.core.lending.origination.models.entities.decision.v1.UnderwritingDecision;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnderwritingDecisionMapper {
    UnderwritingDecisionDTO toDTO(UnderwritingDecision entity);
    UnderwritingDecision toEntity(UnderwritingDecisionDTO dto);
}

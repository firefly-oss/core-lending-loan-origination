package com.catalis.core.lending.origination.core.mappers.decision.v1;

import com.catalis.core.lending.origination.interfaces.dtos.decision.v1.UnderwritingDecisionDTO;
import com.catalis.core.lending.origination.models.entities.decision.v1.UnderwritingDecision;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UnderwritingDecisionMapper {
    
    @Mapping(target = "decisionCodeId", source = "decisionCodeId")
    @Mapping(target = "riskGradeId", source = "riskGradeId")
    UnderwritingDecisionDTO toDTO(UnderwritingDecision entity);
    
    @Mapping(target = "decisionCode", ignore = true)
    @Mapping(target = "riskGrade", ignore = true)
    UnderwritingDecision toEntity(UnderwritingDecisionDTO dto);
}

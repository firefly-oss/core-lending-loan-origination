package com.firefly.core.lending.origination.core.mappers.score.v1;

import com.firefly.core.lending.origination.interfaces.dtos.score.v1.UnderwritingScoreDTO;
import com.firefly.core.lending.origination.models.entities.score.v1.UnderwritingScore;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnderwritingScoreMapper {
    UnderwritingScoreDTO toDTO(UnderwritingScore entity);
    UnderwritingScore toEntity(UnderwritingScoreDTO dto);
}
/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.origination.core.mappers.application.v1;

import com.firefly.core.lending.origination.interfaces.dtos.application.v1.LoanApplicationDTO;
import com.firefly.core.lending.origination.models.entities.application.v1.LoanApplication;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LoanApplicationMapper {
    
    @Mapping(target = "applicationSubStatusId", source = "applicationSubStatusId")
    LoanApplicationDTO toDTO(LoanApplication entity);
    
    LoanApplication toEntity(LoanApplicationDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LoanApplicationDTO dto, @MappingTarget LoanApplication entity);

}

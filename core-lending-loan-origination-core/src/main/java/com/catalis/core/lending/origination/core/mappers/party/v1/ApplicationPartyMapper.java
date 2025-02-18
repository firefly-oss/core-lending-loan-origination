package com.catalis.core.lending.origination.core.mappers.party.v1;

import com.catalis.core.lending.origination.interfaces.dtos.party.v1.ApplicationPartyDTO;
import com.catalis.core.lending.origination.models.entities.party.v1.ApplicationParty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationPartyMapper {
    ApplicationPartyDTO toDTO(ApplicationParty entity);
    ApplicationParty toEntity(ApplicationPartyDTO dto);
}

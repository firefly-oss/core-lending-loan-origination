package com.catalis.core.lending.origination.core.mappers.offer.v1;

import com.catalis.core.lending.origination.interfaces.dtos.offer.v1.ProposedOfferDTO;
import com.catalis.core.lending.origination.models.entities.offer.v1.ProposedOffer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProposedOfferMapper {
    ProposedOfferDTO toDTO(ProposedOffer entity);
    ProposedOffer toEntity(ProposedOfferDTO dto);
}

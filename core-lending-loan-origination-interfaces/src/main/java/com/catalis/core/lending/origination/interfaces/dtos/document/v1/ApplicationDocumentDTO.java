package com.catalis.core.lending.origination.interfaces.dtos.document.v1;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDocumentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long applicationDocumentId;

    @FilterableId
    private Long loanApplicationId;

    @FilterableId
    private Long documentId;

    @FilterableId
    private Long documentTypeId;

    private Boolean isMandatory;
    private Boolean isReceived;
    private LocalDateTime receivedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
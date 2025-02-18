package com.catalis.core.lending.origination.interfaces.dtos.document.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.lending.origination.interfaces.enums.document.v1.DocumentTypeEnum;
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
    private Long applicationDocumentId;

    @FilterableId
    private Long loanApplicationId;

    @FilterableId
    private Long documentId;

    private DocumentTypeEnum documentType;
    private Boolean isMandatory;
    private Boolean isReceived;
    private LocalDateTime receivedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

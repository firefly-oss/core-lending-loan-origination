package com.firefly.core.lending.origination.models.entities.document.v1;

import com.firefly.core.lending.origination.interfaces.enums.document.v1.DocumentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("application_document")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDocument {
    @Id
    @Column("application_document_id")
    private UUID applicationDocumentId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("document_id")
    private UUID documentId;

    @Column("document_type")
    private DocumentTypeEnum documentType;

    @Column("is_mandatory")
    private Boolean isMandatory;

    @Column("is_received")
    private Boolean isReceived;

    @Column("received_at")
    private LocalDateTime receivedAt;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}


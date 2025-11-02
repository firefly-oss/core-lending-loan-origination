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


package com.firefly.core.lending.origination.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
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

    @Column("document_type_id")
    private UUID documentTypeId;

    @Column("is_mandatory")
    private Boolean isMandatory;

    @Column("is_received")
    private Boolean isReceived;

    @Column("received_at")
    private LocalDateTime receivedAt;

    /**
     * Name of the document.
     */
    @Column("document_name")
    private String documentName;

    /**
     * Size of the document file in bytes.
     */
    @Column("file_size_bytes")
    private Long fileSizeBytes;

    /**
     * MIME type of the document.
     */
    @Column("mime_type")
    private String mimeType;

    /**
     * Verification status of the document.
     */
    @Column("verification_status")
    private String verificationStatus; // PENDING, VERIFIED, REJECTED

    /**
     * User who verified the document.
     */
    @Column("verified_by")
    private String verifiedBy;

    /**
     * Timestamp when the document was verified.
     */
    @Column("verified_at")
    private LocalDateTime verifiedAt;

    /**
     * Reason for document rejection.
     */
    @Column("rejection_reason")
    private String rejectionReason;

    /**
     * Expiry date for documents that expire (e.g., ID, insurance).
     */
    @Column("expiry_date")
    private LocalDate expiryDate;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}


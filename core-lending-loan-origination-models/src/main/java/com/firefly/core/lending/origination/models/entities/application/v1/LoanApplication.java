package com.firefly.core.lending.origination.models.entities.application.v1;

import com.firefly.core.lending.origination.interfaces.enums.application.v1.ApplicationStatusEnum;
import com.firefly.core.lending.origination.interfaces.enums.application.v1.SubmissionChannelEnum;
import com.firefly.core.lending.origination.interfaces.enums.status.v1.ApplicationSubStatusEnum;
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

@Table("loan_application")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    @Id
    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("application_number")
    private UUID applicationNumber;

    @Column("application_status")
    private ApplicationStatusEnum applicationStatus;

    @Column("application_sub_status")
    private ApplicationSubStatusEnum applicationSubStatus;

    @Column("application_date")
    private LocalDate applicationDate;

    @Column("submission_channel")
    private SubmissionChannelEnum submissionChannel;
    
    /**
     * Identifier for a known customer who launched the application.
     * This field is null for unknown non-customers.
     */
    @Column("party_id")
    private UUID partyId;
    
    /**
     * Identifier for a known distributor who launched the application.
     * This field is null if not submitted via a distributor.
     */
    @Column("distributor_id")
    private UUID distributorId;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

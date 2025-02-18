package com.catalis.core.lending.origination.models.entities.application.v1;

import com.catalis.core.lending.origination.interfaces.enums.application.v1.ApplicationStatusEnum;
import com.catalis.core.lending.origination.interfaces.enums.status.v1.ApplicationSubStatusEnum;
import com.catalis.core.lending.origination.interfaces.enums.application.v1.SubmissionChannelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("loan_application")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    @Id
    @Column("loan_application_id")
    private Long loanApplicationId;

    @Column("application_number")
    private String applicationNumber;

    @Column("application_status")
    private ApplicationStatusEnum applicationStatus;

    @Column("application_sub_status")
    private ApplicationSubStatusEnum applicationSubStatus;

    @Column("application_date")
    private LocalDate applicationDate;

    @Column("submission_channel")
    private SubmissionChannelEnum submissionChannel;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

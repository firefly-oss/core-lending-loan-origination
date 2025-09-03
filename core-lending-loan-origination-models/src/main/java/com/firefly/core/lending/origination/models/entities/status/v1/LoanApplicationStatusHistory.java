package com.firefly.core.lending.origination.models.entities.status.v1;

import com.firefly.core.lending.origination.interfaces.enums.application.v1.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("loan_application_status_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationStatusHistory {
    @Id
    @Column("status_history_id")
    private UUID statusHistoryId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("old_status")
    private ApplicationStatusEnum oldStatus;

    @Column("new_status")
    private ApplicationStatusEnum newStatus;

    @Column("change_reason")
    private String changeReason;

    @Column("changed_at")
    private LocalDateTime changedAt;

    @Column("changed_by")
    private String changedBy;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
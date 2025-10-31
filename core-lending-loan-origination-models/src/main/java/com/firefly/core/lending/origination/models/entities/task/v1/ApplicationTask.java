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


package com.firefly.core.lending.origination.models.entities.task.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("application_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationTask {
    @Id
    @Column("task_id")
    private UUID taskId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("task_type")
    private String taskType; // REVIEW_DOCUMENTS, VERIFY_INCOME, CALL_APPLICANT, etc.

    @Column("task_status")
    private String taskStatus; // PENDING, IN_PROGRESS, COMPLETED, CANCELLED

    @Column("priority")
    private String priority; // LOW, MEDIUM, HIGH, URGENT

    @Column("assigned_to")
    private String assignedTo;

    @Column("assigned_at")
    private LocalDateTime assignedAt;

    @Column("due_date")
    private LocalDateTime dueDate;

    @Column("completed_at")
    private LocalDateTime completedAt;

    @Column("completed_by")
    private String completedBy;

    @Column("description")
    private String description;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}


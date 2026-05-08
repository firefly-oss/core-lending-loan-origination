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


package com.firefly.core.lending.origination.interfaces.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Partial-update payload for the 12 BE-4 economic / employment fields on an application party.
 *
 * <p>All fields are optional. When a field is {@code null} on the request,
 * the corresponding column on the entity is left untouched.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Partial-update payload for the 12 economic and employment fields on an application party.")
public class EmploymentDataPatchDTO {

    @Size(max = 50)
    @Schema(description = "Employment status", example = "EMPLOYED")
    private String employmentStatus;

    @Size(max = 50)
    @Schema(description = "Free-form employment type label sent by the front-end.")
    private String employmentTypeLabel;

    @Size(max = 255)
    @Schema(description = "Employer name")
    private String employer;

    @Size(max = 255)
    @Schema(description = "Job position / title")
    private String position;

    @Schema(description = "Date when current employment started")
    private LocalDate employmentStartDate;

    @PositiveOrZero(message = "Annual paydays must be non-negative")
    @Schema(description = "Number of paydays per year", example = "14")
    private Short annualPaydays;

    @PositiveOrZero(message = "Monthly salary must be non-negative")
    @Schema(description = "Net monthly salary")
    private BigDecimal monthlySalary;

    @Size(max = 20)
    @Schema(description = "Housing situation", example = "OWNED")
    private String housingType;

    @PositiveOrZero(message = "Housing cost must be non-negative")
    @Schema(description = "Monthly housing cost (rent or mortgage payment)")
    private BigDecimal housingCost;

    @Schema(description = "Date when current housing situation started")
    private LocalDate housingStartDate;

    @PositiveOrZero(message = "Existing loans count must be non-negative")
    @Schema(description = "Number of currently active loans")
    private Short existingLoans;

    @PositiveOrZero(message = "Other debts must be non-negative")
    @Schema(description = "Total monthly amount of other recurring debts")
    private BigDecimal otherDebts;
}

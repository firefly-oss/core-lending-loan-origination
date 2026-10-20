/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.lending.origination.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fireflyframework.utils.annotations.FilterableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPartyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID applicationPartyId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @FilterableId
    @NotNull(message = "Role code ID is required")
    private UUID roleCodeId;

    @DecimalMin(value = "0.0", inclusive = true, message = "Share percentage must be non-negative")
    @DecimalMax(value = "100.0", inclusive = true, message = "Share percentage cannot exceed 100%")
    private BigDecimal sharePercentage;

    @PositiveOrZero(message = "Annual income must be non-negative")
    private BigDecimal annualIncome;

    @PositiveOrZero(message = "Monthly expenses must be non-negative")
    private BigDecimal monthlyExpenses;

    @FilterableId
    @NotNull(message = "Employment type ID is required")
    private UUID employmentTypeId;

    // -----------------------------------------------------------------
    // BE-4 economic / employment data (V15)
    // -----------------------------------------------------------------

    @Size(max = 50)
    @Schema(description = "Employment status", example = "EMPLOYED")
    private String employmentStatus;

    @Size(max = 50)
    @Schema(description = "Free-form employment type label sent by the front-end. " +
            "Distinct from the catalog FK employmentTypeId.")
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
    @Schema(description = "Number of paydays per year (typically 12 or 14)", example = "14")
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

    @Schema(description = "TRUE when this party is the primary applicant for the loan application. " +
            "Enforced unique per loan_application_id by ux_application_party_primary.")
    private Boolean isPrimary;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

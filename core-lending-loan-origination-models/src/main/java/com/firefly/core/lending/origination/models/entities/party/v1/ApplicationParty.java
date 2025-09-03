package com.firefly.core.lending.origination.models.entities.party.v1;

import com.firefly.core.lending.origination.interfaces.enums.party.v1.EmploymentTypeEnum;
import com.firefly.core.lending.origination.interfaces.enums.score.v1.RoleCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("application_party")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationParty {
    @Id
    @Column("application_party_id")
    private UUID applicationPartyId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("party_id")
    private UUID partyId;

    @Column("role_code")
    private RoleCodeEnum roleCode;

    @Column("share_percentage")
    private BigDecimal sharePercentage;

    @Column("annual_income")
    private BigDecimal annualIncome;

    @Column("monthly_expenses")
    private BigDecimal monthlyExpenses;

    @Column("employment_type")
    private EmploymentTypeEnum employmentType;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

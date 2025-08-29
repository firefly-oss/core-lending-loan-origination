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

@Table("application_party")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationParty {
    @Id
    @Column("application_party_id")
    private Long applicationPartyId;

    @Column("loan_application_id")
    private Long loanApplicationId;

    @Column("party_id")
    private Long partyId;

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

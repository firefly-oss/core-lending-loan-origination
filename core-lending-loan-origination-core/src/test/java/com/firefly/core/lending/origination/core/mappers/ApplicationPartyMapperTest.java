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


package com.firefly.core.lending.origination.core.mappers;

import com.firefly.core.lending.origination.interfaces.dtos.ApplicationPartyDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationParty;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Round-trip test for the BE-4 economic / employment fields on {@link ApplicationPartyMapper}.
 */
class ApplicationPartyMapperTest {

    private final ApplicationPartyMapper mapper = Mappers.getMapper(ApplicationPartyMapper.class);

    @Test
    void roundTrips_allTwelveBe4Fields() {
        ApplicationPartyDTO source = ApplicationPartyDTO.builder()
                .applicationPartyId(UUID.randomUUID())
                .loanApplicationId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .roleCodeId(UUID.randomUUID())
                .employmentTypeId(UUID.randomUUID())
                .employmentStatus("EMPLOYED")
                .employmentTypeLabel("PERMANENT_CONTRACT")
                .employer("Acme Corp")
                .position("Lead Engineer")
                .employmentStartDate(LocalDate.of(2018, 3, 15))
                .annualPaydays((short) 14)
                .monthlySalary(new BigDecimal("3500.0000"))
                .housingType("OWNED")
                .housingCost(new BigDecimal("900.0000"))
                .housingStartDate(LocalDate.of(2020, 5, 1))
                .existingLoans((short) 2)
                .otherDebts(new BigDecimal("250.0000"))
                .isPrimary(Boolean.TRUE)
                .build();

        ApplicationParty entity = mapper.toEntity(source);
        ApplicationPartyDTO back = mapper.toDTO(entity);

        // The 12 BE-4 fields
        assertThat(entity.getEmploymentStatus()).isEqualTo("EMPLOYED");
        assertThat(entity.getEmploymentTypeLabel()).isEqualTo("PERMANENT_CONTRACT");
        assertThat(entity.getEmployer()).isEqualTo("Acme Corp");
        assertThat(entity.getPosition()).isEqualTo("Lead Engineer");
        assertThat(entity.getEmploymentStartDate()).isEqualTo(LocalDate.of(2018, 3, 15));
        assertThat(entity.getAnnualPaydays()).isEqualTo((short) 14);
        assertThat(entity.getMonthlySalary()).isEqualByComparingTo(new BigDecimal("3500.0000"));
        assertThat(entity.getHousingType()).isEqualTo("OWNED");
        assertThat(entity.getHousingCost()).isEqualByComparingTo(new BigDecimal("900.0000"));
        assertThat(entity.getHousingStartDate()).isEqualTo(LocalDate.of(2020, 5, 1));
        assertThat(entity.getExistingLoans()).isEqualTo((short) 2);
        assertThat(entity.getOtherDebts()).isEqualByComparingTo(new BigDecimal("250.0000"));
        // is_primary flag also round-trips
        assertThat(entity.getIsPrimary()).isTrue();

        // back to DTO preserves everything
        assertThat(back.getEmploymentStatus()).isEqualTo(source.getEmploymentStatus());
        assertThat(back.getEmploymentTypeLabel()).isEqualTo(source.getEmploymentTypeLabel());
        assertThat(back.getEmployer()).isEqualTo(source.getEmployer());
        assertThat(back.getPosition()).isEqualTo(source.getPosition());
        assertThat(back.getEmploymentStartDate()).isEqualTo(source.getEmploymentStartDate());
        assertThat(back.getAnnualPaydays()).isEqualTo(source.getAnnualPaydays());
        assertThat(back.getMonthlySalary()).isEqualByComparingTo(source.getMonthlySalary());
        assertThat(back.getHousingType()).isEqualTo(source.getHousingType());
        assertThat(back.getHousingCost()).isEqualByComparingTo(source.getHousingCost());
        assertThat(back.getHousingStartDate()).isEqualTo(source.getHousingStartDate());
        assertThat(back.getExistingLoans()).isEqualTo(source.getExistingLoans());
        assertThat(back.getOtherDebts()).isEqualByComparingTo(source.getOtherDebts());
        assertThat(back.getIsPrimary()).isEqualTo(source.getIsPrimary());
    }
}

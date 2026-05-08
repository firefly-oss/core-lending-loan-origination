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


package com.firefly.core.lending.origination.core.services;

import com.firefly.core.lending.origination.core.mappers.ApplicationPartyMapper;
import com.firefly.core.lending.origination.core.services.impl.ApplicationPartyServiceImpl;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationPartyDTO;
import com.firefly.core.lending.origination.interfaces.dtos.EmploymentDataPatchDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationParty;
import com.firefly.core.lending.origination.models.repositories.ApplicationPartyRepository;
import org.fireflyframework.web.error.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationPartyServiceImplEmploymentTest {

    @Mock
    private ApplicationPartyRepository repository;

    @Mock
    private ApplicationPartyMapper mapper;

    @InjectMocks
    private ApplicationPartyServiceImpl service;

    private ApplicationParty entity;
    private ApplicationPartyDTO dto;

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        entity = ApplicationParty.builder()
                .applicationPartyId(id)
                .loanApplicationId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .roleCodeId(UUID.randomUUID())
                .employmentTypeId(UUID.randomUUID())
                .annualIncome(new BigDecimal("50000"))
                .monthlyExpenses(new BigDecimal("1500"))
                .employmentStatus("EMPLOYED")
                .employer("OldCorp")
                .position("Old Position")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        dto = ApplicationPartyDTO.builder()
                .applicationPartyId(id)
                .loanApplicationId(entity.getLoanApplicationId())
                .partyId(entity.getPartyId())
                .roleCodeId(entity.getRoleCodeId())
                .employmentTypeId(entity.getEmploymentTypeId())
                .build();
    }

    @Test
    void updateEmploymentData_appliesOnlyNonNullFields() {
        EmploymentDataPatchDTO patch = EmploymentDataPatchDTO.builder()
                .employer("NewCorp")
                .position("Senior Engineer")
                .monthlySalary(new BigDecimal("3500.0000"))
                .annualPaydays((short) 14)
                .housingType("OWNED")
                .housingCost(new BigDecimal("900.0000"))
                .housingStartDate(LocalDate.of(2020, 5, 1))
                .existingLoans((short) 1)
                .otherDebts(new BigDecimal("150.0000"))
                .employmentStartDate(LocalDate.of(2018, 3, 15))
                .employmentTypeLabel("PERMANENT_CONTRACT")
                // employmentStatus left null on purpose -> must not overwrite "EMPLOYED"
                .build();

        when(repository.findById(entity.getApplicationPartyId())).thenReturn(Mono.just(entity));
        when(repository.save(any(ApplicationParty.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(mapper.toDTO(any(ApplicationParty.class))).thenReturn(dto);

        StepVerifier.create(service.updateEmploymentData(entity.getApplicationPartyId(), patch))
                .expectNext(dto)
                .verifyComplete();

        ArgumentCaptor<ApplicationParty> captor = ArgumentCaptor.forClass(ApplicationParty.class);
        org.mockito.Mockito.verify(repository).save(captor.capture());
        ApplicationParty saved = captor.getValue();

        // changed
        assertThat(saved.getEmployer()).isEqualTo("NewCorp");
        assertThat(saved.getPosition()).isEqualTo("Senior Engineer");
        assertThat(saved.getMonthlySalary()).isEqualByComparingTo(new BigDecimal("3500.0000"));
        assertThat(saved.getAnnualPaydays()).isEqualTo((short) 14);
        assertThat(saved.getHousingType()).isEqualTo("OWNED");
        assertThat(saved.getHousingCost()).isEqualByComparingTo(new BigDecimal("900.0000"));
        assertThat(saved.getHousingStartDate()).isEqualTo(LocalDate.of(2020, 5, 1));
        assertThat(saved.getExistingLoans()).isEqualTo((short) 1);
        assertThat(saved.getOtherDebts()).isEqualByComparingTo(new BigDecimal("150.0000"));
        assertThat(saved.getEmploymentStartDate()).isEqualTo(LocalDate.of(2018, 3, 15));
        assertThat(saved.getEmploymentTypeLabel()).isEqualTo("PERMANENT_CONTRACT");

        // preserved (null on patch -> untouched)
        assertThat(saved.getEmploymentStatus()).isEqualTo("EMPLOYED");
        // unrelated fields untouched
        assertThat(saved.getAnnualIncome()).isEqualByComparingTo(new BigDecimal("50000"));
    }

    @Test
    void updateEmploymentData_errorsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(service.updateEmploymentData(id, EmploymentDataPatchDTO.builder().build()))
                .expectErrorSatisfies(err -> {
                    assertThat(err).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) err;
                    assertThat(be.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(be.getCode()).isEqualTo("APPLICATION_PARTY_NOT_FOUND");
                })
                .verify();
    }

    @Test
    void findPrimaryByApplicationId_returnsMappedDto() {
        UUID applicationId = UUID.randomUUID();
        when(repository.findFirstByLoanApplicationIdAndIsPrimaryTrue(applicationId))
                .thenReturn(Mono.just(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        StepVerifier.create(service.findPrimaryByApplicationId(applicationId))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    void findPrimaryByApplicationId_emptyWhenNoPrimary() {
        UUID applicationId = UUID.randomUUID();
        when(repository.findFirstByLoanApplicationIdAndIsPrimaryTrue(applicationId))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.findPrimaryByApplicationId(applicationId))
                .verifyComplete();
    }
}

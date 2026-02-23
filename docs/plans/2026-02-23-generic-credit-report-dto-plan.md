# Generic CreditReportDTO Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Replace the Equifax-specific CreditReportDTO with a provider-agnostic, individual/business credit report DTO using composition pattern.

**Architecture:** The CreditReportDTO becomes the canonical output format for all credit bureau enrichers. It uses composition via inner classes (ProviderInfoDTO, SubjectInfoDTO, ScoreDTO, AlertDTO, ReportSummaryDTO) with a SubjectTypeEnum discriminator for individual vs business subjects. The EquifaxEnricher's mapToTarget() is updated to populate the new structure.

**Tech Stack:** Java 25, Lombok, Jackson, JUnit 5, Mockito, Spring WebFlux (reactive)

**Design doc:** `docs/plans/2026-02-23-generic-credit-report-dto-design.md`

---

### Task 1: Create SubjectTypeEnum

**Files:**
- Create: `core-data-credit-bureaus/core-data-credit-bureaus-infra/src/main/java/com/firefly/data/credit/bureaus/infra/dtos/SubjectTypeEnum.java`

**Step 1: Create the enum**

```java
package com.firefly.data.credit.bureaus.infra.dtos;

public enum SubjectTypeEnum {
    INDIVIDUAL,
    BUSINESS
}
```

**Step 2: Commit**

```bash
git add core-data-credit-bureaus/core-data-credit-bureaus-infra/src/main/java/com/firefly/data/credit/bureaus/infra/dtos/SubjectTypeEnum.java
git commit -m "feat: add SubjectTypeEnum for individual/business discrimination"
```

---

### Task 2: Rewrite CreditReportDTO with composition pattern

**Files:**
- Modify: `core-data-credit-bureaus/core-data-credit-bureaus-infra/src/main/java/com/firefly/data/credit/bureaus/infra/dtos/CreditReportDTO.java` (full rewrite)

**Step 1: Replace CreditReportDTO with the new composition structure**

```java
package com.firefly.data.credit.bureaus.infra.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditReportDTO {

    private String status;
    private ProviderInfoDTO provider;
    private SubjectInfoDTO subject;
    private List<ScoreDTO> scores;
    private List<AlertDTO> alerts;
    private ReportSummaryDTO summary;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProviderInfoDTO {
        private String providerName;
        private String reportType;
        private String reportId;
        private String reportDate;
        private String referenceNumber;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubjectInfoDTO {
        private SubjectTypeEnum subjectType;

        // Common
        private String fullName;
        private String taxId;

        // Individual-specific (null when subjectType = BUSINESS)
        private String firstName;
        private String lastName;
        private String middleName;
        private String dateOfBirth;

        // Business-specific (null when subjectType = INDIVIDUAL)
        private String legalName;
        private String tradeName;
        private String registrationNumber;
        private String incorporationDate;
        private String industryDescription;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScoreDTO {
        private String modelName;
        private Integer value;
        private String scoreType;
        private Integer rangeMin;
        private Integer rangeMax;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlertDTO {
        private String type;
        private String severity;
        private String code;
        private String message;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportSummaryDTO {
        private Integer totalAccounts;
        private Integer totalInquiries;
        private Integer delinquentAccounts;
        private String oldestAccountDate;
        private Integer publicRecords;
        private Integer collections;
    }
}
```

**Step 2: Commit**

```bash
git add core-data-credit-bureaus/core-data-credit-bureaus-infra/src/main/java/com/firefly/data/credit/bureaus/infra/dtos/CreditReportDTO.java
git commit -m "feat: rewrite CreditReportDTO with provider-agnostic composition structure"
```

---

### Task 3: Update EquifaxEnricher.mapToTarget()

**Files:**
- Modify: `core-data-credit-bureaus/core-data-credit-bureaus-infra/src/main/java/com/firefly/data/credit/bureaus/infra/services/EquifaxEnricher.java:77-119`

**Step 1: Update the mapToTarget method to build the new sub-DTOs**

The method must:
1. Build `ProviderInfoDTO` with providerName="Equifax", reportType="CONSUMER_CREDIT"
2. Build `SubjectInfoDTO` with subjectType=INDIVIDUAL, map firstName/lastName/fullName/taxId/dateOfBirth
3. Build `ScoreDTO` list with scoreType="CREDIT_SCORE" added to each score
4. Build `AlertDTO` list with severity="HIGH" and code from provider for OFAC alerts
5. Build `ReportSummaryDTO` from trades and inquiries counts (nullable)

Replace the `mapToTarget` method (lines 77-119) with:

```java
@Override
protected CreditReportDTO mapToTarget(EquifaxReportResponse response) {
    if (response == null) {
        return null;
    }

    CreditReportDTO.CreditReportDTOBuilder builder = CreditReportDTO.builder()
            .status(response.getStatus());

    Optional.ofNullable(response.getConsumers())
            .map(EquifaxReportResponse.Consumers::getEquifaxUSConsumerCreditReport)
            .filter(list -> !list.isEmpty())
            .map(list -> list.getFirst())
            .ifPresent(report -> {
                // Provider info
                builder.provider(CreditReportDTO.ProviderInfoDTO.builder()
                        .providerName("Equifax")
                        .reportType("CONSUMER_CREDIT")
                        .reportId(report.getIdentifier())
                        .reportDate(report.getReportDate())
                        .referenceNumber(report.getCustomerReferenceNumber())
                        .build());

                // Subject info
                CreditReportDTO.SubjectInfoDTO.SubjectInfoDTOBuilder subjectBuilder =
                        CreditReportDTO.SubjectInfoDTO.builder()
                                .subjectType(SubjectTypeEnum.INDIVIDUAL)
                                .taxId(report.getSubjectSocialNum() != null
                                        ? String.valueOf(report.getSubjectSocialNum()) : null)
                                .dateOfBirth(report.getBirthDate());

                Optional.ofNullable(report.getSubjectName()).ifPresent(name -> {
                    subjectBuilder.firstName(name.getFirstName());
                    subjectBuilder.lastName(name.getLastName());
                    subjectBuilder.middleName(name.getMiddleName());
                    subjectBuilder.fullName(buildFullName(name));
                });

                builder.subject(subjectBuilder.build());

                // Scores
                if (report.getModels() != null) {
                    builder.scores(report.getModels().stream()
                            .map(model -> CreditReportDTO.ScoreDTO.builder()
                                    .modelName(model.getType())
                                    .value(model.getScore())
                                    .scoreType("CREDIT_SCORE")
                                    .build())
                            .collect(Collectors.toList()));
                }

                // Alerts
                if (report.getOfacAlerts() != null) {
                    builder.alerts(report.getOfacAlerts().stream()
                            .map(alert -> CreditReportDTO.AlertDTO.builder()
                                    .type("OFAC")
                                    .severity("HIGH")
                                    .code(alert.getCdcResponseCode())
                                    .message(alert.getLegalVerbiage())
                                    .build())
                            .collect(Collectors.toList()));
                }

                // Summary
                CreditReportDTO.ReportSummaryDTO.ReportSummaryDTOBuilder summaryBuilder =
                        CreditReportDTO.ReportSummaryDTO.builder();
                if (report.getTrades() != null) {
                    summaryBuilder.totalAccounts(report.getTrades().size());
                }
                if (report.getInquiries() != null) {
                    summaryBuilder.totalInquiries(report.getInquiries().size());
                }
                builder.summary(summaryBuilder.build());
            });

    return builder.build();
}

private String buildFullName(EquifaxReportResponse.SubjectName name) {
    StringBuilder sb = new StringBuilder();
    if (name.getFirstName() != null) sb.append(name.getFirstName());
    if (name.getMiddleName() != null) {
        if (!sb.isEmpty()) sb.append(" ");
        sb.append(name.getMiddleName());
    }
    if (name.getLastName() != null) {
        if (!sb.isEmpty()) sb.append(" ");
        sb.append(name.getLastName());
    }
    return sb.isEmpty() ? null : sb.toString();
}
```

**Step 2: Compile check**

Run: `mvn compile -pl core-data-credit-bureaus/core-data-credit-bureaus-infra -f core-data-credit-bureaus/pom.xml`
Expected: BUILD SUCCESS

**Step 3: Commit**

```bash
git add core-data-credit-bureaus/core-data-credit-bureaus-infra/src/main/java/com/firefly/data/credit/bureaus/infra/services/EquifaxEnricher.java
git commit -m "feat: update EquifaxEnricher.mapToTarget to populate new generic CreditReportDTO"
```

---

### Task 4: Update EquifaxEnricherTest

**Files:**
- Modify: `core-data-credit-bureaus/core-data-credit-bureaus-infra/src/test/java/com/firefly/data/credit/bureaus/infra/services/EquifaxEnricherTest.java`

**Step 1: Update test assertions for the new DTO structure**

Replace the test class with updated assertions that verify the new sub-DTOs:

```java
package com.firefly.data.credit.bureaus.infra.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fireflyframework.data.event.EnrichmentEventPublisher;
import org.fireflyframework.data.model.EnrichmentRequest;
import org.fireflyframework.data.observability.JobMetricsService;
import org.fireflyframework.data.observability.JobTracingService;
import org.fireflyframework.data.resiliency.ResiliencyDecoratorService;
import com.firefly.data.credit.bureaus.infra.dtos.CreditReportDTO;
import com.firefly.data.credit.bureaus.infra.dtos.EquifaxReportRequest;
import com.firefly.data.credit.bureaus.infra.dtos.EquifaxReportResponse;
import com.firefly.data.credit.bureaus.infra.dtos.SubjectTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquifaxEnricherTest {

    @Mock
    private JobTracingService tracingService;
    @Mock
    private JobMetricsService metricsService;
    @Mock
    private ResiliencyDecoratorService resiliencyService;
    @Mock
    private EnrichmentEventPublisher eventPublisher;
    @Mock
    private EquifaxService equifaxService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private EquifaxEnricher equifaxEnricher;

    @BeforeEach
    void setUp() {
        equifaxEnricher = new EquifaxEnricher(
                tracingService,
                metricsService,
                resiliencyService,
                eventPublisher,
                equifaxService,
                objectMapper
        );
    }

    @Test
    void mapToTarget_ShouldMapResponseToDTO() {
        // Arrange
        EquifaxReportResponse.OfacAlerts alert = new EquifaxReportResponse.OfacAlerts();
        alert.setLegalVerbiage("OFAC Alert Message");
        alert.setCdcResponseCode("01");

        EquifaxReportResponse.Models model = EquifaxReportResponse.Models.builder()
                .type("FICO")
                .score(750)
                .build();

        EquifaxReportResponse.SubjectName subjectName = EquifaxReportResponse.SubjectName.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        EquifaxReportResponse.EquifaxUSConsumerCreditReport report = EquifaxReportResponse.EquifaxUSConsumerCreditReport.builder()
                .identifier("RPT-001")
                .customerReferenceNumber("REF-123")
                .reportDate("2023-10-27")
                .birthDate("1990-01-01")
                .subjectSocialNum(123456789L)
                .subjectName(subjectName)
                .models(Collections.singletonList(model))
                .ofacAlerts(Collections.singletonList(alert))
                .build();

        EquifaxReportResponse response = EquifaxReportResponse.builder()
                .status("SUCCESS")
                .consumers(EquifaxReportResponse.Consumers.builder()
                        .equifaxUSConsumerCreditReport(Collections.singletonList(report))
                        .build())
                .build();

        // Act
        CreditReportDTO result = equifaxEnricher.mapToTarget(response);

        // Assert
        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());

        // Provider info
        assertNotNull(result.getProvider());
        assertEquals("Equifax", result.getProvider().getProviderName());
        assertEquals("CONSUMER_CREDIT", result.getProvider().getReportType());
        assertEquals("RPT-001", result.getProvider().getReportId());
        assertEquals("2023-10-27", result.getProvider().getReportDate());
        assertEquals("REF-123", result.getProvider().getReferenceNumber());

        // Subject info
        assertNotNull(result.getSubject());
        assertEquals(SubjectTypeEnum.INDIVIDUAL, result.getSubject().getSubjectType());
        assertEquals("John", result.getSubject().getFirstName());
        assertEquals("Doe", result.getSubject().getLastName());
        assertEquals("John Doe", result.getSubject().getFullName());
        assertEquals("123456789", result.getSubject().getTaxId());
        assertEquals("1990-01-01", result.getSubject().getDateOfBirth());

        // Scores
        assertEquals(1, result.getScores().size());
        assertEquals("FICO", result.getScores().get(0).getModelName());
        assertEquals(750, result.getScores().get(0).getValue());
        assertEquals("CREDIT_SCORE", result.getScores().get(0).getScoreType());

        // Alerts
        assertEquals(1, result.getAlerts().size());
        assertEquals("OFAC", result.getAlerts().get(0).getType());
        assertEquals("HIGH", result.getAlerts().get(0).getSeverity());
        assertEquals("01", result.getAlerts().get(0).getCode());
        assertEquals("OFAC Alert Message", result.getAlerts().get(0).getMessage());
    }

    @Test
    void mapToTarget_ShouldHandleNullResponse() {
        assertNull(equifaxEnricher.mapToTarget(null));
    }

    @Test
    void mapToTarget_ShouldHandleEmptyConsumers() {
        EquifaxReportResponse response = EquifaxReportResponse.builder()
                .status("NO_DATA")
                .consumers(EquifaxReportResponse.Consumers.builder()
                        .equifaxUSConsumerCreditReport(Collections.emptyList())
                        .build())
                .build();

        CreditReportDTO result = equifaxEnricher.mapToTarget(response);

        assertNotNull(result);
        assertEquals("NO_DATA", result.getStatus());
        assertNull(result.getSubject());
        assertNull(result.getProvider());
    }

    @Test
    void fetchProviderData_ShouldCallService() {
        // Arrange
        EnrichmentRequest request = EnrichmentRequest.builder()
                .parameters(Map.of("customerReferenceidentifier", "12345"))
                .build();

        String token = "test-token";
        EquifaxReportResponse expectedResponse = EquifaxReportResponse.builder().status("OK").build();

        when(equifaxService.getAccessToken()).thenReturn(Mono.just(token));
        when(equifaxService.getCreditReport(any(EquifaxReportRequest.class), eq(token)))
                .thenReturn(Mono.just(expectedResponse));

        // Act
        EquifaxReportResponse result = equifaxEnricher.fetchProviderData(request).block();

        // Assert
        assertNotNull(result);
        assertEquals("OK", result.getStatus());
    }
}
```

**Step 2: Run tests**

Run: `mvn test -pl core-data-credit-bureaus/core-data-credit-bureaus-infra -f core-data-credit-bureaus/pom.xml`
Expected: All tests PASS

**Step 3: Commit**

```bash
git add core-data-credit-bureaus/core-data-credit-bureaus-infra/src/test/java/com/firefly/data/credit/bureaus/infra/services/EquifaxEnricherTest.java
git commit -m "test: update EquifaxEnricherTest for generic CreditReportDTO structure"
```

---

### Task 5: Final verification

**Step 1: Full module build**

Run: `mvn clean compile test -f core-data-credit-bureaus/pom.xml`
Expected: BUILD SUCCESS, all tests PASS

**Step 2: Verify no compilation errors in dependent modules**

Run: `mvn compile -pl core-data-credit-bureaus/core-data-credit-bureaus-core -f core-data-credit-bureaus/pom.xml`
Expected: BUILD SUCCESS

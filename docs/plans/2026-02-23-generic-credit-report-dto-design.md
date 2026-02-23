# Generic CreditReportDTO Design

## Problem

The current `CreditReportDTO` in `core-data-credit-bureaus` is Equifax-specific and individual-only. It uses fields like `firstName`, `lastName`, `socialNumber`, `birthDate` that are tied to US consumer credit reports. The service needs to support:

1. **Multiple providers** (Equifax, Experian, TransUnion, Dun & Bradstreet, etc.)
2. **Both individuals and businesses** (aligned with the Party → NaturalPerson | LegalEntity pattern in customer-mgmt)
3. **Standardized output** regardless of the provider's response format

## Design Decisions

- **Flat DTO with optional fields** for individual vs business differentiation (no polymorphic deserialization)
- **Composition via inner classes** for ProviderInfo, SubjectInfo, Score, Alert, ReportSummary
- **Standardized summary** format (not detailed tradeline/inquiry data)
- **SubjectTypeEnum** to discriminate between INDIVIDUAL and BUSINESS

## Structure

```
CreditReportDTO
├── status: String                    (SUCCESS, ERROR, PARTIAL)
├── provider: ProviderInfoDTO         (provider metadata)
│   ├── providerName: String          ("Equifax", "Experian", etc.)
│   ├── reportType: String            ("CONSUMER_CREDIT", "BUSINESS_CREDIT", "COMMERCIAL")
│   ├── reportId: String              (provider-assigned report ID)
│   ├── reportDate: String            (report generation date)
│   └── referenceNumber: String       (client/request reference)
├── subject: SubjectInfoDTO           (queried subject data)
│   ├── subjectType: SubjectTypeEnum  (INDIVIDUAL or BUSINESS)
│   ├── fullName: String              (full name or legal name)
│   ├── taxId: String                 (SSN, EIN, RFC, etc.)
│   ├── firstName: String             (individual-only, nullable)
│   ├── lastName: String              (individual-only, nullable)
│   ├── middleName: String            (individual-only, nullable)
│   ├── dateOfBirth: String           (individual-only, nullable)
│   ├── legalName: String             (business-only, nullable)
│   ├── tradeName: String             (business-only, nullable)
│   ├── registrationNumber: String    (business-only, nullable)
│   ├── incorporationDate: String     (business-only, nullable)
│   └── industryDescription: String   (business-only, nullable)
├── scores: List<ScoreDTO>            (credit scores)
│   ├── modelName: String             ("FICO", "VantageScore", "PAYDEX")
│   ├── value: Integer                (numeric score value)
│   ├── scoreType: String             ("CREDIT_SCORE", "BUSINESS_SCORE")
│   ├── rangeMin: Integer             (model minimum, nullable)
│   └── rangeMax: Integer             (model maximum, nullable)
├── alerts: List<AlertDTO>            (warnings and alerts)
│   ├── type: String                  ("OFAC", "FRAUD", "BANKRUPTCY")
│   ├── severity: String              ("HIGH", "MEDIUM", "LOW", "INFO")
│   ├── code: String                  (provider-specific code)
│   └── message: String               (human-readable description)
└── summary: ReportSummaryDTO         (report metrics, nullable)
    ├── totalAccounts: Integer
    ├── totalInquiries: Integer
    ├── delinquentAccounts: Integer    (nullable)
    ├── oldestAccountDate: String      (nullable)
    ├── publicRecords: Integer         (nullable)
    └── collections: Integer           (nullable)
```

## New Enum

`SubjectTypeEnum` in `core-data-credit-bureaus-interfaces/enums/`:
- `INDIVIDUAL`
- `BUSINESS`

## Impact

### Files to modify
1. `CreditReportDTO.java` - Complete rewrite with new structure
2. `EquifaxEnricher.mapToTarget()` - Update mapping to build new sub-DTOs
3. `EquifaxEnricherTest.java` - Update test assertions

### Files to create
1. `SubjectTypeEnum.java` - New enum for subject type discrimination

### Alignment with codebase
- Follows Lombok patterns (@Data, @Builder, @NoArgsConstructor, @AllArgsConstructor) used across all DTOs
- SubjectTypeEnum aligns with PartyKind enum (INDIVIDUAL ↔ INDIVIDUAL, ORGANIZATION ↔ BUSINESS)
- Inner class pattern consistent with current ScoreDTO/AlertDTO approach
- No breaking changes to the DataEnricher framework contract

-- Flyway V1: Create PostgreSQL ENUM types for loan origination domain
-- Database: PostgreSQL

-- Application Status Enum
CREATE TYPE application_status_enum AS ENUM (
    'DRAFT',
    'SUBMITTED',
    'UNDER_REVIEW',
    'APPROVED',
    'REJECTED'
);

-- Application Sub-Status Enum
CREATE TYPE application_sub_status_enum AS ENUM (
    'PENDING_DOCUMENTS',
    'NEEDS_MANUAL_REVIEW',
    'COMPLETE'
);

-- Submission Channel Enum
CREATE TYPE submission_channel_enum AS ENUM (
    'BRANCH',
    'ONLINE',
    'MOBILE',
    'DISTRIBUTOR'
);

-- Collateral Type Enum
CREATE TYPE collateral_type_enum AS ENUM (
    'REAL_ESTATE',
    'VEHICLE',
    'FINANCIAL_ASSET',
    'OTHER'
);

-- Document Type Enum
CREATE TYPE document_type_enum AS ENUM (
    'ID_PROOF',
    'PAYSLIP',
    'PROPERTY_DEED',
    'BANK_STATEMENT',
    'OTHER'
);

-- Employment Type Enum
CREATE TYPE employment_type_enum AS ENUM (
    'SALARIED',
    'SELF_EMPLOYED',
    'UNEMPLOYED',
    'RETIRED',
    'OTHER'
);

-- Role Code Enum
CREATE TYPE role_code_enum AS ENUM (
    'PRIMARY_APPLICANT',
    'CO_APPLICANT',
    'GUARANTOR'
);

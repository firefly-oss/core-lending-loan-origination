-- V1 - CREATE ENUMS

-- 1) loan_application/application_status
CREATE TYPE application_status AS ENUM (
  'DRAFT',
  'SUBMITTED',
  'UNDER_REVIEW',
  'APPROVED',
  'REJECTED'
);

-- 2) loan_application/application_sub_status
CREATE TYPE application_sub_status AS ENUM (
  'PENDING_DOCUMENTS',
  'NEEDS_MANUAL_REVIEW',
  'COMPLETE'
);

-- 3) loan_application/submission_channel
CREATE TYPE submission_channel AS ENUM (
  'BRANCH',
  'ONLINE',
  'MOBILE'
);

-- 4) application_party/role_code
CREATE TYPE role_code AS ENUM (
  'PRIMARY_APPLICANT',
  'CO_APPLICANT',
  'GUARANTOR'
);

-- 5) application_party/employment_type
CREATE TYPE employment_type AS ENUM (
  'SALARIED',
  'SELF_EMPLOYED',
  'UNEMPLOYED',
  'RETIRED',
  'OTHER'
);

-- 6) application_document/document_type
CREATE TYPE document_type AS ENUM (
  'ID_PROOF',
  'PAYSLIP',
  'PROPERTY_DEED',
  'BANK_STATEMENT',
  'OTHER'
);

-- 7) application_collateral/collateral_type
CREATE TYPE collateral_type AS ENUM (
  'REAL_ESTATE',
  'VEHICLE',
  'FINANCIAL_ASSET',
  'OTHER'
);

-- 8) underwriting_decision/decision_code
CREATE TYPE decision_code AS ENUM (
  'APPROVED',
  'DECLINED',
  'WITHDRAWN',
  'COUNTER_OFFER'
);

-- 9) underwriting_decision/risk_grade
CREATE TYPE risk_grade AS ENUM (
  'A',
  'B',
  'C',
  'D',
  'HIGH_RISK'
);
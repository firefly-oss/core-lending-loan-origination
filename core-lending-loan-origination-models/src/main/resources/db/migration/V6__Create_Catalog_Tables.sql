-- V6 - CREATE CATALOG TABLES

-- 1) application_status table
CREATE TABLE application_status (
  application_status_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 2) application_sub_status table
CREATE TABLE application_sub_status (
  application_sub_status_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 3) submission_channel table
CREATE TABLE submission_channel (
  submission_channel_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 4) role_code table
CREATE TABLE role_code (
  role_code_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 5) employment_type table
CREATE TABLE employment_type (
  employment_type_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 6) document_type table
CREATE TABLE document_type (
  document_type_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 7) collateral_type table
CREATE TABLE collateral_type (
  collateral_type_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 8) decision_code table
CREATE TABLE decision_code (
  decision_code_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 9) risk_grade table
CREATE TABLE risk_grade (
  risk_grade_id SERIAL PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Populate initial data from existing enums

-- 1) application_status data
INSERT INTO application_status (code, name, description) VALUES
  ('DRAFT', 'Draft', 'Application is in draft state'),
  ('SUBMITTED', 'Submitted', 'Application has been submitted'),
  ('UNDER_REVIEW', 'Under Review', 'Application is being reviewed'),
  ('APPROVED', 'Approved', 'Application has been approved'),
  ('REJECTED', 'Rejected', 'Application has been rejected');

-- 2) application_sub_status data
INSERT INTO application_sub_status (code, name, description) VALUES
  ('PENDING_DOCUMENTS', 'Pending Documents', 'Waiting for documents to be submitted'),
  ('NEEDS_MANUAL_REVIEW', 'Needs Manual Review', 'Application requires manual review'),
  ('COMPLETE', 'Complete', 'Application is complete');

-- 3) submission_channel data
INSERT INTO submission_channel (code, name, description) VALUES
  ('BRANCH', 'Branch', 'Application submitted at a branch'),
  ('ONLINE', 'Online', 'Application submitted online'),
  ('MOBILE', 'Mobile', 'Application submitted via mobile app');

-- 4) role_code data
INSERT INTO role_code (code, name, description) VALUES
  ('PRIMARY_APPLICANT', 'Primary Applicant', 'Main applicant for the loan'),
  ('CO_APPLICANT', 'Co-Applicant', 'Secondary applicant for the loan'),
  ('GUARANTOR', 'Guarantor', 'Person guaranteeing the loan');

-- 5) employment_type data
INSERT INTO employment_type (code, name, description) VALUES
  ('SALARIED', 'Salaried', 'Employed with regular salary'),
  ('SELF_EMPLOYED', 'Self-Employed', 'Self-employed individual'),
  ('UNEMPLOYED', 'Unemployed', 'Currently not employed'),
  ('RETIRED', 'Retired', 'Retired individual'),
  ('OTHER', 'Other', 'Other employment type');

-- 6) document_type data
INSERT INTO document_type (code, name, description) VALUES
  ('ID_PROOF', 'ID Proof', 'Identity proof document'),
  ('PAYSLIP', 'Payslip', 'Salary slip document'),
  ('PROPERTY_DEED', 'Property Deed', 'Property ownership document'),
  ('BANK_STATEMENT', 'Bank Statement', 'Bank account statement'),
  ('OTHER', 'Other', 'Other document type');

-- 7) collateral_type data
INSERT INTO collateral_type (code, name, description) VALUES
  ('REAL_ESTATE', 'Real Estate', 'Property used as collateral'),
  ('VEHICLE', 'Vehicle', 'Vehicle used as collateral'),
  ('FINANCIAL_ASSET', 'Financial Asset', 'Financial asset used as collateral'),
  ('OTHER', 'Other', 'Other type of collateral');

-- 8) decision_code data
INSERT INTO decision_code (code, name, description) VALUES
  ('APPROVED', 'Approved', 'Application has been approved'),
  ('DECLINED', 'Declined', 'Application has been declined'),
  ('WITHDRAWN', 'Withdrawn', 'Application has been withdrawn'),
  ('COUNTER_OFFER', 'Counter Offer', 'Counter offer has been made');

-- 9) risk_grade data
INSERT INTO risk_grade (code, name, description) VALUES
  ('A', 'Grade A', 'Lowest risk grade'),
  ('B', 'Grade B', 'Low risk grade'),
  ('C', 'Grade C', 'Medium risk grade'),
  ('D', 'Grade D', 'High risk grade'),
  ('HIGH_RISK', 'High Risk', 'Highest risk grade');
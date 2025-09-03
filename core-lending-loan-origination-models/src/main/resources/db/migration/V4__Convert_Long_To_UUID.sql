-- Flyway V4: Convert Long ID fields to UUID
-- Database: PostgreSQL

-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Add new UUID columns alongside existing BIGINT columns
ALTER TABLE loan_application 
ADD COLUMN loan_application_id_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN party_id_uuid UUID,
ADD COLUMN distributor_id_uuid UUID;

-- 2. Update catalog tables - add UUID columns
ALTER TABLE application_status ADD COLUMN application_status_id_uuid UUID DEFAULT uuid_generate_v4();
ALTER TABLE application_sub_status ADD COLUMN application_sub_status_id_uuid UUID DEFAULT uuid_generate_v4();
ALTER TABLE collateral_type ADD COLUMN collateral_type_id_uuid UUID DEFAULT uuid_generate_v4();
ALTER TABLE decision_code ADD COLUMN decision_code_id_uuid UUID DEFAULT uuid_generate_v4();
ALTER TABLE document_type ADD COLUMN document_type_id_uuid UUID DEFAULT uuid_generate_v4();
ALTER TABLE employment_type ADD COLUMN employment_type_id_uuid UUID DEFAULT uuid_generate_v4();
ALTER TABLE risk_grade ADD COLUMN risk_grade_id_uuid UUID DEFAULT uuid_generate_v4();
ALTER TABLE role_code ADD COLUMN role_code_id_uuid UUID DEFAULT uuid_generate_v4();
ALTER TABLE submission_channel ADD COLUMN submission_channel_id_uuid UUID DEFAULT uuid_generate_v4();

-- 3. Update business tables - add UUID columns
ALTER TABLE application_collateral 
ADD COLUMN application_collateral_id_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_application_id_uuid UUID;

ALTER TABLE application_document 
ADD COLUMN application_document_id_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_application_id_uuid UUID,
ADD COLUMN document_id_uuid UUID;

ALTER TABLE application_party 
ADD COLUMN application_party_id_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_application_id_uuid UUID,
ADD COLUMN party_id_uuid UUID;

ALTER TABLE proposed_offer 
ADD COLUMN proposed_offer_id_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_application_id_uuid UUID,
ADD COLUMN product_id_uuid UUID;

ALTER TABLE underwriting_decision 
ADD COLUMN underwriting_decision_id_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_application_id_uuid UUID,
ADD COLUMN decision_code_id_uuid UUID,
ADD COLUMN risk_grade_id_uuid UUID;

ALTER TABLE underwriting_score 
ADD COLUMN underwriting_score_id_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_application_id_uuid UUID;

ALTER TABLE loan_application_status_history 
ADD COLUMN status_history_id_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_application_id_uuid UUID;

-- 4. Create mapping table to preserve Long->UUID relationships (for data integrity during migration)
CREATE TEMPORARY TABLE id_mapping (
    table_name VARCHAR(100),
    old_id BIGINT,
    new_uuid UUID
);

-- 5. Populate UUID foreign key relationships
-- Update loan_application foreign key UUIDs (these will be null for now as they reference external systems)
-- party_id and distributor_id reference external systems - will remain null

-- Update business table foreign keys to reference loan_application UUIDs
UPDATE application_collateral 
SET loan_application_id_uuid = (
    SELECT loan_application_id_uuid 
    FROM loan_application 
    WHERE loan_application.loan_application_id = application_collateral.loan_application_id
);

UPDATE application_document 
SET loan_application_id_uuid = (
    SELECT loan_application_id_uuid 
    FROM loan_application 
    WHERE loan_application.loan_application_id = application_document.loan_application_id
);

UPDATE application_party 
SET loan_application_id_uuid = (
    SELECT loan_application_id_uuid 
    FROM loan_application 
    WHERE loan_application.loan_application_id = application_party.loan_application_id
);

UPDATE proposed_offer 
SET loan_application_id_uuid = (
    SELECT loan_application_id_uuid 
    FROM loan_application 
    WHERE loan_application.loan_application_id = proposed_offer.loan_application_id
);

UPDATE underwriting_decision 
SET loan_application_id_uuid = (
    SELECT loan_application_id_uuid 
    FROM loan_application 
    WHERE loan_application.loan_application_id = underwriting_decision.loan_application_id
);

UPDATE underwriting_score 
SET loan_application_id_uuid = (
    SELECT loan_application_id_uuid 
    FROM loan_application 
    WHERE loan_application.loan_application_id = underwriting_score.loan_application_id
);

UPDATE loan_application_status_history 
SET loan_application_id_uuid = (
    SELECT loan_application_id_uuid 
    FROM loan_application 
    WHERE loan_application.loan_application_id = loan_application_status_history.loan_application_id
);

-- Update catalog foreign key references
UPDATE underwriting_decision 
SET decision_code_id_uuid = (
    SELECT decision_code_id_uuid 
    FROM decision_code 
    WHERE decision_code.decision_code_id = underwriting_decision.decision_code_id
);

UPDATE underwriting_decision 
SET risk_grade_id_uuid = (
    SELECT risk_grade_id_uuid 
    FROM risk_grade 
    WHERE risk_grade.risk_grade_id = underwriting_decision.risk_grade_id
);

-- 6. Drop old BIGINT columns and constraints
-- Drop foreign key constraints first (using actual PostgreSQL-generated constraint names)
ALTER TABLE application_collateral DROP CONSTRAINT IF EXISTS application_collateral_loan_application_id_fkey;
ALTER TABLE application_document DROP CONSTRAINT IF EXISTS application_document_loan_application_id_fkey;
ALTER TABLE application_party DROP CONSTRAINT IF EXISTS application_party_loan_application_id_fkey;
ALTER TABLE proposed_offer DROP CONSTRAINT IF EXISTS proposed_offer_loan_application_id_fkey;
ALTER TABLE underwriting_decision DROP CONSTRAINT IF EXISTS underwriting_decision_loan_application_id_fkey;
ALTER TABLE underwriting_decision DROP CONSTRAINT IF EXISTS underwriting_decision_decision_code_id_fkey;
ALTER TABLE underwriting_decision DROP CONSTRAINT IF EXISTS underwriting_decision_risk_grade_id_fkey;
ALTER TABLE underwriting_score DROP CONSTRAINT IF EXISTS underwriting_score_loan_application_id_fkey;
ALTER TABLE loan_application_status_history DROP CONSTRAINT IF EXISTS loan_application_status_history_loan_application_id_fkey;

-- Drop old BIGINT primary key columns
ALTER TABLE loan_application DROP COLUMN loan_application_id, DROP COLUMN party_id, DROP COLUMN distributor_id;
ALTER TABLE application_status DROP COLUMN application_status_id;
ALTER TABLE application_sub_status DROP COLUMN application_sub_status_id;
ALTER TABLE collateral_type DROP COLUMN collateral_type_id;
ALTER TABLE decision_code DROP COLUMN decision_code_id;
ALTER TABLE document_type DROP COLUMN document_type_id;
ALTER TABLE employment_type DROP COLUMN employment_type_id;
ALTER TABLE risk_grade DROP COLUMN risk_grade_id;
ALTER TABLE role_code DROP COLUMN role_code_id;
ALTER TABLE submission_channel DROP COLUMN submission_channel_id;
ALTER TABLE application_collateral DROP COLUMN application_collateral_id, DROP COLUMN loan_application_id;
ALTER TABLE application_document DROP COLUMN application_document_id, DROP COLUMN loan_application_id, DROP COLUMN document_id;
ALTER TABLE application_party DROP COLUMN application_party_id, DROP COLUMN loan_application_id, DROP COLUMN party_id;
ALTER TABLE proposed_offer DROP COLUMN proposed_offer_id, DROP COLUMN loan_application_id, DROP COLUMN product_id;
ALTER TABLE underwriting_decision DROP COLUMN underwriting_decision_id, DROP COLUMN loan_application_id, DROP COLUMN decision_code_id, DROP COLUMN risk_grade_id;
ALTER TABLE underwriting_score DROP COLUMN underwriting_score_id, DROP COLUMN loan_application_id;
ALTER TABLE loan_application_status_history DROP COLUMN status_history_id, DROP COLUMN loan_application_id;

-- 7. Rename UUID columns to original names
ALTER TABLE loan_application RENAME COLUMN loan_application_id_uuid TO loan_application_id;
ALTER TABLE loan_application RENAME COLUMN party_id_uuid TO party_id;
ALTER TABLE loan_application RENAME COLUMN distributor_id_uuid TO distributor_id;

ALTER TABLE application_status RENAME COLUMN application_status_id_uuid TO application_status_id;
ALTER TABLE application_sub_status RENAME COLUMN application_sub_status_id_uuid TO application_sub_status_id;
ALTER TABLE collateral_type RENAME COLUMN collateral_type_id_uuid TO collateral_type_id;
ALTER TABLE decision_code RENAME COLUMN decision_code_id_uuid TO decision_code_id;
ALTER TABLE document_type RENAME COLUMN document_type_id_uuid TO document_type_id;
ALTER TABLE employment_type RENAME COLUMN employment_type_id_uuid TO employment_type_id;
ALTER TABLE risk_grade RENAME COLUMN risk_grade_id_uuid TO risk_grade_id;
ALTER TABLE role_code RENAME COLUMN role_code_id_uuid TO role_code_id;
ALTER TABLE submission_channel RENAME COLUMN submission_channel_id_uuid TO submission_channel_id;

ALTER TABLE application_collateral RENAME COLUMN application_collateral_id_uuid TO application_collateral_id;
ALTER TABLE application_collateral RENAME COLUMN loan_application_id_uuid TO loan_application_id;
ALTER TABLE application_document RENAME COLUMN application_document_id_uuid TO application_document_id;
ALTER TABLE application_document RENAME COLUMN loan_application_id_uuid TO loan_application_id;
ALTER TABLE application_document RENAME COLUMN document_id_uuid TO document_id;
ALTER TABLE application_party RENAME COLUMN application_party_id_uuid TO application_party_id;
ALTER TABLE application_party RENAME COLUMN loan_application_id_uuid TO loan_application_id;
ALTER TABLE application_party RENAME COLUMN party_id_uuid TO party_id;
ALTER TABLE proposed_offer RENAME COLUMN proposed_offer_id_uuid TO proposed_offer_id;
ALTER TABLE proposed_offer RENAME COLUMN loan_application_id_uuid TO loan_application_id;
ALTER TABLE proposed_offer RENAME COLUMN product_id_uuid TO product_id;
ALTER TABLE underwriting_decision RENAME COLUMN underwriting_decision_id_uuid TO underwriting_decision_id;
ALTER TABLE underwriting_decision RENAME COLUMN loan_application_id_uuid TO loan_application_id;
ALTER TABLE underwriting_decision RENAME COLUMN decision_code_id_uuid TO decision_code_id;
ALTER TABLE underwriting_decision RENAME COLUMN risk_grade_id_uuid TO risk_grade_id;
ALTER TABLE underwriting_score RENAME COLUMN underwriting_score_id_uuid TO underwriting_score_id;
ALTER TABLE underwriting_score RENAME COLUMN loan_application_id_uuid TO loan_application_id;
ALTER TABLE loan_application_status_history RENAME COLUMN status_history_id_uuid TO status_history_id;
ALTER TABLE loan_application_status_history RENAME COLUMN loan_application_id_uuid TO loan_application_id;

-- 8. Add primary key constraints
ALTER TABLE loan_application ADD PRIMARY KEY (loan_application_id);
ALTER TABLE application_status ADD PRIMARY KEY (application_status_id);
ALTER TABLE application_sub_status ADD PRIMARY KEY (application_sub_status_id);
ALTER TABLE collateral_type ADD PRIMARY KEY (collateral_type_id);
ALTER TABLE decision_code ADD PRIMARY KEY (decision_code_id);
ALTER TABLE document_type ADD PRIMARY KEY (document_type_id);
ALTER TABLE employment_type ADD PRIMARY KEY (employment_type_id);
ALTER TABLE risk_grade ADD PRIMARY KEY (risk_grade_id);
ALTER TABLE role_code ADD PRIMARY KEY (role_code_id);
ALTER TABLE submission_channel ADD PRIMARY KEY (submission_channel_id);
ALTER TABLE application_collateral ADD PRIMARY KEY (application_collateral_id);
ALTER TABLE application_document ADD PRIMARY KEY (application_document_id);
ALTER TABLE application_party ADD PRIMARY KEY (application_party_id);
ALTER TABLE proposed_offer ADD PRIMARY KEY (proposed_offer_id);
ALTER TABLE underwriting_decision ADD PRIMARY KEY (underwriting_decision_id);
ALTER TABLE underwriting_score ADD PRIMARY KEY (underwriting_score_id);
ALTER TABLE loan_application_status_history ADD PRIMARY KEY (status_history_id);

-- 9. Add foreign key constraints
ALTER TABLE application_collateral ADD CONSTRAINT fk_application_collateral_loan_application 
    FOREIGN KEY (loan_application_id) REFERENCES loan_application(loan_application_id);

ALTER TABLE application_document ADD CONSTRAINT fk_application_document_loan_application 
    FOREIGN KEY (loan_application_id) REFERENCES loan_application(loan_application_id);

ALTER TABLE application_party ADD CONSTRAINT fk_application_party_loan_application 
    FOREIGN KEY (loan_application_id) REFERENCES loan_application(loan_application_id);

ALTER TABLE proposed_offer ADD CONSTRAINT fk_proposed_offer_loan_application 
    FOREIGN KEY (loan_application_id) REFERENCES loan_application(loan_application_id);

ALTER TABLE underwriting_decision ADD CONSTRAINT fk_underwriting_decision_loan_application 
    FOREIGN KEY (loan_application_id) REFERENCES loan_application(loan_application_id);

ALTER TABLE underwriting_decision ADD CONSTRAINT fk_underwriting_decision_decision_code 
    FOREIGN KEY (decision_code_id) REFERENCES decision_code(decision_code_id);

ALTER TABLE underwriting_decision ADD CONSTRAINT fk_underwriting_decision_risk_grade 
    FOREIGN KEY (risk_grade_id) REFERENCES risk_grade(risk_grade_id);

ALTER TABLE underwriting_score ADD CONSTRAINT fk_underwriting_score_loan_application 
    FOREIGN KEY (loan_application_id) REFERENCES loan_application(loan_application_id);

ALTER TABLE loan_application_status_history ADD CONSTRAINT fk_loan_application_status_history_loan_application 
    FOREIGN KEY (loan_application_id) REFERENCES loan_application(loan_application_id);

-- 10. Recreate indexes for UUID columns
-- Drop existing indexes first to avoid conflicts
DROP INDEX IF EXISTS ux_loan_application_application_number;
DROP INDEX IF EXISTS ux_application_status_code;
DROP INDEX IF EXISTS ux_application_sub_status_code;
DROP INDEX IF EXISTS ux_collateral_type_code;
DROP INDEX IF EXISTS ux_decision_code_code;
DROP INDEX IF EXISTS ux_document_type_code;
DROP INDEX IF EXISTS ux_employment_type_code;
DROP INDEX IF EXISTS ux_risk_grade_code;
DROP INDEX IF EXISTS ux_role_code_code;
DROP INDEX IF EXISTS ux_submission_channel_code;

-- Create indexes
CREATE UNIQUE INDEX ux_loan_application_application_number ON loan_application(application_number);
CREATE UNIQUE INDEX ux_application_status_code ON application_status(code);
CREATE UNIQUE INDEX ux_application_sub_status_code ON application_sub_status(code);
CREATE UNIQUE INDEX ux_collateral_type_code ON collateral_type(code);
CREATE UNIQUE INDEX ux_decision_code_code ON decision_code(code);
CREATE UNIQUE INDEX ux_document_type_code ON document_type(code);
CREATE UNIQUE INDEX ux_employment_type_code ON employment_type(code);
CREATE UNIQUE INDEX ux_risk_grade_code ON risk_grade(code);
CREATE UNIQUE INDEX ux_role_code_code ON role_code(code);
CREATE UNIQUE INDEX ux_submission_channel_code ON submission_channel(code);
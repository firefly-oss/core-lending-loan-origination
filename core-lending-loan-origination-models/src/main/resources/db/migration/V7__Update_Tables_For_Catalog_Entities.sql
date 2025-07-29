-- V7 - UPDATE TABLES FOR CATALOG ENTITIES

-- 1) Add foreign key columns to tables that previously used enums

-- Add decision_code_id to underwriting_decision table
ALTER TABLE underwriting_decision ADD COLUMN decision_code_id BIGINT;

-- Add risk_grade_id to underwriting_decision table
ALTER TABLE underwriting_decision ADD COLUMN risk_grade_id BIGINT;

-- Add document_type_id to application_document table
ALTER TABLE application_document ADD COLUMN document_type_id BIGINT;

-- Add application_status_id to loan_application table
ALTER TABLE loan_application ADD COLUMN application_status_id BIGINT;

-- Add application_sub_status_id to loan_application table
ALTER TABLE loan_application ADD COLUMN application_sub_status_id BIGINT;

-- Add submission_channel_id to loan_application table
ALTER TABLE loan_application ADD COLUMN submission_channel_id BIGINT;

-- Add role_code_id to application_party table
ALTER TABLE application_party ADD COLUMN role_code_id BIGINT;

-- Add employment_type_id to application_party table
ALTER TABLE application_party ADD COLUMN employment_type_id BIGINT;

-- Add collateral_type_id to application_collateral table
ALTER TABLE application_collateral ADD COLUMN collateral_type_id BIGINT;

-- 2) Populate the new foreign key columns based on the enum values

-- Populate decision_code_id in underwriting_decision table
UPDATE underwriting_decision ud
SET decision_code_id = dc.decision_code_id
FROM decision_code dc
WHERE ud.decision_code::text = dc.code;

-- Populate risk_grade_id in underwriting_decision table
UPDATE underwriting_decision ud
SET risk_grade_id = rg.risk_grade_id
FROM risk_grade rg
WHERE ud.risk_grade::text = rg.code;

-- Populate document_type_id in application_document table
UPDATE application_document ad
SET document_type_id = dt.document_type_id
FROM document_type dt
WHERE ad.document_type::text = dt.code;

-- Populate application_status_id in loan_application table
UPDATE loan_application la
SET application_status_id = ast.application_status_id
FROM application_status ast
WHERE la.status::text = ast.code;

-- Populate application_sub_status_id in loan_application table
UPDATE loan_application la
SET application_sub_status_id = ass.application_sub_status_id
FROM application_sub_status ass
WHERE la.sub_status::text = ass.code;

-- Populate submission_channel_id in loan_application table
UPDATE loan_application la
SET submission_channel_id = sc.submission_channel_id
FROM submission_channel sc
WHERE la.submission_channel::text = sc.code;

-- Populate role_code_id in application_party table
UPDATE application_party ap
SET role_code_id = rc.role_code_id
FROM role_code rc
WHERE ap.role_code::text = rc.code;

-- Populate employment_type_id in application_party table
UPDATE application_party ap
SET employment_type_id = et.employment_type_id
FROM employment_type et
WHERE ap.employment_type::text = et.code;

-- Populate collateral_type_id in application_collateral table
UPDATE application_collateral ac
SET collateral_type_id = ct.collateral_type_id
FROM collateral_type ct
WHERE ac.collateral_type::text = ct.code;

-- 3) Make the foreign key columns NOT NULL and add foreign key constraints

-- Add foreign key constraints to underwriting_decision table
ALTER TABLE underwriting_decision ALTER COLUMN decision_code_id SET NOT NULL;
ALTER TABLE underwriting_decision ADD CONSTRAINT fk_underwriting_decision_decision_code
    FOREIGN KEY (decision_code_id) REFERENCES decision_code (decision_code_id);

ALTER TABLE underwriting_decision ALTER COLUMN risk_grade_id SET NOT NULL;
ALTER TABLE underwriting_decision ADD CONSTRAINT fk_underwriting_decision_risk_grade
    FOREIGN KEY (risk_grade_id) REFERENCES risk_grade (risk_grade_id);

-- Add foreign key constraints to application_document table
ALTER TABLE application_document ALTER COLUMN document_type_id SET NOT NULL;
ALTER TABLE application_document ADD CONSTRAINT fk_application_document_document_type
    FOREIGN KEY (document_type_id) REFERENCES document_type (document_type_id);

-- Add foreign key constraints to loan_application table
ALTER TABLE loan_application ALTER COLUMN application_status_id SET NOT NULL;
ALTER TABLE loan_application ADD CONSTRAINT fk_loan_application_application_status
    FOREIGN KEY (application_status_id) REFERENCES application_status (application_status_id);

ALTER TABLE loan_application ALTER COLUMN application_sub_status_id SET NOT NULL;
ALTER TABLE loan_application ADD CONSTRAINT fk_loan_application_application_sub_status
    FOREIGN KEY (application_sub_status_id) REFERENCES application_sub_status (application_sub_status_id);

ALTER TABLE loan_application ALTER COLUMN submission_channel_id SET NOT NULL;
ALTER TABLE loan_application ADD CONSTRAINT fk_loan_application_submission_channel
    FOREIGN KEY (submission_channel_id) REFERENCES submission_channel (submission_channel_id);

-- Add foreign key constraints to application_party table
ALTER TABLE application_party ALTER COLUMN role_code_id SET NOT NULL;
ALTER TABLE application_party ADD CONSTRAINT fk_application_party_role_code
    FOREIGN KEY (role_code_id) REFERENCES role_code (role_code_id);

ALTER TABLE application_party ALTER COLUMN employment_type_id SET NOT NULL;
ALTER TABLE application_party ADD CONSTRAINT fk_application_party_employment_type
    FOREIGN KEY (employment_type_id) REFERENCES employment_type (employment_type_id);

-- Add foreign key constraints to application_collateral table
ALTER TABLE application_collateral ALTER COLUMN collateral_type_id SET NOT NULL;
ALTER TABLE application_collateral ADD CONSTRAINT fk_application_collateral_collateral_type
    FOREIGN KEY (collateral_type_id) REFERENCES collateral_type (collateral_type_id);

-- 4) Drop the enum columns (optional, can be done in a later migration after ensuring everything works)

-- Drop enum columns from underwriting_decision table
-- ALTER TABLE underwriting_decision DROP COLUMN decision_code;
-- ALTER TABLE underwriting_decision DROP COLUMN risk_grade;

-- Drop enum column from application_document table
-- ALTER TABLE application_document DROP COLUMN document_type;

-- Drop enum columns from loan_application table
-- ALTER TABLE loan_application DROP COLUMN status;
-- ALTER TABLE loan_application DROP COLUMN sub_status;
-- ALTER TABLE loan_application DROP COLUMN submission_channel;

-- Drop enum columns from application_party table
-- ALTER TABLE application_party DROP COLUMN role_code;
-- ALTER TABLE application_party DROP COLUMN employment_type;

-- Drop enum column from application_collateral table
-- ALTER TABLE application_collateral DROP COLUMN collateral_type;
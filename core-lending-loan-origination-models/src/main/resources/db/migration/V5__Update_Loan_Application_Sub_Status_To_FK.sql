-- Flyway V5: Update enum columns to FK relationships
-- Database: PostgreSQL

-- Step 1: Add new FK column for application_sub_status_id
ALTER TABLE loan_application 
ADD COLUMN application_sub_status_id UUID;

-- Step 2: Add FK constraint
ALTER TABLE loan_application 
ADD CONSTRAINT fk_loan_application_application_sub_status_id 
FOREIGN KEY (application_sub_status_id) REFERENCES application_sub_status(application_sub_status_id);

-- Step 3: Drop the old enum column
ALTER TABLE loan_application 
DROP COLUMN application_sub_status;

-- Step 4: Create index for performance
CREATE INDEX idx_loan_application_application_sub_status_id 
ON loan_application(application_sub_status_id);

-- Step 5: Add new FK column for document_type_id to application_document
ALTER TABLE application_document 
ADD COLUMN document_type_id UUID;

-- Step 6: Add FK constraint for application_document.document_type_id
ALTER TABLE application_document 
ADD CONSTRAINT fk_application_document_document_type_id 
FOREIGN KEY (document_type_id) REFERENCES document_type(document_type_id);

-- Step 7: Drop the old document_type enum column
ALTER TABLE application_document 
DROP COLUMN document_type;

-- Step 8: Create index for performance
CREATE INDEX idx_application_document_document_type_id 
ON application_document(document_type_id);

-- Step 9: Add new FK column for employment_type_id to application_party
ALTER TABLE application_party 
ADD COLUMN employment_type_id UUID;

-- Step 10: Add FK constraint for application_party.employment_type_id
ALTER TABLE application_party 
ADD CONSTRAINT fk_application_party_employment_type_id 
FOREIGN KEY (employment_type_id) REFERENCES employment_type(employment_type_id);

-- Step 11: Drop the old employment_type enum column
ALTER TABLE application_party 
DROP COLUMN employment_type;

-- Step 12: Create index for performance
CREATE INDEX idx_application_party_employment_type_id 
ON application_party(employment_type_id);

-- Step 13: Add new FK column for role_code_id to application_party
ALTER TABLE application_party 
ADD COLUMN role_code_id UUID;

-- Step 14: Add FK constraint for application_party.role_code_id
ALTER TABLE application_party 
ADD CONSTRAINT fk_application_party_role_code_id 
FOREIGN KEY (role_code_id) REFERENCES role_code(role_code_id);

-- Step 15: Drop the old role_code enum column
ALTER TABLE application_party 
DROP COLUMN role_code;

-- Step 16: Create index for performance
CREATE INDEX idx_application_party_role_code_id 
ON application_party(role_code_id);

-- Step 17: Add new FK column for submission_channel_id to loan_application
ALTER TABLE loan_application 
ADD COLUMN submission_channel_id UUID;

-- Step 18: Add FK constraint for loan_application.submission_channel_id
ALTER TABLE loan_application 
ADD CONSTRAINT fk_loan_application_submission_channel_id 
FOREIGN KEY (submission_channel_id) REFERENCES submission_channel(submission_channel_id);

-- Step 19: Drop the old submission_channel enum column
ALTER TABLE loan_application 
DROP COLUMN submission_channel;

-- Step 20: Create index for performance
CREATE INDEX idx_loan_application_submission_channel_id 
ON loan_application(submission_channel_id);

-- Step 21: Add new FK column for application_status_id to loan_application
ALTER TABLE loan_application 
ADD COLUMN application_status_id UUID;

-- Step 22: Add FK constraint for loan_application.application_status_id
ALTER TABLE loan_application 
ADD CONSTRAINT fk_loan_application_application_status_id 
FOREIGN KEY (application_status_id) REFERENCES application_status(application_status_id);

-- Step 23: Drop the old application_status enum column
ALTER TABLE loan_application 
DROP COLUMN application_status;

-- Step 24: Create index for performance
CREATE INDEX idx_loan_application_application_status_id 
ON loan_application(application_status_id);

-- Step 25: Add new FK column for collateral_type_id to application_collateral
ALTER TABLE application_collateral 
ADD COLUMN collateral_type_id UUID;

-- Step 26: Add FK constraint for application_collateral.collateral_type_id
ALTER TABLE application_collateral 
ADD CONSTRAINT fk_application_collateral_collateral_type_id 
FOREIGN KEY (collateral_type_id) REFERENCES collateral_type(collateral_type_id);

-- Step 27: Drop the old collateral_type enum column
ALTER TABLE application_collateral 
DROP COLUMN collateral_type;

-- Step 28: Create index for performance
CREATE INDEX idx_application_collateral_collateral_type_id 
ON application_collateral(collateral_type_id);
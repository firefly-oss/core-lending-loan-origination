-- V9__Add_Payment_Methods_And_Bank_Accounts.sql
-- Add payment method tracking for disbursement and repayment

-- Create application_bank_account table
CREATE TABLE application_bank_account (
    bank_account_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_application_id UUID NOT NULL,
    account_usage_type VARCHAR(50) NOT NULL,
    account_holder_name VARCHAR(200) NOT NULL,
    bank_name VARCHAR(200) NOT NULL,
    bank_code VARCHAR(50),
    branch_code VARCHAR(50),
    account_number VARCHAR(50) NOT NULL,
    account_type VARCHAR(50),
    currency_code VARCHAR(3),
    iban VARCHAR(34),
    routing_number VARCHAR(50),
    is_verified BOOLEAN DEFAULT FALSE,
    verification_method VARCHAR(50),
    verified_at TIMESTAMP,
    is_primary BOOLEAN DEFAULT FALSE,
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bank_account_loan_application FOREIGN KEY (loan_application_id) 
        REFERENCES loan_application(loan_application_id) ON DELETE CASCADE
);

-- Create indexes for application_bank_account
CREATE INDEX idx_bank_account_loan_application ON application_bank_account(loan_application_id);
CREATE INDEX idx_bank_account_usage_type ON application_bank_account(account_usage_type);
CREATE INDEX idx_bank_account_is_primary ON application_bank_account(is_primary);

-- Add payment method columns to loan_application table
ALTER TABLE loan_application ADD COLUMN disbursement_method_type VARCHAR(50);
ALTER TABLE loan_application ADD COLUMN disbursement_internal_account_id UUID;
ALTER TABLE loan_application ADD COLUMN disbursement_bank_account_id UUID;
ALTER TABLE loan_application ADD COLUMN repayment_method_type VARCHAR(50);
ALTER TABLE loan_application ADD COLUMN repayment_internal_account_id UUID;
ALTER TABLE loan_application ADD COLUMN repayment_bank_account_id UUID;

-- Add foreign key constraint for disbursement_bank_account_id
ALTER TABLE loan_application 
    ADD CONSTRAINT fk_loan_app_disbursement_bank_account 
    FOREIGN KEY (disbursement_bank_account_id) 
    REFERENCES application_bank_account(bank_account_id) ON DELETE SET NULL;

-- Add foreign key constraint for repayment_bank_account_id
ALTER TABLE loan_application 
    ADD CONSTRAINT fk_loan_app_repayment_bank_account 
    FOREIGN KEY (repayment_bank_account_id) 
    REFERENCES application_bank_account(bank_account_id) ON DELETE SET NULL;

-- Add comments
COMMENT ON TABLE application_bank_account IS 'External bank accounts associated with loan applications for disbursement and repayment';
COMMENT ON COLUMN application_bank_account.account_usage_type IS 'Type of account usage: DISBURSEMENT, REPAYMENT, BOTH';
COMMENT ON COLUMN application_bank_account.is_verified IS 'Flag indicating if the account has been verified';
COMMENT ON COLUMN application_bank_account.verification_method IS 'Method used to verify the account: MICRO_DEPOSIT, INSTANT_VERIFICATION, MANUAL, etc.';
COMMENT ON COLUMN application_bank_account.is_primary IS 'Flag indicating if this is the primary account for the application';

COMMENT ON COLUMN loan_application.disbursement_method_type IS 'Method type for loan disbursement: INTERNAL_ACCOUNT, EXTERNAL_ACCOUNT';
COMMENT ON COLUMN loan_application.disbursement_internal_account_id IS 'Internal account ID for disbursement (if disbursement_method_type = INTERNAL_ACCOUNT)';
COMMENT ON COLUMN loan_application.disbursement_bank_account_id IS 'External bank account ID for disbursement (if disbursement_method_type = EXTERNAL_ACCOUNT)';
COMMENT ON COLUMN loan_application.repayment_method_type IS 'Method type for loan repayment: INTERNAL_ACCOUNT, EXTERNAL_ACCOUNT';
COMMENT ON COLUMN loan_application.repayment_internal_account_id IS 'Internal account ID for repayment (if repayment_method_type = INTERNAL_ACCOUNT)';
COMMENT ON COLUMN loan_application.repayment_bank_account_id IS 'External bank account ID for repayment (if repayment_method_type = EXTERNAL_ACCOUNT)';


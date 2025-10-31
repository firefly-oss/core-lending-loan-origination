-- V9__Add_Payment_Methods_And_Bank_Accounts.sql
-- Add payment method tracking for disbursement and repayment
-- This migration creates infrastructure to track how loans are disbursed and how repayments are collected

-- ============================================================================
-- PART 1: Create EXTERNAL bank account table
-- ============================================================================
-- This table stores ONLY external bank accounts (accounts OUTSIDE the Firefly core banking system)
-- For internal accounts (within Firefly), use the internal_account_id fields in loan_application
CREATE TABLE application_external_bank_account (
    external_bank_account_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
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
    CONSTRAINT fk_external_bank_account_loan_application FOREIGN KEY (loan_application_id)
        REFERENCES loan_application(loan_application_id) ON DELETE CASCADE
);

-- Create indexes for application_external_bank_account
CREATE INDEX idx_external_bank_account_loan_application ON application_external_bank_account(loan_application_id);
CREATE INDEX idx_external_bank_account_usage_type ON application_external_bank_account(account_usage_type);
CREATE INDEX idx_external_bank_account_is_primary ON application_external_bank_account(is_primary);

-- ============================================================================
-- PART 2: Add payment method columns to loan_application table
-- ============================================================================
-- These columns track HOW the loan will be disbursed and HOW repayments will be collected
-- Each method has two parts:
--   1. method_type: INTERNAL_ACCOUNT or EXTERNAL_ACCOUNT
--   2. account reference: Either internal_account_id OR external_bank_account_id (mutually exclusive)

-- Disbursement method columns
ALTER TABLE loan_application ADD COLUMN disbursement_method_type VARCHAR(50);
ALTER TABLE loan_application ADD COLUMN disbursement_internal_account_id UUID;
ALTER TABLE loan_application ADD COLUMN disbursement_external_bank_account_id UUID;

-- Repayment method columns
ALTER TABLE loan_application ADD COLUMN repayment_method_type VARCHAR(50);
ALTER TABLE loan_application ADD COLUMN repayment_internal_account_id UUID;
ALTER TABLE loan_application ADD COLUMN repayment_external_bank_account_id UUID;

-- ============================================================================
-- PART 3: Add foreign key constraints
-- ============================================================================
-- Link external bank account references to the external bank account table
ALTER TABLE loan_application
    ADD CONSTRAINT fk_loan_app_disbursement_external_bank_account
    FOREIGN KEY (disbursement_external_bank_account_id)
    REFERENCES application_external_bank_account(external_bank_account_id) ON DELETE SET NULL;

ALTER TABLE loan_application
    ADD CONSTRAINT fk_loan_app_repayment_external_bank_account
    FOREIGN KEY (repayment_external_bank_account_id)
    REFERENCES application_external_bank_account(external_bank_account_id) ON DELETE SET NULL;

-- ============================================================================
-- PART 4: Add documentation comments
-- ============================================================================
COMMENT ON TABLE application_external_bank_account IS 'EXTERNAL bank accounts (outside Firefly core banking) for loan disbursement and repayment. For INTERNAL accounts, use the internal_account_id fields in loan_application.';
COMMENT ON COLUMN application_external_bank_account.account_usage_type IS 'How this external account will be used: DISBURSEMENT (receive loan funds), REPAYMENT (direct debit for installments), or BOTH';
COMMENT ON COLUMN application_external_bank_account.is_verified IS 'Whether the account has been verified (e.g., via micro-deposits, instant verification, or manual verification)';
COMMENT ON COLUMN application_external_bank_account.verification_method IS 'Method used to verify the account: MICRO_DEPOSIT, INSTANT_VERIFICATION, MANUAL, etc.';
COMMENT ON COLUMN application_external_bank_account.is_primary IS 'Whether this is the primary external account for the application';

COMMENT ON COLUMN loan_application.disbursement_method_type IS 'HOW the loan will be disbursed: INTERNAL_ACCOUNT (to Firefly account) or EXTERNAL_ACCOUNT (to external bank). Only ONE of disbursement_internal_account_id or disbursement_external_bank_account_id should be set.';
COMMENT ON COLUMN loan_application.disbursement_internal_account_id IS 'Internal account ID for disbursement (ONLY when disbursement_method_type = INTERNAL_ACCOUNT). References an account in the Firefly core banking system. Must be NULL when method type is EXTERNAL_ACCOUNT.';
COMMENT ON COLUMN loan_application.disbursement_external_bank_account_id IS 'External bank account ID for disbursement (ONLY when disbursement_method_type = EXTERNAL_ACCOUNT). References application_external_bank_account table. Must be NULL when method type is INTERNAL_ACCOUNT.';

COMMENT ON COLUMN loan_application.repayment_method_type IS 'HOW loan repayments will be collected: INTERNAL_ACCOUNT (debit from Firefly account) or EXTERNAL_ACCOUNT (direct debit/domiciliación from external bank). Only ONE of repayment_internal_account_id or repayment_external_bank_account_id should be set.';
COMMENT ON COLUMN loan_application.repayment_internal_account_id IS 'Internal account ID for repayment (ONLY when repayment_method_type = INTERNAL_ACCOUNT). References an account in the Firefly core banking system. Must be NULL when method type is EXTERNAL_ACCOUNT.';
COMMENT ON COLUMN loan_application.repayment_external_bank_account_id IS 'External bank account ID for repayment (ONLY when repayment_method_type = EXTERNAL_ACCOUNT). References application_external_bank_account table for direct debit/domiciliación setup. Must be NULL when method type is INTERNAL_ACCOUNT.';


-- Flyway V13: Rename repayment columns to payment_collection for better clarity
-- Database: PostgreSQL
-- Reason: "Payment Collection" is more comprehensible than "Repayment" for describing how loan payments are collected

-- Rename repayment_method_type to payment_collection_method_type
ALTER TABLE loan_application 
RENAME COLUMN repayment_method_type TO payment_collection_method_type;

-- Rename repayment_internal_account_id to payment_collection_internal_account_id
ALTER TABLE loan_application 
RENAME COLUMN repayment_internal_account_id TO payment_collection_internal_account_id;

-- Rename repayment_external_bank_account_id to payment_collection_external_bank_account_id
ALTER TABLE loan_application 
RENAME COLUMN repayment_external_bank_account_id TO payment_collection_external_bank_account_id;

-- Drop old foreign key constraint
ALTER TABLE loan_application 
DROP CONSTRAINT IF EXISTS fk_loan_app_repayment_external_bank_account;

-- Add new foreign key constraint with updated name
ALTER TABLE loan_application 
ADD CONSTRAINT fk_loan_app_payment_collection_external_bank_account
FOREIGN KEY (payment_collection_external_bank_account_id)
REFERENCES application_external_bank_account(external_bank_account_id);

-- Update column comments
COMMENT ON COLUMN loan_application.payment_collection_method_type IS 'HOW loan payments will be collected: INTERNAL_ACCOUNT (debit from Firefly account) or EXTERNAL_ACCOUNT (direct debit/domiciliación from external bank). Only ONE of payment_collection_internal_account_id or payment_collection_external_bank_account_id should be set.';

COMMENT ON COLUMN loan_application.payment_collection_internal_account_id IS 'Internal account ID for payment collection (ONLY when payment_collection_method_type = INTERNAL_ACCOUNT). References an account in the Firefly core banking system. Must be NULL when method type is EXTERNAL_ACCOUNT.';

COMMENT ON COLUMN loan_application.payment_collection_external_bank_account_id IS 'External bank account ID for payment collection (ONLY when payment_collection_method_type = EXTERNAL_ACCOUNT). References application_external_bank_account table for direct debit/domiciliación setup. Must be NULL when method type is INTERNAL_ACCOUNT.';

-- Update table comment to reflect the new terminology
COMMENT ON TABLE application_external_bank_account IS 'EXTERNAL bank accounts (outside Firefly core banking) for loan disbursement and payment collection. For INTERNAL accounts, use the internal_account_id fields in loan_application.';


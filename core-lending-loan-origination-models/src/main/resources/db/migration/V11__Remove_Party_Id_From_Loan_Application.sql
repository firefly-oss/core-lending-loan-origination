-- Flyway V11: Remove party_id column from loan_application table
-- Database: PostgreSQL
-- Reason: Party relationships are now managed through the application_party table

-- Remove the party_id column from loan_application
ALTER TABLE loan_application 
DROP COLUMN IF EXISTS party_id;


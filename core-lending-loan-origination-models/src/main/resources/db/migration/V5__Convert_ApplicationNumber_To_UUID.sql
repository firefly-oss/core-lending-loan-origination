-- V5__Convert_ApplicationNumber_To_UUID.sql
-- Migration script to convert application_number column from VARCHAR to UUID
-- Date: 2025-07-29

-- 1) Add a new UUID column
ALTER TABLE loan_application ADD COLUMN application_number_uuid UUID;

-- 2) Convert existing application_number values to UUID
-- For existing records, try to convert the string to UUID if it's in UUID format
-- If not, generate a new UUID for each record
UPDATE loan_application 
SET application_number_uuid = 
    CASE 
        WHEN application_number ~ '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$' THEN 
            application_number::UUID
        ELSE 
            gen_random_uuid() 
    END;

-- 3) Drop the old column and rename the new one
ALTER TABLE loan_application DROP COLUMN application_number;
ALTER TABLE loan_application RENAME COLUMN application_number_uuid TO application_number;

-- 4) Add NOT NULL constraint and set default value for auto-generation
ALTER TABLE loan_application ALTER COLUMN application_number SET NOT NULL;
ALTER TABLE loan_application ALTER COLUMN application_number SET DEFAULT gen_random_uuid();

-- 5) Add comment to explain the purpose of this field
COMMENT ON COLUMN loan_application.application_number IS 'Auto-generated UUID that uniquely identifies the application';
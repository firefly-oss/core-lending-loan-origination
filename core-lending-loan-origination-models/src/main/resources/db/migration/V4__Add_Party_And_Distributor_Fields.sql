-- V4__Add_Party_And_Distributor_Fields.sql
-- Migration script to add DISTRIBUTOR to submission_channel enum and add party_id and distributor_id columns to loan_application table
-- Date: 2025-07-29

-- 1) Add DISTRIBUTOR to submission_channel enum
-- Note: PostgreSQL doesn't allow adding values to an enum type directly with ALTER TYPE ... ADD VALUE
-- We need to create a new type, update all tables and functions, and then drop the old type
-- This is a safer approach that works with existing data

-- Create a new enum type with all values including the new one
CREATE TYPE submission_channel_new AS ENUM (
  'BRANCH',
  'ONLINE',
  'MOBILE',
  'DISTRIBUTOR'
);

-- Update the loan_application table to use the new type
-- First, create a new column with the new type
ALTER TABLE loan_application ADD COLUMN submission_channel_new submission_channel_new;

-- Copy data from the old column to the new one
UPDATE loan_application SET submission_channel_new = submission_channel::text::submission_channel_new;

-- Drop the old column and rename the new one
ALTER TABLE loan_application DROP COLUMN submission_channel;
ALTER TABLE loan_application RENAME COLUMN submission_channel_new TO submission_channel;

-- Drop the dependent cast to avoid type drop failure
DROP CAST IF EXISTS (varchar AS submission_channel);

-- Drop the old type
DROP TYPE submission_channel;

-- Rename the new type to the original name
ALTER TYPE submission_channel_new RENAME TO submission_channel;

-- Recreate the cast for the new enum type
CREATE CAST (varchar AS submission_channel)
    WITH INOUT
    AS IMPLICIT;

-- 2) Add party_id and distributor_id columns to loan_application table
ALTER TABLE loan_application ADD COLUMN party_id BIGINT;
ALTER TABLE loan_application ADD COLUMN distributor_id BIGINT;

-- Add comments to explain the purpose of these fields
COMMENT ON COLUMN loan_application.party_id IS 'Identifier for a known customer who launched the application';
COMMENT ON COLUMN loan_application.distributor_id IS 'Identifier for a known distributor who launched the application';
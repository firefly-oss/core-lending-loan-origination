-- Flyway V7: Add distributor_agency_id and distributor_agent_id columns to loan_application table
-- Database: PostgreSQL

-- Add distributor_agency_id column
ALTER TABLE loan_application 
ADD COLUMN distributor_agency_id UUID;

-- Add distributor_agent_id column
ALTER TABLE loan_application 
ADD COLUMN distributor_agent_id UUID;

-- Create indexes for performance
CREATE INDEX idx_loan_application_distributor_agency_id 
ON loan_application(distributor_agency_id);

CREATE INDEX idx_loan_application_distributor_agent_id 
ON loan_application(distributor_agent_id);


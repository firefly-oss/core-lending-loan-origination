-- Flyway V6: Add CANCELLED status to application_status_enum
-- Database: PostgreSQL

-- Add CANCELLED value to the existing application_status_enum
ALTER TYPE application_status_enum ADD VALUE 'CANCELLED';
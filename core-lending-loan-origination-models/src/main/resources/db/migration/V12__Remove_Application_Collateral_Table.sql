-- Flyway V12: Remove collateral-related tables and enums
-- Database: PostgreSQL
-- Reason: Collateral management is now handled by the dedicated core-lending-collateral-management microservice
--         The CollateralCase entity in that service has a loanApplicationId field to link back to loan applications

-- Drop the application_collateral table
DROP TABLE IF EXISTS application_collateral CASCADE;

-- Drop the collateral_type catalog table
DROP TABLE IF EXISTS collateral_type CASCADE;

-- Drop the collateral_type_enum type
DROP TYPE IF EXISTS collateral_type_enum CASCADE;


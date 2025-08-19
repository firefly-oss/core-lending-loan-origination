-- Flyway V3: Create implicit casts from varchar to domain ENUM types
-- Database: PostgreSQL

-- Application Status casts
CREATE CAST (varchar AS application_status_enum) WITH INOUT AS IMPLICIT;

-- Application Sub-Status casts
CREATE CAST (varchar AS application_sub_status_enum) WITH INOUT AS IMPLICIT;

-- Submission Channel casts
CREATE CAST (varchar AS submission_channel_enum) WITH INOUT AS IMPLICIT;

-- Collateral Type casts
CREATE CAST (varchar AS collateral_type_enum) WITH INOUT AS IMPLICIT;

-- Document Type casts
CREATE CAST (varchar AS document_type_enum) WITH INOUT AS IMPLICIT;

-- Employment Type casts
CREATE CAST (varchar AS employment_type_enum) WITH INOUT AS IMPLICIT;

-- Role Code casts
CREATE CAST (varchar AS role_code_enum) WITH INOUT AS IMPLICIT;

-- V3__Create_Casts.sql

-------------------------
-- application_status
-------------------------
CREATE CAST (varchar AS application_status)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- application_sub_status
-------------------------
CREATE CAST (varchar AS application_sub_status)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- submission_channel
-------------------------
CREATE CAST (varchar AS submission_channel)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- role_code
-------------------------
CREATE CAST (varchar AS role_code)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- employment_type
-------------------------
CREATE CAST (varchar AS employment_type)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- document_type
-------------------------
CREATE CAST (varchar AS document_type)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- collateral_type
-------------------------
CREATE CAST (varchar AS collateral_type)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- decision_code
-------------------------
CREATE CAST (varchar AS decision_code)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- risk_grade
-------------------------
CREATE CAST (varchar AS risk_grade)
    WITH INOUT
    AS IMPLICIT;

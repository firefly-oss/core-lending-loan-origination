-- Flyway V2: Create tables using ENUM types
-- Database: PostgreSQL

-- 1) Core table: Loan Application
CREATE TABLE loan_application (
    loan_application_id BIGSERIAL PRIMARY KEY,
    application_number UUID,
    application_status application_status_enum,
    application_sub_status application_sub_status_enum,
    application_date DATE,
    submission_channel submission_channel_enum,
    party_id BIGINT,
    distributor_id BIGINT,
    note TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE UNIQUE INDEX ux_loan_application_application_number ON loan_application(application_number);

-- 2) Catalog tables
CREATE TABLE application_status (
    application_status_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_application_status_code ON application_status(code);

CREATE TABLE application_sub_status (
    application_sub_status_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_application_sub_status_code ON application_sub_status(code);

CREATE TABLE collateral_type (
    collateral_type_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_collateral_type_code ON collateral_type(code);

CREATE TABLE decision_code (
    decision_code_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_decision_code_code ON decision_code(code);

CREATE TABLE document_type (
    document_type_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_document_type_code ON document_type(code);

CREATE TABLE employment_type (
    employment_type_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_employment_type_code ON employment_type(code);

CREATE TABLE risk_grade (
    risk_grade_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_risk_grade_code ON risk_grade(code);

CREATE TABLE role_code (
    role_code_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_role_code_code ON role_code(code);

CREATE TABLE submission_channel (
    submission_channel_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX ux_submission_channel_code ON submission_channel(code);

-- 3) Domain tables
CREATE TABLE application_collateral (
    application_collateral_id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT REFERENCES loan_application(loan_application_id),
    collateral_type collateral_type_enum,
    estimated_value NUMERIC(19,4),
    ownership_details TEXT,
    is_primary_collateral BOOLEAN,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE INDEX ix_application_collateral_loan_application_id ON application_collateral(loan_application_id);

CREATE TABLE underwriting_decision (
    underwriting_decision_id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT REFERENCES loan_application(loan_application_id),
    decision_date TIMESTAMP WITHOUT TIME ZONE,
    decision_code_id BIGINT REFERENCES decision_code(decision_code_id),
    approved_amount NUMERIC(19,4),
    approved_interest_rate NUMERIC(9,6),
    tenor_months INTEGER,
    risk_grade_id BIGINT REFERENCES risk_grade(risk_grade_id),
    remarks TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE INDEX ix_underwriting_decision_loan_application_id ON underwriting_decision(loan_application_id);
CREATE INDEX ix_underwriting_decision_decision_code_id ON underwriting_decision(decision_code_id);
CREATE INDEX ix_underwriting_decision_risk_grade_id ON underwriting_decision(risk_grade_id);

CREATE TABLE application_document (
    application_document_id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT REFERENCES loan_application(loan_application_id),
    document_id BIGINT,
    document_type document_type_enum,
    is_mandatory BOOLEAN,
    is_received BOOLEAN,
    received_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE INDEX ix_application_document_loan_application_id ON application_document(loan_application_id);

CREATE TABLE proposed_offer (
    proposed_offer_id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT REFERENCES loan_application(loan_application_id),
    product_id BIGINT,
    requested_amount NUMERIC(19,4),
    requested_tenor_months INTEGER,
    requested_interest_rate NUMERIC(9,6),
    valid_until DATE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE INDEX ix_proposed_offer_loan_application_id ON proposed_offer(loan_application_id);

CREATE TABLE application_party (
    application_party_id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT REFERENCES loan_application(loan_application_id),
    party_id BIGINT,
    role_code role_code_enum,
    share_percentage NUMERIC(5,2),
    annual_income NUMERIC(19,4),
    monthly_expenses NUMERIC(19,4),
    employment_type employment_type_enum,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE INDEX ix_application_party_loan_application_id ON application_party(loan_application_id);
CREATE INDEX ix_application_party_party_id ON application_party(party_id);

CREATE TABLE underwriting_score (
    underwriting_score_id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT REFERENCES loan_application(loan_application_id),
    score_value NUMERIC(19,4),
    scorecard_name VARCHAR(255),
    reason_codes TEXT,
    scoring_timestamp TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE INDEX ix_underwriting_score_loan_application_id ON underwriting_score(loan_application_id);

CREATE TABLE loan_application_status_history (
    status_history_id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT REFERENCES loan_application(loan_application_id),
    old_status application_status_enum,
    new_status application_status_enum,
    change_reason TEXT,
    changed_at TIMESTAMP WITHOUT TIME ZONE,
    changed_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE INDEX ix_loan_application_status_history_loan_application_id ON loan_application_status_history(loan_application_id);

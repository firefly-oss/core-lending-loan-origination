-- V2 - CREATE TABLES (IF NOT EXISTS) USING THE ENUM TYPES

-- ------------------------------------------------------------------------
-- TABLE: loan_application
-- ------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS loan_application (
                                                loan_application_id       BIGSERIAL PRIMARY KEY,
                                                application_number        VARCHAR(50) NOT NULL,
    application_status        application_status NOT NULL,
    application_sub_status    application_sub_status,
    application_date          DATE,
    submission_channel        submission_channel,
    note                      TEXT,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW()
    );

-- ------------------------------------------------------------------------
-- TABLE: loan_application_status_history
-- ------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS loan_application_status_history (
                                                               status_history_id         BIGSERIAL PRIMARY KEY,
                                                               loan_application_id       BIGINT NOT NULL,
                                                               old_status                application_status,
                                                               new_status                application_status,
                                                               change_reason             VARCHAR(255),
    changed_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    changed_by                VARCHAR(100),
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_loan_app_status_history_app
    FOREIGN KEY (loan_application_id)
    REFERENCES loan_application (loan_application_id)
    );

-- ------------------------------------------------------------------------
-- TABLE: application_party
-- ------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS application_party (
                                                 application_party_id      BIGSERIAL PRIMARY KEY,
                                                 loan_application_id       BIGINT NOT NULL,
                                                 party_id                  BIGINT NOT NULL,
                                                 role_code                 role_code,
                                                 share_percentage          DECIMAL(10, 4),
    annual_income             DECIMAL(15, 2),
    monthly_expenses          DECIMAL(15, 2),
    employment_type           employment_type,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_app_party_loan_app
    FOREIGN KEY (loan_application_id)
    REFERENCES loan_application (loan_application_id)
    );

-- ------------------------------------------------------------------------
-- TABLE: application_document
-- ------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS application_document (
                                                    application_document_id   BIGSERIAL PRIMARY KEY,
                                                    loan_application_id       BIGINT NOT NULL,
                                                    document_id               BIGINT NOT NULL,  -- Reference to Document Manager domain
                                                    document_type             document_type,
                                                    is_mandatory              BOOLEAN DEFAULT FALSE,
                                                    is_received               BOOLEAN DEFAULT FALSE,
                                                    received_at               TIMESTAMP,
                                                    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_doc_loan_app
    FOREIGN KEY (loan_application_id)
    REFERENCES loan_application (loan_application_id)
    );

-- ------------------------------------------------------------------------
-- TABLE: application_collateral
-- ------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS application_collateral (
                                                      application_collateral_id BIGSERIAL PRIMARY KEY,
                                                      loan_application_id       BIGINT NOT NULL,
                                                      collateral_type           collateral_type,
                                                      estimated_value           DECIMAL(15, 2),
    ownership_details         TEXT,
    is_primary_collateral     BOOLEAN DEFAULT FALSE,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_coll_loan_app
    FOREIGN KEY (loan_application_id)
    REFERENCES loan_application (loan_application_id)
    );

-- ------------------------------------------------------------------------
-- TABLE: proposed_offer
-- ------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS proposed_offer (
                                              proposed_offer_id         BIGSERIAL PRIMARY KEY,
                                              loan_application_id       BIGINT NOT NULL,
                                              product_id                BIGINT NOT NULL,  -- Reference to Product Management domain
                                              requested_amount          DECIMAL(15, 2),
    requested_tenor_months    INT,
    requested_interest_rate   DECIMAL(5, 2),
    valid_until               DATE,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_offer_loan_app
    FOREIGN KEY (loan_application_id)
    REFERENCES loan_application (loan_application_id)
    );

-- ------------------------------------------------------------------------
-- TABLE: underwriting_score
-- ------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS underwriting_score (
                                                  underwriting_score_id     BIGSERIAL PRIMARY KEY,
                                                  loan_application_id       BIGINT NOT NULL,
                                                  score_value               DECIMAL(5, 2),
    scorecard_name            VARCHAR(100),
    reason_codes              TEXT,   -- could store JSON array
    scoring_timestamp         TIMESTAMP,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_score_loan_app
    FOREIGN KEY (loan_application_id)
    REFERENCES loan_application (loan_application_id)
    );

-- ------------------------------------------------------------------------
-- TABLE: underwriting_decision
-- ------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS underwriting_decision (
                                                     underwriting_decision_id  BIGSERIAL PRIMARY KEY,
                                                     loan_application_id       BIGINT NOT NULL,
                                                     decision_date             TIMESTAMP,
                                                     decision_code             decision_code,
                                                     approved_amount           DECIMAL(15, 2),
    approved_interest_rate    DECIMAL(5, 2),
    tenor_months              INT,
    risk_grade                risk_grade,
    remarks                   TEXT,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_dec_loan_app
    FOREIGN KEY (loan_application_id)
    REFERENCES loan_application (loan_application_id)
    );

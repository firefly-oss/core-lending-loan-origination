-- Flyway V8: Add new entities and enhance existing tables
-- Database: PostgreSQL

-- =====================================================
-- PHASE 1: CRITICAL ENTITIES
-- =====================================================

-- 1. Application Verification Table
CREATE TABLE application_verification (
    verification_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    verification_type VARCHAR(50) NOT NULL, -- IDENTITY, INCOME, EMPLOYMENT, REFERENCE, ADDRESS
    verification_status VARCHAR(50) NOT NULL, -- PENDING, IN_PROGRESS, VERIFIED, FAILED, WAIVED
    verification_method VARCHAR(50), -- MANUAL, AUTOMATED, THIRD_PARTY
    verified_by VARCHAR(255),
    verified_at TIMESTAMP WITHOUT TIME ZONE,
    verification_data JSONB, -- Flexible verification data
    notes TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_verification_loan_application_id ON application_verification(loan_application_id);
CREATE INDEX idx_application_verification_status ON application_verification(verification_status);
CREATE INDEX idx_application_verification_type ON application_verification(verification_type);

-- 2. Application Condition Table
CREATE TABLE application_condition (
    condition_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    condition_type VARCHAR(50) NOT NULL, -- PRE_APPROVAL, POST_APPROVAL, CLOSING
    condition_category VARCHAR(100), -- DOCUMENT, VERIFICATION, LEGAL, FINANCIAL
    description TEXT NOT NULL,
    is_mandatory BOOLEAN DEFAULT TRUE,
    status VARCHAR(50) NOT NULL, -- PENDING, IN_PROGRESS, SATISFIED, WAIVED, EXPIRED
    due_date DATE,
    satisfied_date TIMESTAMP WITHOUT TIME ZONE,
    satisfied_by VARCHAR(255),
    waived_reason TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_condition_loan_application_id ON application_condition(loan_application_id);
CREATE INDEX idx_application_condition_status ON application_condition(status);
CREATE INDEX idx_application_condition_type ON application_condition(condition_type);

-- 3. Application Task Table
CREATE TABLE application_task (
    task_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    task_type VARCHAR(100) NOT NULL, -- REVIEW_DOCUMENTS, VERIFY_INCOME, CALL_APPLICANT, etc.
    task_status VARCHAR(50) NOT NULL, -- PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    priority VARCHAR(20), -- LOW, MEDIUM, HIGH, URGENT
    assigned_to VARCHAR(255),
    assigned_at TIMESTAMP WITHOUT TIME ZONE,
    due_date TIMESTAMP WITHOUT TIME ZONE,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    completed_by VARCHAR(255),
    description TEXT,
    notes TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_task_loan_application_id ON application_task(loan_application_id);
CREATE INDEX idx_application_task_status ON application_task(task_status);
CREATE INDEX idx_application_task_assigned_to ON application_task(assigned_to);
CREATE INDEX idx_application_task_due_date ON application_task(due_date);

-- 4. Application Fee Table
CREATE TABLE application_fee (
    fee_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    fee_type VARCHAR(100) NOT NULL, -- ORIGINATION, PROCESSING, APPRAISAL, INSURANCE, etc.
    fee_name VARCHAR(255) NOT NULL,
    fee_amount NUMERIC(19,4),
    fee_percentage NUMERIC(9,6),
    is_refundable BOOLEAN DEFAULT FALSE,
    is_financed BOOLEAN DEFAULT FALSE,
    payment_status VARCHAR(50) NOT NULL, -- PENDING, PAID, WAIVED, REFUNDED
    paid_at TIMESTAMP WITHOUT TIME ZONE,
    waived_reason TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_fee_loan_application_id ON application_fee(loan_application_id);
CREATE INDEX idx_application_fee_payment_status ON application_fee(payment_status);

-- =====================================================
-- PHASE 2: IMPORTANT ENTITIES
-- =====================================================

-- 5. Application Communication Table
CREATE TABLE application_communication (
    communication_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    communication_type VARCHAR(50) NOT NULL, -- EMAIL, SMS, PHONE, IN_PERSON, SYSTEM
    direction VARCHAR(20) NOT NULL, -- INBOUND, OUTBOUND
    subject VARCHAR(500),
    content TEXT,
    recipient VARCHAR(255),
    sender VARCHAR(255),
    status VARCHAR(50), -- SENT, DELIVERED, FAILED, READ
    sent_at TIMESTAMP WITHOUT TIME ZONE,
    delivered_at TIMESTAMP WITHOUT TIME ZONE,
    read_at TIMESTAMP WITHOUT TIME ZONE,
    metadata JSONB,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_communication_loan_application_id ON application_communication(loan_application_id);
CREATE INDEX idx_application_communication_type ON application_communication(communication_type);
CREATE INDEX idx_application_communication_sent_at ON application_communication(sent_at);

-- 6. Application Comment Table
CREATE TABLE application_comment (
    comment_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    comment_type VARCHAR(50) NOT NULL, -- INTERNAL, EXTERNAL, SYSTEM
    comment_text TEXT NOT NULL,
    is_pinned BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_comment_loan_application_id ON application_comment(loan_application_id);
CREATE INDEX idx_application_comment_is_pinned ON application_comment(is_pinned);

-- 7. Application Exception Table
CREATE TABLE application_exception (
    exception_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    exception_type VARCHAR(100) NOT NULL, -- POLICY, CREDIT, COLLATERAL, INCOME, etc.
    exception_reason TEXT NOT NULL,
    requested_by VARCHAR(255),
    requested_at TIMESTAMP WITHOUT TIME ZONE,
    approval_status VARCHAR(50) NOT NULL, -- PENDING, APPROVED, REJECTED
    approved_by VARCHAR(255),
    approved_at TIMESTAMP WITHOUT TIME ZONE,
    rejection_reason TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_exception_loan_application_id ON application_exception(loan_application_id);
CREATE INDEX idx_application_exception_approval_status ON application_exception(approval_status);

-- =====================================================
-- PHASE 4: DESIRABLE ENTITIES
-- =====================================================

-- 8. Application External Call Table
CREATE TABLE application_external_call (
    call_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    service_name VARCHAR(100) NOT NULL, -- CREDIT_BUREAU, FRAUD_CHECK, INCOME_VERIFICATION, etc.
    call_type VARCHAR(50),
    request_data JSONB,
    response_data JSONB,
    status VARCHAR(50) NOT NULL, -- SUCCESS, FAILED, TIMEOUT
    response_time_ms INTEGER,
    error_message TEXT,
    called_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_external_call_loan_application_id ON application_external_call(loan_application_id);
CREATE INDEX idx_application_external_call_service_name ON application_external_call(service_name);
CREATE INDEX idx_application_external_call_called_at ON application_external_call(called_at);

-- 9. Application Metric Table
CREATE TABLE application_metric (
    metric_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_application_id UUID REFERENCES loan_application(loan_application_id),
    metric_name VARCHAR(100) NOT NULL,
    metric_value NUMERIC(19,4),
    metric_unit VARCHAR(50),
    calculated_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_application_metric_loan_application_id ON application_metric(loan_application_id);
CREATE INDEX idx_application_metric_name ON application_metric(metric_name);

-- =====================================================
-- PHASE 3: ENHANCE EXISTING TABLES
-- =====================================================

-- Enhance loan_application table
ALTER TABLE loan_application 
ADD COLUMN business_unit VARCHAR(100),
ADD COLUMN branch_id UUID,
ADD COLUMN loan_purpose VARCHAR(100),
ADD COLUMN loan_purpose_description TEXT,
ADD COLUMN submitted_at TIMESTAMP WITHOUT TIME ZONE,
ADD COLUMN first_review_at TIMESTAMP WITHOUT TIME ZONE,
ADD COLUMN decision_due_date DATE,
ADD COLUMN expected_closing_date DATE,
ADD COLUMN loan_officer_id UUID,
ADD COLUMN assigned_at TIMESTAMP WITHOUT TIME ZONE,
ADD COLUMN is_rush BOOLEAN DEFAULT FALSE,
ADD COLUMN is_exception BOOLEAN DEFAULT FALSE,
ADD COLUMN requires_manual_review BOOLEAN DEFAULT FALSE,
ADD COLUMN external_reference_number VARCHAR(255),
ADD COLUMN source_system VARCHAR(100);

CREATE INDEX idx_loan_application_branch_id ON loan_application(branch_id);
CREATE INDEX idx_loan_application_loan_officer_id ON loan_application(loan_officer_id);
CREATE INDEX idx_loan_application_decision_due_date ON loan_application(decision_due_date);
CREATE INDEX idx_loan_application_is_rush ON loan_application(is_rush);

-- Enhance application_document table
ALTER TABLE application_document
ADD COLUMN document_name VARCHAR(500),
ADD COLUMN document_url TEXT,
ADD COLUMN file_size_bytes BIGINT,
ADD COLUMN mime_type VARCHAR(100),
ADD COLUMN verification_status VARCHAR(50),
ADD COLUMN verified_by VARCHAR(255),
ADD COLUMN verified_at TIMESTAMP WITHOUT TIME ZONE,
ADD COLUMN rejection_reason TEXT,
ADD COLUMN expiry_date DATE;

CREATE INDEX idx_application_document_verification_status ON application_document(verification_status);
CREATE INDEX idx_application_document_expiry_date ON application_document(expiry_date);

-- Enhance proposed_offer table
ALTER TABLE proposed_offer
ADD COLUMN offer_status VARCHAR(50),
ADD COLUMN accepted_at TIMESTAMP WITHOUT TIME ZONE,
ADD COLUMN accepted_by VARCHAR(255),
ADD COLUMN rejection_reason TEXT,
ADD COLUMN monthly_payment NUMERIC(19,4),
ADD COLUMN total_interest NUMERIC(19,4),
ADD COLUMN total_amount NUMERIC(19,4),
ADD COLUMN apr NUMERIC(9,6);

CREATE INDEX idx_proposed_offer_status ON proposed_offer(offer_status);


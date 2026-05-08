-- Flyway V15: BE-4 - Economic and employment data on application_party
-- Database: PostgreSQL
-- Adds 12 economic / employment columns + an is_primary flag on application_party,
-- backfills is_primary for the earliest party of each existing application,
-- enforces a partial unique index (one primary per application),
-- and adds simulation_id on loan_application linking back to the simulation table (V14).
-- Note: existing column employment_type_id (FK to employment_type catalog) is left untouched.
--       The new employment_type_label is a free-form VARCHAR carrying the label sent by the front-end.

ALTER TABLE application_party
    ADD COLUMN employment_status      VARCHAR(50),
    ADD COLUMN employment_type_label  VARCHAR(50),
    ADD COLUMN employer               VARCHAR(255),
    ADD COLUMN position               VARCHAR(255),
    ADD COLUMN employment_start_date  DATE,
    ADD COLUMN annual_paydays         SMALLINT,
    ADD COLUMN monthly_salary         NUMERIC(19, 4),
    ADD COLUMN housing_type           VARCHAR(20),
    ADD COLUMN housing_cost           NUMERIC(19, 4),
    ADD COLUMN housing_start_date     DATE,
    ADD COLUMN existing_loans         SMALLINT,
    ADD COLUMN other_debts            NUMERIC(19, 4),
    ADD COLUMN is_primary             BOOLEAN NOT NULL DEFAULT FALSE;

-- Backfill: mark the earliest party (created_at, then application_party_id) of each
-- existing application as the primary one, so the partial unique index below holds
-- without conflicts.
UPDATE application_party ap
   SET is_primary = TRUE
 WHERE ap.application_party_id = (
           SELECT inner_ap.application_party_id
             FROM application_party inner_ap
            WHERE inner_ap.loan_application_id = ap.loan_application_id
            ORDER BY inner_ap.created_at, inner_ap.application_party_id
            LIMIT 1
       );

-- Enforce: at most one primary party per application.
CREATE UNIQUE INDEX ux_application_party_primary
    ON application_party (loan_application_id)
    WHERE is_primary = TRUE;

-- Soft link from a loan application back to the simulation that produced it.
-- Soft link (no FK) because future product types may produce simulations in
-- their own tables (no shared polymorphic table — see V14 header).
ALTER TABLE loan_application
    ADD COLUMN simulation_id UUID;

CREATE INDEX ix_loan_application_simulation_id ON loan_application (simulation_id);

COMMENT ON COLUMN application_party.employment_status IS 'Employment status (e.g. EMPLOYED, SELF_EMPLOYED, RETIRED, UNEMPLOYED).';
COMMENT ON COLUMN application_party.employment_type_label IS 'Free-form employment type label sent by the front-end. Distinct from the FK column employment_type_id.';
COMMENT ON COLUMN application_party.employer IS 'Name of the employer.';
COMMENT ON COLUMN application_party.position IS 'Job position / title.';
COMMENT ON COLUMN application_party.employment_start_date IS 'Date when current employment started.';
COMMENT ON COLUMN application_party.annual_paydays IS 'Number of paydays per year (typically 12 or 14).';
COMMENT ON COLUMN application_party.monthly_salary IS 'Net monthly salary.';
COMMENT ON COLUMN application_party.housing_type IS 'Housing situation (OWNED, RENTED, MORTGAGED, FAMILY, OTHER).';
COMMENT ON COLUMN application_party.housing_cost IS 'Monthly housing cost (rent or mortgage payment).';
COMMENT ON COLUMN application_party.housing_start_date IS 'Date when current housing situation started.';
COMMENT ON COLUMN application_party.existing_loans IS 'Number of currently active loans.';
COMMENT ON COLUMN application_party.other_debts IS 'Total monthly amount of other recurring debts.';
COMMENT ON COLUMN application_party.is_primary IS 'TRUE if this party is the primary applicant for the loan application. Enforced unique per loan_application_id by ux_application_party_primary.';

COMMENT ON COLUMN loan_application.simulation_id IS 'Soft link to the simulation that produced this application (table simulation, V14). No FK across bounded contexts.';

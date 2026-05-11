-- Flyway V17: Seed canonical default rows for the document_type lookup table.
--
-- These rows let the domain tier translate a textual code supplied by the
-- experience BFF (e.g. "PAYSLIP") into a stable documentTypeId UUID without
-- forcing every upstream caller to know the FK value. The IDs below are
-- deterministic so the domain handler can reference them as compile-time
-- constants and fall back to OTHER (...d99) when an upstream caller has
-- not supplied a code or supplies an unknown one.
--
-- ON CONFLICT DO NOTHING keeps the migration idempotent: if a row with
-- the same code was inserted by an earlier ad-hoc seed (or a test
-- fixture), the existing row wins. The unique index on `code` guarantees
-- the conflict target is well-defined.
-- Database: PostgreSQL
INSERT INTO document_type (document_type_id, code, name, description, is_active)
VALUES
    ('00000000-0000-0000-0000-000000000d01', 'PAYSLIP', 'Payslip',
     'Employee payslip evidencing regular employment income.', TRUE),
    ('00000000-0000-0000-0000-000000000d02', 'ID_DOCUMENT', 'ID Document',
     'Government-issued identity document (national ID, passport, residence permit).', TRUE),
    ('00000000-0000-0000-0000-000000000d03', 'BANK_STATEMENT', 'Bank Statement',
     'Bank account statement supporting income or affordability assessment.', TRUE),
    ('00000000-0000-0000-0000-000000000d04', 'TAX_RETURN', 'Tax Return',
     'Annual personal or corporate tax return.', TRUE),
    ('00000000-0000-0000-0000-000000000d05', 'CONTRACT', 'Contract',
     'Employment, supplier, or other contractual document.', TRUE),
    ('00000000-0000-0000-0000-000000000d99', 'OTHER', 'Other',
     'Default catch-all category used when the document type is unknown or unmapped.', TRUE)
ON CONFLICT (code) DO NOTHING;

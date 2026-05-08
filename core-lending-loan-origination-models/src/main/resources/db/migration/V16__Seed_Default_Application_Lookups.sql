-- Flyway V16: Seed canonical default rows for the three lookup tables
-- referenced as @NotNull FKs by loan_application.
--
-- The IDs below are deterministic so domain-tier sagas can reference them
-- as compile-time constants when an upstream caller (e.g., experience BFF)
-- has not yet supplied a value. Codes mirror the corresponding enums:
--   ApplicationStatusEnum.DRAFT
--   ApplicationSubStatusEnum.PENDING_DOCUMENTS
--   SubmissionChannelEnum.ONLINE
--
-- ON CONFLICT DO NOTHING keeps the migration idempotent: if a row with the
-- same code was inserted by an earlier ad-hoc seed (or a test fixture), the
-- existing row wins. The unique index on `code` guarantees the conflict
-- target is well-defined.
-- Database: PostgreSQL
INSERT INTO application_status (application_status_id, code, name, description, is_active)
VALUES
    ('00000000-0000-0000-0000-000000000a01', 'DRAFT', 'Draft',
     'Initial state assigned when an application is first submitted by the experience tier.', TRUE)
ON CONFLICT (code) DO NOTHING;

INSERT INTO application_sub_status (application_sub_status_id, code, name, description, is_active)
VALUES
    ('00000000-0000-0000-0000-000000000b01', 'PENDING_DOCUMENTS', 'Pending Documents',
     'Default sub-status for newly created applications awaiting supporting documents.', TRUE)
ON CONFLICT (code) DO NOTHING;

INSERT INTO submission_channel (submission_channel_id, code, name, description, is_active)
VALUES
    ('00000000-0000-0000-0000-000000000c01', 'ONLINE', 'Online',
     'Default submission channel used when no explicit channel is supplied (e.g., self-service BFF).', TRUE)
ON CONFLICT (code) DO NOTHING;

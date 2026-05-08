-- Flyway V14: Create simulation table
-- Database: PostgreSQL
-- Purpose: Persist loan simulations for PERSONAL_LOAN and LEASING products.
--          Calculation is performed by domain-core-pricing-engine (calculation only).
--          Persistence happens here in the lending bounded context.
-- Notes:
--   - No FK to product (cross-service ownership; product lives in core-common-product-mgmt).
--   - Future product types create their OWN simulation table (no shared polymorphic table).

CREATE TABLE simulation (
    simulation_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id        UUID,
    product_type      VARCHAR(50)              NOT NULL,
    requested_amount  NUMERIC(19, 2)           NOT NULL,
    term              INTEGER                  NOT NULL,
    purpose           VARCHAR(50),
    sector            VARCHAR(50),
    asset_type        VARCHAR(50),
    monthly_payment   NUMERIC(19, 2),
    tin               NUMERIC(7, 4),
    tae               NUMERIC(7, 4),
    total_amount      NUMERIC(19, 2),
    currency          CHAR(3)                  NOT NULL DEFAULT 'EUR',
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX ix_simulation_product_type ON simulation (product_type);

COMMENT ON TABLE simulation IS 'Lending simulations (PERSONAL_LOAN, LEASING). Calculation is performed by domain-core-pricing-engine; this table only persists the result.';
COMMENT ON COLUMN simulation.product_id IS 'Reference to a product in core-common-product-mgmt. No FK because the product service is in a different bounded context.';
COMMENT ON COLUMN simulation.product_type IS 'Product family discriminator (e.g. PERSONAL_LOAN, LEASING).';
COMMENT ON COLUMN simulation.tin IS 'Nominal interest rate (Tasa de Interes Nominal) as a percentage with 4 decimal precision.';
COMMENT ON COLUMN simulation.tae IS 'Annual equivalent rate (Tasa Anual Equivalente) as a percentage with 4 decimal precision.';

package com.firefly.core.lending.origination.models.repositories.decision.v1;

import com.firefly.core.lending.origination.models.entities.decision.v1.UnderwritingDecision;
import com.firefly.core.lending.origination.models.repositories.BaseRepository;

import java.util.UUID;

public interface UnderwritingDecisionRepository extends BaseRepository<UnderwritingDecision, UUID> {
}

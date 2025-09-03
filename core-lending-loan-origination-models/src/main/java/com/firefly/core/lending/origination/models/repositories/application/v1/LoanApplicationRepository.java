package com.firefly.core.lending.origination.models.repositories.application.v1;

import com.firefly.core.lending.origination.models.entities.application.v1.LoanApplication;
import com.firefly.core.lending.origination.models.repositories.BaseRepository;

import java.util.UUID;

public interface LoanApplicationRepository extends BaseRepository<LoanApplication, UUID> {
}

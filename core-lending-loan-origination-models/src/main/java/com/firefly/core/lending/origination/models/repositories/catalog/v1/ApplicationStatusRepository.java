package com.firefly.core.lending.origination.models.repositories.catalog.v1;

import com.firefly.core.lending.origination.models.entities.catalog.v1.ApplicationStatus;
import com.firefly.core.lending.origination.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ApplicationStatusRepository extends BaseRepository<ApplicationStatus, Long> {
    Mono<ApplicationStatus> findByCode(String code);
}
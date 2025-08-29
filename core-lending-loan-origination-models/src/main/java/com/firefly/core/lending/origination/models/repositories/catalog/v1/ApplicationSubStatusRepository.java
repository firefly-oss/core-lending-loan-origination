package com.firefly.core.lending.origination.models.repositories.catalog.v1;

import com.firefly.core.lending.origination.models.entities.catalog.v1.ApplicationSubStatus;
import com.firefly.core.lending.origination.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ApplicationSubStatusRepository extends BaseRepository<ApplicationSubStatus, Long> {
    Mono<ApplicationSubStatus> findByCode(String code);
}
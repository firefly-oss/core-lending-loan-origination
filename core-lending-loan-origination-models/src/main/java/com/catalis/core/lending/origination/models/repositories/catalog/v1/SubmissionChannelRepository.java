package com.catalis.core.lending.origination.models.repositories.catalog.v1;

import com.catalis.core.lending.origination.models.entities.catalog.v1.SubmissionChannel;
import com.catalis.core.lending.origination.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SubmissionChannelRepository extends BaseRepository<SubmissionChannel, Long> {
    Mono<SubmissionChannel> findByCode(String code);
}
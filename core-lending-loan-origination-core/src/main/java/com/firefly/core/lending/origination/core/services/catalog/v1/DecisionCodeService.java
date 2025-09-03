package com.firefly.core.lending.origination.core.services.catalog.v1;

import com.firefly.core.lending.origination.models.entities.catalog.v1.DecisionCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DecisionCodeService {
    Flux<DecisionCode> findAll();
    Mono<DecisionCode> findById(UUID id);
    Mono<DecisionCode> findByCode(String code);
    Mono<DecisionCode> save(DecisionCode decisionCode);
    Mono<Void> deleteById(UUID id);
}
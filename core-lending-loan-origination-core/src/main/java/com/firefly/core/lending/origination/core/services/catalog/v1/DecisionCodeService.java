package com.firefly.core.lending.origination.core.services.catalog.v1;

import com.firefly.core.lending.origination.models.entities.catalog.v1.DecisionCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DecisionCodeService {
    Flux<DecisionCode> findAll();
    Mono<DecisionCode> findById(Long id);
    Mono<DecisionCode> findByCode(String code);
    Mono<DecisionCode> save(DecisionCode decisionCode);
    Mono<Void> deleteById(Long id);
}
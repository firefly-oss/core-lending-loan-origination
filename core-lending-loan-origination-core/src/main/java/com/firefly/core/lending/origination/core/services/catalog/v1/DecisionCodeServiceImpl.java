package com.firefly.core.lending.origination.core.services.catalog.v1;

import com.firefly.core.lending.origination.models.entities.catalog.v1.DecisionCode;
import com.firefly.core.lending.origination.models.repositories.catalog.v1.DecisionCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DecisionCodeServiceImpl implements DecisionCodeService {

    private final DecisionCodeRepository decisionCodeRepository;

    @Override
    public Flux<DecisionCode> findAll() {
        return decisionCodeRepository.findAll();
    }

    @Override
    public Mono<DecisionCode> findById(UUID id) {
        return decisionCodeRepository.findById(id);
    }

    @Override
    public Mono<DecisionCode> findByCode(String code) {
        return decisionCodeRepository.findByCode(code);
    }

    @Override
    public Mono<DecisionCode> save(DecisionCode decisionCode) {
        LocalDateTime now = LocalDateTime.now();
        
        if (decisionCode.getDecisionCodeId() == null) {
            decisionCode.setCreatedAt(now);
        }
        
        decisionCode.setUpdatedAt(now);
        
        return decisionCodeRepository.save(decisionCode);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return decisionCodeRepository.deleteById(id);
    }
}
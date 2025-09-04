/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
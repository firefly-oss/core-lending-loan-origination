package com.firefly.core.lending.origination.web.controllers.catalog.v1;

import com.firefly.core.lending.origination.core.services.catalog.v1.DecisionCodeService;
import com.firefly.core.lending.origination.models.entities.catalog.v1.DecisionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/decision-codes")
@RequiredArgsConstructor
public class DecisionCodeController {

    private final DecisionCodeService decisionCodeService;

    @GetMapping
    public Flux<DecisionCode> getAllDecisionCodes() {
        return decisionCodeService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<DecisionCode> getDecisionCodeById(@PathVariable UUID id) {
        return decisionCodeService.findById(id);
    }

    @GetMapping("/code/{code}")
    public Mono<DecisionCode> getDecisionCodeByCode(@PathVariable String code) {
        return decisionCodeService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DecisionCode> createDecisionCode(@Valid @RequestBody DecisionCode decisionCode) {
        return decisionCodeService.save(decisionCode);
    }

    @PutMapping("/{id}")
    public Mono<DecisionCode> updateDecisionCode(@PathVariable UUID id, @Valid @RequestBody DecisionCode decisionCode) {
        decisionCode.setDecisionCodeId(id);
        return decisionCodeService.save(decisionCode);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteDecisionCode(@PathVariable UUID id) {
        return decisionCodeService.deleteById(id);
    }
}